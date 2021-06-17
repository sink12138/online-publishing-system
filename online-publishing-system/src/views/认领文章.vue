<template>
  <div class="claim">
    <h1>认领文章</h1>
    <div class="search">
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
    <router-view />
    <div class="table">
      <el-card class="box-card">
        <el-table
          :data="tableData.slice((currentPage - 1) * 8, currentPage * 8)"
          border
          stripe
          style="width: 100%"
        >
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
                layout="prev, next, jumper, pager"
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
          <el-button type="success" plain @click="submit()">提交申请</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<style scoped>
.claim {
  opacity: 0.7;
}
.search {
  margin-bottom: 40px;
}
.search .el-input {
  width: 600px;
  height: 50px;
}
.search .el-input >>> .el-input__inner {
  height: 50px;
}
.search .el-select {
  width: 100px;
}
.claimArticle {
  margin-top: 40px;
}
</style>

<script>
export default {
  name: "Search",
  data() {
    return {
      formInline: {
        articleId: 0,
      },
      search: {
        searchType: "author",
        searchString: "",
      },
      currentPage: 1, //当前页数
      tableData: [
        // {
        //   articleId: "",
        //   title: "",
        //   keywords: "",
        //   firstAuthor: "",
        // },
      ], //存放从后端传来的数据
    };
  },
  mounted() {
    this.$axios.get("http://82.156.190.251:80/apis/home").then((res) => {
      this.search.searchString = res.data.realName;
    });
    this.searchArticle();
  },
  methods: {
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
    },
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
        params: { articleId: articleId },
        responseTpe: "blob",
      }).then(
        (response) => {
          console.log(response);
          const filename = decodeURIComponent(
            response.headers["content-disposition"].split(";")[1].split("=")[1]
          );
          console.log(filename);
          this.load(response.data, filename);
          console.log("下载中");
        },
        (err) => {
          alert(err);
        }
      );
    },
    load(data, filename) {
      if (!data) {
        return;
      }
      let url = window.URL.createObjectURL(
        new Blob([data], { type: "application/force-download;charset=utf-8" })
      );
      let link = document.createElement("a");
      link.style.display = "none";
      link.href = url;
      link.download = filename;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      window.URL.revokeObjectURL(url);
    },
    claim(articleId) {
      this.$axios({
        method: "post",
        url: "http://82.156.190.251:80/apis/author/claim",
        data: { articleId: Number(articleId) },
      }).then((res) => {
        console.log(res);
        if (res.data.success == true) {
          this.$message({
            message: "认领成功！",
            type: "success",
          });
        } else {
          this.$message({
            message: res.data.message,
          });
        }
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
