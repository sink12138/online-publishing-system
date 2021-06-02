<template>
  <div >
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin' }">后台首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/admin/documents' }">资源管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/admin/documents/view' }">查看文章</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>