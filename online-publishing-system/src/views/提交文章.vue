<template>
  <div class="submit">
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
      <el-button type="primary" size="mini" @click="getfile"
        >提交文件</el-button
      >
    </div>
    <div class="articleinfo" v-if="this.hasfile">
      <h3>文章信息</h3>
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="标题">
          <el-input v-model="formInline.title" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item label="摘要">
          <el-input v-model="formInline.abstract" placeholder="摘要"></el-input>
        </el-form-item>
        <el-form-item label="关键词">
          <el-input
            v-model="formInline.keywords"
            placeholder="关键词"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="第一作者"
          v-if="this.status != '审核通过' || this.status != '审核未通过'"
        >
          <el-input
            v-model="formInline.firstAuthor"
            placeholder="第一作者"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="其他作者"
          v-if="this.status != '审核通过' || this.status != '审核未通过'"
        >
          <el-input
            v-model="formInline.otherAuthors"
            placeholder="其他作者"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">提交</el-button>
        </el-form-item>
      </el-form>
    </div>
    <router-link to="/author">
      <el-button type="primary"> 取消提交 </el-button>
    </router-link>
  </div>
</template>

<script>
export default {
  data() {
    return {
      hasfile: false,
      articleBufferId: 0,
      status: "",
      formInline: {
        title: "",
        abstract: "",
        keywords: "",
        firstAuthor: "",
        otherAuthors: "",
      },
      fileList: [],
    };
  },
  created: function () {
    this.articleBufferId = this.$route.query.articleId;
    this.status = this.$route.query.status;
    if (this.articleBufferId == undefined) this.articleBufferId = 0;
    console.log(this.articleBufferId);
    console.log(this.status);
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
      if (this.status == "审核通过" || this.status == "审核未通过") {
        let formData = new FormData();
        formData.append("file", this.fileList[0]);
        formData.append("articleBufferId", 0);
        formData.append("overwrite", this.articleBufferId);
        this.$axios({
          method: "post",
          url: "http://82.156.190.251:80/apis/author/revise/upload",
          data: formData,
        }).then(
          (response) => {
            this.articleBufferId = Number(response.data.articleBufferId);
            console.log(response);
            console.log(this.articleBufferId);
            if (response.data.success == true) {
              this.$message({
                message: "修改文章上传成功！",
                type: "success",
              });
            } else {
              this.$message({
                message: response.data.message,
              });
            }
          },
          (err) => {
            alert(err);
          }
        );
      } else {
        let formData = new FormData();
        formData.append("file", this.fileList[0]);
        formData.append("articleBufferId", 0);
        this.$axios({
          method: "post",
          url: "http://82.156.190.251:80/apis/author/new/upload",
          data: formData,
        }).then(
          (response) => {
            console.log(response);
            this.articleBufferId = Number(response.data.articleBufferId);
            console.log(this.articleBufferId);
            if (response.data.success == true) {
              this.$message({
                message: "文章上传成功！",
                type: "success",
              });
            } else {
              this.$message({
                message: response.data.message,
              });
            }
          },
          (err) => {
            alert(err);
          }
        );
      }
    },
    onSubmit() {
      alert("submit!");
      if (this.status == "审核通过" || this.status == "审核未通过") {
        this.$axios({
          method: "post",
          url: "http://82.156.190.251:80/apis/author/revise/submit",
          data: JSON.stringify({
            articleBufferId: Number(this.articleBufferId),
            title: this.formInline.title,
            abstract: this.formInline.abstract,
            keywords: this.formInline.keywords.split(","),
          }),
        }).then(
          (response) => {
            console.log(response);
            if (response.data.success == true) {
              this.$message({
                message: "修改文章提交成功！",
                type: "success",
              });
            } else {
              this.$message({
                message: response.data.message,
              });
            }
          },
          (err) => {
            alert(err);
          }
        );
      } else {
        this.$axios({
          method: "post",
          url: "http://82.156.190.251:80/apis/author/new/submit",
          data: JSON.stringify({
            articleBufferId: Number(this.articleBufferId),
            title: this.formInline.title,
            abstract: this.formInline.abstract,
            keywords: this.formInline.keywords.split(","),
            firstAuthor: this.formInline.firstAuthor,
            otherAuthors: this.formInline.otherAuthors.split(","),
          }),
        }).then(
          (response) => {
            console.log(response);
            if (response.data.success == true) {
              this.$message({
                message: "文章提交成功！",
                type: "success",
              });
            } else {
              this.$message({
                message: response.data.message,
              });
            }
          },
          (err) => {
            alert(err);
          }
        );
      }
    },
  },
};
</script>
