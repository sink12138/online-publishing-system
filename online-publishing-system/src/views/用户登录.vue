<template>
  <div class="Login">
    <div class="head">
      <h1>登录页面</h1>
      <br />
    </div>
    <div id="login">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="Email">
          <el-input v-model="formInline.email" placeholder="Email"></el-input>
        </el-form-item>
        <el-form-item label="Password">
          <el-input
            v-model="formInline.password"
            prefix-icon="el-icon-lock"
            show-password
          ></el-input>
        </el-form-item>
        <div v-if="this.$store.state.isLogin == true">
          <el-form-item>
            <router-link to="/"
              ><el-button type="info" @click="Logout"
                >退出登录</el-button
              ></router-link
            >
          </el-form-item>
          <el-form-item>
            <router-linkF to="/"
              ><el-button type="info">返回主页</el-button></router-linkF
            >
          </el-form-item>
        </div>
        <div v-else>
          <el-form-item>
            <router-link to="/"
              ><el-button type="primary" @click="Login"
                >登录</el-button
              ></router-link
            >
          </el-form-item>
          <el-form-item>
            <router-link to="/"
              ><el-button type="info">返回主页</el-button></router-link
            >
          </el-form-item>
        </div>
      </el-form>
    </div>
  </div>
</template>

<style>
.Login {
  background-color: #fff;
}
</style>

<script>
export default {
  data() {
    return {
      formInline: {
        email: "",
        password: "",
      },
      success: false,
      role: 0,
    };
  },
  mounted: function () {
    console.log("登录情况", this.$store.state.isLogin);
    if (sessionStorage.getItem("isLogin") == undefined)
      sessionStorage.setItem("isLogin", false);
    if (sessionStorage.getItem("role") == undefined)
      sessionStorage.setItem("role", 0);
    if (sessionStorage.getItem("isLogin") == true) {
      this.$store.commit("login");
      console.log("登录更新");
    } else if (sessionStorage.getItem("isLogin") == false) {
      this.$store.commit("logout");
      sessionStorage.setItem("role", 0);
    }
    if (
      sessionStorage.getItem("role") == 1 ||
      sessionStorage.getItem("role") == 3 ||
      sessionStorage.getItem("role") == 5 ||
      sessionStorage.getItem("role") == 7
    )
      this.$store.commit("setEditor");
    if (
      sessionStorage.getItem("role") == 2 ||
      sessionStorage.getItem("role") == 3 ||
      sessionStorage.getItem("role") == 6 ||
      sessionStorage.getItem("role") == 7
    )
      this.$store.commit("setReviewer");
    if (sessionStorage.getItem("role") >= 4) this.$store.commit("setWriter");
  },
  methods: {
    Login() {
      this.$axios({
        method: "post",
        url: "http://82.156.190.251:80/apis/login",
        data: JSON.stringify(this.formInline),
      }).then((res) => {
        if (res.data.success == true) {
          this.role = res.data.role;
          sessionStorage.setItem("role", this.role);
          sessionStorage.setItem("isLogin", true);
          if (
            this.role == 1 ||
            this.role == 3 ||
            this.role == 5 ||
            this.role == 7
          )
            this.$store.commit("setEditor");
          if (
            this.role == 2 ||
            this.role == 3 ||
            this.role == 6 ||
            this.role == 7
          )
            this.$store.commit("setReviewer");
          if (this.role >= 4) this.$store.commit("setWriter");
          console.log(this.formInline);
          console.log("登录页面用户角色", this.$store.state.role);
          this.$store.commit("login");
          this.$message({
            message: "登录成功！",
            type: "success",
          });
          sessionStorage.setItem("email", this.formInline.email);
          sessionStorage.setItem("password", this.formInline.password);
        } else {
          alert(res.data.message);
        }
        console.log(res);
      });
    },
    Logout() {
      sessionStorage.setItem("role", 0);
      sessionStorage.setItem("isLogin", false);
      this.$axios({
        method: "post",
        url: "http://82.156.190.251:80/apis/logout",
      }).then((res) => {
        console.log(res);
      });
      console.log("logout submit!");
      this.$store.commit("logout");
      this.$message({
        message: "退出登录成功！",
      });
    },
  },
};
</script>
