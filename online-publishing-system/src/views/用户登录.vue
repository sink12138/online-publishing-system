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
            placeholder="Password"
          ></el-input>
        </el-form-item>
        <div v-if="$store.state.isLogin">
          <el-form-item>
            <router-link to="/"
              ><el-button type="info" @click="Logout"
                >退出登录</el-button
              ></router-link
            >
          </el-form-item>
          <el-form-item>
            <router-linkF to="/"
              ><el-button type="info"
                >返回主页</el-button
              ></router-linkF
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
              ><el-button type="info"
                >返回主页</el-button
              ></router-link
            >
          </el-form-item>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>

const axios = require("axios");
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
  methods: {
    Login() {
      axios({
        Headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        method: "post",
        url: "/logout",
        data: JSON.stringify(this.formInline),
      }).then((res) => {
        this.role=res.role;
        if(this.role % 2 == 1) this.$store.commit('setEditor');
        if(this.role == 2 || this.role == 3 || this.role == 6 || this.role == 7) this.$store.commit('setReviewer');
        if(this.role >= 4) this.$store.commit('setWriter');
        console.log(res);
      });
      console.log(this.formInline);
      console.log("submit!");
      this.$store.commit("login");
      this.$message({
        message: "登录成功！",
        type: "success",
      });
    },
    Logout() {
      axios({
        Headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        method: "post",
        url: "/logout",
      }).then((res) => {
        console.log(res);
      });
      console.log("submit!");
      this.$store.commit("logout");
      this.$message({
        message: "退出登录成功！",
      });
    },
  },
};
</script>