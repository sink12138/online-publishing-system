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
        articleId: "",
        reviewersId: "",
      },
    };
  },
  methods: {
    CertifySuccess() {
      if (this.formInline.articleId === "") {
        alert("请输入文章编号！");
        return;
      }
      else{
        if(this.formInline.reviewersId === ""){
          alert("请输入分配的审稿人编号！")
          return;
        }
      }
      let JsonAssignArticle = JSON.stringify(this.formInline);
      console.log(JsonAssignArticle);
      this.$store.commit("Assign");
      this.$axios({
        method: "post",
        url: "http://82.156.190.251:80/apis/editor/assign",
        data: JsonAssignArticle,
      }).then((res) => {
        console.log(res);
        if (res.date.success == true) {
          this.$message({
            showClose: true,
            message: "分配审稿人成功",
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
      window.location.href = "../editor";
    },
    Cancel() {
      window.location.href = "../editor";
    },
  },
};
</script>
