<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户名称" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="衣服名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入衣服名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号码" prop="phoneNumber">
        <el-input
          v-model="queryParams.phoneNumber"
          placeholder="请输入手机号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="衣服字符串" prop="clothesJson">-->
<!--        <el-input-->
<!--          v-model="queryParams.clothesJson"-->
<!--          placeholder="请输入衣服字符串"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="二维码" prop="qrCode">-->
<!--        <el-input-->
<!--          v-model="queryParams.qrCode"-->
<!--          placeholder="请输入二维码"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="衣服图片" prop="image">-->
<!--        <el-input-->
<!--          v-model="queryParams.image"-->
<!--          placeholder="请输入衣服图片"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="爆款分类" prop="clothesType">-->
<!--        <el-select v-model="queryParams.clothesType" placeholder="请选择爆款分类" clearable size="small">-->
<!--          <el-option-->
<!--            v-for="dict in clothesTypeOptions"-->
<!--            :key="dict.dictValue"-->
<!--            :label="dict.dictLabel"-->
<!--            :value="dict.dictValue"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:userWardrobe:remove']"
        >删除</el-button>
      </el-col>
<!--	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
    </el-row>

    <el-table v-loading="loading" :data="userWardrobeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户名称" align="center" prop="userName" />
      <el-table-column label="手机号" align="center" prop="phoneNumber" />
      <el-table-column label="用户头像" align="center" >
        <template scope="scope">
          <!--<img :src="getBaseUrl() + scope.row.faceImg" width="50px" height="60px">-->
          <img v-if="scope.row.headImg" :src=" getBaseUrl() + scope.row.headImg" width="50px" height="60px">
          <img v-else="scope.row.headImg" :src="defaultImage" width="50px" height="60px">
        </template>
        <!--<img class="pic-404__child left" src="@/assets/404_images/404_cloud.png" alt="404">-->
      </el-table-column>
      <el-table-column label="衣服名称" align="center" prop="name" />
      <el-table-column label="二维码" align="center" >
        <template scope="scope">
          <!--<img :src="getBaseUrl() + scope.row.faceImg" width="50px" height="60px">-->
          <img v-if="scope.row.qrCode" :src=" getBaseUrl() + scope.row.qrCode" width="50px" height="60px">
          <img v-else="scope.row.qrCode" :src="defaultImage" width="50px" height="60px">
        </template>
        <!--<img class="pic-404__child left" src="@/assets/404_images/404_cloud.png" alt="404">-->
      </el-table-column>
      <el-table-column label="衣服图片" align="center" >
        <template scope="scope">
          <!--<img :src="getBaseUrl() + scope.row.faceImg" width="50px" height="60px">-->
          <img v-if="scope.row.image" :src=" getBaseUrl() + scope.row.image" width="50px" height="60px">
          <img v-else="scope.row.image" :src="defaultImage" width="50px" height="60px">
        </template>
        <!--<img class="pic-404__child left" src="@/assets/404_images/404_cloud.png" alt="404">-->
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row,1)"
            v-hasPermi="['system:userWardrobe:edit']"
          >添加爆款</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row,2)"
            v-hasPermi="['system:userWardrobe:edit']"
          >添加推荐设计</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdateOne(scope.row)"
            v-hasPermi="['system:userWardrobe:edit']"
          >添加模型大类白膜</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:userWardrobe:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            :ref="getBaseUrl() + scope.row.qrCode"
            icon="el-icon-download"
            @click="downLoad(getBaseUrl() + scope.row.qrCode)"
          >下载二维码</el-button>
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

    <!-- 添加或修改衣柜列表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="衣服名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入衣服名称" />
        </el-form-item>
        <el-form-item v-show="ifShow" label="爆款分类" prop="clothesType">
          <el-select v-model="form.clothesType" placeholder="请选择爆款分类">
            <el-option
              v-for="dict in clothesTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图片" prop="image">
          <el-upload
            class="avatar-uploader"
            :action="uploadImgUrl"
            :headers="headers"
            :show-file-list="false"
            :on-success="handleAvatarSuccess">
            <img width="100px" height="100px" v-if="form.image" :src="getImage(form.image)" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="板材数量" prop="boardCount">
          <el-input v-model="form.boardCount" placeholder="请输入板材数量" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


    <el-dialog :title="title" :visible.sync="openOne" width="500px" append-to-body>
      <el-form ref="formOne" :model="formOne" :rules="rules" label-width="80px">
        <el-form-item  label="版型大类" prop="styleId">
          <el-select v-model="formOne.styleId" placeholder="请选择版型大类">
            <el-option
              v-for="style in modelStyleList"
              :key="style.id"
              :label="style.name"
              :value="style.id"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormOne">确 定</el-button>
        <el-button @click="cancelOne">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listUserWardrobe, getUserWardrobe, delUserWardrobe, addUserWardrobe, updateUserWardrobe, exportUserWardrobe } from "@/api/system/userWardrobe";
