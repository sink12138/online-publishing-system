<template>
  <div class="upload">
    <h1>上传文章</h1>
    <div class="file">
      <el-upload
        class="upload-demo"
        action="/author/new/upload/post/"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :before-remove="beforeRemove"
        multiple
        :limit="3"
        :on-exceed="handleExceed"
        :file-list="fileList"
        accept=".doc,.docx,.pdf,.zip"
      >
        <el-button size="small" type="primary"
          >点击上传<i class="el-icon-upload el-icon--right"></i
        ></el-button>
        <div slot="tip" class="el-upload__tip">
          只能上传doc,docx,pdf,zip文件，且不超过20mb
        </div>
      </el-upload>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      fileList: [],
    };
  },
  methods: {
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      );
    },
    beforeRemove(file) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
  },
};
