<template>
  <div class="about">
    <h1>编辑主页</h1>
    <router-link to="/editor/authors">
        <el-row>
            <el-button type="primary">作者身份管理</el-button>
        </el-row>
    </router-link>
    <router-link to="/editor/reviewers">
        <el-row>
            <el-button type="primary">审稿人身份管理</el-button>
        </el-row>
    </router-link>
    <router-link to="/editor/articles">
        <el-row>
            <el-button type="primary">文章管理</el-button>
        </el-row>
    </router-link>
  </div>
</template>
