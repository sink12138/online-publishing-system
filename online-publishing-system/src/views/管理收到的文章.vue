<template>
  <div class="reviewer">
    <div>
      <h1>您收到的的文章如下</h1>
    </div>
    <div class="articles">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column
          v-model="formInline.articleBufferId"
          label="文章 ID"
          prop="articleBufferId"
        >
        </el-table-column>
        <el-table-column
          v-model="formInline.title"
          label="文章标题"
          prop="title"
        >
        </el-table-column>
        <el-table-column
          v-model="formInline.authors"
          label="作者"
          prop="authors"
        >
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="100">
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
          formInline: {
            articldBufferId: "",
            title: "",
            authors: "",
          },
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
      let JsonConfirmArticleBufferId = JSON.stringify({
        articldBufferId: Number(this.formInline.articldBufferId),
      });
      console.log(JsonConfirmArticleBufferId);
      this.$store.commit("confirm");
      this.$axios({
        methods:"post",
        url:"http://82.156.190.251:80/apis/editor/accept",
        data: JsonConfirmArticleBufferId,
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
      let JsonRefuseArticleBufferId = JSON.stringify({
        articldBufferId: Number(this.formInline.articldBufferId),
      });
      console.log(JsonRefuseArticleBufferId);
      this.$store.commit("refuse");
      this.$axios({
        methods:"post",
        url:"http://82.156.190.251:80/apis/editor/reject",
        data: JsonRefuseArticleBufferId,
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
  }
}
</script>
