<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单号" prop="orderSn">
        <el-input
          v-model="queryParams.orderSn"
          placeholder="请输入订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="订单类型" prop="modelType">
        <el-select v-model="queryParams.modelType" placeholder="请选择订单类型" clearable size="small">
          <el-option
            v-for="dict in orderModelType"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="支付方式" prop="payType">
        <el-select v-model="queryParams.payType" placeholder="请选择支付类型" clearable size="small">
          <el-option
            v-for="dict in payType"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!--    <el-row :gutter="10" class="mb8">-->
    <!--      <el-col :span="1.5">-->
    <!--        <el-button-->
    <!--          type="primary"-->
    <!--          plain-->
    <!--          icon="el-icon-plus"-->
    <!--          size="mini"-->
    <!--          @click="handleAdd"-->
    <!--          v-hasPermi="['system:info:add']"-->
    <!--        >新增</el-button>-->
    <!--      </el-col>-->
    <!--      <el-col :span="1.5">-->
    <!--        <el-button-->
    <!--          type="success"-->
    <!--          plain-->
    <!--          icon="el-icon-edit"-->
    <!--          size="mini"-->
    <!--          :disabled="single"-->
    <!--          @click="handleUpdate"-->
    <!--          v-hasPermi="['system:info:edit']"-->
    <!--        >修改</el-button>-->
    <!--      </el-col>-->
    <!--      <el-col :span="1.5">-->
    <!--        <el-button-->
    <!--          type="danger"-->
    <!--          plain-->
    <!--          icon="el-icon-delete"-->
    <!--          size="mini"-->
    <!--          :disabled="multiple"-->
    <!--          @click="handleDelete"-->
    <!--          v-hasPermi="['system:info:remove']"-->
    <!--        >删除</el-button>-->
    <!--      </el-col>-->
    <!--      <el-col :span="1.5">-->
    <!--        <el-button-->
    <!--          type="warning"-->
    <!--          plain-->
    <!--          icon="el-icon-download"-->
    <!--          size="mini"-->
    <!--          @click="handleExport"-->
    <!--          v-hasPermi="['system:info:export']"-->
    <!--        >导出</el-button>-->
    <!--      </el-col>-->
    <!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
    <!--    </el-row>-->

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!--      <el-table-column label="订单表主键" align="center" prop="id" />-->

      <el-table-column label="订单号" align="center" prop="orderSn" />
      <el-table-column label="用户名称" align="center" prop="userName" />

      <!--      <el-table-column label="地址id" align="center" prop="address" />-->
      <el-table-column label="手机号" align="center" prop="phone" />
      <el-table-column label="收货人" align="center" prop="consignee" />


      <!--      <el-table-column label="商品金额" align="center" prop="amount" />-->
      <!--      <el-table-column label="订单类型" align="center" prop="modelType" />-->
      <!--      <el-table-column label="支付账号" align="center" prop="payAccount" />-->

      <el-table-column label="商户名称" align="center" prop="storeName" />
      <el-table-column label="订单状态" align="center" prop="status" :formatter="orderTypeFormat" />
      <el-table-column label="支付方式" align="center" prop="payType" :formatter="payTypeFormat"/>
      <el-table-column label="支付金额" align="center" prop="payAmount" />


      <!--      <el-table-column label="备注" align="center" prop="remark" />-->
      <el-table-column label="支付时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime}}</span>
        </template>
      </el-table-column>
      <!--      <el-table-column label="删除时间" align="center" prop="delTime" width="180">-->
      <!--        <template slot-scope="scope">-->
      <!--          <span>{{ parseTime(scope.row.delTime, '{y}-{m}-{d}') }}</span>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <!--      <el-table-column label="删除人" align="center" prop="delBy" />-->
      <!--      <el-table-column label="是否超时 0：否，1：是，已取消订单" align="center" prop="timeOutFlag" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini"
                     type="text"
                     icon="el-icon-edit" @click="handleDetails(scope.row,2)" v-loading.fullscreen.lock="loading" element-loading-text="拼命加载中">订单详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:info:remove']"
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
    <!-- 订单详情 -->
    <el-dialog title="订单详情"   :visible.sync="dialogFormVisible">
      <el-form :model="form" :inline="true" >
        <el-row>
          <el-col :span="20">
            <el-form-item label="订单号"   :label-width="formLabelWidth">
              <el-input v-model="singleOrder.tbOrderInfo.orderSn"  style="width: 200px;" v-bind:disabled="singleOrderVisible" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="用户信息"  :label-width="formLabelWidth">
              <i>头像：</i><img  width="50px" height="50px" :src="getImage(singleOrder.tbUserInfo.headImg)"/>
              <i>昵称：</i><el-input v-model="singleOrder.tbUserInfo.userNickname"  style="width: 150px;" v-bind:disabled="singleOrderVisible" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="20">
            <el-form-item label="商户号"  :label-width="formLabelWidth">
              <el-input v-model="singleOrder.tbStore.name"  style="width: 200px;" v-bind:disabled="singleOrderVisible" autocomplete="off"></el-input>
            </el-form-item>

            <!--            <el-form-item label="商家详情"  :label-width="formLabelWidth">-->
            <!--              <el-input type="textarea"  v-model="singleOrder.tbStore.description" style="width: 200px;" v-bind:disabled="singleOrderVisible" autocomplete="off"></el-input>-->
            <!--            </el-form-item>-->
          </el-col>
        </el-row>

        <!--        <el-upload-->
        <!--          class="upload-demo"-->
        <!--          action="https://jsonplaceholder.typicode.com/posts/"-->
        <!--          :on-preview="handlePreview"-->
        <!--          :on-remove="handleRemove"-->
        <!--          :before-remove="beforeRemove"-->
        <!--          multiple-->
        <!--          :limit="3"-->
        <!--          :on-exceed="handleExceed"-->
        <!--          :file-list="fileList" style="margin-top: 40px;position:absolute;z-index: 20" v-show="imgShow">-->
        <!--          <el-button size="small" type="primary">商品图上传</el-button>-->
        <!--        </el-upload>-->

        <!-- 普通订单商品详情 -->
        <h2 v-if="singleOrder.tbOrderInfo.modelType==1">商品信息</h2>
        <el-row v-if="singleOrder.tbOrderInfo.modelType==1">
          <el-col :span="20">
            <el-form-item label="商品"  :label-width="formLabelWidth">
              <el-upload
                class="avatar-uploader"
                action="/dev-api/common/upload"
                :headers="headers"
                :show-file-list="false"
                :on-success="(response,file,fileList)=>handleAvatarSuccess(response, file,fileList,singleOrder.tbMall)"
                :disabled="true">
                <img width="100px" height="100px" v-if="singleOrder.tbMall.img" :src="getImage(singleOrder.tbMall.img)" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
              {{singleOrder.tbMall.name}}
            </el-form-item>

            <el-form-item label="商家详情"  :label-width="formLabelWidth">
              <el-input v-model="singleOrder.tbMall.description" type="textarea" style="width: 230px;" v-bind:disabled="singleOrderVisible" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="20">
            <el-form-item label="详情图" disabled :label-width="formLabelWidth">
              <el-upload
                class="upload-demo"
                :action="uploadImgUrl"
                :file-list="tbMallImag"
                :headers="headers"
                :disabled="true"
                :on-success="(response,file,fileList)=>handleAvatarSuccess(response, file,fileList,singleOrder.tbOrderDerails)"
                list-type="picture">
              </el-upload>
            </el-form-item>

            <el-form-item label="价格"  :label-width="formLabelWidth">
              {{singleOrder.tbOrderDerails.amount}}
            </el-form-item>

            <el-form-item label="数量"  :label-width="formLabelWidth">
              {{singleOrder.tbOrderDerails.quantity}}
            </el-form-item>

            <el-form-item label="颜色"  :label-width="formLabelWidth">
              {{singleOrder.tbOrderDerails.colour}}
            </el-form-item>

            <el-form-item label="尺寸"  :label-width="formLabelWidth">
              {{singleOrder.tbOrderDerails.size}}
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 模型订单属性 -->
        <h2 v-else-if="singleOrder.tbOrderInfo.modelType==2">模型信息</h2>
        <el-row v-if="singleOrder.tbOrderInfo.modelType==2">
          <el-col :span="8">
            <el-form-item label="商品名称"   :label-width="formLabelWidth">
              <img width="100px" height="100px" :src="getImage(singleOrder.tbModelStyle.img)"/>              {{singleOrder.tbModelStyle.name}}

            </el-form-item>
          </el-col>

          <el-col :span="24" style="text-align: center">
            <el-form-item label="板块大类部件" :label-width="formLabelWidth">
              <ul id="nav">
                <li style="width:500px;text-align:center;border: 1px solid red" v-for="tbplclist in singleOrder.tbModelStyle.tbPlateClassList">
                  <i>
                    {{tbplclist.name}}
                  </i>
                  <el-upload
                    class="avatar-uploader"
                    action="/dev-api/common/upload"
                    :headers="headers"
                    :show-file-list="false"
                    :on-success="(response,file,fileList)=>handleAvatarSuccess(response, file,fileList,tbplclist)"
                    :disabled="true">
                    <img width="50px" height="50px" v-if="tbplclist.img" :src="getImage(tbplclist.img)" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                  </el-upload>
                  <ul>
                    <li v-for="tbplctlist in tbplclist.tbPlateCutWayList" style="width:100%;margin-left: -18px">
                      {{tbplctlist.name}}
                      <el-upload
                        class="avatar-uploader"
                        action="/dev-api/common/upload"
                        :headers="headers"
                        :show-file-list="false"
                        :on-success="(response,file,fileList)=>handleAvatarSuccess(response, file,fileList,tbplctlist)"
                        :disabled="true">
                        <img width="50px" height="50px" v-if="tbplctlist.img" :src="getImage(tbplctlist.img)" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                      </el-upload>

                      <ul>
                        <li v-for="tbcmlist in tbplctlist.tbComponentList"  style="width:100%;margin-left: -20px">
                          {{tbcmlist.name}}
                          <el-upload
                            class="avatar-uploader"
                            action="/dev-api/common/upload"
                            :headers="headers"
                            :show-file-list="false"
                            :on-success="(response,file,fileList)=>handleAvatarSuccess(response, file,fileList,tbcmlist)"
                            :disabled="true">
                            <img width="50px" height="50px" v-if="tbcmlist.picture" :src="getImage(tbcmlist.picture)" class="avatar">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                          </el-upload>

                          <ul v-if="tbcmlist.tbClassifyList!=null">
                            <li v-for="tbclssflist in tbcmlist.tbClassifyList" style="width:100%;margin-left: -20px">
                              {{tbclssflist.name}}
                              <ul>
                                <li v-for="tbptlist in tbclssflist.tbPatternList" style="width:100%;margin-left: -20px">
                                  {{tbptlist.name}}
                                  <p>
                                    <img width="50px" slot="reference" :id="tbptlist.id" height="50px" v-if="tbptlist.picture"  :src="getImage(tbptlist.picture)" class="avatar" title="点击修改面料" @click="getPatterByClassId(tbclssflist,tbptlist.id)">
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
            </el-form-item>
          </el-col>
        </el-row>
        <h2>收货信息</h2>
        <el-row>
          <el-col :span="30">
            <el-form-item label="收货人" v-if="singleOrder.tbOrderInfo.tbAddressBook!=null"  :label-width="formLabelWidth">
              {{singleOrder.tbOrderInfo.tbAddressBook.consignee}}
            </el-form-item>

            <el-form-item label="联系电话" v-if="singleOrder.tbOrderInfo.tbAddressBook!=null"  :label-width="formLabelWidth">
              {{singleOrder.tbOrderInfo.tbAddressBook.phone}}
            </el-form-item>

            <el-form-item label="收货地址" v-if="singleOrder.tbOrderInfo.tbAddressBook!=null"  :label-width="formLabelWidth">
              {{singleOrder.tbOrderInfo.tbAddressBook.provinceName}}省{{singleOrder.tbOrderInfo.tbAddressBook.cityName}}市{{singleOrder.tbOrderInfo.tbAddressBook.districtName}}
            </el-form-item>

            <el-form-item label="详细地址" v-if="singleOrder.tbOrderInfo.tbAddressBook!=null"  :label-width="formLabelWidth">
              {{singleOrder.tbOrderInfo.tbAddressBook.label}}
            </el-form-item>

            <el-form-item label="手机号"  :label-width="formLabelWidth">
              {{singleOrder.tbOrderInfo.phone}}
            </el-form-item>

            <el-form-item label="支付账户"  :label-width="formLabelWidth">
              {{singleOrder.tbOrderInfo.payAccount}}
            </el-form-item>

          </el-col>
        </el-row>
        <h2>订单状态</h2>
        <el-row>
          <el-col :span="30">
            <el-form-item label="支付方式"  :label-width="formLabelWidth">
              {{payTypeFormat(singleOrder.tbOrderInfo)}}
            </el-form-item>
            <el-form-item label="订单状态"  :label-width="formLabelWidth">
              {{orderTypeFormat(singleOrder.tbOrderInfo)}}
            </el-form-item>
          </el-col>

          <el-col :span="30">
            <el-form-item label="创建时间" v-if="singleOrder.tbOrderInfo.createTime!=null"  :label-width="formLabelWidth">
              {{singleOrder.tbOrderInfo.createTime}}
            </el-form-item>
            <el-form-item label="删除时间" v-if="singleOrder.tbOrderInfo.delTime!=null"  :label-width="formLabelWidth">
              {{singleOrder.tbOrderInfo.delTime}}
              <el-form-item label="删除人" v-if="singleOrder.tbOrderInfo.delTime!=null"  :label-width="formLabelWidth">
                {{singleOrder.tbOrderInfo.delBy}}
              </el-form-item>
            </el-form-item>


            <el-form-item label="结账时间" v-if="singleOrder.tbOrderInfo.updateTime!=null"  :label-width="formLabelWidth">
              {{singleOrder.tbOrderInfo.updateTime}}
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="订单备注"  :label-width="formLabelWidth">
          <el-input type="textarea"  v-model="singleOrder.tbOrderInfo.remark" style="width: 700px;" v-bind:disabled="singleOrderVisible" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateOrderInfo(singleOrder.tbOrderInfo)">确 定</el-button>
      </div>
    </el-dialog>

    <el-radio-group v-show="false" v-model="direction">
      <el-radio label="rtl">从右往左开</el-radio>
    </el-radio-group>

    <!-- 部件面料 -->
    <el-drawer
      title="部件面料"
      :visible.sync="visiblePattern"
      :direction="direction">
      <el-table
        :data="tbPatternComponents"
        height="100%"
        @row-dblclick="handleCurrentChange"
        highlight-current-row
        border
        style="width: 100%">
        <el-table-column
          prop="name"
          label="面料名称"
          width="180">
        </el-table-column>
        <el-table-column
          prop="picture"
          label="面料图片">
          <template slot-scope="scope">
            <img width="50px" slot="reference"  height="50px" v-if="scope.row.picture"  :src="getImage(scope.row.picture)">
          </template>
        </el-table-column>
      </el-table>
    </el-drawer>

    <!-- 添加或修改订单信息
