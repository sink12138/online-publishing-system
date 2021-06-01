<template>
  <div class="home">
    <div class="head">
      <h1>OPS</h1>
    </div>
    <div id="nav">
      <router-link to="/home">个人信息</router-link> |
      <router-link to="/author" v-if="$store.state.role >= 4">
        作者主页
      </router-link>
      <router-link
        to="/reviewer"
        v-if="
          $store.state.role == 2 ||
          $store.state.role == 3 ||
          $store.state.role == 6 ||
          $store.state.role == 7
        "
      >
        审稿人主页
      </router-link>
      <router-link to="/editor" v-if="$store.state.role % 2 == 1">
        编辑主页
      </router-link>
      | 搜索类型：
      <select v-model="search.searchType" style="height: 24px">
        <option label="标题" value="title"></option>
        <option label="关键词" value="keyword"></option>
        <option label="作者" value="author"></option>
      </select>
      <input
        type="text"
        style="height: 20px; width: 400px"
        v-model="search.searchString"
      />
      <el-button
        type="primary"
        icon="el-icon-search"
        size="mini"
        @click="searchArticle"
        >搜索</el-button
      >
      | <router-link to="/register">Register</router-link> |
      <router-link to="/login">Login</router-link> |
      <router-link to="/admin">Go to Admin</router-link>
    </div>
    <router-view />
    <child title="搜索结果" :parent-fun="parentFun" :parent="this"></child>
  </div>
</template>

<style>
.body {
  text-align: center;
}
</style>

<script>
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