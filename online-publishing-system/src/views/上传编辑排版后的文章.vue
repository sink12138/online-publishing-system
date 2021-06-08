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
      <el-input v-model="formInline.articleId" placeholder="authorId">
      </el-input>
      <el-button type="primary" size="mini" @click="getfile"
        >提交文件</el-button
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
        articleId,
      },
      fileList: [],
    };
  },
  methods: {
    getfile() {
      this.fileList = this.$refs.file.files;
      console.log(this.fileList);
      if (this.fileList[0].size > 20 * 1024 * 1024) {
        alert("文件最大为20兆");
        this.fileList = null;
        this.hasfile = false;
      }
      if (this.fileList != null) this.hasfile = true;
      let formData = new FormData();
      formData.append("file", this.fileList[0]);
      formData.append("articleId", 0);
      formData.append("overwrite",this.formInline.articleId);
      this.$axios({
        method: "post",
        url: "http://82.156.190.251:80/apis/editor/upload",
        data: formData,
      }).then(
        console.log("上传完毕")
      )
    },
  },
};
</script>
