<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编号" prop="orderSn">
        <el-input
          v-model="queryParams.orderSn"
          placeholder="请输入订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="订单类型" prop="modelType">
        <el-select v-model="queryParams.modelType" placeholder="请选择订单类型">
          <el-option v-for="(item,index) in orderModelTypeList" :key="index" :label="item.dictLabel" :value="item.dictValue">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:order:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:order:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单编号" align="center" prop="orderSn" />
      <el-table-column label="商品名称" align="center" prop="name" />
      <el-table-column label="手机号" align="center" prop="phone" />
      <el-table-column label="收货人" align="center" prop="consignee" />
      <el-table-column label="图片" align="center" prop="image" width="100">
        <template slot-scope="scope">
          <img :src="getImage(scope.row.image)" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="支付金额" align="center" prop="payAmount" />
      <el-table-column label="支付方式" align="center" prop="payType">
        <template slot-scope="scope">
          <span v-for="data in payTypeList" v-if="data.dictSort==scope.row.payType">
            {{data.dictLabel}}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="支付账号" align="center" prop="payAccount" />
      <el-table-column label="支付时间" align="center" prop="payTime" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.payTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="订单状态" align="center" prop="status">
        <template slot-scope="scope">
          <span v-for="data in orderStatusList" v-if="data.dictSort==scope.row.status">
            {{data.dictLabel}}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="订单类型" align="center" prop="modelType">
        <template slot-scope="scope">
          <span v-for="data in orderModelTypeList" v-if="data.dictSort==scope.row.modelType">
            {{data.dictLabel}}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleOrderDesc(scope.row)"
            v-hasPermi="['system:order:edit']"
          >订单详情</el-button>
          <el-button v-if="scope.row.status==11"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleDetermineRefund(scope.row)"
            v-hasPermi="['system:order:edit']"
          >确定退款</el-button>
          <el-button v-if="scope.row.status==6"
                     size="mini"
                     type="text"
                     icon="el-icon-edit"
                     @click="handleDetermineRefund(scope.row)"
                     v-hasPermi="['system:order:edit']"
          >确定退货</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:order:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改订单对话框 -->

    <div v-if="form!=null">
      <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body >
        <el-descriptions title="订单信息" direction="vertical" :column="4" border>
          <el-descriptions-item label="订单号">{{form.orderSn}}</el-descriptions-item>
          <el-descriptions-item label="订单名称">{{form.createTime}}</el-descriptions-item>
          <el-descriptions-item label="下单时间">{{form.name}}</el-descriptions-item>
          <el-descriptions-item label="订单类型">
            <span v-for="data in orderModelTypeList" v-if="data.dictSort==form.modelType">
              <el-tag size="small">{{data.dictLabel}}</el-tag>
            </span>
          </el-descriptions-item>

          <el-descriptions-item label="订单状态">
              <span v-for="data in orderStatusList" v-if="data.dictSort==form.status">
              <el-tag size="small">{{data.dictLabel}}</el-tag>
            </span>
          </el-descriptions-item>
        </el-descriptions>

        <el-descriptions title="商品详情" direction="vertical" :column="4" border v-if="form.modelType==1">
          <el-descriptions-item label="商品图">
            <img :src="getImage(form.image)" :width="50" :height="50"/>
          </el-descriptions-item>
          <el-descriptions-item label="商品详情图" :span="2">
            <img v-for="img in form.imgsList" :src="getImage(img.img)" :width="50" :height="50" style="margin-left: 20px"/>
          </el-descriptions-item>
        </el-descriptions>

        <el-descriptions title="订单详情" direction="vertical" :column="4" border>
          <el-descriptions-item label="颜色" v-if="form.modelType==1">{{form.tbOrderDerails.colour}}色</el-descriptions-item>
          <el-descriptions-item label="尺码">{{form.tbOrderDerails.size}}</el-descriptions-item>
          <el-descriptions-item label="数量">{{form.tbOrderDerails.quantity}}</el-descriptions-item>
          <el-descriptions-item label="单价">￥{{form.tbOrderDerails.amount}}</el-descriptions-item>
          <el-descriptions-item label="总金额">￥{{form.tbOrderDerails.totalAmount}}</el-descriptions-item>
          <el-descriptions-item label="付款金额">￥{{form.payAmount}}</el-descriptions-item>
        </el-descriptions>

        <el-descriptions title="优惠信息" direction="vertical" :column="4" border v-if="form.tbCoupon!=null">
          <el-descriptions-item label="优惠劵标题">{{form.tbCoupon.couponTitle}}</el-descriptions-item>
          <el-descriptions-item label="优惠价格">￥{{form.tbCoupon.couponNumber}}</el-descriptions-item>
          <el-descriptions-item label="优惠图">
            <img :src="getImage(form.tbCoupon.couponUrl)" :width="100" :height="50"/>
          </el-descriptions-item>
          <el-descriptions-item label="劵类型">
            <span v-for="data in couponTypeList" v-if="form.tbCoupon.couponAllavailable==data.dictSort">
              <el-tag size="small">{{data.dictLabel}}</el-tag>
            </span>
          </el-descriptions-item>
        </el-descriptions>

        <el-descriptions title="支付信息" direction="vertical" :column="4" border>
          <el-descriptions-item label="支付方式">
              <span v-for="data in payTypeList" v-if="data.dictSort==form.payType">
                {{data.dictLabel}}
              </span>
          </el-descriptions-item>
          <el-descriptions-item label="支付金额">{{form.payAmount}}</el-descriptions-item>
          <el-descriptions-item label="支付账号">{{form.payAccount}}</el-descriptions-item>
          <el-descriptions-item label="支付时间">{{form.payTime}}</el-descriptions-item>
        </el-descriptions>

