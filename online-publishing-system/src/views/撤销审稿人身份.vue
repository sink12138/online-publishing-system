<template>
  <div class="certify">
    <h1>撤销审稿人身份页面</h1>
    <br />
    <div id="cancelreviewer">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="审稿人ID">
          <el-input
            v-model="formInline.reviewerId"
            placeholder="reviewerId"
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
const axios = require("axios");
export default {
  data() {
    return {
      formInline: {
        reviewerId: "",
      },
    };
  },
  methods: {
    cancel() {
      if(this.formInline.reviewerId ===""){
        alert("请输入审稿人编号！");
        return;
      }
      let JsonCancelReviewerId = JSON.stringify(this.formInline);
      console.log(JsonCancelReviewerId);
      this.$store.commit("cancel");
      sessionStorage.setItem("reviewerId", this.formInline.reviewerId);
      this.$axios({
        method: "post",
        url: "http://82.156.190.251:80/apis/editor/cancel/reviewer",
        data: JsonCancelReviewerId,
      }).then((res) => {
        console.log(res);
        if (res.date.success == true) {
          this.$message({
            showClose: true,
            message: "撤销审稿人身份成功",
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
      window.location.href = "../";
    },
  },
};
</script>
