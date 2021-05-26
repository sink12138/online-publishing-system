<template>
  <div class="admin" style="height: 100%">
    <el-container style="height: 100%">
      <el-aside width="200px" style="height: 100%">
        <div class="demo-type" align="center">
          <div>
            <el-avatar icon="el-icon-user-solid"></el-avatar>
          </div>
        </div>
        <el-row class="tac">
          <el-col>
            <el-menu
              default-active="this.$route.path"
              class="el-menu-vertical-demo"
              @open="handleOpen"
              @close="handleClose"
              background-color="#D3DCE6"
              text-color="#000"
              active-text-color="#a4a5cf"
              router
              style="height: 100%">
              <el-submenu index="1">
                <template slot="title">
                  <i class="el-icon-user"></i>
                  <span>账号管理</span>
                </template>
                <el-menu-item-group>
                  <el-menu-item index="/admin/account" >查看账号</el-menu-item>
                  <el-menu-item index="/admin/writer">查看作者</el-menu-item>
                  <el-menu-item index="/admin/referee">查看审稿人</el-menu-item>
                  <el-menu-item index="/admin/editor">查看编辑</el-menu-item>
                </el-menu-item-group>
              </el-submenu>
              <el-submenu index="2">
                <template slot="title">
                  <i class="el-icon-document"></i>
                  <span>资源管理</span>
                </template>
                <el-menu-item-group>
                  <el-menu-item index="/admin/documents" >资源管理</el-menu-item>
                  <el-menu-item index="/admin/documents/view" >查看文章</el-menu-item>
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
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/admin' }">后台首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/referee' }">查看审稿人</el-breadcrumb-item>
        </el-breadcrumb>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<style>
  .admin {
    position: fixed;
    height: 100%;
  }
  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: left;
    line-height: 100px;
  }
  .el-main {
    background-color: #fff;
    color: #333;
    text-align: center;
    line-height: 160px;
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
      }
    }
  }
</script>