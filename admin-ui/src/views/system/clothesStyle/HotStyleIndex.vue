<template>
  <div class="app-container">
    <el-steps :active="stepsStatus">
      <el-step title="请选择需要爆款的衣服模型"></el-step>
      <el-step title="爆款模型选配"></el-step>
      <el-step title="爆款详情编辑"></el-step>
    </el-steps>
    <el-row :gutter="20">
      <!-- 左侧版型小类（步骤1） -->
      <div v-show="stepsStatus==1?true:false">
        <el-form :model="modelStyleQueryParams" ref="queryForm" :inline="true" v-show="modelStyleshowSearch" label-width="68px">
          <el-form-item label="名称" prop="name">
            <el-input
              v-model="modelStyleQueryParams.name"
              placeholder="请输入名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="编号" prop="code">
            <el-input
              v-model="modelStyleQueryParams.code"
              placeholder="请输入编号"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="cyan" icon="el-icon-search" size="mini" @click="getList">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <el-table v-loading="modelStyleloading" :data="modelStyleList" @cell-dblclick="handleModelStyleChange">
          <el-table-column label="名称" align="center" prop="name" >
          </el-table-column>
          <el-table-column label="编号" align="center" prop="code" />
          <el-table-column label="图片" align="center" >
            <template scope="scope">
              <img v-if="scope.row.img" :src=" getBaseUrl() + scope.row.img" width="50px" height="60px">
              <img v-else="scope.row.img" :src="defaultImage" width="50px" height="60px">
            </template>
          </el-table-column>
        </el-table>
        <pagination
          v-show="modelStyletotal>0"
          :total="modelStyletotal"
          :page.sync="modelStyleQueryParams.pageNum"
          :limit.sync="modelStyleQueryParams.pageSize"
          @pagination="getList"
        />
      </div>
      <!-- （步骤2） -->
      <div  v-show="stepsStatus==2?true:false">
        <el-col :span="10" style="">
          <!-- 部件大类 -->
          <el-row :span="1" style="">
            <el-collapse class="el-col el-col-24 el-col-xs-24"  style="border: none!important; "  accordion>
              <el-collapse-item  style="margin-right: 0px!important;" >
                <template slot="title" style="position:relative" >
                  <p style="font-size: 16px!important;font-weight:bold!important;" >部件大类</p>
                </template>
                <!--swiper-->
                <div  class="swiper-container" >
                  <div class="swiper-wrapper" >
                    <div  @click="parts(data.id,index,data)" v-bind:class="{activeAll:index==isActiveAll}" class="swiper-slide" v-for="(data,index) in PlateClassList" style="width:221px; display: inline-block;"   >
                      <el-card   class="box-card" style="border: none;-webkit-box-shadow:none!important;padding-right: 0px!important;padding-left: 0px!important;">
                        <div slot="header" style="font-weight: bold;font-size: 13px;background:gray;width: 79%;margin: 0 auto;color: white;border-bottom:none!important;align-content: center" ><span>{{data.name}}</span></div>
                        <img  :src="getBaseUrl()+data.img" width="100px" height="100px">
                      </el-card>
                    </div>
                  </div>
                  <!-- 如果需要分页器 -->
                  <div class="swiper-pagination"></div>
                  <!-- 如果需要导航按钮 -->
                  <!--<div class="swiper-button-prev"></div>-->
                  <!--                    <div class="swiper-button-next"></div>-->
                </div>
              </el-collapse-item>

            </el-collapse>
          </el-row>
          <!-- 部件小类 -->
          <el-row>
            <p style="font-size: 16px!important;font-weight:bold!important;" >部件小类</p>
            <div class="el-col el-col-4 el-col-xs-4" style="margin-top: 20px;" v-for="(data,index) in WayList">
              <div style="margin: 0 auto;position: relative;">
                <img width="100px" height="100px" :src="getBaseUrl()+data.img" :id="data.id" >
                <input type="radio" name="numerical" :key="data.id"  @change="checkedBox($event,data.id,data.classId,data)" v-model="modelPojo.radio" :true-label="1" :false-label="0"  style="position: absolute;" :value="data.id" />
                <!--                <el-checkbox :key="data.id"  @change="checkedBox($event,data.id,data.classId)" v-model="data.indexCount" :true-label="1" :false-label="0"  style="position: absolute;"></el-checkbox>-->
              </div>
            </div>
          </el-row>
          <!-- 部件小块 -->
          <el-row>
            <p style="font-size: 16px!important;font-weight:bold!important;" >部件小块</p>
            <div class="el-col el-col-4 el-col-xs-4" style="margin-top: 20px;" v-for="(data,index) in ComponentsList">
              <div style="margin: 0 auto;position: relative;" @click="hanleComponen(data)">
                <img width="100px" height="100px" :src="getBaseUrl()+data.picture" :id="data.id" >
                <a  style="position:absolute;font-size: 18px;color: #1ab394"><i class="el-icon-circle-plus-outline"></i></a>
                <!--                <el-checkbox :key="data.id"  @change="checkedBox($event,data.id,data.classId)" v-model="data.indexCount" :true-label="1" :false-label="0"  style="position: absolute;"></el-checkbox>-->
              </div>
              <div style="display: inline-block;">
                <div v-for="(color,index) in Jsoncomponent" v-if="data.id==color.id">
                  <div v-if="color.outBottomFabric!=null">
                    <p style="font-size: 16px!important;font-weight:bold!important;" >
                      {{color.outBottomFabric[0].pattern[0].classf}}
                    </p>
                    <img width="100px" height="100px" :src="getBaseUrl()+color.outBottomFabric[0].pattern[0].path">
                  </div>

                  <div v-if="color.outPattern!=null">
                    <p style="font-size: 16px!important;font-weight:bold!important;" >
                      {{color.outPattern[0].pattern[0].classf}}
                    </p>
                    <img width="100px" height="100px" :src="getBaseUrl()+color.outPattern[0].pattern[0].path">
                  </div>

                  <div v-if="color.outUnfilled!=null">
                    <p style="font-size: 16px!important;font-weight:bold!important;" >
                      {{color.outUnfilled[0].pattern[0].classf}}
                    </p>
                    <img width="100px" height="100px" :src="getBaseUrl()+color.outUnfilled[0].pattern[0].path">
                  </div>
                </div>
              </div>
            </div>
          </el-row>
        </el-col>

        <el-radio-group v-show="false" v-model="direction">
          <el-radio label="rtl">从右往左开</el-radio>
        </el-radio-group>
        <!-- 部件面料 -->
        <el-drawer
          :title="pattenTitle"
          :visible.sync="visiblePattern"
          :direction="direction">
          <!-- 面料分类 -->
          <el-tabs>
            <el-tab-pane :name="key" v-for="(value,key) in ClassifyList" :key="key" :label="key" v-if="value.length>0">
              <vue-scroll :span="3" class="el-col el-col-3 el-col-xs-3"  style="background: #5a5e66;color: white;height:207px;">
                <p style="cursor:pointer;text-indent:15px" @click="handleClass(data)" v-bind:class="{active:index==isActive}"   v-for="(data,index) in value" >{{data.name}}</p>
              </vue-scroll>
            </el-tab-pane>
          </el-tabs>
          <!-- 面料 -->
          <el-table
            :data="PatternList"
            max-height="500"
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
                <img width="50px" slot="reference"  height="50px" v-if="scope.row.picture"  :src="getBaseUrl()+scope.row.picture">
              </template>
            </el-table-column>
          </el-table>
        </el-drawer>
        <!-- 右侧部件小类 -->
        <el-button type="success" @click="cancel(1)">上一步</el-button>
        <el-button type="success" @click="submint">下一步</el-button>
      </div>
      <!-- 步骤3 -->
      <div v-show="stepsStatus==3?true:false" style="width: 500px">
        <el-form ref="form" :model="form"  label-width="80px">
          <el-form-item label="爆款名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入爆款名称"/>
          </el-form-item>
          <el-form-item label="库存" prop="boardCount">
            <el-input-number v-model="form.boardCount":min="0" :max="9999" label="请输入库存"></el-input-number>
          </el-form-item>
          <el-form-item label="价格" prop="price">
            <el-input-number v-model="form.price":min="0" label="请输入价格"></el-input-number>
          </el-form-item>
          <el-form-item label="爆款类型" prop="clothesType">
            <el-select v-model="form.clothesType" placeholder="请选择">
              <el-option label="日爆款" value="1"></el-option>
              <el-option label="周爆款" value="2"></el-option>
              <el-option label="年爆款" value="3"></el-option>
            </el-select>
          </el-form-item>
