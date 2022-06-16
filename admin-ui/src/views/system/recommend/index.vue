<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="衣服名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入衣服名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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

    <el-table v-loading="loading" :data="recommendList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
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
        <el-form-item label="衣服名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入衣服名称" />
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
import { listRecommend, getRecommend, delRecommend, addRecommend, updateRecommend, exportRecommend } from "@/api/system/recommend";
import { formatDate } from "@/api/system/date";
export default {
  name: "Recommend",
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
      // 设计推荐表格数据
      recommendList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        clothesJson: null,
        qrCode: null,
        image: null,
        boardCount: null
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
    /** 查询设计推荐列表 */
    getList() {
      this.loading = true;
      listRecommend(this.queryParams).then(response => {
        this.recommendList = response.rows;
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
        boardCount: null
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
      this.title = "添加设计推荐";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRecommend(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改设计推荐";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRecommend(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addRecommend(this.form).then(response => {
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
      this.$confirm('是否确认删除设计推荐编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delRecommend(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有设计推荐数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportRecommend(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
