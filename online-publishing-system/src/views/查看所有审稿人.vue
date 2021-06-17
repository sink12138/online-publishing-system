<template>
  <div class="reviewer">
    <div>
      <h1>所有审稿人名单</h1>
    </div>
    <div class="table">
      <template> </template>
      <el-table
        :data="
          tableData.slice((currentPage - 1) * pagesize, currentPage * pagesize)
        "
        :header-cell-style="{ height: '60px' }"
        style="height: 100%;width: 100%;padding-top:10px;scoped"
      >
        <el-table-column
          prop="reviewerId"
          label="审稿人编号"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="realName"
          label="真实姓名"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="institution"
          label="所属机构"
          align="center"
        ></el-table-column>
        <el-table-column label="撤销审稿人身份">
          <template slot-scope="scope"
            ><el-button
              type="text"
              size="small"
              @click="cancelReviewer(scope.row)"
              >撤销审稿人</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <router-link to="/editor/certify/reviewer">
          <el-button type="ops">认证审稿人</el-button>
        </router-link>
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
.reviewer {
  opacity: 0.7;
}
.table {
  text-align: center;
}
.pagination {
  display: inline;
  position: fixed;
  justify-content: center;
  left: 700px;
  bottom: 60px;
}
.el-button--ops.is-active,
.el-button--ops:active {
  background: #000000;
  border-color: #000000;
  color: #fff;
}
.el-button--ops:focus,
.el-button--ops:hover {
  background: #ababab;
  border-color: #ababab;
  color: #000000;
}
.el-button--ops {
  color: #fff;
  background: #000000;
  border-color: #000000;
  position: relative;
  left: -620px;
  top: 30px;
}
</style>

<script>
export default {
  mounted: function () {
    this.$axios({
      method: "get",
      url: "http://82.156.190.251:80/apis/editor/reviewers",
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
    cancelReviewer(row) {
      this.$confirm(
        "您正撤销编号为"+
        row.reviewerId+
        "的审稿人的身份，是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        let JsonCancelReviewerId = JSON.stringify({
          reviewerId: Number(row.reviewerId),
        });
        console.log(JsonCancelReviewerId);
        this.$store.commit("cancel");
        this.$axios({
          method: "post",
          url: "http://82.156.190.251:80/apis/editor/cancel/reviewer",
          data: JsonCancelReviewerId,
        }).then((res) => {
          console.log(res);
          if (res.data.success == true) {
            this.$message({
              showClose: true,
              message: "撤销审稿人身份成功",
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
      });
    },
    Return() {
      window.location.href = "../editor";
    },
  },
  data() {
    return {
      tableData: [
        {
          reviewerId: 0,
          realName: "",
          institution: "",
        },
      ],
      pagesize: 8,
      currentPage: 1,
      reviewerData: "",
      total: 0,
    };
  },
};
</script>
