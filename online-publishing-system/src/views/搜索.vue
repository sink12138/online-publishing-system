<template>
  <div class="Search">
    <div class="table">
      <el-main>
        <el-input class="input" v-model="search.searchString" size="large">
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
      </el-main>
    </div>
  </div>
</template>

<script>
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
      ], //存放从后端传来的数据
    };
  },
  props: ["table"],
  mounted() {
    this.fetchdata();
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
    searchArticle() {
      this.$axios({
        method: "get",
        url: "http://82.156.190.251:80/apis/search",
        params: this.search,
      })
        .then((response) => {
          console.log("搜索内容", this.search);
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
          articleId: articleId,
        },
        responseTpe: "blob",
      }).then((res) => {
        console.log(res);
        const filename = decodeURIComponent(
          res.headers["content-disposition"].split(";")[1].split("=")[1]
        );
        console.log(filename);
        this.load(res.data, filename);
      });
      console.log("submit!");
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
      });
      console.log("认领请求提交!");
    },
    fetchdata() {
      this.search.searchType = this.$route.query.searchType;
      this.search.searchString = this.$route.query.searchString;
      console.log(this.search.searchType);
      console.log(this.search.searchString);
      this.$axios({
        method: "get",
        url: "http://82.156.190.251:80/apis/search",
        params: this.search,
      }).then(
        (response) => {
          console.log(response);
          var arraylist = new Array();
          arraylist = response.data;
          console.log(arraylist);
          if (response.data[0].success == true) {
            this.tableData = arraylist.slice(1);
            console.log(this.tableData);
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

<style scoped>
.main {
  display: flex;
  justify-content: center;
  align-content: center;
}
.Search {
  display: flex;
  justify-content: center;
  align-content: center;
  width: 100%;
}
.input {
  margin-bottom: 40px;
}
.box-card {
  width: 1000px;
}
.el-input {
  width: 600px;
  height: 50px;
}
.el-input >>> .el-input__inner{
  height: 50px;
}
.el-select {
  width: 100px;
}
</style>