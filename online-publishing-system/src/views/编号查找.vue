<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin/home' }">后台首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/admin/documents' }">资源管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/admin/documents/number-search' }">编号查找</el-breadcrumb-item>
    </el-breadcrumb>
  </div>      
</template>