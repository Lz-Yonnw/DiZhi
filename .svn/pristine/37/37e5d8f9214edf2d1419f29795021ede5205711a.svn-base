<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="爆款名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入爆款名称"
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
<!--      <el-form-item label="衣服二维码" prop="qrCode">-->
<!--        <el-input-->
<!--          v-model="queryParams.qrCode"-->
<!--          placeholder="请输入衣服二维码"-->
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
      <el-form-item label="爆款类型" prop="clothesType">
        <el-select v-model="queryParams.clothesType" placeholder="请选择爆款类型" clearable size="small">
          <el-option
            v-for="dict in clothesTypeOptions"
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
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:clothesStyle:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:clothesStyle:remove']"
        >删除</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="clothesStyleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="爆款名称" align="center" prop="name" />
      <el-table-column label="爆款类型" align="center" prop="clothesType" :formatter="clothesTypeFormat" />
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
      <el-table-column label="库存" align="center" prop="boardCount" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:clothesStyle:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:clothesStyle:remove']"
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

    <!-- 添加或修改爆款列表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="爆款名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入爆款名称" />
        </el-form-item>
        <el-form-item label="爆款类型" prop="clothesType">
          <el-select v-model="form.clothesType" placeholder="请选择爆款类型">
            <el-option
              v-for="dict in clothesTypeOptions"
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
import { listClothesStyle, getClothesStyle, delClothesStyle, addClothesStyle, updateClothesStyle, exportClothesStyle } from "@/api/system/clothesStyle";
import { formatDate } from "@/api/system/date";
export default {
  name: "ClothesStyle",
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
      // 爆款列表表格数据
      clothesStyleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 1：日度爆款  2：周度爆款  3：年度爆款字典
      clothesTypeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        clothesJson: null,
        qrCode: null,
        image: null,
        clothesType: null
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
    this.getDicts("clothes_type").then(response => {
      this.clothesTypeOptions = response.data;
    });
  },
  methods: {
    getBaseUrl(){
      console.log(process.env.VUE_APP_IMG_API);
      return process.env.VUE_APP_IMG_BASE_API;
    },
    /** 查询爆款列表列表 */
    getList() {
      this.loading = true;
      listClothesStyle(this.queryParams).then(response => {
        this.clothesStyleList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    downLoad(url) {
      var a = document.createElement('a')
      let picName=formatDate();
      a.download = name || picName
      // 设置图片地址
      a.href =url;
      a.click();

    },
    // 1：日度爆款  2：周度爆款  3：年度爆款字典翻译
    clothesTypeFormat(row, column) {
      return this.selectDictLabel(this.clothesTypeOptions, row.clothesType);
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
      this.title = "添加爆款列表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getClothesStyle(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改爆款列表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateClothesStyle(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addClothesStyle(this.form).then(response => {
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
      this.$confirm('是否确认删除爆款列表编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delClothesStyle(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有爆款列表数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportClothesStyle(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
