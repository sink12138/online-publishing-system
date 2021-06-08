<template>
  <div class="reviewer">
    <div>
      <h1>所有作者名单</h1>
    </div>
    <div class="table">
      <el-table
        :data="
          claimData.slice((currentPage - 1) * pagesize, current * pagesize)
        "
        :header-cell-style="{ height: '0px' }"
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
        <div class="pagination">
          <el-pagination
            background
            layout="prev, pager, next, jumper"
            :total="total"
            :page-size="6"
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
      url: "http://82.156.190.251:80/apis/editor/reviews",
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
          articleId: 0,
          title: "",
          authorId: 0,
          realName: "",
          email: "",
          confirmed:false,
        },
      ],
      pagesize: 56,
      currentPage: 1,
      authorData: "",
      total: 0,
    };
  },
};
</script>
