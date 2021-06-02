<template>
  <el-form :inline="true" :model="formInline" class="demo-form-inline">
  <el-form-item label="待认证审稿人的账号">
    <el-input v-model="formInline.accountId" placeholder="待认证审稿人的账号"></el-input>
  </el-form-item>
  <el-form-item label="审稿人所属组织">
    <el-input v-model="formInline.organization" placeholder="审稿人所属组织"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="CertifySuccess">确定</el-button>
  </el-form-item>
  <el-form-item>
    <el-button type="info" @click="Cancel">取消</el-button>
  </el-form-item>
</el-form>
</template>

<script>
  export default {
    data() {
      return {
        formInline: {
          accountId: '',
          organization: ''
        }
      }
    },
    methods: {
      CertifySuccess() {
        alert('认证成功！');
      },
      Cancel() {
        alert('认证取消！');
      }
    }
  }
</script>