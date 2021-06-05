<template>
  <div>
    <div class="breadcrumb">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin/home' }">后台首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/clean' }">删除信息</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="clean">
      <el-button
        plain
        @click="handleClean">
        清除无效信息
      </el-button>
    </div>
    <router-view/>
  </div>
</template>

<style scoped>
.clean {
  position: relative;
  top: 180px;
}
.el-button {
  background-color: #ffb061;
  height: 160px;
  width: 250px;
  font-size: 32px;
}
</style>

<script>
  export default {
    methods: {
      handleClean() {
        this.$axios.post('http://82.156.190.251:80/apis/admin/clean')
        .then((res) =>{
          if (res.data.success == true) {
            this.$notify.success({
              title: '成功',
              message: '清除无效信息成功',
              showClose: false
            });
          } else {
            this.$notify.warning({
              title: '请重试',
              message: res.data.message,
              showClose: false
            });
          }
        })
        
      }
    }
  }
</script>