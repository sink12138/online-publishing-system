<template>
  <div class="reviewer">
    <div>
      <h1>文章认领申请如下</h1>
    </div>
    <div class="articles">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column label="文章 ID" prop="articleId"> </el-table-column>
        <el-table-column label="作者 ID" prop="authorId"> </el-table-column>
        <el-table-column label="作者" prop="authors"> </el-table-column>
        <el-table-column fixed="right" label="文章操作" width="100">
          <template
            ><el-button type="text" size="small" @click="confirmArticle"
              >确认</el-button
            >
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
          articleId: "",
          authorId: "",
        },
      ],
    };
  },
  methods: {
    confirmArticle() {
      this.$notify({
        title: "确认成功",
        message: "您已成功接收该文章",
        type: "success",
      });
      let JsonConfirmArticleId = JSON.stringify({
        articleId: Number(this.formInline.articleId),
        authorId: Number(this.formInline.authorId),
        confirm:true,
      });
      console.log(JsonConfirmArticleId);
      this.$store.commit("confirm");
      this.$axios({
        methods: "post",
        url: "http://82.156.190.251:80/apis/editor/confirm/claim",
        data: JsonConfirmArticleId,
      }).then((res) => {
        console.log(res);
        if (res.data.success == true) {
          this.$message({
            showClose: true,
            message: "接受文章成功",
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
    },
    refuseArticle() {
      this.$notify.info({
        title: "拒绝成功",
        message: "您已成功拒绝该文章",
      });
      let JsonRefuseArticleId = JSON.stringify({
        articleId: Number(this.formInline.articleId),
        authorId: Number(this.formInline.authorId),
        confirm:false,
      });
      console.log(JsonRefuseArticleId);
      this.$store.commit("refuse");
      this.$axios({
        methods: "post",
        url: "http://82.156.190.251:80/apis/editor/confirm/claim",
        data: JsonRefuseArticleId,
      }).then((res) => {
        console.log(res);
        if (res.data.success == true) {
          this.$message({
            showClose: true,
            message: "拒绝文章成功",
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
    },
  },
};
</script>
