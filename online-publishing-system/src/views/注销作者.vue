<template>
  <div class="about">
    <h1>注销作者页面</h1>
    <router-link to='/'>返回主页</router-link> 
    <el-button type="warning" plain @click="cancel">注销作者身份</el-button>
  </div>
</template>

<script>
export default {
  methods: {
    cancel() {
      this.$confirm('此操作将注销作者身份, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios({
            method:"post",
            url:"http://82.156.190.251:80/apis/author/cancel",
          })
          .then((res)=>{
            console.log(res);
          });
          console.log('submit!');
          this.$store.commit("logout");
          this.$message({
            message: '注销成功！',
            type:'success'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消注销'
          });          
        });
    },
  },
};
</script>