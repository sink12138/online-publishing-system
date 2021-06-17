<template>
  <div class="main" style="height: 100%">
    <el-container style="height: 100%">
      <el-header>
        <div class="link1">
          <router-link to="/" v-on:click="convert()">
            <img alt="logo" style="width: 27px;height: 27px; margin-top:6px;" src="../assets/logo_black.png">
          </router-link>
          <el-dropdown>
            <router-link to="/author">
              <el-button
              type="info"
              plain
              v-if="$store.state.role >= 4 && $store.state.isLogin">
                作者主页
              </el-button>
            </router-link>
            <el-dropdown-menu>
              <router-link to="/author">
                <el-dropdown-item>我的文章</el-dropdown-item>
              </router-link>
              <router-link to="/author/submit">
                <el-dropdown-item>提交文章</el-dropdown-item>
              </router-link>
              <router-link to="/author/claim">
                <el-dropdown-item>认领文章</el-dropdown-item>
              </router-link>
            </el-dropdown-menu>
          </el-dropdown>
          <el-dropdown>
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
              ">
              审稿人主页
              </el-button>
            </router-link>
          </el-dropdown>
          <el-dropdown>
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
              ">
                编辑主页
              </el-button>
            </router-link>
            <el-dropdown-menu>
              <router-link to="/editor/authors">
                <el-dropdown-item>作者管理</el-dropdown-item>
              </router-link>
              <router-link to="/editor/reviewers">
                <el-dropdown-item>审稿人管理</el-dropdown-item>
              </router-link>
              <router-link to="/editor/articles">
                <el-dropdown-item>文章管理</el-dropdown-item>
              </router-link>
              <router-link to="/editor/claims">
                <el-dropdown-item>文章认领申请</el-dropdown-item>
              </router-link>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <div class="link2">
          <el-dropdown>
            <el-button class="user" icon="el-icon-user-solid" style="font-size:30px">
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <router-link to="/">
                <el-dropdown-item>主页</el-dropdown-item>
              </router-link>
              <router-link to="/login" v-if="this.$store.state.isLogin == false">
                <el-dropdown-item>登录</el-dropdown-item>
              </router-link>
              <div v-if="this.$store.state.isLogin == true">
                <el-dropdown-item @click.native="Logout()">登出</el-dropdown-item>
              </div>
              <router-link to="/register" v-if="this.$store.state.isLogin == false">
                <el-dropdown-item>注册</el-dropdown-item>
              </router-link>
              <router-link to="/home" v-if="this.$store.state.isLogin == true">
                <el-dropdown-item>个人信息</el-dropdown-item>
              </router-link>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>
      <el-main>
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
  background-image: url("../assets/Background.png");
  background-size: cover;
}
.el-header {
  background-color: #ffffff;
  opacity: 0.9;
  height: 40px !important;
}
.el-main {
  background-color: #f8f8f863;
}
a {
  text-decoration: none;
}
.router-link-active {
  text-decoration: none;
}
.el-button {
  border: 0;
  border-radius: 0 0 0 0;
  height: 40px;
  font-size: 16px;
  font-weight: 400;
  color: #000;
  background-color: #fff;
}
.link1 .el-button {
  position: relative;
  top: -7.5px;
  left: 20px;
}
.user {
  display: flex;
  align-items: center;
  justify-content: center;
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
  position: relative;
  left: 40px;
}
.link2 {
  display: inline;
  float: right;
  position: relative;
  left: -40px;
}
.search {
  margin-top: 100px;
}
.articles {
  margin: 0 auto;
  margin-top: 40px;
  width: 1200px;
  position: relative;
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
      /*imgList: [
        {
          name: "logo",
          src: require("../assets/logo.jpg"),
          title: "Logo"
        },
        {
          name: "bg1",
          src: require("../assets/Canva - Green and Brown Mountains Under Blue Sky.jpg"),
          title: "bg1"
        },
        {
          name: "bg2",
          src: require("../assets/Canva - Green Leafed Plant.jpg"),
          title: "bg2"
        },
        {
          name: "bg3",
          src: require("../assets/Canva - Majestic Landscape.jpg"),
          title: "bg3"
        },
        {
          name: "bg4",
          src: require("../assets/Canva - open Book Pages on Surface.jpg"),
          title: "bg4"
        },
        {
          name: "bg5",
          src: require("../assets/Canva - Water Mill Near Body of Water.jpg"),
          title: "bg5"
        }
      ],*/
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
        "/search?searchType=" + this.search.searchType + "&searchString=" + this.search.searchString
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
      window.location.href='/';
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
