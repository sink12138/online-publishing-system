<template>
  <div class="home">
      <div id="nav">
        <router-link to="/home">个人信息</router-link> |
        <router-link to="/author">作者主页</router-link> |
        <router-link to="/reviewer">审稿人主页</router-link> |
        <router-link to="/editor">编辑主页</router-link> |
        <input type="text" style="height:21.5px;width:400px">
        <router-link to="/search"><el-button type="primary" icon="el-icon-search" size="mini">搜索</el-button></router-link> |                
        <router-link to="/register">Register</router-link> |
        <router-link to="/login">Login</router-link>
      </div>
      <router-view/>
    <HelloWorld msg="?"/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'Home',
  components: {
    HelloWorld
  }
}
</script>
