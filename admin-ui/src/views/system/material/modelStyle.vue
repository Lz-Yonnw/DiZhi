<!--
<template>
  <div >
    &lt;!&ndash; 添加衣服 &ndash;&gt;
    <el-button type="primary" @click="dialogFormVisible = true">+添加衣服</el-button>

    <el-dialog title="添加衣服" :visible.sync="dialogFormVisible">

      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="衣服名称" prop="name">
          <el-input v-model="ruleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="衣服类型" prop="parentId">
          <el-select v-model="ruleForm.parentId" placeholder="请选择衣服的类型">
            <el-option label="卫衣" value="shanghai"></el-option>
            <el-option label="衬衫" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="衣服编号" prop="code">
          <el-input v-model="ruleForm.code"></el-input>
        </el-form-item>
        <el-form-item label="图片" prop="img">
          <el-input v-model="ruleForm.img"></el-input>
        </el-form-item>


        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>

    export default {
        name: "modelStyle.vue",
      data() {
        return {
          dialogFormVisible: false,
          ruleForm: {
            name: '',
            parentId: '',
            code: '',
            img: ''
          },
          rules: {
            name: [
              { required: true, message: '衣服的名称', trigger: 'blur' },
            ],
            parentId: [
              { required: true, message: '衣服类型', trigger: 'change' }
            ],

            code: [
              { required: true, message: '衣服编号', trigger: 'blur' }
            ],
            img: [
              { required: true, message: '衣服图片', trigger: 'blur' }
            ]
          }
        };
      },
      methods: {
        submitForm(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              alert('提交!');
            } else {
              console.log('error submit!!');
              return false;
            }
          });
        },
        resetForm(formName) {
          this.$refs[formName].resetFields();
        }
      }
    }
</script>

<style scoped>

</style>

<script>

  import {addModelStyle,listModelStyle } from "@/api/system/modelStyle";
</script>
-->
