<template>
  <div class="app-container">
    <el-steps :active="stepsStatus">
      <el-step title="请输入商品名称查询商品"></el-step>
      <el-step title="管理商品规格"></el-step>
    </el-steps>

    <!-- 商品查询 -->
    <div v-show="stepsStatus==1?true:false">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true"  label-width="68px">
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
      <el-table v-loading="loading" :data="mallList" >
        <el-table-column label="商品编号" align="center" prop="id" />
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
            <img width="60px" height="60px" v-if="scope.row.img" :src="getImage(scope.row.img)" class="avatar">
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="stepState(2,scope.row.id)"
              v-hasPermi="['system:mall:edit']"
            >查看SKU规格</el-button>
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
    </div>
    <!-- 商品规格 -->
    <div v-show="stepsStatus==2?true:false">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['system:mall:add']"
          >新增规格</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
      <el-table
        :data="mallSkuList"
        style="width: 100%">
        <el-table-column
          label="颜色        尺寸"
          width="100">
          <template slot-scope="scope">
            <span v-for="(val, key) in scope.row.specsValueIdJSon">{{val}} &nbsp;</span>
          </template>
        </el-table-column>


        <el-table-column
          label="价格"
          width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.price }}</span>
          </template>
        </el-table-column>

        <el-table-column
          label="库存"
          width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.amount }}</span>
          </template>
        </el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-button @click="stepState(1)">重新选择商品</el-button>
    </div>

    <!-- 添加and编辑规格 -->
    <el-dialog id="innerLogFour" :title="skuTitle" :visible.sync="openFour"  append-to-body>
      <el-form  :model="skuForm"  label-width="80px">
        <el-form-item  prop="specsValueIdJSon"  v-for="(val, key) in skuForm.specsValueIdJSon" :value="val" :label="indexInfo(key)">
            {{val}}
        </el-form-item>

        <div v-if="addAndModify=='add'">
          <el-form-item label="请选择规格" >
            <el-select v-model="specsForm.specsKey" placeholder="请选择规格" :label-in-value="true"  @change="getspecKey">
              <el-option v-for="specs in specsKeyList" :value="specs.specsName">{{specs.specsName}}</el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="请选择规格属性">
            <el-select v-model="specsForm.specsValue" placeholder="请选择规格名称" :label-in-value="true"  @change="getspecsValue">
              <el-option v-for="specs in specsVeluKeyList" :value="specs.specsName">{{specs.specsName}}</el-option>
            </el-select>
          </el-form-item>


          <el-form-item label="当前已选择的规格">
            <span v-for="(val,key) in specsKeyValueMapName" :key="key">
              {{val[0]}} - {{val[1]}} &nbsp;&nbsp;&nbsp;&nbsp;
            </span>
          </el-form-item>
        </div>



        <el-form-item label="价格 " prop="price">
          <el-input-number v-model="skuForm.price"  :min="1" :max="99999"></el-input-number>
        </el-form-item>

        <el-form-item label="库存 " prop="amount">
          <el-input-number v-model="skuForm.amount"  :min="1" :max="99999"></el-input-number>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormFour">确 定</el-button>
        <el-button @click="cancelFour">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import { insertMallSku,delTbMallSku,getMallSpecsChilds,getMallSkuById,updateMallSku,getMallSku,listMall } from "@/api/system/goods";
  import { getToken } from "@/utils/auth";
  import fa from '../../../../element-ui/src/locale/lang/fa'
  import ca from '../../../../element-ui/src/locale/lang/ca'
  export default {
    name: "specsMall",
    data() {
      return {
        //表单 标题
        skuTitle:'规格属性编辑',
        //规格
        skuForm:{
          price:0,
          amount:0,
        },
        specsKeyValueMapId:null,
        specsKeyValueMapName:null,
        specsForm:{
          specsKey:null,
          specsValue:null,

          specsKeyId:null,
          specsValueId:null,
        },
        openFour:false,
        //修改添加
        addAndModify:"",
        //步骤条进度
        stepsStatus:1,
        // 遮罩层
        loading: true,
        valueStatus: true,
        // 选中数组
        ids: [],
        checkList:[],
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
        //商品规格SKU
        mallSkuList:[],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
        headers: {
          Authorization: 'Bearer ' + getToken()
        },
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
        //SKU key
        specsKeyList:[],
        specsVeluKeyList:[],
        specsValueKeyValueList:[],
        mallId:0,
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 添加规格*/
      handleAdd(){
        this.reset();
        this.openFour = true;
        this.title = "添加商品";
        this.addAndModify = "add";
        this.specsKeyValueMapId = new Map();
        this.specsKeyValueMapName = new Map();
        //联级规格属性查询
        getMallSpecsChilds(0).then(response=>{
          this.specsKeyList = response.data;
        })
      },
      //属性key下拉列表事件
      getspecKey(val){
        let specsId = null;
         this.specsKeyList.find((item)=>{
           if(item.specsName==val){
             specsId = item.id;
           }
          //筛选出匹配数据，是对应数据的整个对象
        });
        //联级规格属性查询
        if(specsId!=null){
          getMallSpecsChilds(specsId).then(response=>{
            this.specsVeluKeyList = response.data;
          })
        }
        this.specsForm.specsKeyId = specsId;
        this.specsForm.specsKey = val;
        this.specsForm.specsValue = "";
      },
      //属性Value下拉列表事件
      getspecsValue(val){
        let specsId = null;
        this.specsVeluKeyList.find((item)=>{
          if(item.specsName==val){
            specsId = item.id;
          }
        });
        this.specsForm.specsValueId = specsId;
        this.specsKeyValueMapId.set(this.specsForm.specsKeyId,specsId);
        this.specsKeyValueMapName.set(this.specsForm.specsKey,val);

        console.log(this.specsForm.specsKey,val)

      },
      //删除SKu 属性
      handleDelete(row){
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          },
        ).then(() => {
          //删除
          delTbMallSku(row.id).then(response=>{
            this.$message({
              type: 'success',
              message: '删除成功!',
            });
            this.stepState(2,this.skuForm.mallId)
            this.openFour = false;
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });

      },
      indexInfo: function (value) {
        return value.toString();
      },
      //编辑事件
      handleEdit(index,row){
        let _this = this;
        getMallSkuById(row.id).then(response=>{
          _this.skuForm = response.data;
          _this.skuForm.specsValueIdJSon = JSON.parse(_this.skuForm.specsValueIdJSon);
          _this.addAndModify = 'modify'
        })

        this.openFour = true;
      },
      /** 保存and修改*/
      submitFormFour(){
        //修改
        if(this.addAndModify=='modify'){
          this.skuForm.specsValueIdJSon = JSON.stringify(this.skuForm.specsValueIdJSon);
          updateMallSku(this.skuForm).then(response=>{
            this.$message({
              type: 'success',
              message: '修改成功!',
            });
          })
        }else if(this.addAndModify=='add'){
            this.skuForm.mallId = this.mallId;
            this.skuForm.specsData = '';

            if(!(this.specsKeyValueMapId.size>=2)){
              console.log(this.specsKeyValueMapId)
              this.$message({
                type: 'error',
                message: '请选择规格，再保存！',
              });
              return false;
            };
            this.specsKeyValueMapId.forEach((value,key)=>{
              this.skuForm.specsData += key+"-"+value+",";
            })
            insertMallSku(this.skuForm).then(response=>{
              this.$message({
                type: 'success',
                message: '添加成功!',
              });
            })
        }
        this.stepState(2,this.skuForm.mallId)
        this.openFour = false;
      },
      //取消
      cancelFour(){
        this.openFour = false;
        this.skuForm = {};
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
      getImage(img){
        return "https://app-1255978856.cos.ap-shanghai.myqcloud.com/"+img;
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      //查看商品SKU规格
      stepState(status,mallId){
        this.mallId = mallId;
        this.stepsStatus = status;
        //获取商品SKU
        if(status==2){
          getMallSku(mallId).then(response=>{
            this.mallSkuList = response.rows;
            this.mallSkuList.forEach(item=>{
              item.specsValueIdJSon = JSON.parse(item.specsValueIdJSon)
            })
          })
        }
      },
      // 表单重置
      reset() {
        this.skuForm = {
          price:0,
          amount:0
        },
          this.specsForm = {
            specsKey:null,
            specsValue:null,

            specsKeyId:null,
            specsValueId:null,
          },
          this.specsKeyList = {},
          this.specsVeluKeyList = {}
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
  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
</style>