<!--          <el-form-item label="衣服二维码" prop="qrCode">-->
<!--            <el-input v-model="form.qrCode" placeholder="请输入衣服二维码" />-->
<!--          </el-form-item>-->
          <el-form-item label="衣服图片" prop="image">
            <el-upload
              class="avatar-uploader"
              :action="uploadImgUrl"
              :headers="headers"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <img width="100px" height="100px" v-if="form.image!=null" :src="getImage(form.image)" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel(2)">上一步</el-button>
        </div>
      </div>
    </el-row>
  </div>
</template>

<script>
  import { listModelStyle  } from "@/api/system/modelStyle";
  import { listPlateClass  } from "@/api/system/plateClass";
  import { listWay } from "@/api/system/way";
  import { getClassifyByComponentId } from "@/api/system/classify";
  import { getComponents } from "@/api/system/material";
  import { getClassifyPictureById } from "@/api/system/componentPattern";
  import { addClothesStyle } from "@/api/system/clothesStyle";
  import { Swiper, Navigation } from 'swiper'
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";

  import { getToken } from "@/utils/auth";
  import fa from '../../../element-ui/src/locale/lang/fa'
  import { importTemplate } from '../../../api/system/user'
  export default {
    name:'HotStyleIndex',
    data(){
      return{
        // 非单个禁用
        uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
        headers: {
          Authorization: 'Bearer ' + getToken()
        },
        form: {
          boardCount:0,
          price:0,
        },
        //JSON模型
        clothes_json:[],
        //版型
        modelStyle:{},
        //部件大类
        plateClass:{},
        //部件小类
        way:{},
        //部件小块
        component:{},

        //存放模型选中的数据
        modelPojo:{
          radio:0,
        },

        colorType:["图案","","不可填充","面料"],
        colorstrType:["outPattern","","outUnfilled","outBottomFabric"],

        //面料图层是否可见
        visiblePattern:false,
        direction: 'rtl',
        //面料图层标题
        pattenTitle:"",

        //当前分类
        Classify:null,


        JsonmodelStyle:[],
        JsonplateClass:[],
        Jsonway:[],
        Jsoncomponent:[],

        //步骤条进度
        stepsStatus:1,

        //图案
        outPatternEntity:null,
        //不可填充
        outUnfilledEntity:null,
        //面料
        outBottomFabricEntity:null,

        isActiveAll:0,
        isActive:0,
        // 显示搜索条件
        modelStyleshowSearch: true,
        //版型小类
        modelStyleList:[],
        // 版型小类总条数
        modelStyletotal: 0,
        modelStyleloading:true,
        // 版型查询参数
        modelStyleQueryParams: {
          pageNum: 1,
          pageSize: 10,
          name: null,
          code: null,
          parentId: null,
          img: null
        },
        //部件大类
        PlateClassList:[],
        // 部件大类查询参数
        PlateClassQueryParams: {
          pageNum: 1,
          pageSize: null,
          name: null,
          code: null,
          parentId: null,
          img: null
        },
        //部件小类
        WayList:[],
        // 部件大类查询参数
        WayQueryParams: {
          pageNum: 1,
          pageSize: null,
          name: null,
          code: null,
          parentId: null,
          img: null
        },
        //部件小块
        ComponentsList:[],
        // 部件小块查询参数
        ComponentQueryParams: {
          pageNum: 1,
          pageSize: null,
          name: null,
          code: null,
          parentId: null,
          img: null
        },
        //面料分类
        ClassifyList:[],
        //面料颜色
        PatternList:[],
      }
    },
    mounted(){
      let mySwiper = new Swiper ('.swiper-container', {
        slidesPerView: 6,
        grabCursor:true,

        observer:true,
        // centeredSlides: true,
        autoplay: {
          delay: 2500,
          disableOnInteraction: false
        },
        loop: true,
        pagination: {
          el: '.swiper-pagination',
          clickable: true
        },
        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev'
        },
      })


      let mySwiperfour = new Swiper ('.swiper-container_four', {
        slidesPerView: 5,
        grabCursor:true,
        lazyLoading : true,
        observer:true,
        // centeredSlides: true,
        autoplay: {
          delay: 2500,
          disableOnInteraction: false
        },
        loop: true,
        pagination: {
          el: '.swiper-pagination',
          clickable: true
        },
        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev'
        },
      })

      let mySwipertwo = new Swiper ('.swiper-container_two', {
        slidesPerView: 5,
        grabCursor:true,

        observer:true,
        // centeredSlides: true,
        autoplay: {
          delay: 2500,
          disableOnInteraction: false
        },
        loop: true,
        pagination: {
          el: '.swiper-pagination',
          clickable: true
        },
        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev'
        },
      })

    },
    created(){
      this.getList();
    },
    methods:{
      //保存设计爆款
      submitForm(){
        addClothesStyle(this.form).then(response=>{
          this.$message({
            type: 'success',
            message: '添加成功!',
          });
          this.getList();
          this.cancel(1);
        }).catch(error=>{
          _this.dialogFormVisible = false;
          this.$message({
            type: 'info',
            message: '添加失败！'
          });
        })
      },
      //步骤
      cancel(steps){
        switch (steps) {
          case 1:
            this.empty();
            this.modelPojo.radio = 0;
            this.stepsStatus = 1;
            break;
          case 2:
            this.stepsStatus = 2;
            break;
          case 3:
            this.stepsStatus = 3;
            break;
        }
      },
      empty(){
        this.PlateClassList = [],
        this.WayList = [],
        this.ComponentsList = [],
        this.ClassifyList = [],
        this.PatternList = [],
        this.JsonmodelStyle=[],
        this.JsonplateClass=[],
        this.Jsonway=[],
        this.Jsoncomponent=[]
      },
      //图片上传
      handleAvatarSuccess(res,file,fileList){
        this.form.image = res.fileName;
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
      getImage(img){
        return "https://app-1255978856.cos.ap-shanghai.myqcloud.com/"+img;
      },
      //版型小类
      getList(){
        listModelStyle(this.modelStyleQueryParams).then(response=>{
          this.modelStyleList = response.rows;
          this.modelStyletotal = response.total;
          this.modelStyleloading = false;
          this.ComponentsList = [];
        })
      },
      //双击版型小类事件
      handleModelStyleChange(row, column, cell, event){
        this.getListPlateClass(row.id);
        this.modelStyle = row;

        this.stepsStatus = 2;
        this.form.modelstyleId = row.id;

        this.empty();
      },
      //版型小类参数重置
      resetQuery(){
        this.modelStyleQueryParams = {
          pageNum: 1,
          pageSize: null,
          name: null,
          code: null,
          parentId: null,
          img: null
        }
        this.getList();
      },
      //部件大类
      getListPlateClass(parentId){
        this.PlateClassQueryParams.parentId = parentId;
        listPlateClass(this.PlateClassQueryParams).then(response=>{
          this.PlateClassList = response.rows;

        })
      },
      //部件大类点击事件
      parts(parentId,index,val){
        this.plateClass = val;
        this.isActiveAll=index;
        this.WayQueryParams.parentId = parentId;
        //查询部件小类
        listWay(this.WayQueryParams).then(response=>{
          this.WayList = response.rows;
          this.ComponentsList = [];

          //部件小类已选中的值
          this.WayList.forEach(way=>{
            this.JsonplateClass.forEach(plaeclass=>{
              if(way.id==plaeclass.id){
                this.modelPojo.radio = way.id
                //查询部件小块
                getComponents(way.id).then(response=>{
                  this.ComponentsList = response.data;
                  this.ComponentsList.forEach(components=>{
                    this.Jsoncomponent.forEach(jsoncom=>{
                      if(components.id==jsoncom.id){
                        //面料颜色渲染
                        let color = jsoncom.outBottomFabric[0].pattern[0];
                        components.pattern = color;
                      }
                    })
                  })
                });
              }
            })
          })
        });

        console.log(this.Jsoncomponent);

        //版型大类数据处理
        if(this.JsonmodelStyle.length==0){
          const modelstyle = {
            "id":val.id,
            "parentId":val.parentId,
            "plateCutWay":[],
          }
          this.JsonmodelStyle.push(modelstyle)
        }else {

          var fign = true;
          this.JsonmodelStyle.forEach(item=>{
            if(item.id==val.id){
              fign = false;
            }
          })
          if(fign){
            const modelstyle = {
              "id":val.id,
              "parentId":val.parentId,
              "plateCutWay":[],
            }
            this.JsonmodelStyle.push(modelstyle)
          }
        }
      },
      //选中部件小类
      checkedBox(val,id,classId,entity){
        this.way = entity;
        //查询部件小块
        getComponents(id).then(response=>{
          this.ComponentsList = response.data;
        });
        if(this.JsonplateClass.length==0){
          const modelstyle = {
            "id":entity.id,
            "parentId":entity.parentId,
            "componentList":[],
          }
          this.JsonplateClass.push(modelstyle)
        }else {
          var fign = true;
          this.JsonplateClass.forEach(item=>{
            if(item.parentId==entity.parentId){
              item.id = entity.id
              item.parentId = entity.parentId
              fign = false;
            }
          })
          if(fign){
            const modelstyle = {
              "id":entity.id,
              "parentId":entity.parentId,
              "componentList":[],
            }
            this.JsonplateClass.push(modelstyle);
          }
        }

      },
      //部件小块事件
      hanleComponen(data){

        this.ClassifyList = [],
        this.PatternList = [],
        this.component = data;
        //当前部件小块下的所有分类
        getClassifyByComponentId(data.id,null).then(response=>{
          this.ClassifyList = response.data;
        })
        this.visiblePattern = true;
      },
      //部件小块颜色
      henlaPatten(type){
        //类型 1：图案 2.不可填充配饰 3.表层面料  4.底层面料  5.可填充配饰  6.内部 图案 7. 内部 不可填充 8.内部  表层面料 9.内部 底层面料',
        switch (type) {
          case 1:
            this.pattenTitle = "图案"
            break;
          case 2:
            this.pattenTitle = "不可填充配饰"
            break
          case 4:
            this.pattenTitle = "表层面料"
            break;
        }
      },
      //分类事件
      handleClass(val){
        this.Classify = val;
        //获取分类下的所有面料
        getClassifyPictureById(val.id).then(response=>{
          this.PatternList = response.data;
        })
      },
      //面料选择行
      handleCurrentChange(val) {
        this.outBottomFabricEntity = val;
        if(this.Jsoncomponent.length==0){
          this.addComponentColor(val,this.Classify.type-1);
        }else {
          let fign = true;
          this.colorReplace(val,this.Classify.type-1,(data)=>{
              fign =  data;
          });
          if(fign){
            this.addComponentColor(val,this.Classify.type-1);
          }
        }
      },
      //判断部件小块下是否有面料颜色  如果有就替换掉面料颜色   fign==部件小块下是否有面料颜色  val==当前颜色
      colorReplace(val,type,callback){
        this.Jsoncomponent.forEach(componentList=>{
          if(componentList.id==this.component.id){
            if(componentList.id==this.Classify.parentId){
              componentList.outBottomFabric = [];
              let classif =
                {
                  "id":this.Classify.id,
                  "parentId":this.component.id,
                  "pattern":[],
                }
              componentList.outBottomFabric.push(classif)
              //面料颜色
              if(classif.id==val.classId){
                let patternstyle = {
                  "id":this.outBottomFabricEntity.id,
                  "typeId":this.Classify.id,
                  "path":this.outBottomFabricEntity.picture,
                  "name":this.outBottomFabricEntity.name,
                  "classf":this.colorType[type],
                }
                classif.pattern.push(patternstyle);
              }
            }
            callback(false);
          }
        })
      },
      //ADD添加部件小块面料颜色 val==当前颜色  type==部件小块颜色类型  面料颜色  不可填颜色 图案
      addComponentColor(val,type){
        let componentList = {
          "id":this.component.id,
          "parentId":this.component.parentId,
        }
        this.$set(componentList, this.colorstrType[type], []); // 将moneyNum转换为Number类型
        this.Jsoncomponent.push(componentList);
        //分类
        if(componentList.id==this.Classify.parentId){
          let classif =
            {
              "id":this.Classify.id,
              "parentId":this.component.id,
              "pattern":[],
            }
          //判断当前保存的是那个分类 颜色
          switch (this.Classify.type) {
            case 1:
              componentList.outPattern.push(classif)
              break;
            case 2:
              componentList.outUnfilled.push(classif)
              break;
            case 4:
              componentList.outBottomFabric.push(classif)
              break;
          }
          //面料颜色
          if(classif.id==val.classId){
            let patternstyle = {
              "id":this.outBottomFabricEntity.id,
              "typeId":this.Classify.id,
              "path":this.outBottomFabricEntity.picture,
              "name":this.outBottomFabricEntity.name,
              "classf":this.colorType[type],
            }
            classif.pattern.push(patternstyle);
          }
        }
        console.log(JSON.stringify(this.Jsoncomponent))
      },

      //封装部件小块分类颜色JSON

      //提交模型参数  TODO 需做模型校验
      submint(){
        let json = JSON.stringify(this.JsonmodelStyle);
        this.getModelDate(json);


        this.stepsStatus = 3;
      },

      //处理设计的模型数据
      getModelDate(json){
        let jsonPlate = JSON.stringify(this.JsonplateClass);

        let s = JSON.parse(jsonPlate);
        s.forEach(plateclass=>{
          this.Jsoncomponent.forEach(comp=>{
            if(plateclass.id==comp.parentId){
              plateclass.componentList.push(comp)
            }
          })
        })
        let j= JSON.parse(json);
        j.forEach(modelstyle=>{
          s.forEach(plateclass=>{
            if(modelstyle.id==plateclass.parentId){
              modelstyle.plateCutWay.push(plateclass)
            }
          })
        })
        this.form.clothesJson = JSON.stringify(j);


      },
      getBaseUrl(){
        return process.env.VUE_APP_IMG_BASE_API;
      },
    }
  }
