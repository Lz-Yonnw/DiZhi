<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="banner" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="图片地址" prop="bannerUrl">-->
<!--        <el-input-->
<!--          v-model="queryParams.bannerUrl"-->
<!--          placeholder="请输入图片地址"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="类型: 1:开机页   2.首页" prop="bannerType">-->
<!--        <el-select v-model="queryParams.bannerType" placeholder="请选择类型: 1:开机页   2.首页" clearable size="small">-->
<!--          <el-option label="请选择字典生成" value="" />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="跳转地址" prop="jumpLink">-->
<!--        <el-input-->
<!--          v-model="queryParams.jumpLink"-->
<!--          placeholder="请输入跳转地址"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:banner:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:banner:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:banner:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:banner:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bannerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="跳转地址" align="center" prop="id" />-->
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="图片" align="center" >
        <template scope="scope">
          <!--<img :src="getBaseUrl() + scope.row.faceImg" width="50px" height="60px">-->
          <img v-if="scope.row.bannerUrl" :src=" getBaseUrl() + scope.row.bannerUrl" width="50px" height="60px">
          <img v-else="scope.row.bannerUrl" :src="defaultImage" width="50px" height="60px">
        </template>
        <!--<img class="pic-404__child left" src="@/assets/404_images/404_cloud.png" alt="404">-->
      </el-table-column>
<!--      <el-table-column label="类型: 1:开机页   2.首页" align="center" prop="bannerType" />-->
      <el-table-column label="跳转地址" align="center" prop="jumpLink" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:banner:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:banner:remove']"
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

    <!-- 添加或修改banner列表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="图片" prop="bannerUrl">
          <el-upload
            ref="bannerUrl"
            :file-list="fileList"
            :action="uploadImgUrl"
            :headers="headers"
            :on-remove="handleRemove"
            :before-upload="quillImgBefore"
            :on-success="quillImgSuccess"
            :limit="1"
            list-type="picture-card"
            accept=".jpg,.jpeg,.png"
            name="file">

            <i class="el-icon-plus"></i>
            <!--            <img v-if="imageUrl" :src="imageUrl" class="avatar">-->
            <!--            <i v-else class="el-icon-plus avatar-uploader-icon"></i>-->
          </el-upload>
        </el-form-item>
<!--        <el-form-item label="类型: 1:开机页   2.首页" prop="bannerType">-->
<!--          <el-select v-model="form.bannerType" placeholder="请选择类型: 1:开机页   2.首页">-->
<!--            <el-option label="请选择字典生成" value="" />-->
<!--          </el-select>-->
<!--        </el-form-item>-->
        <el-form-item label="跳转地址" prop="jumpLink">
          <el-input v-model="form.jumpLink" placeholder="请输入跳转地址" />
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
import { listBanner, getBanner, delBanner, addBanner, updateBanner, exportBanner } from "@/api/system/banner";
import logoImg from '@/assets/image/default-icon.png'
import { getToken } from "@/utils/auth";

export default {
  name: "Banner",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      defaultImage: logoImg,

      // 遮罩层
      loading: true,
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      fileList:[],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // banner列表表格数据
      bannerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        bannerUrl: null,
        bannerType: 1,
        jumpLink: null,
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
    getBaseUrl(){
      return process.env.VUE_APP_IMG_BASE_API;
    },
    /** 查询banner列表列表 */
    getList() {
      this.loading = true;
      listBanner(this.queryParams).then(response => {
        this.bannerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    quillImgSuccess(res, file) {
      // res为图片服务器返回的数据
      // 如果上传成功
      if (res.code == 200) {
        // 插入图片  res.url为服务器返回的图片地址
        // this.form.pictureUrl=res.url;
        this.form.bannerUrl=res.fileName;
      } else {
        this.$message.error("图片插入失败");
      }
    },
    //删除图片操作
    handleRemove(file, fileList) {
      this.fileList=[];
      this.form.bannerUrl='';
      this.timer = setTimeout(()=>{   //设置延迟执行
        console.log('ok');
      },1000);
    },
    // 图片上传前校验方法
    quillImgBefore(file) {
      let fileType = file.type;
      if(fileType === 'image/jpg'||fileType === 'image/jpeg' || fileType === 'image/png'){
        return true;
      }else {
        this.$message.error('请插入图片类型文件(jpg)');
        return false;
      }
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.fileList=[];
      this.form = {
        id: null,
        name: null,
        bannerUrl: null,
        bannerType: 1,
        jumpLink: null,
        createTime: null,
        updateTime: null
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
      this.title = "添加banner列表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBanner(id).then(response => {
        this.form = response.data;
        this.open = true;
        if(response.data.bannerUrl != null && response.data.bannerUrl != ''){
          this.fileList = [{url: process.env.VUE_APP_IMG_BASE_API + response.data.bannerUrl}]
        }
        this.title = "修改banner列表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {

            updateBanner(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            this.form.bannerType=1;
            addBanner(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除banner列表编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delBanner(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有banner列表数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportBanner(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
