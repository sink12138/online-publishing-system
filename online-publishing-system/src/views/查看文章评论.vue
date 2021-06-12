<template>
  <div class="reviewer">
    <div>
      <h1>文章评论如下</h1>
    </div>
    <template>
      <el-button type="primary" @click="Return">返回</el-button>
    </template>
    <div>
      <el-form :inline="true" :model="search" class="demo-form-inline">
        <el-form-item label="文章编号">
          <el-input v-model="search.articleId" placeholder="articleId"
            >请输入需要查看评论的文章id
          </el-input>
        </el-form-item>
      </el-form>
      <template
        ><el-button type="primary" @click="searchArticle">查找</el-button>
      </template>
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
      </el-table>
    </div>
  </div>
</template>
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
