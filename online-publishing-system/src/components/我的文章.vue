<template>
  <el-table class="myarticles" :data="tableData" style="width: 100%">
    <el-table-column type="expand">
      <template slot-scope="props">
        <el-form label-position="left" inline class="demo-table-expand">
          <el-form-item label="文章ID">
            <span>{{ props.row.articleId }}</span>
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
    <el-table-column label="文章 ID" prop="articleId"> </el-table-column>
    <el-table-column label="文章标题" prop="title"> </el-table-column>
    <el-table-column label="文章状态" prop="status"> </el-table-column>
    <el-table-column fixed="right" label="操作" width="400">
      <template slot-scope="scope">
        <el-dropdown>
          <span class="el-dropdown-link">
            操作文章<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-button
              type="text"
              size="small"
              v-if="
                (scope.row.status == '审核通过' ||
                  scope.row.status == '审核未通过') &&
                scope.row.authorized
              "
              @click="submit(scope.row)"
              >提交文章</el-button
            >
            <el-button
              type="text"
              size="small"
              v-if="scope.row.status == '审核通过' && scope.row.authorized"
              @click="confirmDraft(scope.row)"
              >确认终稿</el-button
            >
            <el-button
              type="text"
              size="small"
              v-if="
                (scope.row.status == '审核通过' ||
                  scope.row.status == '审核未通过') &&
                scope.row.authorized
              "
              @click="abort(scope.row)"
              >终止出版</el-button
            >
            <el-button
              type="text"
              size="small"
              v-if="
                (scope.row.status == '审核通过' ||
                  scope.row.status == '审核未通过') &&
                scope.row.authorized
              "
              @click="reviews(scope.row)"
              >查看评论</el-button
            >
            <el-button
              type="text"
              size="small"
              v-if="scope.row.status == '已出版' && scope.row.authorized"
              @click="withdraw(scope.row)"
              >撤稿</el-button
            >
          </el-dropdown-menu>
        </el-dropdown>
      </template>
    </el-table-column>
  </el-table>
</template>

<style>
.myarticles {
  width: 600px;
}
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
      tableData: [],
    };
  },
  created() {
    this.convert();
  },
  methods: {
    submit(row) {
      this.article = row.articleId;
      this.status = row.status;
      this.firstAuthor = row.firstAuthor;
      this.otherAuthors = row.otherAuthors;
      this.$router.push(
        "/author/submit?articleId=" +
          this.article +
          "&status=" +
          this.status +
          "&firstAuthor=" +
          this.firstAuthor +
          "&otherAuthors=" +
          this.otherAuthors
      );
      console.log(row.articleID);
    },
    convert: function () {
      this.$axios
        .get("http://82.156.190.251:80/apis/author/articles")
        .then((res) => {
          var arraylist = new Array();
          arraylist = res.data;
          this.tableData = arraylist.slice(1);
        });
    },
    withdraw(row) {
      console.log(row.articleId);
      this.$axios({
        method: "post",
        url: "http://82.156.190.251:80/apis/author/withdraw",
        data: JSON.stringify({
          articleId: Number(row.articleId),
        }),
      }).then(
        (response) => {
          console.log(response);
        },
        (err) => {
          alert(err);
        }
      );
      location.reload();
    },
    confirmDraft(row) {
      this.$axios({
        method: "post",
        url: "http://82.156.190.251:80/apis/author/confirm/draft",
        data: JSON.stringify({
          articleId: Number(row.articleId),
        }),
      }).then(
        (response) => {
          console.log(response);
        },
        (err) => {
          alert(err);
        }
      );
      console.log(row);
      location.reload();
    },
    reviews(row) {
      this.article = row.articleId;
      console.log(this.article);
      this.$router.push("/author/reviews?articleId=" + this.article);
    },
    abort(row) {
      this.$axios({
        method: "post",
        url: "http://82.156.190.251:80/apis/author/abort",
        data: JSON.stringify({
          articleId: Number(row.articleId),
        }),
      }).then(
        (response) => {
          console.log(response);
        },
        (err) => {
          alert(err);
        }
      );
      console.log(row);
      location.reload();
    },
  },
};
</script>
