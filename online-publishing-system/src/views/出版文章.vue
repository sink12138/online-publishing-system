<template>
  <div class="certify">
    <h1>出版文章页面</h1>
    <br />
    <div id="publish">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="待出版的文章编号">
          <el-input
            v-model="formInline.articleId"
            placeholder="articleId"
          ></el-input>
        </el-form-item>
        <el-form-item label="文献标识符">
          <el-input
            v-model="formInline.identifier"
            placeholder="identifier"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">提交</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      formInline: {
        articleId: "",
        identifier: "",
      },
    };
  },
  methods: {
    onSubmit() {
      let JsonPublishArticle = JSON.stringify(this.formInline);
      console.log(JsonPublishArticle);
      this.$store.commit("publish");
      sessionStorage.setItem("articleId", this.formInline.articleId);
      window.location.href = "../";
      alert("出版成功！");
    },
  },
};
</script>
