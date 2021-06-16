<template>
  <div class="main" style="height: 100%">
    <el-container style="height: 100%">
      <el-header>
        <div class="link1">
          <router-link to="/main">
            <el-button type="info" plain @click="convert()">返回主页</el-button>
          </router-link>
          <router-link to="/home">
            <el-button type="info" plain v-if="$store.state.isLogin"
              >个人信息</el-button
            >
          </router-link>
          <router-link to="/author">
            <el-button
              type="info"
              plain
              v-if="$store.state.role >= 4 && $store.state.isLogin"
              >作者主页</el-button
            >
          </router-link>
          <router-link to="/reviewer">
            <el-button
              type="info"
              plain
              v-if="
                ($store.state.role == 2 ||
                  $store.state.role == 3 ||
                  $store.state.role == 6 ||
                  $store.state.role == 7) &&
                $store.state.isLogin
              "
              >审稿人主页
            </el-button>
          </router-link>
          <router-link to="/editor">
            <el-button
              type="info"
              plain
              v-if="
                ($store.state.role == 1 ||
                  $store.state.role == 3 ||
                  $store.state.role == 5 ||
                  $store.state.role == 7) &&
                $store.state.isLogin
              "
              >编辑主页</el-button
            >
          </router-link>
        </div>
        <div class="link2">
          <router-link to="/login">
            <el-button type="info" plain v-if="$store.state.isLogin==false">登录</el-button>
          </router-link>
          <router-link to="/main" v-if="this.$store.state.isLogin == true">
            <el-button type="info" plain @click="Logout">登出</el-button>
          </router-link>
          <router-link to="/register">
            <el-button type="info" plain v-if="$store.state.isLogin==false">注册</el-button>
          </router-link>
        </div>
      </el-header>
      <el-main>
        <div class="search" v-show="this.$route.path == '/main' || this.$route.path == 'main/search'">
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
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<style scoped>
.main {
  position: fixed;
  height: 100%;
  width: 100%;
  background-image: url("../assets/Canva - Water Mill Near Body of Water.jpg");
  background-size: cover;
}
.el-header {
  background-color: #909090;
}
.el-main {
  background-color: #fff;
  opacity: 0.86;
}
.el-button {
  border-radius: 0 0 0 0;
  height: 60px;
  font-size: 20px;
  font-weight: 600;
  background-color: #cccccc;
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
.link1 {
  display: inline;
  float: left;
}
.link2 {
  display: inline;
  float: right;
}
.search {
  margin-top: 100px;
}
</style>

<script>
export default {
  name: "Search",
  data() {
    return {
      search: {
        searchType: "title",
        searchString: "",
      },
    };
  },
  mounted() {
    this.convert();
  },
  methods: {
    convert() {
      var logi = sessionStorage.getItem("isLogin");
      var rol = sessionStorage.getItem("role");
      console.log(
        "用户角色 home",
        this.$store.state.role,
        this.$store.state.isLogin
      );
      console.log("logi,rol", logi, rol);
      if (logi == undefined)
        //初始化
        sessionStorage.setItem("isLogin", false);
      if (rol == undefined) sessionStorage.setItem("role", 0);
      if (this.$store.state.role == 0 && rol != 0) {
        //角色初始化
        if (logi == "true") {
          //登录初始化
          this.$store.commit("logout");
          this.$store.commit("login");
          console.log("刷新登录");
        } else if (logi == "false") {
          this.$store.commit("logout");
          sessionStorage.setItem("role", 0);
          console.log("刷新退出登录");
        }
        if (rol == 1 || rol == 3 || rol == 5 || rol == 7)
          this.$store.commit("setEditor");
        if (rol == 2 || rol == 3 || rol == 6 || rol == 7)
          this.$store.commit("setReviewer");
        if (sessionStorage.getItem("role") >= 4)
          this.$store.commit("setWriter");
        console.log(
          "用户角色更新",
          this.$store.state.role,
          this.$store.state.isLogin
        );
      }
    },
    searchArticle() {
      this.$router.push(
        "/main/search?searchType=" + this.search.searchType + "&searchString=" + this.search.searchString
      );
      if(this.$route.path == '/search'){
        this.$router.go(0);
      }
    },
    Logout() {
      sessionStorage.setItem("role", 0);
      sessionStorage.setItem("isLogin", false);
      this.$axios({
        method: "post",
        url: "http://82.156.190.251:80/apis/logout",
      }).then((res) => {
        console.log(res);
      });
      console.log("logout submit!");
      this.$store.commit("logout");
      this.$message({
        message: "退出登录成功！",
      });
    },
    // article(articleId) {
    //   sessionStorage.setItem("articleId", articleId);
    //   window.location.href = "../article";
    // },
    // searchArticle() {
    //   this.$axios({
    //     method: "get",
    //     url: "http://82.156.190.251:80/apis/search",
    //     params: this.search,
    //   })
    //     .then((response) => {
    //       console.log("搜索内容", this.search);
    //       console.log(response);
    //       var arraylist = new Array();
    //       arraylist = response.data;
    //       this.success = arraylist[0].success;
    //       if (this.success == true) {
    //         this.results = arraylist[0].results;
    //         this.tableData = arraylist.slice(1);
    //         console.log(this.tableData);
    //       }
    //     })
    //     .catch((err) => console.log(err));
    // },
    // parentFun() {
    //   console.log("搜索");
    // },
    // handleCurrentChange(val) {
    //   console.log(`当前页: ${val}`);
    //   this.currentPage = val;
    // },
    // download(articleId) {
    //   this.$axios({
    //     method: "get",
    //     url: "http://82.156.190.251:80/apis/download",
    //     params: {
    //       articleId: articleId,
    //     },
    //     responseTpe: "blob",
    //   }).then((res) => {
    //     console.log(res);
    //     const filename = decodeURIComponent(
    //       res.headers["content-disposition"].split(";")[1].split("=")[1]
    //     );
    //     console.log(filename);
    //     this.load(res.data, filename);
    //   });
    //   console.log("submit!");
    // },
    // load(data, filename) {
    //   if (!data) {
    //     return;
    //   }
    //   let url = window.URL.createObjectURL(
    //     new Blob([data], { type: "application/force-download;charset=utf-8" })
    //   );
    //   let link = document.createElement("a");
    //   link.style.display = "none";
    //   link.href = url;
    //   link.download = filename;
    //   document.body.appendChild(link);
    //   link.click();
    //   document.body.removeChild(link);
    //   window.URL.revokeObjectURL(url);
    // },
    // claim(articleId) {
    //   this.$axios({
    //     method: "post",
    //     url: "http://82.156.190.251:80/apis/author/claim",
    //     data: { articleId: Number(articleId) },
    //   }).then((res) => {
    //     console.log(res);
    //   });
    //   console.log("认领请求提交!");
    // },
    // fetchdata() {
    //   this.$axios({
    //     method: "get",
    //     url: "http://82.156.190.251:80/apis/search",
    //     params: this.search,
    //   }).then(
    //     (response) => {
    //       console.log(response);
    //       var arraylist = new Array();
    //       arraylist = response.data;
    //       this.success = response.success;
    //       if (this.success == true) {
    //         this.tableData = arraylist.slice(1);
    //       }
    //     },
    //     (err) => {
    //       console.log(err);
    //     }
    //   );
    // },
  },
};
</script>
