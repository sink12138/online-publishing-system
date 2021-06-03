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
        <el-form-item label="第一作者">
          <el-input
            v-model="formInline.firstAuthor"
            placeholder="第一作者"
          ></el-input>
        </el-form-item>
        <el-form-item label="其他作者">
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
    <router-link to="/author"> <el-button type="primary"> 取消提交 </el-button> </router-link>
  </div>
</template>

<script>
const axios = require("axios");
export default {
  data() {
    return {
      hasfile: false,
      articleBufferId: 0,
      status: '',
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
    this.articleBufferId = this.$route.query.articleID;
    this.status = this.$route.query.status;
    if (this.articleBufferId == undefined) this.articleBufferId = 0;
    alert(this.articleBufferId);
    alert(this.status);
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
    },
    onSubmit() {
      alert("submit!");
      if(this.status == '审核通过' || this.status == '审核未通过'){
        axios({
          methods: "post",
          url: "http://82.156.190.251:80/apis/author/revise/upload",
          params: JSON.stringify({
            articleBufferId: 0,
            file: this.fileList[0],
            overwrite: this.articleBufferId,
          }),
        }).then(
          (response) => {
            this.articleBufferId = response.articleBufferId;
            console.log(response);
          },
          (err) => {
            alert(err);
          }
        );
        axios({
          methods: "post",
          url: "http://82.156.190.251:80/apis/author/revise/submit",
          params: JSON.stringify({
            articleBufferId: this.articleBufferId,
            title: this.formInline.title,
            abstract: this.formInline.abstract,
            keywords: this.formInline.keywords,
          }),
        }).then(
          (response) => {
            console.log(response);
          },
          (err) => {
            alert(err);
          }
        );
      }
      else {
        axios({
          methods: "post",
          url: "http://82.156.190.251:80/apis/author/new/upload",
          params: JSON.stringify({
            articleBufferId: 0,
            file: this.fileList[0],
          }),
        }).then(
          (response) => {
            console.log(response);
            this.articleBufferId = response.articleBufferId;
          },
          (err) => {
            alert(err);
          }
        );
        axios({
          methods: "post",
          url: "http://82.156.190.251:80/apis/author/new/submit",
          params: JSON.stringify({
            articleBufferId: this.articleBufferId,
            title: this.formInline.title,
            abstract: this.formInline.abstract,
            keywords: this.formInline.keywords,
            firstAuthor: this.formInline.firstAuthor,
            otherAuthors: this.formInline.otherAuthors,
          }),
        }).then(
          (response) => {
            console.log(response);
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