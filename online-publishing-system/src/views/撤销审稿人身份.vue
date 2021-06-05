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
      this.$axios({
        method: "post",
        url: "/cancel/reviewer",
        data: {
          reviewerId: this.formInline.reviewerId,
        },
      }).then((res) => {
        console.log(res);
      });
      console.log("cancel!");
    },
  },
};
</script>
