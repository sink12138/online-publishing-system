<template>
  <div class="reviewer">
    <div>
      <h1>所有文章认领申请</h1>
    </div>
    <template>
      <el-button type="primary" @click="Return">返回</el-button>
    </template>
    <div class="table">
      <el-table
        :data="
          tableData.slice((currentPage - 1) * pagesize, currentPage * pagesize)
        "
        :header-cell-style="{ height: '60px' }"
        style="height: 100%;width: 100%;padding-top:10px;scoped"
      >
        <el-table-column
          prop="articleId"
          label="文章编号"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="title"
          label="文章标题"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="authorId"
          label="申请者作者编号"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="realName"
          label="申请者真实姓名"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="email"
          label="申请者邮箱"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="confirmed"
          label="是否已确认"
          align="center"
        ></el-table-column>
        <el-table-column label="申请操作">
          <template slot-scope="scope">
            <el-button
              type="text"
              size="small"
              @click="confirmArticle(scope.row)"
              >确认</el-button
            >
            <el-button
              type="text"
              size="small"
              @click="refuseArticle(scope.row)"
              >拒绝</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          background
          layout="prev, pager, next, jumper"
          :total="total"
          :page-size="10"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
        >
        </el-pagination>
      </div>
      <router-view></router-view>
    </div>
  </div>
</template>

<style>
.breadcrumb {
  position: relative;
  text-align: center;
}
.table {
  position: fixed;
  text-align: center;
}
.pagination {
  display: inline;
  position: fixed;
  justify-content: center;
  bottom: 60px;
}
</style>

<script>
export default {
  mounted: function () {
    this.$axios({
      method: "get",
      url: "http://82.156.190.251:80/apis/editor/claims",
    })
      .then((response) => {
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
    confirmArticle(row) {
      let JsonConfirmArticleId = JSON.stringify({
        authorId: Number(row.authorId),
        articleId: Number(row.articleId),
        confirm: true,
      });
      console.log(JsonConfirmArticleId);
      this.$store.commit("confirm");
      this.$axios({
        method: "post",
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
      location.reload();
    },
    refuseArticle(row) {
      let JsonRefuseArticleId = JSON.stringify({
        articleId: Number(row.articleId),
        authorId: Number(row.authorId),
        confirm: false,
      });
      console.log(JsonRefuseArticleId);
      this.$store.commit("refuse");
      this.$axios({
        method: "post",
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
      location.reload();
    },
    Return() {
      window.location.href = "../editor/articles";
    },
  },
  data() {
    return {
      tableData: [
        {
          articleId: 0,
          title: "",
          authorId: 0,
          realName: "",
          email: "",
          confirmed: false,
        },
      ],
      pagesize: 10,
      currentPage: 1,
      total: 0,
    };
  },
};
</script>
