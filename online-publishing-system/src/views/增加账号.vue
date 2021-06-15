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
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>添加账号</span>
        </div>
        <el-form ref="form" :model="accountForm" label-width="80px">
            <el-form-item label="邮箱" prop="email" >
              <el-input v-model="accountForm.email"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input type="password" v-model="accountForm.password"></el-input>
            </el-form-item>
            <el-form-item label="真实姓名" prop="readname">
              <el-input v-model="accountForm.realname"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button plain @click="submitForm('accountForm')">创建</el-button>
            </el-form-item>
        </el-form>
      </el-card>
    </div>
    <router-view></router-view>
  </div>
</template>

<style>
.add {
  display: flex;
  position: relative;
  margin: 0 auto;
  text-align: center;
  width: 600px;
  height: 600px;
  align-items: center;
  justify-content: center;
}
.box-card {
  background-color: #A3D0C3;
}
.box-card input {
  background-color: rgb(210, 245, 233);
}
</style>

<script>
export default {
  methods: {
    submitForm(){
      let JsonAccount = JSON.stringify(this.accountForm);
      this.$axios({
          method:"post",
          url:'http://82.156.190.251:80/apis/admin/insert/account',
          data:JsonAccount,
      })
      .then((res)=>{
          console.log(res);
          if (res.data.success == true) {
            this.$message({
              showClose: true,
              message: '添加账号成功',
              type: 'success'
            });
          }
          else {
            this.$message({
              showClose: true,
              message: res.data.message,
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
          realname: '',
      },
    }
  }
}
</script>