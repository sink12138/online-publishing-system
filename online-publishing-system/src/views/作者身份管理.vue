<template>
    <div class="editor/authors">
        <h1>作者身份管理</h1>
        <el-button type="primary" @click="open">撤销作者身份</el-button>
        <router-link to="/editor/check/authors">
            <el-row>
                <el-button type="primary">查看所有作者</el-button>
            </el-row>
        </router-link> 
    </div>
</template>
<script>
  export default {
    methods: {
      open() {
        this.$prompt('请输入待撤销作者身份的作者id', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
        }).then(({ value }) => {
          this.$message({
            type: 'success',
            message: '编号为' + value + '的作者已撤销身份'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入'
          });       
        });
      }
    }
  }
</script>