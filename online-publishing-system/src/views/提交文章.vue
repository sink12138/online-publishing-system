<template>
  <div class="submit">
    <h1>提交文章</h1>
    <div class="articleinfo">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="标题">
          <el-input v-model="formInline.title" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item label="摘要">
          <el-input v-model="formInline.abstract" placeholder="摘要"></el-input>
        </el-form-item>
        <el-form-item label="关键词">
          <el-input v-model="formInline.keywords" placeholder="关键词"></el-input>
        </el-form-item>
        <el-form-item label="第一作者">
          <el-input v-model="formInline.firstAuthor" placeholder="第一作者"></el-input>
        </el-form-item>
        <el-form-item label="其他作者">
          <el-input v-model="formInline.otherAuthors" placeholder="其他作者"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">修改</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        formInline: {
          title: '',
          abstract: '',
          keywords: '',
          firstAuthor: '',
          otherAuthors: ''
        }
      }
    },
    methods: {
      onSubmit() {
        alert('submit!');
      }
    }
  }
</script>