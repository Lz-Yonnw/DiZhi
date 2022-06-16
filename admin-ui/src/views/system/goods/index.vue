<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
<!--      <el-form-item label="店家id" prop="tbStoreId">-->
<!--        <el-input-->
<!--          v-model="queryParams.tbStoreId"-->
<!--          placeholder="请输入店家"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="商品名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入商品名称"
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
          v-hasPermi="['system:mall:add']"
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
          v-hasPermi="['system:mall:edit']"
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
          v-hasPermi="['system:mall:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:mall:export']"
        >导出</el-button>
      </el-col>
<!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
    </el-row>

    <el-table v-loading="loading" :data="mallList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="商品id" align="center" prop="id" />
<!--      <el-table-column label="店家id" align="center" prop="tbStoreId" />-->
      <el-table-column label="商品名称" align="center" prop="name" />
      <el-table-column label="价格" align="center" prop="price" />
      <el-table-column label="商品描述" align="center" prop="description" />
      <el-table-column label="商品上下架" align="center" prop="status">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status==null?false:true"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="图片" align="center" prop="img">
        <template slot-scope="scope">
          <img width="100px" height="100px" v-if="scope.row.img" :src="getImage(scope.row.img)" class="avatar">
        </template>
      </el-table-column>
<!--      <el-table-column label="商品类别" align="center" prop="mallType"/>-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:mall:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleCounp(scope.row)"
          >此商品优惠劵关联</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:mall:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-drawer
      title="商品优惠劵管理"
      :visible.sync="drawer"
      direction="rtl"
      >
<!--      :before-close="handleClose"-->
<!--      <el-select v-model="value1" multiple placeholder="选择添加的优惠劵">-->
<!--        <el-option-->
<!--          v-for="item in options"-->
<!--          :key="item.value"-->
<!--          :label="item.label"-->
<!--          :value="item.value">-->
<!--        </el-option>-->
<!--      </el-select>-->
      <el-popover
        placement="right"
        width="400"
        trigger="click">
        <!-- 未关联优惠劵列表 -->
        <el-table
          ref="multipleTable"
          :data="availableCouponList"
          tooltip-effect="dark"
          style="width: 100%"
          @selection-change="handleSelectionChangeCoupon">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column
            prop="couponTitle"
            label="优惠劵标题"
            width="180">
          </el-table-column>
          <el-table-column
            label="图片"
            width="150">
            <template slot-scope="scope">
              <img width="150px" v-if="scope.row.couponUrl" :src="getImage(scope.row.couponUrl)" class="avatar">
            </template>
          </el-table-column>
          <el-table-column
            prop="couponPrice"
            label="优惠价格">
          </el-table-column>
        </el-table>
        <el-button slot="reference" @click="associated">关联优惠劵</el-button>
        <el-button type="primary" round @click="insertCoupon">确定添加</el-button>

      </el-popover>

      <!-- 优惠劵显示表格 -->
      <el-table
        :data="mallCouponList"
        style="width: 100%">
        <el-table-column
          prop="tbCoupon.couponTitle"
          label="优惠劵标题"
          width="180">
        </el-table-column>
        <el-table-column
          label="图片"
          width="150">
          <template slot-scope="scope">
            <img width="150px" v-if="scope.row.tbCoupon.couponUrl" :src="getImage(scope.row.tbCoupon.couponUrl)" class="avatar">
          </template>
        </el-table-column>
        <el-table-column
          prop="tbCoupon.couponPrice"
          label="优惠价格">
        </el-table-column>
      </el-table>
    </el-drawer>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改商品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item v-if="open" label="选择店铺" prop="tbStoreId">
          <el-select v-model="form.tbStoreId" placeholder="请选择">
            <el-option
              v-for="item in storeList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input-number v-model="form.price" :min="1" :max="9999999" label="请输入价格"></el-input-number>
        </el-form-item>
        <el-form-item label="商品描述" prop="description">
          <el-input v-model="form.description" placeholder="请输入商品描述" />
        </el-form-item>
        <el-form-item label="商品主图" prop="img">
          <el-upload
            class="avatar-uploader"
            :action="uploadImgUrl"
            :headers="headers"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img width="100px" height="100px" v-if="form.img" :src="getImage(form.img)" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="商品详情图">
          <el-upload
            class="upload-demo"
            :action="uploadImgUrl"
            :file-list="tbMallImag"
            :headers="headers"
            :on-success="handleAvatarImgsSuccess"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            list-type="picture">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>

<!--        <el-form-item label="商品规格">-->


