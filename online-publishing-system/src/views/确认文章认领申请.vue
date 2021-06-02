<template>
  <div class="reviewer">
    <div>
      <h1>文章认领申请如下</h1>
    </div>
    <div class="articles">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column label="文章 ID" prop="articleId"> </el-table-column>
        <el-table-column label="作者 ID" prop="authorId"> </el-table-column>
        <el-table-column label="文章标题" prop="title"> </el-table-column>
        <el-table-column label="作者" prop="authors"> </el-table-column>
        <el-table-column fixed="right" label="文章操作" width="100">
          <template
            ><el-button type="text" size="small" @click="confirmArticle"
              >确认</el-button>
            <el-button @click="refuseArticle" type="text" size="small"
              >拒绝</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      tableData: [
        {
          articldId: "",
          title: "",
          authors: "",
        },
      ],
    };
  },
  methods: {
    confirmArticle() {
      this.$notify({
        title: "确认成功",
        message: "您已成功确认该申请",
        type: "success",
      });
    },
    refuseArticle() {
      this.$notify.info({
        title: "拒绝成功",
        message: "您已成功拒绝该申请",
      });
    },
  },
};
</script>