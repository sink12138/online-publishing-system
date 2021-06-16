<template>
  <div class="certify">
    <h1>出版文章页面</h1>
    <el-button class="back" type="info" icon="el-icon-back" @click="Return"></el-button>
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
          <el-button type="info" @click="onSubmit">提交</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<style scoped>
.back {
  position: fixed;
  left: 60px;
  top: 100px;
}
</style>

<script>
export default {
  data() {
    return {
      formInline: {
        articleId: 0,
        identifier: "",
      },
    };
  },
  methods: {
    onSubmit() {
      if (this.formInline.articleId === "") {
        alert("请输入文章编号！");
        return;
      } else {
        if (this.formInline.identifier === "") {
          alert("请输入文章标识符！");
          return;
        }
      }
      let JsonPublishArticle = JSON.stringify({
        articleId: Number(this.formInline.articleId),
        identifier: this.formInline.identifier,
      });
      console.log(JsonPublishArticle);
      this.$store.commit("publish");
      this.$axios({
        method: "post",
        url: "http://82.156.190.251:80/apis/editor/publish",
        data: JsonPublishArticle,
      }).then((res) => {
        console.log(res);
        if (res.data.success == true) {
          this.$message({
            showClose: true,
            message: "出版文章成功",
            type: "success",
          });
        } else {
          this.$message({
            showClose: true,
            message: "文章编号错误！",
            type: "error",
          });
        }
      });
      window.location.href = "../editor/articles";
    },
    Return(){
      window.location.href="../editor/articles"
    }
  },
};
</script>
