<template>
  <div>
    <div class="breadcrumb">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin/home' }">后台首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/accounts' }">查看账号</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/accounts/add' }">添加账号</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="add">
        <el-form ref="form" :model="accountForm" label-width="80px">
            <el-form-item label="邮箱" prop="email" >
                <el-input v-model="accountForm.email"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input type="password" v-model="accountForm.pass"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button plain @click="submitForm('accountForm')">创建</el-button>
            </el-form-item>
        </el-form>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  methods: {
    submitForm(){
      let accountdata = JSON.stringify(this.accountForm);
      this.$axios({
          method:"post",
          url:'xxx',
          data:accountdata,
      })
      .then((res)=>{
          console.log(res);
          if (res.success == true) {
            this.$message({
              showClose: true,
              message: '添加账号成功',
              type: 'success'
            });
          }
          else {
            this.$message({
              showClose: true,
              message: res.message,
              type: 'error'
            });
          }
      })
      .catch(error => console.log(error))
    }
  },
  data() {
    return {
      accountForm: {
          email: '',
          password: '',
      },
    }
  }
}
</script>