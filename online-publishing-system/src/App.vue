<template>
  <div id="app">
    <div id="head">
      <h1>欢迎使用网上出版系统</h1>
    </div>
    <div id="nav">
      <router-link to="/home">个人信息</router-link> |
      <router-link to="/author">作者主页</router-link> |
      <router-link to="/reviewer">审稿人主页</router-link> |
      <router-link to="/editor">编辑主页</router-link> |
      <img alt="search logo" src="./assets/search.jpg" width="20" height="20"><input type="text"> 搜索 
      <router-link to="/">Home</router-link> |
      <div v-if="$store.state.isLogin">
        <router-link to="/login">Login</router-link> |
      </div>
      <div v-else>
        <router-link to="/login">Logout</router-link> |
      </div>
      <router-link to="/register">Register</router-link>
    </div>
    <router-view/>
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
