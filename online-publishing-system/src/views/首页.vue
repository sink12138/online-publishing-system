<template>
  <div class="main" style="height: 100%">
    <el-container style="height: 100%">
      <el-header>
        <div class="link1">
          <router-link to="/" v-on:click="convert()">
            <img alt="logo" style="width: 27px;height: 27px;margin-top: 6px" src="../assets/logo_white.png">
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
  background-image: url("../assets/Home.jpg");
  background-size: cover;
}
.el-header {
  background-color: #1f1f1f;
  height: 40px !important;
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
  color: #fff;
  background-color: #1f1f1f;
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
  width: 800px;
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
  },
};
</script>
