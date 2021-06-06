<template>
  <div class="certify">
    <h1>撤销作者身份页面</h1>
    <br />
    <div id="cancelauthor">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="作者ID">
          <el-input
            v-model="formInline.authorId"
            placeholder="authorId"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="cancel">撤销</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div>
      <br />
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      formInline: {
        authorId: "",
      },
    };
  },
  methods: {
    cancel() {
      if(this.formInline.authorId ===""){
        alert("请输入作者编号！");
        return;
      }
      let JsonCancelAuthorId = JSON.stringify(this.formInline);
      console.log(JsonCancelAuthorId);
      this.$store.commit("cancel");
      sessionStorage.setItem("authorId", this.formInline.authorId);
      this.$axios({
        method: "post",
        url: "http://82.156.190.251:80/apis/editor/cancel/author",
        data: JsonCancelAuthorId,
      }).then((res) => {
        console.log(res);
        if (res.data.success == true) {
          this.$message({
            showClose: true,
            message: "撤销作者身份成功",
            type: "success",
          });
        } else {
          this.$message({
            showClose: true,
            message: res.data.message,
            type: "error",
          });
        }
      });
      //window.location.href = "../";
    },
  },
};
</script>
