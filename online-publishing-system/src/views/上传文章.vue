<template>
  <div class="upload">
    <h1>上传文章</h1>
    <input type="file" class="file" />
    <div class="file">
      <el-upload
        class="upload"
        action=url
        :on-change="handleChange"
        :file-list="fileList"
      >
        <el-button size="small" type="primary">点击上传</el-button>
        <div slot="tip" class="el-upload__tip">
          只能上传jpg/png文件，且不超过500kb
        </div>
      </el-upload>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      url:"/author/upload/post",
      articleBufferId: 0,
      fileList: [],
    };
  },
  created: function () {
    this.articleBufferId = this.$route.query.ID;
    if (this.articleBufferId == undefined) this.articleBufferId = 0;
    alert(this.articleBufferId);
  },
  methods: {
    handleChange(file, fileList) {
      this.fileList = fileList.slice(-3);
    },
  },
};
