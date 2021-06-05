<template>
  <el-form :inline="true" :model="formInline" class="demo-form-inline">
    <el-form-item label="待分配审稿人的文章编号">
      <el-input
        v-model="formInline.articleId"
        placeholder="待分配审稿人的文章编号"
      ></el-input>
    </el-form-item>
    <el-form-item label="分配的审稿人编号列表">
      <el-input
        v-model="formInline.reviewersId"
        placeholder="分配的审稿人编号列表"
      ></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="CertifySuccess">确定</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="info" @click="Cancel">取消</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  data() {
    return {
      formInline: {
        articlesId: "",
        reviewersId: "",
      },
    };
  },
  methods: {
    CertifySuccess() {
      this.$axios({
        method: "post",
        url: "/cancel/author",
        data: {
          authorId: this.formInline.authorId,
        },
      }).then((res) => {
        console.log(res);
      });
      console.log("cancel!");
    },
    Cancel() {
      window.location.href = "../";
    },
  },
};
</script>
