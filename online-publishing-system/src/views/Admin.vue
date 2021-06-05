<template>
  <div class="admin" style="height: 100%">
    <el-container style="height: 100%">
      <el-aside width="200px" style="height: 100%">
        <div style="line-height: 40px;padding:10px" class="demo-type" align="center">
          <div>
            <el-avatar icon="el-icon-user-solid"></el-avatar>
          </div>
          <div>
            <el-popconfirm
              @confirm="handleConfirm" 
              confirm-button-text='确认'
              cancel-button-text='取消'
              title="确认登出？">
              <el-button slot="reference">登出</el-button>
            </el-popconfirm>
          </div>
        </div>
        <el-row class="tac">
          <el-col>
            <el-menu
              default-active="this.$route.path"
              class="el-menu-vertical-demo"
              @open="handleOpen"
              @close="handleClose"
              background-color="#A3D0C3"
              unique-opened
              text-color="#000"
              active-text-color="#FFF"
              router
              style="height: 100%">
              <el-submenu index="1">
                <template slot="title">
                  <i class="el-icon-user"></i>
                  <span>账号管理</span>
                </template>
                <el-menu-item-group>
                  <el-menu-item index="/admin/accounts" >查看账号</el-menu-item>
                  <el-menu-item index="/admin/authors">查看作者</el-menu-item>
                  <el-menu-item index="/admin/reviewers">查看审稿人</el-menu-item>
                  <el-menu-item index="/admin/editors">查看编辑</el-menu-item>
                </el-menu-item-group>
              </el-submenu>
              <el-submenu index="2">
                <template slot="title">
                  <i class="el-icon-document"></i>
                  <span>资源管理</span>
                </template>
                <el-menu-item-group>
                  <el-menu-item index="/admin/documents" >资源管理</el-menu-item>
                  <el-menu-item index="/admin/documents/number-search">编号查找</el-menu-item>
                  <el-menu-item index="/admin/documents/key-search">关键字查找</el-menu-item>
                </el-menu-item-group>
              </el-submenu>
              <el-menu-item index="/admin/clean">
                <i class="el-icon-delete"></i>
                <span slot="title">删除信息</span>
              </el-menu-item>
              <el-menu-item index="/admin/settings">
                <i class="el-icon-setting"></i>
                <span slot="title">网站设置</span>
              </el-menu-item>
            </el-menu>
          </el-col>
        </el-row>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<style scoped>
body {
  font-weight: 600;
  font-family: "Microsoft YaHei";
}
.admin {
  background-image:url("../assets/Canva - Majestic Landscape.jpg");
  background-size: cover;
  position: fixed;
  height: 100%;
  width: 100%;
}
.el-aside {
  background-color: #A3D0C3;
  color: #333;
  text-align: left;
  line-height: 100px;
  font-weight: 400 !important;
  font-family: "Microsoft YaHei";
  opacity: 0.85;
}
.el-aside button {
  background-color: #d5f6ee;
  height: 40px;
  width: 80px;
}
.el-main {
  background-color: #fff;
  color: #333;
  text-align: center;
  font-family: "Microsoft YaHei";
  opacity: 0.7;
}
</style>

<script>
export default {
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    handleConfirm() {
      this.$store.commit("adminLogout");
      this.$axios({
        method:"post",
        url:'http://82.156.190.251:8090/admin/logout',
      })
      location.reload();
    }
  }
}
</script>