<template>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column type="expand">
      <template slot-scope="props">
        <el-form label-position="left" inline class="demo-table-expand">
          <el-form-item label="文章ID">
            <span>{{ props.row.articleID }}</span>
          </el-form-item>
          <el-form-item label="文章标题">
            <span>{{ props.row.title }}</span>
          </el-form-item>
          <el-form-item label="关键字">
            <span>{{ props.row.keywords }}</span>
          </el-form-item>
          <el-form-item label="第一作者">
            <span>{{ props.row.firstAuthor }}</span>
          </el-form-item>
          <el-form-item label="其他作者">
            <span>{{ props.row.otherAuthors }}</span>
          </el-form-item>
          <el-form-item label="文章状态">
            <span>{{ props.row.status }}</span>
          </el-form-item>
        </el-form>
      </template>
    </el-table-column>
    <el-table-column label="文章 ID" prop="articleID"> </el-table-column>
    <el-table-column label="文章标题" prop="title"> </el-table-column>
    <el-table-column label="文章状态" prop="status"> </el-table-column>
    <el-table-column fixed="right" label="操作" width="400">
      <template slot-scope="scope">
        <el-button type="text" size="small" :disabled="(scope.row.status=='审核通过'||scope.row.status=='审核未通过')&&scope.row.authorized?false:true" @click="submit(scope.row)"
          >提交文章</el-button
        >
        <el-button type="text" size="small" :disabled="(scope.row.status=='审核通过')&&scope.row.authorized?false:true" @click="confirmDraft(scope.row)"
          >确认终稿</el-button
        >
        <el-button type="text" size="small" :disabled="(scope.row.status=='审核通过'||scope.row.status=='审核未通过')&&scope.row.authorized?false:true" @click="abort(scope.row)"
          >中止出版</el-button
        >
        <el-button type="text" size="small" :disabled="(scope.row.status=='已出版')&&scope.row.authorized?false:true" @click="withdraw(scope.row)"
          >撤稿</el-button
        >
      </template>
    </el-table-column>
  </el-table>
</template>

<style>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>

<script>
export default {
  data() {
    return {
      article: 0,
      status: "",
      tableData: [
        {
          articleID: 0,
          title: "",
          keywords: "",
          firstAuthor: "",
          otherAuthors: "",
          status: "审核未通过",
          authorized: false,
        },
        {
          articleID: 123,
          title: "a",
          keywords: "abc",
          firstAuthor: "12",
          otherAuthors: "as",
          status: "审核通过",
          authorized: false,
        },
        {
          articleID: 345,
          title: "",
          keywords: "",
          firstAuthor: "",
          otherAuthors: "",
          status: "待接收",
          authorized: false,
        },
        {
          articleID: 13345,
          title: "",
          keywords: "",
          firstAuthor: "",
          otherAuthors: "",
          status: "已出版",
          authorized: false,
        },
      ],
    };
  },
  created() {
    this.convert();
  },
  methods: {
    submit(row) {
      this.article = row.articleID;
      this.status = row.status;
      this.$router.push(
        "http://82.156.190.251:80/apis/author/submit?articleID=" + this.article + "&status=" + this.status
      );
      console.log(row.articleID);
    },
    convert: function () {
      this.$axios.get("http://82.156.190.251:80/apis/author/articles").then((res) => {
        var arraylist = new Array();
        arraylist = res.data;
        this.tableData = arraylist.slice(1);
      });
    },
    withdraw(row) {
      this.$axios.post("http://82.156.190.251:80/apis/author/withdraw", Number(row.articleID));
      console.log(row);
    },
    confirmDraft(row) {
      this.$axios.post("http://82.156.190.251:80/apis/author/confirm/draft", Number(row.articleID));
      console.log(row);
    },
    abort(row) {
      this.$axios.post("http://82.156.190.251:80/apis/author/abort", Number(row.articleID));
      console.log(row);
    },
  },
};
</script>