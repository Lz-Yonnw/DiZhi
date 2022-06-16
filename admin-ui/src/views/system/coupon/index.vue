<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="优惠劵标题" prop="couponTitle">
        <el-input
          v-model="queryParams.couponTitle"
          placeholder="请输入优惠劵标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否为所有商品通用优惠劵" prop="couponAllavailable">
        <el-select v-model="queryParams.couponAllavailable" placeholder="请选择活动区域">
          <el-option label="通用卷" value="1"></el-option>
          <el-option label="指定商品卷" value="2"></el-option>
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:coupon:add']"
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
          v-hasPermi="['system:coupon:edit']"
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
          v-hasPermi="['system:coupon:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:coupon:export']"
        >导出</el-button>
      </el-col>
<!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
    </el-row>

    <el-table v-loading="loading" :data="couponList" @selection-change="handleSelectionChange">
<!--      <el-table-column type="selection" width="55" align="center" />-->
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="优惠劵标题" align="center" prop="couponTitle" />
      <el-table-column label="path图片路径" align="center" prop="couponUrl">
        <template slot-scope="scope">
          <img width="100px" height="100px" v-if="scope.row.couponUrl" :src="getImage(scope.row.couponUrl)" class="avatar">
        </template>
      </el-table-column>
      <el-table-column label="满减价格" align="center" prop="couponLimit" />
      <el-table-column label="优惠价格" align="center" prop="couponPrice" />
      <el-table-column label="发卷数量" align="center" prop="couponNumber" />
      <el-table-column label="是否为所有商品通用优惠劵 1：通用  2：指定商品" align="center" prop="couponAllavailable">
        <template slot-scope="scope">
          <span>{{scope.row.createStatus.couponAllavailable==1?'通过卷':'指定商品卷'}}</span>
        </template>
      </el-table-column>
      <el-table-column label="优惠劵状态" align="center" prop="createStatus">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.createStatus==0?false:true"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:coupon:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:coupon:remove']"
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

    <!-- 添加或修改优惠劵对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="优惠劵标题" prop="couponTitle">
          <el-input v-model="form.couponTitle" placeholder="请输入优惠劵标题" />
        </el-form-item>
        <el-form-item label="path图片路径" prop="couponUrl">
          <el-upload
            class="avatar-uploader"
            :action="uploadImgUrl"
            :headers="headers"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img width="150px" height="30"  v-if="form.couponUrl" :src="getImage(form.couponUrl)" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="满减价格" prop="couponLimit">
          <el-input-number v-model="form.couponLimit" :min="1" :max="999999"></el-input-number>
        </el-form-item>
        <el-form-item label="优惠价格" prop="couponPrice">
          <el-input-number v-model="form.couponPrice" :min="1" :max="999999"></el-input-number>
        </el-form-item>
        <el-form-item label="发卷数量" prop="couponNumber">
          <el-input-number v-model="form.couponNumber" :min="1" :max="999999"></el-input-number>
        </el-form-item>
        <el-form-item label="是否为所有商品通用优惠劵 1：通用  2：指定商品" prop="couponAllavailable">
          <el-input v-model="form.couponAllavailable" placeholder="请输入是否为所有商品通用优惠劵 1：通用  2：指定商品" />
        </el-form-item>

        <el-form-item label="状态" prop="createStatus">
          <el-switch
            v-model="form.createStatus"
            active-color="#13ce66"
            inactive-color="#ff4949"
            :active-value="1"
            :inactive-value="0"
          >
          </el-switch>
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
import { listCoupon, getCoupon, delCoupon, addCoupon, updateCoupon } from "@/api/system/coupon";
import { getToken } from "@/utils/auth";

export default {
  name: "Coupon",
  data() {
    return {
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
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
      // 优惠劵表格数据
      couponList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        couponTitle: null,
        couponUrl: null,
        couponLimit: null,
        couponPrice: null,
        couponNumber: null,
        couponAllavailable: null,
        createStatus: 1
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    //图片上传之前函数
    beforeAvatarUpload(file) {

    },
    handleAvatarSuccess(res, file) {
      this.form.couponUrl = res.fileName;
    },
    getImage(img){
      console.log("进1")
      return "https://app-1255978856.cos.ap-shanghai.myqcloud.com/"+img;
    },
    /** 查询优惠劵列表 */
    getList() {
      this.loading = true;
      listCoupon(this.queryParams).then(response => {
        this.couponList = response.rows;
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
        couponTitle: null,
        couponUrl: null,
        couponLimit: null,
        couponPrice: null,
        couponNumber: null,
        couponAllavailable: null,
        createTime: null,
        createStatus: 1
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
      this.title = "添加优惠劵";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCoupon(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改优惠劵";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCoupon(this.form).then(response => {
              this.$message({
                type: 'success',
                message: '修改成功!',
              });
              this.open = false;
              this.getList();
            });
          } else {
            addCoupon(this.form).then(response => {
              this.$message({
                type: 'success',
                message: '新增成功！!',
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
      this.$modal.confirm('是否确认删除优惠劵编号为"' + ids + '"的数据项？').then(function() {
        return delCoupon(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/coupon/export', {
        ...this.queryParams
      }, `coupon_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
</style>
