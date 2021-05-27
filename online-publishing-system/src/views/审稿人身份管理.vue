<template>
    <div class="editor/reviewers">
        <h1>审稿人身份管理</h1>
        <el-button type="primary" @click="open">撤销审稿人身份</el-button>
        <router-link to="/editor/check/reviewers">
            <el-row>
                <el-button type="primary">查看所有审稿人</el-button>
            </el-row>
        </router-link> 
        <router-link to="/editor/certify/reviewers">
            <el-row>
                <el-button type="primary">认证审稿人身份</el-button>
            </el-row>
        </router-link> 
    </div>
</template>
<script>
  export default {
    methods: {
      open() {
        this.$prompt('请输入待撤销审稿人身份的审稿人', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
        }).then(({ value }) => {
          this.$message({
            type: 'success',
            message: '编号为' + value + '的审稿人已撤销身份'
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