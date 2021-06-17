<template>
  <div class="submit">
    <h1>提交文章</h1>
    <div class="uploadArticle">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>上传文件</span>
          <h5>请提交doc,docx,pdf,zip文件</h5>
        </div>
        <div class="text item">
          <input
            type="file"
            class="file"
            ref="file"
            accept=".doc,.docx,.pdf,.zip"
          />
          <el-button type="primary" @click="getfile" icon="el-icon-upload"
            >提交文件</el-button
          >
        </div>
      </el-card>
    </div>
    <div class="articleinfo" v-if="this.hasfile">
      <div class="text">文章信息</div>
      <el-form class="article_info">
        <el-form-item label="标题">
          <el-input v-model="formInline.title" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item label="摘要">
          <el-input
            type="textarea"
            autosize
            v-model="formInline.abstract"
            placeholder="摘要"
          ></el-input>
        </el-form-item>
        <el-form-item label="关键词" class="tag">
          <el-tag
            :key="tag"
            v-for="tag in dynamicTags"
            closable
            :disable-transitions="false"
            @close="handleClose(tag)"
          >
            {{ tag }}
          </el-tag>
          <el-input
            class="input-new-tag"
            v-if="inputVisible"
            v-model="inputValue"
            ref="saveTagInput"
            size="small"
            @keyup.enter.native="handleInputConfirm"
            @blur="handleInputConfirm"
          >
          </el-input>
          <el-button
            v-else
            size="small"
            class="button-new-tag"
            @click="showInput"
            >+ 关键词</el-button
          >
        </el-form-item>
        <el-form-item
          label="第一作者"
          v-if="this.status != '审核通过' && this.status != '审核未通过'"
        >
          <el-input
            v-model="formInline.firstAuthor"
            placeholder="第一作者"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="其他作者"
          class="tag"
          v-if="this.status != '审核通过' && this.status != '审核未通过'"
        >
          <el-tag
            :key="tag"
            v-for="tag in dynamicTags2"
            closable
            :disable-transitions="false"
            @close="handleClose2(tag)"
          >
            {{ tag }}
          </el-tag>
          <el-input
            class="input-new-tag"
            v-if="inputVisible2"
            v-model="inputValue2"
            size="small"
            ref="saveTagInput2"
            @keyup.enter.native="handleInputConfirm2"
            @blur="handleInputConfirm2"
          >
          </el-input>
          <el-button
            v-else
            class="button-new-tag"
            size="small"
            @click="showInput2"
            >+ 作者</el-button
          >
        </el-form-item>
        <el-form-item
          label="第一作者"
          v-if="this.status == '审核通过' || this.status == '审核未通过'"
        >
          {{ this.firstAuthor }}
        </el-form-item>
        <el-form-item
          label="其他作者"
          v-if="this.status == '审核通过' || this.status == '审核未通过'"
        >
          {{ this.otherAuthors }}
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
      dynamicTags: [],
      dynamicTags2: [],
      inputVisible: false,
      inputVisible2: false,
      inputValue: "",
      inputValue2: "",
      hasfile: false,
      articleBufferId: 0,
      status: "",
      formInline: {
        title: "",
        abstract: "",
        // keywords: "",
        firstAuthor: "",
        // otherAuthors: null,
      },
      fileList: [],
      returnId: 0,
      firstAuthor: "",
      otherAuthors: "",
    };
  },
  created: function () {
    this.returnId = this.$route.query.articleId;
    this.status = this.$route.query.status;
    this.firstAuthor = this.$route.query.firstAuthor;
    this.otherAuthors = this.$route.query.otherAuthors;
    if (this.otherAuthors == "undefined") this.otherAuthors = "无";
    if (this.returnId == undefined) this.returnId = 0;
    console.log(this.returnId);
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
        formData.append("articleBufferId", this.articleBufferId);
        formData.append("overwrite", this.returnId);
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
        formData.append("articleBufferId", this.articleBufferId);
        console.log(formData);
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
      if (this.status == "审核通过" || this.status == "审核未通过") {
        this.$axios({
          method: "post",
          url: "http://82.156.190.251:80/apis/author/revise/submit",
          data: JSON.stringify({
            articleBufferId: Number(this.articleBufferId),
            title: this.formInline.title,
            abstract: this.formInline.abstract,
            keywords: this.dynamicTags, // this.formInline.keywords.split(","),
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
            keywords: this.dynamicTags, // this.formInline.keywords.split(","),
            firstAuthor: this.formInline.firstAuthor,
            otherAuthors: this.dynamicTags2, // this.formInline.otherAuthors.split(","),
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
      this.$router.push("/author");
    },
    handleClose(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
    },

    handleClose2(tag) {
      this.dynamicTags2.splice(this.dynamicTags2.indexOf(tag), 1);
    },

    showInput() {
      this.inputVisible = true;
      this.$nextTick((_) => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    showInput2() {
      this.inputVisible2 = true;
      this.$nextTick((_) => {
        this.$refs.saveTagInput2.$refs.input.focus();
      });
    },

    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        this.dynamicTags.push(inputValue);
      }
      this.inputVisible = false;
      this.inputValue = "";
    },

    handleInputConfirm2() {
      let inputValue = this.inputValue2;
      if (inputValue) {
        this.dynamicTags2.push(inputValue);
      }
      this.inputVisible2 = false;
      this.inputValue2 = "";
    },
  },
};
</script>

<style scoped>
.text {
  margin-top: 20px;
  padding-top: 20px;
  font-size: 18px;
  font-weight: 600;
}
.box-card {
  width: 500px;
  margin: 0 auto;
}
.submit {
  opacity: 0.7;
}
.el-tag + .el-tag {
  margin-left: 10px;
}
.tag {
  text-align: left;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
  position: relative;
  left: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
  position: relative;
  left: 0;
}
.submit {
  text-align: center;
}
.articleinfo {
  background-color: #fff;
  width: 800px;
  margin: 0 auto;
}
.articleinfo .el-button {
  margin-bottom: 10px;
}
.el-form-item {
  margin: 20px;
}
</style>
