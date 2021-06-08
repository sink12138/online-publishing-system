<template>
  <div class="reviewer">
    <div>
      <h1>所有审稿人名单</h1>
    </div>
    <div class="table">
      <el-table
        :data="
          reviewerData.slice((currentPage - 1) * pagesize, current * pagesize)
        "
        :header-cell-style="{ height: '60px' }"
        style="height: 100%;width: 300%;padding-top:10px;scoped"
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
        <div class="pagination">
          <el-pagination
            background
            layout="prev, pager, next, jumper"
            :total="total"
            :page-size="3"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
          >
          </el-pagination>
        </div>
        <router-view></router-view>
      </el-table>
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
    searchArticle() {},
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
      pagesize: 3,
      currentPage: 1,
      reviewerData: "",
      total: 0,
    };
  },
};
</script>