<!--&lt;!&ndash;            <el-cascader&ndash;&gt;-->
<!--&lt;!&ndash;              :show-all-levels="false"&ndash;&gt;-->
<!--&lt;!&ndash;              v-model="addSpecsValues"&ndash;&gt;-->
<!--&lt;!&ndash;              :options="mallSpecsList"&ndash;&gt;-->
<!--&lt;!&ndash;              :props="{ expandTrigger: 'hover' }"&ndash;&gt;-->
<!--&lt;!&ndash;              @change="handleChange"></el-cascader>&ndash;&gt;-->
<!--&lt;!&ndash;          <p v-for="(item, key) in form.listHashMap" :key="key">&ndash;&gt;-->
<!--&lt;!&ndash;              {{key}}&ndash;&gt;-->
<!--&lt;!&ndash;              <el-checkbox v-for="city in item" v-model="checkList" @change="handleCheckedCitiesChange"  checked :label="city.skuId">&ndash;&gt;-->
<!--&lt;!&ndash;&lt;!&ndash;                SKUID:{{city.skuId}}</br>&ndash;&gt;&ndash;&gt;-->
<!--&lt;!&ndash;                {{city.specsName}}&ndash;&gt;-->
<!--&lt;!&ndash;                库存：{{city.stock}}&ndash;&gt;-->
<!--&lt;!&ndash;              </el-checkbox>&ndash;&gt;-->
<!--&lt;!&ndash;              &nbsp;&ndash;&gt;-->
<!--&lt;!&ndash;            </p>&ndash;&gt;-->
<!--        </el-form-item>-->

        <el-form-item label="商品上下架" prop="status">
          <el-switch
            v-model="form.status"
            active-color="#13ce66"
            inactive-color="#ff4949"
            :active-value="1"
            :inactive-value="2"
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
import { insertMallCoupon,getIsNoMallCoupon,getMallCoupon,getMallSpecs,storeList,listMall, getMall, delMall, addMall, updateMall,getMallSku,updateMallSku,insertMallSku,getMallSpecsChilds } from "@/api/system/goods";
import { getToken } from "@/utils/auth";
export default {
  name: "Mall",
  data() {
    return {
      //添加优惠劵id数组
      couponIds:[],
      drawer: false,
      availableCouponList:[],
      // 遮罩层
      loading: true,
      // 商品优惠劵列表
      mallCouponList:[],
      valueStatus: true,
      // 选中数组
      ids: [],
      checkList:[],
      //商品规格SKU
      mallSkuList:[],
      // 非单个禁用
      single: true,
      checkedCities:[],
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 商品表格数据
      mallList: [],
      //记录添加的规格
      addSpecsValues:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      skuTitle:'规格属性编辑',
      //规格属性
      mallSpecsList:[],
      //店铺数据
      storeList:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tbStoreId: null,
        name: null,
        price: null,
        description: null,
        status: 1,
        img: null,
        mallType: null
      },
      // 表单参数
      form: {
        tbMallSkuList:[],
      },
      //商品详情图
      tbMallImag: [],

      // 表单校验
      rules: {
        tbStoreId: [
          { required: true, message: "店家id不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "商品名称不能为空", trigger: "blur" }
        ],

        price: [
          { required: true, message: "价格不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "商品上下架不能为空", trigger: "blur" }
        ],
        img: [
          { required: true, message: "图片不能为空", trigger: "blur" }
        ],
        mallType: [
          { required: true, message: "商品类别不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getStoreList();
  },
  methods: {
    handleSelectionChangeCoupon(val){
      this.couponIds = val.map(item => item.id)
    },
    //添加商品优惠劵
    insertCoupon(){
      let _this = this;
        insertMallCoupon(JSON.stringify(this.couponIds),this.form.id).then(response=>{
          this.$message({
            type: 'success',
            message: '关联成功!',
          });
          _this.handleCounp(_this.form);
        })
    },
    //获取可关联的优惠劵
    associated(){
      getIsNoMallCoupon(this.form.id).then(response=>{
        this.availableCouponList = response.data;
        console.log(this.availableCouponList)
      })
    },
    //获取商品优惠劵
    handleCounp(row){
      this.form = row;
      getMallCoupon(row.id).then(response=>{
        this.mallCouponList = response.data;
      })
      this.drawer = true;
    },
    /** 查询商品列表 */
    getList() {
      this.loading = true;
      listMall(this.queryParams).then(response => {
        this.mallList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 店铺*/
    getStoreList(){
      console.log("进");
      storeList().then(response=>{
        this.storeList = response.rows;
      })
    },
    getImage(img){
      return "https://app-1255978856.cos.ap-shanghai.myqcloud.com/"+img;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    handleAvatarSuccess(res, file) {
      this.form.img = res.fileName;
    },
    handleAvatarImgsSuccess(res,file,fileList){
      const imgs = {
        'name':file.name,
        'img':res.fileName,
        'url':this.getImage(res.fileName)
      }
      this.tbMallImag.push(imgs)
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
    //图片删除
    handleRemove(file, fileList) {
        this.tbMallImag = fileList;
    },
    handlePreview(file) {
      console.log(file);
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        tbStoreId: null,
        name: null,
        price: null,
        description: null,
        status: 0,
        createTime: null,
        updateTime: null,
        img: null,
        mallType: null
      };
      this.resetForm("form");
      this.tbMallImag=[]
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
      this.title = "添加商品";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      let _this = this;
      const id = row.id || this.ids
      getMall(id).then(response => {
        _this.form = response.data;
        _this.open = true;
        _this.title = "修改商品";
        //详情图显示
        _this.form.imgsList.forEach((mallImg=>{
          const imgs = {
            'name':mallImg.img,
            'id':mallImg.id,
            'url':this.getImage(mallImg.img),
            'img':mallImg.img,
          }
          _this.tbMallImag.push(imgs)
        }))
        //规格
        this.stepState(2,id);
      });

    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMall(JSON.stringify(this.form),JSON.stringify(this.tbMallImag)).then(response => {
              this.$message({
                type: 'success',
                message: '修改成功!',
              });
              this.open = false;
              this.getList();
            });
          } else {
            addMall(JSON.stringify(this.form),JSON.stringify(this.tbMallImag)).then(response => {
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

      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        },
      ).then(() => {
        //删除
        delMall(ids).then(response=>{
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
      this.download('system/mall/export', {
        ...this.queryParams
      }, `mall_${new Date().getTime()}.xlsx`)
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
