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
      <el-form-item label="编号" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--<el-form-item label="上级ID" prop="parentId">
        <el-input
          v-model="queryParams.parentId"
          placeholder="请输入上级ID 1-7"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->

<!--      <el-form-item label="图片" prop="img">-->
<!--        <el-input-->
<!--          v-model="queryParams.img"-->
<!--          placeholder="请输入图片"-->
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
          v-hasPermi="['system:modelStyle:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:modelStyle:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:modelStyle:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:modelStyle:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="modelStyleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="图片" align="center" prop="id" />-->
      <el-table-column label="名称" align="center" prop="name" >
        <template slot-scope="scope">
          <router-link :to="'/modelType/class/asList/' + scope.row.id" class="link-type">
            <span>{{ scope.row.name }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="编号" align="center" prop="code" />
<!--      <el-table-column label="上级ID" align="center" prop="parentId" />-->
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
            v-hasPermi="['system:modelStyle:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:modelStyle:remove']"
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

    <!-- 添加或修改版型小类对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="版型分类:" prop="parentId">
          <el-select v-model="form.parentId" placeholder="请选择衣服分类" >
            <el-option v-for="(item, index) in modelTypeList" :key="index" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>

        <el-form-item label="编号" prop="code">
          <el-input v-model="form.code" placeholder="请输入编号" />
        </el-form-item>

        <el-form-item label="尺码" prop="size">
          <el-select v-model="form.size" multiple placeholder="请选择尺码">
            <el-option
              v-for="data in modelSizeClassifyList"
              :key="data.dictValue"
              :label="data.dictLabel"
              :value="data.dictLabel">
            </el-option>
          </el-select>
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

        <el-form-item label="尺码表" prop="sizedescUrl">
          <el-upload
            class="avatar-uploader"
            :action="uploadImgUrl"
            :headers="headers"
            :show-file-list="false"
            :on-success="handleSizeAvatarSuccess"
            :before-upload="quillImgBefore"
            accept=".jpg,.jpeg,.png">
            <img width="100px" height="100px" v-if="form.sizedescUrl" :src="getImage(form.sizedescUrl)" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
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
import { listModelStyle, getModelStyle, delModelStyle,getModelStyleAsList, addModelStyle, updateModelStyle, exportModelStyle,getFourList } from "@/api/system/modelStyle";
import { listModelType } from "@/api/system/modelType";
import logoImg from '@/assets/image/default-icon.png'
import { getToken } from "@/utils/auth";
import { formatDate } from "@/api/system/date";
import {getDicts} from "@/api/system/dict/data"
import { addClothesStyle} from "@/api/system/clothesStyle";
import fa from '../../../element-ui/src/locale/lang/fa'
import he from '../../../element-ui/src/locale/lang/he'
import fi from '../../../element-ui/src/locale/lang/fi'
export default {
  name: "ModelStyle",
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
      defaultImage: logoImg,
      // 非单个禁用
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
      fileList:[],
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      parentId: null,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 版型小类表格数据
      modelStyleList: [],
      //版型大类表格数据
      modelTypeList: [],
      //树形数据
      queryParamsType: '',
      categoryId:null,
      //分类
      categoryOptions: [],
      // 树型数据
      treeData: "",
      //模型尺码分类
      modelSizeClassifyList:[],
      defaultProps: {
        //树形控件的树形绑定对象
        children: 'children',//设置通过chiledren树形展示节点信息
        label: 'label',//通过label设置树形节点文本展示
        disabled: function (data,node) {
          if (data.type === 9){
            return true;
          }
        }
      },
      //记录每层的打开节点
      preOpenNode: {
        max: 0
      },
      watch: {},


      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        code: null,
        parentId: null,
        img: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {

      }
    };
  },
  created() {
    this.getList()
    this.getModelSizeClassifyList();
    this.getModelTypeList()
    this.getFourList()
    const id = this.$route.params && this.$route.params.id;
    this.parentId=id;
    this.queryParams.parentId=id;
    this.getModelStyleAsList();

    this.getProjectDate();
  },

  methods: {
    //获取模型尺码分类列表
    getModelSizeClassifyList(){
      getDicts("model_size_classify").then(response=>{
        this.modelSizeClassifyList = response.data;
      })
    },
    getImage(img){
      return "https://app-1255978856.cos.ap-shanghai.myqcloud.com/"+img;
    },
    handleAvatarSuccess(res, file) {
      this.form.img = res.fileName;
    },
    handleSizeAvatarSuccess(res, file){
      this.form.sizedescUrl = res.fileName;
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
    /** 查询版型小类列表 */
    getList() {
      this.loading = true;
      listModelStyle(this.queryParams).then(response => {
        // this.modelStyleList = this.handleTree(response.data,"deptId");
        this.modelStyleList = response.rows;
        this.total = response.total;

        this.loading = false;
      });
    },
    /** 查询版型大类列表 */
    getModelTypeList(){
      listModelType(null).then(response => {
         this.modelTypeList = response.rows;
         this.total = response.total;
         this.loading = false;
      })



    },
    getFourLists(){
      getFourList(null).then(res =>{
        this.categoryOptions = res.rows;
        this.total = response.total;
        this.total = res.total;
      })
    },
    //我是在弹出框中显示树形控件
    SiteAccountRelship(row){
      this.reset();
      this.open = true;
      this.title = "title";

      let param = {
        stationId: row.id
      }
      //查询已选择的role
      listStationUserRelation(param).then(res=>{
        this.hasChoosenRole = res.rows
        this.$nextTick(() =>{
          //弹出框弹出时，选中查到的数据
          this.$refs.tree.setCheckedNodes(this.hasChoosenRole);
        })
      })
    },
    //拿到所有的role
    checkChange(){
      this.hasChoosenRoleNew = this.$refs.tree.getCheckedNodes();
    },
    //树权限
    handleCheckedTreeNodeAll(value,type){
      this.$refs.tree.setCheckedNodes(value ? this.categoryOptions: [])
    },





    getModelStyleAsList(){
      this.loading = true;
      getModelStyleAsList(this.queryParams).then(response => {
        console.log(response);
        this.modelStyleList = response.rows;
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
      this.fileList=[];
      this.form = {
        id: null,
        name: null,
        code: null,
        parentId: null,
        img: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getModelStyleAsList();
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
    handleAdd(row) {
      this.reset();
      if (row != undefined) {
        this.form.parentId = row.deptId;
      }
      this.open = true;
      this.title = "添加版型小类";
      addModelStyle.then(respone =>{
        this.modelStyleList = this.handleTree(respone.data,"deptId")
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getModelStyle(id).then(response => {
        this.form = response.data;
        this.form.size = JSON.parse(this.form.size)
        this.open = true;
        if(response.data.img != null && response.data.img != ''){
          this.fileList = [{url: process.env.VUE_APP_IMG_BASE_API + response.data.img}]
        }
        this.title = "修改版型小类";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            this.form.size = JSON.stringify(this.form.size);
            updateModelStyle(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getModelStyleAsList();
              }
            });
          } else {
            // this.form.parentId=this.parentId;
            addModelStyle(this.form).then(response => {

              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getModelStyleAsList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除版型小类编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delModelStyle(ids);
        }).then(() => {
          this.getModelStyleAsList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有版型小类数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportModelStyle(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>

