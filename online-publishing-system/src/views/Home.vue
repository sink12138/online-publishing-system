<template>
  <div class="home">
      <div id="nav">
        <el-menu
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect"
          router
          background-color="#d5f6ee"
          text-color="#000"
          active-text-color="#ffd04b"
          style="text-align: center">
          <el-menu-item index="/home">个人信息</el-menu-item>
          <el-menu-item index="/author">作者主页</el-menu-item>
          <el-menu-item index="/reviewer">审稿人主页</el-menu-item>
          <el-menu-item index="/editor">编辑主页</el-menu-item>
          <el-menu-item index="/register">Register</el-menu-item>
          <el-menu-item index="/login">Login</el-menu-item>
          <el-menu-item index="/admin/home">Go to Admin</el-menu-item>
        </el-menu>
        <div class="search bar">
          <form>
            <input type="text" placeholder="请输入要搜索的文章...">
            <router-link to="/search">
              <el-button type="primary" icon="el-icon-search" size="mini">
              </el-button>
            </router-link>
          </form>
        </div>
        <div id="article-shows" class="article-shows">
          <div v-for="(value,name) in object" v-bind:key="name">
            <h3>{{ name }}: {{ value }}</h3>
          </div>
          <!--<el-carousel indicator-position="outside">
            <el-carousel-item v-for='item in 4' :key='item'>
              // eslint-disable-next-line vue/require-v-for-key
              <div v-for="(value,name) in object" v-bind:key="name">
                <h3>{{ name }}: {{ value }}</h3>
              </div>
            </el-carousel-item>
          </el-carousel>-->
        </div>
      </div>
      <router-view/>
  </div>
</template>

<style>
body {
  text-align: center;
  margin: 0;
  padding: 0;
  background-image:url("../assets/Canva - Water Mill Near Body of Water.jpg");
  background-size: cover;
  font-weight: 600;
  font-family: "Microsoft YaHei","宋体","Segoe UI", "Lucida Grande", Helvetica, Arial,sans-serif, FreeSans, Arimo;
}
.home .el-menu {
  display: flex;
  padding: 0;
  font-weight: 800;
  font-family: "Microsoft YaHei","Operator Mono";
  padding-left: 120px !important;
  opacity: 0.8;
}
.home .el-menu-item {
  padding-left: 40px !important;
  padding-right: 40px !important;
}
.search {
  padding: 30px 0;
  opacity: 0.8;
}
.search input, button {
    border: none;
    outline: none;
}
.search input {
    width: 80%;
    height: 60px;
    padding-left: 13px;
}
.search button {
    height: 66px;
    width: 60px;
    cursor: pointer;
    position: absolute;
}
.search form {
  position: relative;
  width: 340px;
  margin: 0 auto;
}
.bar {background: #A3D0C3;}
.bar input {
  border: 2px solid #7BA7AB;
  border-radius: 5px;
  background: #d5f6ee;
  color: #9E9C9C;
}
.bar button {
  top: 0;
  right: 0;
  background: #7BA7AB;
  border-radius: 0 5px 5px 0;
}
.article-shows{
  background: #A3D0C3;
  opacity: 0.8;
}
</style>

<script>
  export default {
    data() {
      return {
        activeIndex: '',
      };
    },
    methods: {
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
      }
    }
  }
  /*new Vue({
    id:"#article-shows",
    data: {
      Object: {
        Title: 'Developmental bifurcation of human T follicular regulatory cells',
        Author: 'Saumya Kumar',
        Date: '28 May 2021',
        Source: 'Science Immunology: Vol. 6, Issue 59'
      }
    }
  })*/
</script>