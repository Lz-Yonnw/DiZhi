<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户账户" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户账户"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别：0：男，1：女，2：保密" prop="gender">
        <el-input
          v-model="queryParams.gender"
          placeholder="请输入性别：0：男，1：女，2：保密"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号码" prop="phoneNumber">
        <el-input
          v-model="queryParams.phoneNumber"
          placeholder="请输入手机号码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="注册时间" prop="registerTime">-->
<!--        <el-date-picker clearable-->
<!--          v-model="queryParams.registerTime"-->
<!--          type="date"-->
<!--          value-format="yyyy-MM-dd"-->
<!--          placeholder="请选择注册时间">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
      <el-form-item label="省份" prop="province">
        <el-input
          v-model="queryParams.province"
          placeholder="请输入省份"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:info:add']"
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
          v-hasPermi="['system:info:edit']"
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
          v-hasPermi="['system:info:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:info:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
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
<!--      <el-table-column label="简介" align="center" prop="intro" />-->
<!--      <el-table-column label="标签" align="center" prop="tags" />-->
<!--      <el-table-column label="个性签名" align="center" prop="signature" />-->
      <el-table-column label="省份" align="center" prop="province" />
<!--      <el-table-column label="城市" align="center" prop="city" />-->
<!--      <el-table-column label="国家" align="center" prop="country" />-->
<!--      <el-table-column label="区域" align="center" prop="area" />-->
<!--      <el-table-column label="个人主页大图" align="center" prop="homePic" />-->
<!--      <el-table-column label="生日" align="center" prop="birthday" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.birthday, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="身高" align="center" prop="height" />-->
      <el-table-column label="余额" align="center" prop="price" />
<!--      <el-table-column label="是否首次登录 0:首次登录  1:第二次登录" align="center" prop="isFirst" />-->
<!--      <el-table-column label="体重" align="center" prop="weight" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:info:edit']"
          >修改</el-button>
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

    <!-- 添加或修改用户信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户编号" prop="userId" v-if="formVisible">
          <el-input v-model="form.userId" disabled  placeholder="请输入用户id" />
        </el-form-item>
        <el-form-item label="用户昵称" prop="userNickname">
          <el-input v-model="form.userNickname" placeholder="请输入用户昵称" />
        </el-form-item>
        <el-form-item label="用户账户" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户账户" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="1">女</el-radio>
            <el-radio label="0">男</el-radio>
          </el-radio-group>

        </el-form-item>
        <el-form-item label="手机号码" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" placeholder="请输入手机号码" />
        </el-form-item>
<!--        <el-form-item label="密码" prop="password">-->
<!--          <el-input v-model="form.password" placeholder="请输入密码" />-->
<!--        </el-form-item>-->
        <el-form-item label="邮箱" prop="eMail">
          <el-input v-model="form.eMail" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="微信号" prop="wechatNumber">
          <el-input v-model="form.wechatNumber" placeholder="请输入微信号" />
        </el-form-item>
        <el-form-item label="QQ号码" prop="qqNumber">
          <el-input v-model="form.qqNumber" placeholder="请输入QQ号码" />
        </el-form-item>
        <el-form-item label="头像" prop="headImg">
          <el-upload
            class="avatar-uploader"
            :action="uploadImgUrl"
            :headers="headers"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img width="100px" height="100px" v-if="form.headImg" :src="getImage(form.headImg)" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
<!--        <el-form-item label="微信openID" prop="openId">-->
<!--          <el-input v-model="form.openId" placeholder="请输入微信openID" />-->
<!--        </el-form-item>-->
        <el-form-item label="注册时间" prop="registerTime" v-if="formVisible">
          <el-date-picker clearable
            v-model="form.registerTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择注册时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="注册方式" prop="source">
