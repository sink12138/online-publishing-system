<template>
  <div class="about">
    <h1>注销作者页面</h1>
    <el-button type="warning" plain @click="cancel">注销作者身份</el-button>
  </div>
</template>

<script>
export default {
  methods: {
    cancel() {
      this.$confirm("此操作将注销作者身份, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$axios({
            method: "post",
            url: "http://82.156.190.251:80/apis/author/cancel",
          }).then((res) => {
            console.log(res);
            if (res.data.success == false) {
              this.$message({
                message: res.data.message,
              });
            } else {
              this.$message({
                message: "注销成功",
                type: "success",
              });
            }
          });
          console.log("cancel submit!");
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消注销",
          });
        });
    },
  },
};
</script>