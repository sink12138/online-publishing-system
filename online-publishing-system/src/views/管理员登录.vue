<template>
    <div class="管理员登录">
        <div class="admin-bg">
            <img src="../assets/adminbg.jpg" alt="bg" />
        </div>
        <div class="admin-wrapper">
                <div class="admin-login">
                    <div class="login-center">
                        <form action="#!" method="post">
                            <div class="text-center">
                                <h1>后台登录系统</h1>
                                <router-link to="/home"> <img alt="home" src="../assets/logo.jpg"> </router-link>
                            </div>
                            <div class="login-text">
                                <el-input
                                    placeholder="请输入用户名"
                                    prefix-icon="el-icon-user"
                                    v-model="adminMess.username">
                                </el-input>
                            </div>
                            <div class="login-text">
                                <el-input
                                    placeholder="请输入密码"
                                    prefix-icon="el-icon-lock"
                                    v-model="adminMess.password"
                                    show-password>
                                </el-input>
                            </div>
                            <el-button v-on:click=adminLogin type="success" round icon="el-icon-right">
                                登录
                            </el-button>
                        </form>
                    </div>
                </div>
        </div>
        <router-view/>
    </div>
</template>

<style>
.admin-bg{
    position: absolute;
    height: 100%;
    width: 100%;
    z-index: -1;
}
.admin-wrapper {
    position: absolute;
    height: 100%;
    width: 100%;
    z-index: 0;
}
.admin-login {
    display: flex !important;
    align-items: center !important;
    justify-content: center !important;
}
.login-center {
    background: #fff;
    min-width: 38.25rem;
    padding: 2.14286em 3.57143em;
    border-radius: 5px;
    margin: 2.85714em 0;
}
.login-header {
    margin-bottom: 1.5rem !important;
}
.login-text {
    padding-left: 33%;
    padding-right: 33%;
    padding-bottom: 15px;
}
</style>

<script>
  //const axios = require('axios');
  export default {
    data() {
      return {
          adminMess:{
            "username":"",
            "password":""
          }
      }
    },
    methods:{
        adminLogin(){
            let admindata = JSON.stringify(this.adminMess);
            this.$axios({
                method:"post",
                url:'xxx',
                data:admindata,
            })
            .then((res)=>{
                console.log(res);
            })
            this.$store.commit('adminLogin');
            if (this.$store.state.adminLogin) {
                window.location.href='../admin';
            }
            else {
                this.$alert('请重试', '密码错误', {
                    confirmButtonText: '确定',
                }).then(() => {
                    this.$message({
                        type:'info',
                        message:'请重试'
                    });
                });
            }
            /*let admindata = JSON.stringify(this.adminMess);
            axios({
                method:"post",
                url:"xxx",
                data:admindata
            })
            .then((res)=>{
                console.log(res);
                var result = true;
                if (result) {
                    this.$store.commit('adminLogin');
                    if (this.$store.state.adminLogin) {
                        window.location.href='../admin';
                    }
                }
                else {
                    this.$alert('请重试', '密码错误', {
                        confirmButtonText: '确定',
                    }).then(() => {
                        this.$message({
                            type:'info',
                            message:'请重试'
                        });
                    });
                }
            });*/
        }
    }
  }
</script>