<!--        <el-descriptions :title="form.status==6?'退货信息':'退款信息'" direction="vertical" :column="4" border>-->
<!--          <el-descriptions-item label="支付方式">-->
<!--              <span v-for="data in payTypeList" v-if="data.dictSort==form.payType">-->
<!--                {{data.dictLabel}}-->
<!--              </span>-->
<!--          </el-descriptions-item>-->
<!--          <el-descriptions-item label="退货原因">{{form.tbOrderLog.oeason}}</el-descriptions-item>-->
<!--          <el-descriptions-item label="退货详情">{{form.tbOrderLog.details}}</el-descriptions-item>-->
<!--          <el-descriptions-item label="补充说明">{{form.tbOrderLog.oeason}}</el-descriptions-item>-->
<!--          <el-descriptions-item label="反馈图">-->
<!--            {{form.tbOrderLog.pathImgJson}}-->
<!--          </el-descriptions-item>-->
<!--          <el-descriptions-item label="退款金额">￥{{form.payAmount}}</el-descriptions-item>-->
<!--          <el-descriptions-item label="退货时间">{{form.payTime}}</el-descriptions-item>-->
<!--        </el-descriptions>-->



        <el-descriptions title="收货信息" direction="vertical" :column="4" border>
          <el-descriptions-item label="收货人">{{form.consignee}}</el-descriptions-item>
          <el-descriptions-item label="联系方式">{{form.phone}}</el-descriptions-item>
          <el-descriptions-item label="性别">{{form.tbAddressBook.sex}}</el-descriptions-item>
          <el-descriptions-item label="收货地址">{{form.tbAddressBook.provinceName}}--{{form.tbAddressBook.cityName}}--{{form.tbAddressBook.districtName}}</el-descriptions-item>
          <el-descriptions-item label="详细地址">{{form.tbAddressBook.detail}}</el-descriptions-item>
        </el-descriptions>



        <el-descriptions title="模型详情" direction="vertical" :column="4" border v-if="form.modelType!=1">
          <el-descriptions-item label="模型图">
            <img :src="getImage(form.image)" :width="100" :height="100"/>
          </el-descriptions-item>
          <el-descriptions-item label="模型详情" :span="2">
            <ul id="nav">
              <li v-for="tbplclist in form.tbModelStyle.tbPlateClassList">
                {{tbplclist.name}}
                <img :src="getImage(tbplclist.img)" :width="50" :height="50" style="margin-left: 20px"/>
                <ul>
                  <li v-for="tbplctlist in tbplclist.tbPlateCutWayList">
                    {{tbplctlist.name}}
                    <img :src="getImage(tbplctlist.img)" :width="50" :height="50" style="margin-left: 20px"/>
                    <ul>
                      <li v-for="tbcmlist in tbplctlist.tbComponentList">
                        {{tbcmlist.name}}
                        <img :src="getImage(tbcmlist.picture)" :width="50" :height="50" style="margin-left: 20px"/>
                        <ul v-if="tbcmlist.tbClassifyList!=null">
                          <li v-for="tbclssflist in tbcmlist.tbClassifyList" style="width:100%;margin-left: -20px;">
                            {{tbclssflist.name}}
                            <ul>
                              <li v-for="tbptlist in tbclssflist.tbPatternList" style="width:100%;margin-left: -20px;">
                                {{tbptlist.name}}
                                <p>
                                  <img width="50px" slot="reference" :id="tbptlist.id" height="50px" style="margin-left: 80px" v-if="tbptlist.picture"  :src="getImage(tbptlist.picture)" class="avatar" title="点击修改面料" @click="getPatterByClassId(tbclssflist,tbptlist.id)">
                                </p>
                              </li>
                            </ul>
                          </li>
                        </ul>
                      </li>
                    </ul>
                  </li>
                </ul>
              </li>
            </ul>

            <!--                <ul id="nav">-->
            <!--                  <li style="width:500px;text-align:center;border: 1px solid red" v-for="tbplclist in .tbPlateClassList">-->
            <!--                    <i>-->
            <!--                      {{tbplclist.name}}-->
            <!--                    </i>-->
            <!--                    <el-upload-->
            <!--                      class="avatar-uploader"-->
            <!--                      action="/dev-api/common/upload"-->
            <!--                      :headers="headers"-->
            <!--                      :show-file-list="false"-->
            <!--                      :on-success="(response,file,fileList)=>handleAvatarSuccess(response, file,fileList,tbplclist)"-->
            <!--                      :disabled="true">-->
            <!--                      <img width="50px" height="50px" v-if="tbplclist.img" :src="getImage(tbplclist.img)" class="avatar">-->
            <!--                      <i v-else class="el-icon-plus avatar-uploader-icon"></i>-->
            <!--                    </el-upload>-->
            <!--                  </li>-->
            <!--                </ul>-->
          </el-descriptions-item>
        </el-descriptions>


        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>

  </div>
