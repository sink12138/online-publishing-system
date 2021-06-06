<template>
  <div class="home" style="height: 100%">
    <el-container style="height: 100%">
      <el-header>
        <div class="link1">
          <router-link to="/">
            <el-button type="info" plain>返回首页</el-button>
          </router-link>
          <router-link to="/home">
            <el-button type="info" plain v-if="$store.state.isLogin"
              >个人信息</el-button
            >
          </router-link>
          <router-link to="/author">
            <el-button type="info" plain v-if="$store.state.role >= 4"
              >作者主页</el-button
            >
          </router-link>
          <router-link to="/reviewer">
            <el-button
              type="info"
              plain
              v-if="
                $store.state.role == 2 ||
                $store.state.role == 3 ||
                $store.state.role == 6 ||
                $store.state.role == 7
              "
              >审稿人主页
            </el-button>
          </router-link>
          <router-link to="/editor">
            <el-button type="info" plain v-if="$store.state.role % 2 == 1"
              >编辑主页</el-button
            >
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
        <div class="search" v-show="this.$route.path == '/'">
          <el-input v-model="search.searchString" size="large">
            <el-select
              v-model="search.searchType"
              slot="prepend"
              placeholder="搜索类型"
            >
              <el-option label="标题" value="title"></el-option>
              <el-option label="关键词" value="keyword"></el-option>
              <el-option label="作者" value="author"></el-option>
            </el-select>
            <el-button
              slot="append"
              icon="el-icon-search"
              size="mini"
              @click="searchArticle"
            >
            </el-button>
          </el-input>
        </div>
        <div class="table" v-show="this.$route.path == '/'">
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
                label="操作"
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
                label="认领"
                align="center"
                v-if="$store.state.role >= 4"
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
      </el-main>
    </el-container>
  </div>
</template>

<style scoped>
.home {
  position: fixed;
  height: 100%;
  width: 100%;
  background-image: url("../assets/Canva - Water Mill Near Body of Water.jpg");
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
export default {
  name: "Search",
  data() {
    return {
      search: {
        searchType: "title",
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
      ],  //存放从后端传来的数据
    };
  },
  created: function () {
    if (sessionStorage.getItem("isLogin") == true) this.$store.commit("login");
    else if (sessionStorage.getItem("isLogin") == false)
      this.$store.commit("logout");
    if (sessionStorage.getItem("role") % 2 == 1)
      this.$store.commit("setEditor");
    if (
      sessionStorage.getItem("role") == 2 ||
      sessionStorage.getItem("role") == 3 ||
      sessionStorage.getItem("role") == 6 ||
      sessionStorage.getItem("role") == 7
    )
      this.$store.commit("setReviewer");
    if (sessionStorage.getItem("role") >= 4) this.$store.commit("setWriter");
  },
  mounted() {
    this.fetchdata();
  },
  methods: {
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
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
    },
    download(articleId) {
      this.$axios({
        method: "get",
        url: "http://82.156.190.251:80/apis/download",
        params:{
          articleId:Number(articleId),
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
        data: {"articleId":Number(articleId)},
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