<!--          <el-input v-model="form.source" placeholder="请输入注册方式  1：普通，2：小程序，3：支付宝" />-->
          <el-radio-group v-model="form.source">
            <el-radio :label="1">普通</el-radio>
            <el-radio :label="2">小程序</el-radio>
            <el-radio :label="3">支付宝</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="简介" prop="intro">
          <el-input v-model="form.intro" placeholder="请输入简介" />
        </el-form-item>
        <el-form-item label="标签" prop="tags">
          <el-input v-model="form.tags" placeholder="请输入标签" />
        </el-form-item>
        <el-form-item label="个性签名" prop="signature">
          <el-input v-model="form.signature" placeholder="请输入个性签名" />
        </el-form-item>
        <el-form-item label="国家" prop="country">
          <el-input v-model="form.country" placeholder="请输入国家" />
        </el-form-item>
        <el-form-item label="省份" prop="province">
          <el-input v-model="form.province" placeholder="请输入省份" />
        </el-form-item>
        <el-form-item label="城市" prop="city">
          <el-input v-model="form.city" placeholder="请输入城市" />
        </el-form-item>
        <el-form-item label="区域" prop="area">
          <el-input v-model="form.area" placeholder="请输入区域" />
        </el-form-item>
        <el-form-item label="个人主页大图" prop="homePic">
          <el-upload
            class="avatar-uploader"
            :action="uploadImgUrl"
            :headers="headers"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img width="100px" height="100px" v-if="form.homePic" :src="getImage(form.homePic)" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="生日" prop="birthday">
          <el-date-picker clearable
            v-model="form.birthday"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择生日">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="身高" prop="height">
          <el-input v-model="form.height" placeholder="请输入身高" />
        </el-form-item>
        <el-form-item label="余额" prop="price">
          <el-input v-model="form.price" disabled  placeholder="请输入余额" />

        </el-form-item>
        <el-form-item label="是否首次登录" prop="isFirst">
<!--          <el-input v-model="form.isFirst" placeholder="请输入是否首次登录 0:首次登录  1:第二次登录" />-->
          <el-radio-group v-model="form.isFirst">
            <el-radio disabled :label="0">首次登录</el-radio>
            <el-radio disabled :label="1">第二次登录</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="体重" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入体重" />
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
import { listInfo, getInfo, delInfo, addInfo, updateInfo } from "@/api/system/infoUser";
import { getToken } from "@/utils/auth";
export default {
  name: "Info",
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
      // 用户信息表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      //隐藏列
      formVisible:false,
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
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
        weight: null
      },
      // 表单参数
      form: {},
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "用户id不能为空", trigger: "blur" }
        ],
        gender: [
          { required: true, message: "性别：0：男，1：女，2：保密不能为空", trigger: "blur" }
        ],
        phoneNumber: [
          { required: true, message: "手机号码不能为空", trigger: "blur" }
        ],
        price: [
          { required: true, message: "余额不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getImage(img){
      return "https://app-1255978856.cos.ap-shanghai.myqcloud.com/"+img;
    },
    /** 查询用户信息列表 */
    getList() {
      this.loading = true;
      listInfo(this.queryParams).then(response => {
        this.infoList = response.rows;
        this.total = response.total;
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
        status: 0,
        intro: null,
        tags: null,
        signature: null,
        province: null,
        city: null,
        country: null,
        area: null,
        homePic: null,
        updateTime: null,
        birthday: null,
        height: null,
        price: null,
        isFirst: null,
        weight: null
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
    //头像上传成功
    handleAvatarSuccess(res, file) {
      this.form.homePic = res.fileName;
    },
    //图片上传之前函数
    beforeAvatarUpload(file) {
      // const isJPG = file.type === 'image/jpeg/png';
      // const isLt2M = file.size / 1024 / 1024 < 2;
      //
      // if (!isJPG) {
      //   this.$message.error('上传头像图片只能是 JPG 格式!');
      // }
      // if (!isLt2M) {
      //   this.$message.error('上传头像图片大小不能超过 2MB!');
      // }
      // return isJPG && isLt2M;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用户信息";
      this.formVisible = false;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      this.formVisible = true;
      getInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInfo(this.form).then(response => {
              this.$message({
                type: 'success',
                message: '修改成功!',
              });
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then(response => {
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
        delInfo(ids).then(response=>{
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