</script>

<style>
  /deep/.el-collapse-item__header{
    width: 12.5%!important;
    border-bottom:none;
  }
  /deep/.el-col-3{
    width: 12.5%!important;
  }

  .uploader-example {
    width: 90%;
    padding: 15px;
    margin: 40px auto 0;
    font-size: 12px;
    box-shadow: 0 0 10px rgba(0, 0, 0, .4);
  }

  .uploader-example .uploader-btn {
    margin-right: 4px;
  }

  .uploader-example .uploader-list {
    max-height: 440px;
    overflow: auto;
    overflow-x: hidden;
    overflow-y: auto;
  }

  .active{
    background: #1ab394;
  }

  .activeAll{
    border: 2px solid red;
  }

  .swiper-container {
    width: 100%;
    height: 100%;
  }
  .swiper-container_one{

    width: 100%;
    height: 100%;
  }

  swiper-container_two{
    width: 100%;
    height: 100%;
  }
  swiper-container_four{
    width: 100%;
    height: 100%;
  }
  /deep/.swiper-slide{
    width:229.6px;
  }


  /deep/ .el-checkbox__inner{
    border: 1px solid red;
  }

  /deep/ .el-dialog{
    float:right;
    min-height: 800px;
  }

  #innerLog /deep/ .el-dialog{
    float:none!important;
    min-height: 800px;
  }

  #innerLogFour /deep/ .el-dialog{
    float:none!important;
    width: 600px;
    min-height: 600px;
  }

  #innerLogThree /deep/ .el-dialog{
    float:none!important;
    min-height: 800px;
  }

  #innerLogTwo /deep/ .el-dialog{
    float:none!important;
    min-height: 400px;
  }

  #innerLogOne /deep/ .el-dialog{
    float:none!important;
    min-height: min-content;
  }


  /deep/.el-card__header{
    border:none;
  }
  .swiper-slide {
    text-align: center;
    font-size: 18px;

    /*background-color: rgb(128, 134, 132);*/

    /* Center slide text vertically */
    display: -webkit-box;
    display: -ms-flexbox;
    display: -webkit-flex;
    display: flex;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    -webkit-justify-content: center;
    justify-content: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    -webkit-align-items: center;
    align-items: center;
    /*transition: 300ms;*/
    /*transform: scale(0.8);*/
  }
  /*.swiper-slide-active,*/
  /*.swiper-slide-duplicate-active {*/
  /*  transform: scale(1);*/
  /*}*/

  .text {
    background-color: aquamarine;
    width: 400px;
    height: 400px;
  }
</style>
