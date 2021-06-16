<template>
  <div class="reviewer">
    <div>
      <h1>文章评论如下</h1>
      <el-button class="back" type="info" icon="el-icon-back" @click="Return"></el-button>
    </div>
    <div>
      <el-form :inline="true" :model="search" class="demo-form-inline">
        <el-form-item label="文章编号">
          <el-input placeholder="请输入需要查看评论的文章id" v-model="search.articleId">
            <el-button slot="append" icon="el-icon-search" @click="searchArticle"></el-button>
          </el-input>
        </el-form-item>
      </el-form>
    </div>
    <div class="articles">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column label="评论内容" prop="comments"> </el-table-column>
        <el-table-column label="是否通过" prop="pass">
          <template slot-scope="scope">
            <span v-if="scope.row.pass == true"> 通过 </span>
            <span v-if="scope.row.pass == false"> 不通过 </span>
          </template>
        </el-table-column>
        <el-table-column label="评论时间" prop="date"> </el-table-column>
        <el-table-column label="审稿人真实姓名" prop="realName">
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<style>
.back {
  position: fixed;
  left: 60px;
  top: 100px;
}
</style>

<script>
export default {
  data() {
    return {
      search: {
        articleId: 0,
      },
      tableData: [
        {
          comments: "",
          pass: "",
          date: "",
        },
      ],
    };
  },
  created: function () {
    this.search.articleId = this.$route.query.articleId;
    if (this.search.articleId == undefined) this.returnId = 0;
    console.log(this.search.articleId);
    this.$axios({
      method: "get",
      url: "http://82.156.190.251:80/apis/editor/reviews",
      params: { articleId: this.search.articleId },
      responseTpe: "blob",
    })
      .then((response) => {
        console.log(this.search);
        console.log(response);
        var arraylist = new Array();
        arraylist = response.data;
        this.success = arraylist[0].success;
        if (this.success == true) {
          this.results = arraylist[0].results;
          this.tableData = arraylist.slice(1);
          console.log(this.tableData);
        }
      })
      .catch((err) => console.log(err));
  },
  methods: {
    searchArticle() {
      if (this.search.articleId === 0) {
        alert("请输入文章编号！");
      }
      if (this.search.articleId <= 0) {
        alert("请输入正确的文章编号！");
      }
      this.$axios({
        method: "get",
        url: "http://82.156.190.251:80/apis/editor/reviews",
        params: { articleId: this.search.articleId },
        responseTpe: "blob",
      })
        .then((response) => {
          console.log(this.search);
          console.log(response);
          var arraylist = new Array();
          arraylist = response.data;
          this.success = arraylist[0].success;
          if (this.success == true) {
            this.results = arraylist[0].results;
            this.tableData = arraylist.slice(1);
            console.log(this.tableData);
          }
          if (this.success == false) {
            this.$message({
              showClose: true,
              message: "请输入正确的文章编号！",
              type: "error",
            });
          }
        })
        .catch((err) => console.log(err));
    },
    Return() {
      window.location.href = "../editor/articles";
    },
  },
};
</script>
