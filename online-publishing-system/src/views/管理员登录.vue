<template>
    <div class="管理员登录">
        <div class="admin-wrapper">
                <div class="admin-login">
                    <div class="login-center">
                        <form action="#!" method="post">
                            <div class="text-center">
                                <h1 style="margin:0">后台登录系统</h1>
                                <router-link to="/"> <img alt="home" src="../assets/logo.jpg"> </router-link>
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
html, body {
    height: 100%;
    width: 100%;
    margin: 0;
}
.管理员登录 {
    height: 100%;
    width: 100%;
    margin: 0;
    background:url('../assets/Canva - Green Leafed Plant.jpg');
    background-size:cover;
    background-position: top center;
}
.管理员登录 button {
    width: 100px;
    background: #A3D0C3;
}
.admin-wrapper {
    height: 100%;
    width: 100%;
}
.admin-login {
    display: flex;
    align-items: center;
    justify-content: center;
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
            let JsonAdmin = JSON.stringify(this.adminMess);
            console.log(JsonAdmin);
            /*this.$store.commit('adminLogin');
            sessionStorage.setItem("adminPassword",this.adminMess.password);
            window.location.href='../admin/home';*/
            this.$axios({
                method:'post',
                url:'http://82.156.190.251:80/apis/admin/login',
                data:JsonAdmin,
            })
            .then((res)=>{
                console.log(res);
                console.log(res.data.success);
                if (res.data.success == true) 
                    this.$store.commit('adminLogin')
                else this.$store.commit('adminLogout');
                if (sessionStorage.getItem("admin") == 'login') { 
                    sessionStorage.setItem("adminPassword",this.adminMess.password);
                    window.location.href='../admin/home'
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
            })
            .catch(error => console.log(error))
        }
    }
  }
</script>