import {addRecommend} from "@/api/system/recommend";
import {getList,updateModelStyle} from "@/api/system/modelStyle";
import logoImg from '@/assets/image/default-icon.png'
import { getToken } from "@/utils/auth";
import { formatDate } from "@/api/system/date";
import { addClothesStyle} from "@/api/system/clothesStyle";
import {updateWhiteDesign } from "@/api/system/whiteDesign";
export default {
  name: "UserWardrobe",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      imageUrl:"https://app-1255978856.cos.ap-shanghai.myqcloud.com//upload/2022/03/24/1396841abfc245268714bf2591cd0642.jpg",
      defaultImage: logoImg,
      // 非单个禁用
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      pathUrl:"https://app-1255978856.cos.ap-shanghai.myqcloud.com/",
      single: true,
      modelStyleList:[],
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      type: null,
      fileList:[],
      // 总条数
      total: 0,
      ifShow: false,
      // 衣柜列表表格数据
      userWardrobeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openOne: false,
      // 0:正常  1：日度爆款  2：周度爆款  3：年度爆款字典
      clothesTypeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        userName:null,
        name: null,
        clothesJson: null,
        qrCode: null,
        image: null,
        phoneNumber:null,
        clothesType: null
      },
      // 表单参数
      form: {},
      formOne:{},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    getList().then(response => {
      this.modelStyleList=response.data;
    });
    this.getDicts("clothes_type").then(response => {
      this.clothesTypeOptions = response.data;

    });
  },
  methods: {
    getImage(img){
      return "https://app-1255978856.cos.ap-shanghai.myqcloud.com/"+img;
    },
    handleAvatarSuccess(res, file) {
      this.form.image = res.fileName;
    },
    getBaseUrl(){
      return process.env.VUE_APP_IMG_BASE_API;
    },
    quillImgSuccess(res, file,fileList) {
      // res为图片服务器返回的数据
      console.log("上传成功！")
      // 如果上传成功
      if (res.code == 200) {
        // 插入图片  res.url为服务器返回的图片地址
        // this.form.pictureUrl=res.url;
        this.form.image=res.fileName;
      } else {
        this.$message.error("图片插入失败");
      }
    },
    //删除图片操作
    handleRemove(file, fileList) {
      this.fileList=[];
      this.form.image='';
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
    downLoad(url) {
      var a = document.createElement('a')
      let picName=formatDate();
      a.download = name || picName
      // 设置图片地址
      a.href =url;
      a.click();

    },
    /** 查询衣柜列表列表 */
    getList() {
      this.loading = true;
      listUserWardrobe(this.queryParams).then(response => {
        this.userWardrobeList = response.rows;
        this.total = response.total;
        this.loading = false;

        console.log(this.userWardrobeList)
      });
    },
    // 0:正常  1：日度爆款  2：周度爆款  3：年度爆款字典翻译
    clothesTypeFormat(row, column) {
      return this.selectDictLabel(this.clothesTypeOptions, row.clothesType);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    cancelOne() {
      this.openOne = false;
      this.resetOne();
    },
    // 表单重置
    reset() {
      this.fileList =[];
      this.form = {
        id: null,
        userId: null,
        name: null,
        clothesJson: null,
        qrCode: null,
        image: null,
        createTime: null,
        updateTime: null,
        clothesType: null
      };
      this.resetForm("form");
    },

    resetOne() {
      this.formOne = {
        id: null,
        styleId: null,
      };
      this.resetForm("formOne");
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
      this.title = "添加衣柜列表";
    },
    /** 修改按钮操作 */
    handleUpdate(row,type) {
      this.reset();
      const id = row.id || this.ids
      this.type=type;
      //1 :爆款  2:设计推荐   3:  白膜管理
      if (type==1){
        this.ifShow=true;
        getUserWardrobe(id).then(response => {
          this.form = response.data;
          this.open = true;
          if(response.data.image != null && response.data.image != ''){
            console.log("path："+this.pathUrl)
            this.fileList = [{url: this.pathUrl + response.data.image}]
            console.log(this.fileList);
          }
          this.title = "添加爆款";
        });
      }else if(type==2){
        this.ifShow=false;
        getUserWardrobe(id).then(response => {
          this.form = response.data;
          this.open = true;
          if(response.data.image != null && response.data.image != ''){
            this.fileList = [{url: this.pathUrl + response.data.image}]
          }
          this.title = "添加设计推荐";
        });
      }
    },
    handleUpdateOne(row){
      this.resetOne();
      const id = row.id || this.ids
      this.formOne.wardrobeId=id;
      this.openOne = true;
      this.title = "添加版型大类白膜";
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            if (this.type==1){
              addClothesStyle(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增爆款成功");
                this.open = false;
                this.getList();
              }
            });

            }else if(this.type==2){
              addRecommend(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("新增设计推荐成功");
                  this.open = false;
                  this.getList();
                }
                });
            }

          } else {
            addUserWardrobe(this.form).then(response => {
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
    submitFormOne() {
      this.$refs["formOne"].validate(valid => {
        if (valid) {
          updateWhiteDesign(this.formOne).then(response => {
            this.msgSuccess("新增模板大类白膜成功");
            this.openOne = false;
            this.getList();
          });

        }
      });

    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除衣柜列表编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delUserWardrobe(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有衣柜列表数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportUserWardrobe(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
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
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