</template>

<script>
  import { refundAudit,listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/system/order";
  import {getDicts} from "@/api/system/dict/data"

  export default {
    name: "Order",
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 订单表格数据
        orderList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        //订单状态字典
        orderStatusList:[],
        //订单类型字典
        orderModelTypeList:[],
        //支付类型
        payTypeList:[],
        //劵类型
        couponTypeList:[],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          orderSn: null,
          name: null,
          phone: null,
          consignee: null,
          image: null,
          payAmount: null,
          payType: null,
          payAccount: null,
          payTime: null,
          refundTime: null,
          receivedTime: null,
          status: 11,
          modelType: null,
          delTime: null,
          delBy: null,
          timeOutFlag: null,
          clothesJson: null
        },
        // 表单参数
        form: {
          tbAddressBook:{},
          tbModelStyle:{},
          tbOrderDerails:{},
        },
        // 表单校验
        rules: {
        }
      };
    },
    created() {
      this.getList();
      this.getOrderStatusData();
      this.getOrderModelTypeData();
      this.getPayTypeData();
    },
    methods: {
      /** 申请退款*/
      handleDetermineRefund(row){
        refundAudit(row.orderSn).then(response=>{

        }).catch(error=>{

        })
      },
      /** 订单详情*/
      handleOrderDesc(row){
        this.reset();
        getOrder(row.orderSn).then(response=>{
          this.form = response.data;
          console.log(this.form)
          this.open = true;
          this.title = "订单详情";
        })
      },
      //获取订单状态
      getOrderStatusData(){
        getDicts("order_status").then(response=>{
          this.orderStatusList = response.data;
        })
      },
      //优惠劵类型
      getCouponTypeData(){
        getDicts("coupon_type").then(response=>{
          this.couponTypeList = response.data;
        })
      },
      //获取订单类型
      getOrderModelTypeData(){
        getDicts("order_model_type").then(response=>{
          this.orderModelTypeList = response.data;
        })
      },
      getPayTypeData(){
        getDicts("pay_type").then(response=>{
          this.payTypeList = response.data;
        })
      },
      getImage(img){
        return "https://app-1255978856.cos.ap-shanghai.myqcloud.com/"+img;
      },
      /** 查询订单列表 */
      getList() {
        this.loading = true;
        listOrder(this.queryParams).then(response => {
          this.orderList = response.rows;
          this.total = response.total;
          this.loading = false;
          console.log(this.orderList)
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          orderSn: null,
          name: null,
          phone: null,
          consignee: null,
          image: null,
          payAmount: null,
          createTime: null,
          payType: null,
          payAccount: null,
          payTime: null,
          refundTime: null,
          receivedTime: null,
          remark: null,
          status: 11,
          modelType: null,
          delTime: null,
          delFlag: null,
          delBy: null,
          timeOutFlag: null,
          clothesJson: null
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length!==1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加订单";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getOrder(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改订单";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateOrder(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addOrder(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$modal.confirm('是否确认删除订单编号为"' + ids + '"的数据项？').then(function() {
          return delOrder(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('system/order/export', {
          ...this.queryParams
        }, `order_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>

<style>
  #nav li {
    display: inline;
    float:left;
    margin-left: 150px;
  }
</style>
