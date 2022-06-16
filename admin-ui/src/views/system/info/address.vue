<template>
  <div class="app-container">
    <el-steps :active="stepsStatus">
      <el-step title="请输入用户手机号查询收货地址"></el-step>
      <el-step title="收货地址"></el-step>
    </el-steps>

    <!-- 用户信息 -->
    <div v-if="stepsStatus==1">
      <el-form :model="queryParamsUser" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="手机号" prop="phoneNumber">
          <el-input
            v-model="queryParamsUser.phoneNumber"
            placeholder="请输入手机号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="infoList" >
        <el-table-column label="用户编号" align="center" prop="userId" />
        <el-table-column label="用户昵称" align="center" prop="userNickname" />
        <el-table-column label="用户账户" align="center" prop="userName" />
        <el-table-column label="性别" align="center" prop="gender">
          <template slot-scope="scope">
            <i v-if="scope.row.gender==1">女</i><i v-if="scope.row.gender==0">男</i>
          </template>
        </el-table-column>
        <el-table-column label="手机号码" width="110" align="center" prop="phoneNumber" />
        <el-table-column label="邮箱" align="center" prop="eMail" />
        <el-table-column label="头像" align="center" prop="headImg">
          <template slot-scope="scope">
            <div class="block"><el-avatar :size="50" :src="getImage(scope.row.headImg)"></el-avatar></div>
          </template>
        </el-table-column>
        <el-table-column label="注册时间" align="center" prop="registerTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.registerTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <!--      <el-table-column label="注册方式  1：普通，2：小程序，3：支付宝" align="center" prop="source" />-->
        <el-table-column label="账号状态" align="center" prop="status">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.status"
              active-color="#13ce66"
              inactive-color="#ff4949"
              :active-value="0"
              :inactive-value="1"
              disabled
            >
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="省份" align="center" prop="province" />

        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="getAddress(scope.row)"
              v-hasPermi="['system:info:edit']"
            >查看我的收货地址</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="totaluser>0"
        :total="totaluser"
        :page.sync="queryParamsUser.pageNum"
        :limit.sync="queryParamsUser.pageSize"
        @pagination="geUsertList"
      />


    </div>

    <!-- 收货地址 -->
    <div v-if="stepsStatus==2">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['system:book:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['system:book:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['system:book:remove']"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system:book:export']"
          >导出</el-button>
          <el-button @click="stepsStatus=1">重新选择用户</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
      <!-- 收货地址 -->
      <el-table v-loading="loading" :data="bookList" @selection-change="handleSelectionChange">
        <el-table-column label="地址表id" align="center" prop="id" />
        <el-table-column label="收货人" align="center" prop="consignee" />
        <el-table-column label="手机号" align="center" prop="phone" />
        <el-table-column label="性别" align="center" prop="sex"/>
        <el-table-column label="省级名称" align="center" prop="provinceName" />
        <el-table-column label="市级名称" align="center" prop="cityName" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:book:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:book:remove']"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>


      <!-- 添加或修改地址详细对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="收货人" prop="consignee">
            <el-input v-model="form.consignee" placeholder="请输入收货人" />
          </el-form-item>
          <el-form-item label="性别" prop="sex">
<!--            <el-input v-model="form.sex" placeholder="请输入性别" />-->

            <el-radio-group v-model="form.sex">
              <el-radio label="女">女</el-radio>
              <el-radio label="男">男</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="form.phone" placeholder="请输入手机号" />
          </el-form-item>


          <el-form-item label="省级名称" prop="provinceName">
            <el-select v-model='form.provinceName' placeholder="请选择省级名称" :label-in-value="true"  @change="addressChange(1,$event)">
              <el-option v-for="item in provinceList" :value="item.name">{{item.name}}</el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="市级名称" prop="cityName">
            <el-select v-model='form.cityName' placeholder="请输入市级名称" :label-in-value="true" @change="addressChange(2,$event)">
              <el-option v-for="item in cityList" :value="item.name">{{item.name}}</el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="区级名称" prop="districtName">
            <el-select v-model='form.districtName' placeholder="请输入区(县)名称" :label-in-value="true" @change="addressChange(3,$event)">
              <el-option v-for="item in districtList" :value="item.name">{{item.name}}</el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="详细地址" prop="detail">
            <el-input v-model="form.detail" placeholder="请输入详细地址" />
          </el-form-item>
          <el-form-item label="标签" prop="label">
            <el-input v-model="form.label" placeholder="请输入标签" />
          </el-form-item>
          <el-form-item label="是否默认收货地址" prop="isDefault">
<!--            <el-input v-model="form.isDefault" placeholder="请输入是否默认 0 否 1是" />-->
            <el-switch
              v-model="form.isDefault"
              active-color="#13ce66"
              inactive-color="#ff4949"
              :active-value="1"
              :inactive-value="0"
            >
            </el-switch>
          </el-form-item>
          <el-form-item label="创建人" prop="createUserName">
            <el-input v-model="form.createUserName" disabled placeholder="请输入创建人" />
          </el-form-item>
          <el-form-item label="修改人" prop="updateUser" v-if="form.updateUser!=null">
            <el-input v-model="form.updateUser" placeholder="请输入修改人" />
          </el-form-item>
<!--          <el-form-item label="是否删除" prop="isDeleted">-->
<!--&lt;!&ndash;            <el-input v-model="form.isDeleted" placeholder="请输入是否删除" />&ndash;&gt;-->
<!--            <el-switch-->
<!--              v-model="form.isDeleted"-->
<!--              active-color="#13ce66"-->
<!--              inactive-color="#ff4949"-->
<!--              :active-value="0"-->
<!--              :inactive-value="1"-->
<!--            >-->
<!--            </el-switch>-->
<!--          </el-form-item>-->
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>

  </div>
</template>

<script>
  import { getRegionParentId,listBook, getBook, delBook, addBook, updateBook } from "@/api/system/addressBook";
  import { listInfo } from "@/api/system/infoUser";
  import ar from '../../../element-ui/src/locale/lang/ar'

  export default {
    name: "Book",
    data() {
      return {
        stepsStatus:1,
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
        totaluser:0,
        // 地址详细表格数据
        bookList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          userId: null,
          consignee: null,
          phone: null,
          sex: null,
          provinceCode: null,
          provinceName: null,
          cityCode: null,
          cityName: null,
          districtCode: null,
          districtName: null,
          detail: null,
          label: null,
          isDefault: null,
          createUser: null,
          updateUser: null,
          isDeleted: 1
        },
        // 查询参数
        queryParamsUser: {
          pageNum: 1,
          pageSize: 10,
          userId: null,
          userNickname: null,
          userName: null,
          gender: null,
          phoneNumber: null,
          password: null,
          eMail: null,
          wechatNumber: null,
          qqNumber: null,
          headImg: null,
          openId: null,
          registerTime: null,
          source: null,
          status: null,
          intro: null,
          tags: null,
          signature: null,
          province: null,
          city: null,
          country: null,
          area: null,
          homePic: null,
          birthday: null,
          height: null,
          price: null,
          isFirst: null,
          weight: null,
        },

        // 表单参数
        form: {},
        // 用户信息表格数据
        infoList: [],
        provinceList:[],//省级地址
        cityList:[],//市级地址
        districtList:[],//区(县)地址

        // 表单校验
        rules: {
          userId: [
            { required: true, message: "用户id不能为空", trigger: "blur" }
          ],
          consignee: [
            { required: true, message: "收货人不能为空", trigger: "blur" }
          ],
          phone: [
            { required: true, message: "手机号不能为空", trigger: "blur" }
          ],
          sex: [
            { required: true, message: "性别不能为空", trigger: "change" }
          ],
          provinceName: [
            { required: true, message: "省级名称不能为空", trigger: "blur" }
          ],
          cityName: [
            { required: true, message: "市级名称不能为空", trigger: "blur" }
          ],
          districtName: [
            { required: true, message: "区级名称不能为空", trigger: "blur" }
          ],
          detail: [
            { required: true, message: "详细地址不能为空", trigger: "blur" }
          ],
          isDefault: [
            { required: true, message: "是否默认 0 否 1是不能为空", trigger: "blur" }
          ],
          createTime: [
            { required: true, message: "创建时间不能为空", trigger: "blur" }
          ],
          updateTime: [
            { required: true, message: "更新时间不能为空", trigger: "blur" }
          ],
          isDeleted: [
            { required: true, message: "是否删除不能为空", trigger: "blur" }
          ],
        }
      };
    },
    created() {
      this.geUsertList();
    },
    methods: {
      addressChange(type,val){
        let Code = 0;
        switch (type) {
          case 1:
            this.form.provinceName = val;
            this.provinceList.find(province=>{
              if(province.name==val){
                Code = province.id;
                this.form.provinceCode = Code;
                this.form.cityName = null;
                this.form.districtName = null;
              }
            })
            break;
          case 2:
            this.form.cityName = val;
            this.cityList.find(province=>{
              if(province.name==val){
                Code = province.id;
                this.form.cityCode = Code;
                this.form.districtName = "";
              }
            })
            break;
          case 3:
            this.form.districtName = val;
            this.districtList.find(province=>{
              if(province.name==val){
                Code = province.id;
                this.form.districtCode = Code;
              }
            })
            break;
        }
        this.getRegionParentList(type+1,Code);
      },
      //获取省市级地址
      getRegionParentList(type,id){
        getRegionParentId(id).then(response=>{
          switch (type) {
            case 1:
              this.provinceList = response.rows;
              break;
            case 2:
              this.cityList = response.rows;
              break;
            case 3:
              this.districtList = response.rows;
              break
          }
        })
      },
      //查看收货地址
      getAddress(row){
        this.stepsStatus = 2;
        this.queryParams.userId = row.id;
        this.getList(this.queryParams);

      },
      getImage(img){
        return "https://app-1255978856.cos.ap-shanghai.myqcloud.com/"+img;
      },
      /** 查询地址详细列表 */
      getList() {
        this.loading = true;
        listBook(this.queryParams).then(response => {
          this.bookList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      /** 查询用户信息列表 */
      geUsertList() {
        this.loading = true;
        listInfo(this.queryParamsUser).then(response => {
          this.infoList = response.rows;
          this.totaluser = response.total;
          this.loading = false;
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
          userId: null,
          consignee: null,
          phone: null,
          sex: null,
          provinceCode: null,
          provinceName: null,
          cityCode: null,
          cityName: null,
          districtCode: null,
          districtName: null,
          detail: null,
          label: null,
          isDefault: null,
          createTime: null,
          updateTime: null,
          createUser: null,
          updateUser: null,
          isDeleted: null
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.geUsertList();
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
        this.title = "添加地址详细";
        this.form.userId = this.queryParams.userId;
        this.getRegionParentList(1,0);
        this.cityList = [];
        this.districtList = [];
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getBook(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改地址详细";
          this.getRegionParentList(1,0);
          this.getRegionParentList(2,this.form.provinceCode);
          this.getRegionParentList(3,this.form.cityCode);

          let arr = document.cookie.split('; ');
          for(let i=0;i<arr.length;i++){
            if(arr[i].includes("userId")){
              this.form.createUser = arr[i].substr(arr[i].indexOf("=")+1);
            }else if(arr[i].includes("username")){
              this.form.createUserName = arr[i].substr(arr[i].indexOf("=")+1);
            }
          }
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateBook(this.form).then(response => {
                this.$message({
                  type: 'success',
                  message: '修改成功!',
                });
                this.open = false;
                this.getList();
              });
            } else {
              console.log(this.form)
              addBook(this.form).then(response => {
                this.$message({
                  type: 'success',
                  message: '新增成功!',
                });
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
        this.$confirm('此操作将删除该用户, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          },
        ).then(() => {
          //删除
          delBook(ids).then(response=>{
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
        this.download('system/book/export', {
          ...this.queryParams
        }, `book_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>
