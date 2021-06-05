<template>
  <div class="home">
      <!--<div id="nav">
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
          <el-carousel indicator-position="outside">
            <el-carousel-item v-for='item in 4' :key='item'>
              // eslint-disable-next-line vue/require-v-for-key
              <div v-for="(value,name) in object" v-bind:key="name">
                <h3>{{ name }}: {{ value }}</h3>
              </div>
            </el-carousel-item>
          </el-carousel>
        </div>
      </div>
      <router-view/>-->
    <div class="head">
      <h1>OPS</h1>
    </div>
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
        <el-menu-item index="/home" v-if="$store.state.isLogin">个人信息</el-menu-item>
        <el-menu-item index="/author" v-if="$store.state.role >= 4">作者主页</el-menu-item>
        <el-menu-item index="/reviewer" v-if="$store.state.role == 2 || $store.state.role == 3 || $store.state.role == 6 || $store.state.role == 7">审稿人主页</el-menu-item>
        <el-menu-item index="/editor" v-if="$store.state.role % 2 == 1">编辑主页</el-menu-item>
        <el-menu-item index="/register">Register</el-menu-item>
        <el-menu-item index="/login">Login</el-menu-item>
        <el-menu-item index="/admin/home">Go to Admin</el-menu-item>
      </el-menu>
      <div class="search">
        <el-input v-model="search.searchString" size="large">
          <el-select v-model="search.searchType" slot="prepend" placeholder="搜索类型">
            <el-option label="标题" value="title"></el-option>
            <el-option label="关键词" value="keyword"></el-option>
            <el-option label="作者" value="author"></el-option>
          </el-select>
          <el-button
          slot="append"
          icon="el-icon-search"
          size="mini"
          @click="searchArticle">
          </el-button>
        </el-input>
      </div>
      <router-view />
      <child title="搜索结果" :parent-fun="parentFun" :parent="this"></child>
    </div>
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
  background: #A3D0C3;
  position: relative;
  padding: 30px 0;
  opacity: 0.8;
}
.search input {
  font-size: 20px;
}
.search .el-input {
  width: 600px;
}
.el-select .el-input {
  width: 120px;
}
.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
</style>

<script>
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
import child from "../components/搜索.vue";
export default {
  name: "Search",
  components: {
    child,
  },
  data() {
    return {
      search: {
        searchType: "",
        searchString: "",
      },
      currentPage: 1, //当前页数
      pageSize: 10, //每页获取条数（页面大小）
      table: [], //存放从后端传来的数据
    };
  },
  mounted() {},
  methods: {
    searchArticle() {
      this.$axios({
        methods: "get",
        url: "http://82.156.190.251:80/apis/search",
        params: {
          searchType: this.search.searchType,
          searchString: this.search.searchString,
        },
      }).then(
        (response) => {
          var arraylist = new Array();
          arraylist = response.data;
          this.success = arraylist[0].success;
          if (this.success == true) {
            this.results = arraylist[0].results;
            this.table = arraylist.slice(1);
          }
        },
        (err) => {
          alert(err);
        }
      );
    },
    parentFun() {
      console.log("搜索");
    },
  },
};
</script>