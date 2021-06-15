<template>
    <div class="home">
        <div class="search">
          <el-input v-model="search.searchString" size="large" :rows="10">
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
        <div class="user">
          <el-dropdown>
            <span class="el-dropdown-link">
              <i class="el-icon-more" style="font-size:33px"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <router-link to="/login">
                <el-dropdown-item>登录</el-dropdown-item>
              </router-link>
              <router-link to="/register">
                <el-dropdown-item>注册</el-dropdown-item>
              </router-link>
              <router-link to="/admin">
                <el-dropdown-item>Admin</el-dropdown-item>
              </router-link>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <router-view></router-view>
    </div>
</template>

<style scoped>
.home {
  position: fixed;
  height: 100%;
  width: 100%;
  background-image: url("../assets/Canva - Majestic Landscape.jpg");
  background-size: cover;
  display: flex;
  justify-content: center;
  align-items: center;
}
.user {
  position: fixed;
  top: 60px;
  right: 240px;
}
a {
  text-decoration: none;
}
.router-link-active {
  text-decoration: none;
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
  methods: {
      searchArticle() {
      this.$router.push(
        "/main/search?searchType=" + this.search.searchType + "&searchString=" + this.search.searchString
      );
      if(this.$route.path == '/search'){
        this.$router.go(0);
      }
    },
  }
}
</script>