对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="支付金额" prop="payAmount">
          <el-input v-model="form.payAmount" placeholder="请输入支付金额" />
        </el-form-item>
        <el-form-item label="商品金额" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入商品金额" />
        </el-form-item>
        <el-form-item label="订单号" prop="orderSn">
          <el-input v-model="form.orderSn" placeholder="请输入订单号" />
        </el-form-item>
        <el-form-item label="商户id" prop="storeId">
          <el-input v-model="form.storeId" placeholder="请输入商户id" />
        </el-form-item>
        <el-form-item label="衣服id" prop="mallId">
          <el-input v-model="form.mallId" placeholder="请输入衣服id" />
        </el-form-item>
        <el-form-item label="用户id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户id" />
        </el-form-item>
        <el-form-item label="支付账号" prop="payAccount">
          <el-input v-model="form.payAccount" placeholder="请输入支付账号" />
        </el-form-item>
        <el-form-item label="地址id" prop="address">
          <el-input v-model="form.address" placeholder="请输入地址id" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="收货人" prop="consignee">
          <el-input v-model="form.consignee" placeholder="请输入收货人" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="支付时间" prop="payTime">
          <el-date-picker clearable
                          v-model="form.payTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择支付时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="删除时间" prop="delTime">
          <el-date-picker clearable
                          v-model="form.delTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择删除时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否删除 0：否，1：是" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入是否删除 0：否，1：是" />
        </el-form-item>
        <el-form-item label="删除人" prop="delBy">
          <el-input v-model="form.delBy" placeholder="请输入删除人" />
        </el-form-item>
        <el-form-item label="是否超时 0：否，1：是，已取消订单" prop="timeOutFlag">
          <el-input v-model="form.timeOutFlag" placeholder="请输入是否超时 0：否，1：是，已取消订单" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listInfo, getInfo, delInfo, addInfo, updateInfo } from "@/api/system/info";
  import { getClassifyPictureById } from "@/api/system/componentPattern";
  import { formatDate } from "@/api/system/date";
  import fa from '../../../element-ui/src/locale/lang/fa'
  import { getToken } from "@/utils/auth";
  import Vue from "vue"
  export default {
    name: "orderInfo",
    data() {
      return {
        orgPatternId:0,
        direction: 'rtl',
        //面料图层是否可见
        visiblePattern:false,
        //面料修改entity集合
        tbPatterns:[],
        //商品详情图
        tbMallImag:[],
        //面料
        tbPatternComponents:[],
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
        // 详情上传图片按钮
        imgShow: false,
        // 非单个禁用
        uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
        // 总条数
        total: 0,
        // 订单信息表格数据
        infoList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 订单状态
        orderStatus:[],


        //商品图
        mallImagesList:[],
        //详情图
        mallDescImageList:[],
        //模型图
        modelTypeImageList:[],

        // 订单类型
        orderModelType:[],
        //支付方式
        payType:[],
        dialogFormVisible: false,
        formLabelWidth: '120px',
        //单个订单
        singleOrder:{
          tbOrderInfo:{
            list:null
          },//订单详情
          tbUserInfo:{},//用户
          tbStore:{},//商户
          tbMall:{},//商品
          TbMallImgs:[],
          tbOrderDerails:{},//详情
          tbModelStyle:{},//模型
          tbPatternMap:{},//布料Map
        },
        singleOrderParams:{
          tbOrderInfo:{},//订单详情
          tbPatternMap:{},//布料Map
        },
        singleOrderVisible:true,
        headers: {
          Authorization: 'Bearer ' + getToken()
        },
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          payAmount: null,
          amount: null,
          orderSn: null,
          modelType: null,
          storeId: null,
          mallId: null,
          userId: null,
          status: 4,
          payType: null,
          payAccount: null,
          address: null,
          phone: null,
          consignee: null,
          payTime: null,
          delTime: null,
          delBy: null,
          timeOutFlag: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          payAmount: [
            { required: true, message: "支付金额不能为空", trigger: "blur" }
          ],
          amount: [
            { required: true, message: "商品金额不能为空", trigger: "blur" }
          ],
          orderSn: [
            { required: true, message: "订单号不能为空", trigger: "blur" }
          ],
          storeId: [
            { required: true, message: "商户id不能为空", trigger: "blur" }
          ],
          mallId: [
            { required: true, message: "衣服id不能为空", trigger: "blur" }
          ],
          userId: [
            { required: true, message: "用户id不能为空", trigger: "blur" }
          ],
          status: [
            { required: true, message: "状态  0：未支付，1：待发货，2：支付失败，3：退款中，4：已退款，5：退款失败，6：待收货，7：已收货，8：待评价 9：已支付不能为空", trigger: "blur" }
          ],
          address: [
            { required: true, message: "地址id不能为空", trigger: "blur" }
          ],
          phone: [
            { required: true, message: "手机号不能为空", trigger: "blur" }
          ],
          consignee: [
            { required: true, message: "收货人不能为空", trigger: "blur" }
          ],
        }
      };
    },
    created() {
      this.getList();
      /** 订单类型*/
      this.getDicts("order_model_type").then(response=>{
        this.orderModelType = response.data;
      })
      /** 订单状态*/
      this.getDicts("order_status").then(response=>{
        this.orderStatus = response.data;
        console.log(this.orderStatus)
      });
      /** 支付方式*/
      this.getDicts("pay_type").then(response=>{
        this.payType = response.data;
      })
    },
    methods: {
      //面料选择行
      handleCurrentChange(val) {
        this.$confirm('是否修改当前部件面料颜色, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          },
        ).then(() => {
          console.log(val)
          //修改部件颜色
          let ele = document.getElementById(this.orgPatternId);
          ele.src = this.getImage(val.picture)
          const beanImg = {
            'oldId':this.orgPatternId,
            'typeId':val.typeId,
            'newId':val.patternId,
          }
          console.log(beanImg);
          if(this.modelTypeImageList.length>0){
            for(let i=0;i<this.modelTypeImageList.length;i++){
              if(this.modelTypeImageList[i].typeId==val.typeId){
                return true;
              }
            }
          }
          this.modelTypeImageList.push(beanImg)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消修改面料颜色'
          });
        });
      },
      /** 根据模型分类获取所有面料*/
      getPatterByClassId(row,id){
        this.orgPatternId = id;

        console.log(row);
        console.log(id)
        getClassifyPictureById(row.id).then(response=>{
          this.tbPatternComponents = response.data;
          this.visiblePattern = true;
        })
      },
      /** 查询订单信息列表 */
      getList() {
        this.loading = true;
        this.queryParams.del_flag = 0;
        if(this.queryParams.status==""){
          this.queryParams.status = null;
        }
        if(this.queryParams.payType==""){
          this.queryParams.payType = null;
        }
        if(this.queryParams.modelType==""){
          this.queryParams.modelType = null;
        }
        listInfo(this.queryParams).then(response => {
          this.infoList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      getUserName(row){
        return "张三"
      },
      //订单类型
      orderTypeFormat(row, column) {
        return this.selectDictLabel(this.orderStatus, row.status);
      },
      //支付方式
      payTypeFormat(row,column){
        return this.selectDictLabel(this.payType,row.payType)
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      //TODO 上传需要修改
      handleAvatarSuccess(res,file,fileList, pathimg){
        if(this.singleOrder.tbPatternMap==null){
          this.singleOrder.tbPatternMap=new Map;
        }
        let beanImg = {
          id:pathimg.id,
          name:file.name,
          fileUrl:res.fileName
        }

        if(pathimg.image){
          //商品详情
          pathimg.image = res.fileName;
          this.mallDescImageList.push(beanImg)
        }else if (pathimg.picture){
          //面板
          pathimg.picture = res.fileName;
          pathimg.name = file.name;
          this.modelTypeImageList.push(beanImg)
        }else if(pathimg.img){
          //商品图
          pathimg.img = res.fileName;
          this.mallImagesList.push(beanImg)
        }


        console.log(this.mallImagesList)
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          payAmount: null,
          amount: null,
          orderSn: null,
          modelType: null,
          storeId: null,
          mallId: null,
          userId: null,
          status: 4,
          createTime: null,
          payType: null,
          payAccount: null,
          address: null,
          phone: null,
          consignee: null,
          remark: null,
          payTime: null,
          delTime: null,
          delFlag: null,
          delBy: null,
          timeOutFlag: null,
          updateTime: null
        };
        this.resetForm("form");
      },
      getImage(img){
        return "https://app-1255978856.cos.ap-shanghai.myqcloud.com/"+img;
      },
      /** 订单详情 and 修改*/
      handleDetails(row,type){
        let _this = this;
        _this.loading = true;
        getInfo(row.id).then(response=>{
          _this.singleOrder = response.data;
          _this.loading = false;
          this.dialogFormVisible = true;
          response.data.tbMall.imgsList.forEach(item=>{
            item.url = this.getImage(item.img);
            item.name = item.img;
          })
          this.tbMallImag = response.data.tbMall.imgsList;

          console.log(this.singleOrder)

        })
        //1详情 ：2修改
        if(type==1){
          _this.singleOrderVisible = true;
        }else {
          _this.singleOrderVisible = true;
        }
      },
      /** 确定修改*/
      updateOrderInfo(orderInfo){
        console.log("进")
        let _this = this;
        // if(!_this.singleOrderVisible){

        //this.singleOrder.tbOrderInfo.list =this.mallImagesList;

        const images = {
          "mallImagesList":_this.mallImagesList,
          "modelTypeImageList":_this.modelTypeImageList,
          "mallDescImageList":_this.mallDescImageList,
        }
        updateInfo(JSON.stringify(orderInfo),JSON.stringify(images)).then(response=>{
          _this.dialogFormVisible = false;
          this.$message({
            type: 'success',
            message: '修改成功!',
          });

        }).catch(error=>{
          _this.dialogFormVisible = false;
          this.$message({
            type: 'info',
            message: '修改失败！'
          });
        })
        // }else {
        //   _this.dialogFormVisible = false;
        // }
        _this.mallImagesList = [];
        _this.mallDescImageList = [];
        _this.modelTypeImageList = [];
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
        this.title = "添加订单信息";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        // let _this = this;
        // _this.loading = true;
        // _this.singleOrderVisible = false;
        // getInfo(row.id).then(response=>{
        //   _this.singleOrder = response.data;
        //   _this.loading = false;
        //   this.dialogFormVisible = true;
        // })
        // // updateInfo(row).then(response=>{
        // //
        // // })
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateInfo(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addInfo(this.form).then(response => {
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
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          },
        ).then(() => {
          //删除
          delInfo(row.id,"张三").then(response=>{
            this.$message({
              type: 'success',
              message: '删除成功!',
            });
            this.getList();
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('system/info/export', {
          ...this.queryParams
        }, `info_${new Date().getTime()}.xlsx`)
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
  .menuModelUl{
    border: 1px solid forestgreen;
  }
  .menuModelUl, .menuModelUl li {
    margin-left: 10px;
    list-style: none;
    display: inline-block;
    float: left;
  }

  .menuModelUl li i{
    /*display: flex;*/
    /*justify-content:center;*/
    /*align-items:Center;*/
  }
  .centeruser{
    display: flex;
    justify-content:center;
    align-items:Center;
  }
  .el-dialog{
    display: flex;
    flex-direction: column;
    margin:0 !important;
    position:absolute;
    top:50%;
    left:50%;
    transform:translate(-50%,-50%);
    /*height:600px;*/
    max-height:calc(100% - 30px);
    max-width:calc(100% - 30px);
  }
  .el-dialog .el-dialog__body{
    flex:1;
    overflow: auto;
  }
</style>
