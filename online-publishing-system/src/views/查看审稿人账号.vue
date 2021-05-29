<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin' }">后台首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/admin/referee' }">查看审稿人</el-breadcrumb-item>
    </el-breadcrumb>
  </div>      
</template>