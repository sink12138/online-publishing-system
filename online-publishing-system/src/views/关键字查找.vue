<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin' }">后台首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/admin/documents' }">资源管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/admin/documents/key-search' }">关键字查找</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>