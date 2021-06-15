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
      | <router-link to="/author">作者主页</router-link>
    </div>
    <router-view />
    <div class="table">
      <el-card class="box-card">
        <el-table :data="tableData" border stripe style="width: 100%">
          <el-table-column
            prop="articleId"
            label="文章ID"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="title"
            label="标题"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="keywords"
            label="关键词"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="firstAuthor"
            label="第一作者"
            align="center"
          ></el-table-column>
          <el-table-column
            label="下载"
            align="center"
            v-if="$store.state.isLogin == true"
          >
            <template slot-scope="scope" class="active">
              <el-button
                @click="download(scope.row.articleId)"
                type="text"
                icon="el-icon-download"
              ></el-button>
            </template>
          </el-table-column>
          <el-table-column
            label="文章信息"
            align="center"
            v-if="$store.state.isLogin == true"
          >
            <template slot-scope="scope" class="active">
              <el-button
                @click="article(scope.row.articleId)"
                type="text"
                icon="el-icon-info"
              ></el-button>
            </template>
          </el-table-column>
          <el-table-column
            label="认领"
            align="center"
            v-if="$store.state.role >= 4 && $store.state.isLogin == true"
          >
            <template slot-scope="scope" class="active">
              <el-button
                @click="claim(scope.row.articleId)"
                type="text"
                icon="el-icon-success"
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-row :gutter="20">
          <el-col :span="6" :offset="12">
            <div class="block">
              <el-pagination
                background
                @current-change="handleCurrentChange"
                :current-page.sync="currentPage"
                :page-size="8"
                layout="total, prev, next, jumper, pager"
                :total="tableData.size - 1"
              ></el-pagination>
            </div>
          </el-col>
        </el-row>
      </el-card>
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
export default {
  name: "Search",
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
      tableData: [
        {
          articleId: "",
          title: "",
          keywords: "",
          firstAuthor: "",
        },
      ], //存放从后端传来的数据
    };
  },
  mounted() {},
  methods: {
    article(articleId) {
      sessionStorage.setItem("articleId", articleId);
      window.location.href = "../article";
    },
    submit() {
      var Js = { articleId: Number(this.formInline.articleId) };
      console.log(Js);
      this.$axios({
        method: "post",
        url: "http://82.156.190.251:80/apis/author/claim",
        data: Js,
      }).then((res) => {
        console.log(res);
      });
      console.log("submit!");
    },
    searchArticle() {
      this.$axios({
        method: "get",
        url: "http://82.156.190.251:80/apis/search",
        params: this.search,
      })
        .then((response) => {
          console.log(this.search);
          console.log(response);
          var arraylist = new Array();
          arraylist = response.data;
          this.success = arraylist[0].success;
          if (this.success == true) {
            this.results = arraylist[0].results;
            this.tableData = arraylist.slice(1);
            console.log(this.tableData);
          }
        })
        .catch((err) => console.log(err));
    },
    parentFun() {
      console.log("搜索");
    },
    download(articleId) {
      this.$axios({
        method: "get",
        url: "http://82.156.190.251:80/apis/download",
        params: {
          articleId: Number(articleId),
        },
      }).then((res) => {
        console.log(res);
      });
      console.log("submit!");
    },
    claim(articleId) {
      this.$axios({
        method: "post",
        url: "http://82.156.190.251:80/apis/author/claim",
        data: { articleId: Number(articleId) },
      }).then((res) => {
        console.log(res);
      });
      console.log("submit!");
    },
    fetchdata() {
      this.$axios({
        method: "get",
        url: "http://82.156.190.251:80/apis/search",
        params: this.search,
      }).then(
        (response) => {
          console.log(response);
          var arraylist = new Array();
          arraylist = response.data;
          this.success = response.success;
          if (this.success == true) {
            this.tableData = arraylist.slice(1);
          }
        },
        (err) => {
          console.log(err);
        }
      );
    },
  },
};
</script>
