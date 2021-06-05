<template>
  <div class="home" style="height: 100%">
    <el-container style="height: 100%">
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
      <el-header>
        <div class="link1">
          <router-link to="/">
            <el-button type="info" plain>返回首页</el-button>
          </router-link>
          <router-link to="/home">
            <el-button type="info" plain>个人信息</el-button>
          </router-link>
          <router-link to="/author">
            <el-button type="info" plain v-if="$store.state.role >= 4">作者主页</el-button>
          </router-link >
          <router-link to="/reviewer">
            <el-button type="info" plain
              v-if="$store.state.role == 2 
              || $store.state.role == 3 
              || $store.state.role == 6 
              || $store.state.role == 7">审稿人主页
            </el-button>
          </router-link>
          <router-link to="/editor">
            <el-button  type="info" plain v-if="$store.state.role % 2 == 1">编辑主页</el-button>
          </router-link>
        </div>
        <div class="link2">
          <router-link to="/login">
            <el-button type="info" plain>登录</el-button>
          </router-link>
          <router-link to="/register">
            <el-button type="info" plain>注册</el-button>
          </router-link>
          <router-link to="/admin">
            <el-button type="info" plain>Admin</el-button>
          </router-link>
        </div>
      </el-header>
      <el-main>
        <router-view></router-view>
        <div class="search" v-show="this.$route.path=='/'">
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
          <child title="搜索结果" :parent-fun="parentFun" :parent="this"></child>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<style scoped>
.home {
  position: fixed;
  height: 100%;
  width: 100%;
  background-image:url("../assets/Canva - Water Mill Near Body of Water.jpg");
  background-size: cover;
}
.el-header {
  background-color: #909090;
  opacity: 0.85;
}
.el-main {
  background-color: #fff;
  opacity: 0.9;
}
.el-button {
  border-radius: 0 0 0 0;
  height: 60px;
  font-size: 20px;
  font-weight: 600;
  background-color: #cccccc;
}
.link1 {
  display: inline;
  float: left;
}
.link2 {
  display: inline;
  float: right;
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
const axios = require("axios");
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
      axios({
        methods: "get",
        url: "/search",
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