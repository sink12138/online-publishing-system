<template>
  <div class="upload">
    <h1>提交文章</h1>
    <div class="uploadArticle">
      <h3>上传文件</h3>
      <h5>请提交doc,docx,pdf,zip文件</h5>
      <input
        type="file"
        class="file"
        ref="file"
        accept=".doc,.docx,.pdf,.zip"
      />
      <el-input v-model="formInline.articleId" placeholder="对应的文章编号">文章编号
      </el-input>
      <el-button type="primary" @click="getfile" icon="el-icon-upload"
        >提交文件</el-button
      >
      <el-button type="primary" @click="Return" icon="el-icon-upload"
        >返回</el-button
      >
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      hasfile: false,
      articleId: 0,
      formInline: {
        articleId: 0,
      },
      uploadfile:''
    };
  },
  methods: {
    getfile() {
      this.$message({
        showClose: true,
        message: "提交成功！",
        type: "success",
      });
      let file = this.$refs.file.files[0];
      this.uploadfile = file;
      console.log(this.uploadfile);
      if (this.uploadfile.size > 20 * 1024 * 1024) {
        alert("文件最大为20兆");
        this.hasfile = false;
      }
      if (this.uploadfile != "") this.hasfile = true;
      console.log(this.hasfile);
      let formData = new FormData();
      formData.append("file", this.uploadfile);
      formData.append("articleId", Number(this.formInline.articleId));
      console.log(formData);
      this.$axios({
        method: "post",
        url: "http://82.156.190.251:80/apis/editor/upload",
        data: formData,
      }).then((res) => {
        console.log(res);
        if (res.data.success == true) {
          this.$message({
            showClose: true,
            message: "上传成功！",
            type: "success",
          });
        } else {
          this.$message({
            showClose: true,
            message: "上传错误！",
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
