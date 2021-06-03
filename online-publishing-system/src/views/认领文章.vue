<template>
  <div class="claim">
    <h1>认领文章</h1>
    <div class="search">
      搜索类型：
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
      | <router-link to="/author">作者主页</router-link> |
      <router-link to="/">主页</router-link>
    </div>
    <router-view />
    <div>
      <child title="搜索结果" :parent-fun="parentFun" :parent="this"></child>
    </div>
    <div class="claimArticle">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="认领文章ID">
          <el-input
            v-model="formInline.articleId"
            placeholder="articleId"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submit()">提交申请</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

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
      formInline: {
        articleId: 0,
      },
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
    submit() {
      axios({
        method: "post",
        url: "/author/claim",
        data: {
          articleId: this.formInline.articleId,
        },
      }).then((res) => {
        console.log(res);
      });
      console.log("submit!");
    },
    searchArticle() {
      axios({
        methods: "get",
        url: "/search",
        params: JSON.stringify({
          searchType: this.search.searchType,
          searchString: this.search.searchString,
        }),
      }).then(
        (response) => {
          var arraylist = new Array();
          arraylist = response.data;
          this.success = arraylist[0].success;
          if (this.success == true) {
            this.results = arraylist[0].results;
            this.table = arraylist.slice(0);
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