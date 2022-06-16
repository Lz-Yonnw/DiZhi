<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="品牌分类" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择品牌分类" clearable size="small">
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
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
          v-hasPermi="['system:modelType:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:modelType:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:modelType:remove']"
        >删除</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['system:modelType:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
<!--	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
    </el-row>

    <el-table v-loading="loading" :data="modelTypeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="品牌分类" align="center" prop="id" />-->
      <el-table-column label="名称" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <router-link :to="'/modelType/modelStyle/asList/' + scope.row.id" class="link-type">
            <span>{{ scope.row.name }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="品牌分类" align="center" prop="type" :formatter="typeFormat" />
      <el-table-column label="图片" align="center" >
        <template scope="scope">
          <!--<img :src="getBaseUrl() + scope.row.faceImg" width="50px" height="60px">-->
          <img v-if="scope.row.img" :src=" getBaseUrl() + scope.row.img" width="50px" height="60px">
          <img v-else="scope.row.img" :src="defaultImage" width="50px" height="60px">
        </template>
        <!--<img class="pic-404__child left" src="@/assets/404_images/404_cloud.png" alt="404">-->
      </el-table-column>


      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:modelType:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:modelType:remove']"
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

    <!-- 添加或修改版型大类列表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="图片" prop="img">
          <el-upload
            class="avatar-uploader"
            :action="uploadImgUrl"
            :headers="headers"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="quillImgBefore"
            accept=".jpg,.jpeg,.png">
            <img width="100px" height="100px" v-if="form.img" :src="getImage(form.img)" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="品牌分类" prop="type">
          <el-select v-model="form.type" placeholder="请选择品牌分类">
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
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
import { listModelType, getModelType, delModelType, addModelType, updateModelType, exportModelType } from "@/api/system/modelType";
import logoImg from '@/assets/image/default-icon.png'
import { getToken } from "@/utils/auth";
import { formatDate } from "@/api/system/date";
import { addClothesStyle} from "@/api/system/clothesStyle";
export default {
  name: "ModelType",
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
      defaultImage: logoImg,
      // 非单个禁用
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
      fileList:[],
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      // 总条数
      total: 0,
      // 版型大类列表表格数据
      modelTypeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 品牌分类字典
      typeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        type: null
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
    this.getDicts("model_type").then(response => {
      this.typeOptions = response.data;
    });
  },
  methods: {
    getImage(img){
      return "https://app-1255978856.cos.ap-shanghai.myqcloud.com/"+img;
    },
    handleAvatarSuccess(res, file) {
      this.form.img = res.fileName;
    },
    getBaseUrl(){
      return process.env.VUE_APP_IMG_BASE_API;
    },
    quillImgSuccess(res, file) {
      // res为图片服务器返回的数据
      // 如果上传成功
      if (res.code == 200) {
        // 插入图片  res.url为服务器返回的图片地址
        // this.form.pictureUrl=res.url;
        this.form.img=res.fileName;
      } else {
        this.$message.error("图片插入失败");
      }
    },
    //删除图片操作
    handleRemove(file, fileList) {
      this.fileList=[];
      this.form.img='';
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
    /** 查询版型大类列表列表 */
    getList() {
      this.loading = true;
      listModelType(this.queryParams).then(response => {
        this.modelTypeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 品牌分类字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.fileList =[];
      this.form = {
        id: null,
        name: null,
        type: null
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
      this.title = "添加版型大类列表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getModelType(id).then(response => {
        this.form = response.data;
        this.open = true;
        if(response.data.img != null && response.data.img != ''){
          this.fileList = [{url: process.env.VUE_APP_BASE_API + response.data.img}]
        }
        this.title = "修改版型大类列表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateModelType(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addModelType(this.form).then(response => {
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
      this.$confirm('是否确认删除版型大类列表编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delModelType(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有版型大类列表数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportModelType(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
