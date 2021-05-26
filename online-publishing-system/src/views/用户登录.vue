<template>
  <div class="Login">
    <div id="login">
      Username: <input id="username">
      Password: <input id="password">
    </div>
    <div v-if="$store.state.isLogin">
      <el-button v-on:click="logout" type="info">退出登录</el-button>
    </div>
    <div v-else>
      <el-button v-on:click="login" type="primary">登录</el-button>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    login() {
      this.$store.commit("login");
    },
    logout() {
      this.$store.commit("logout");
    },
  },
};
</script>