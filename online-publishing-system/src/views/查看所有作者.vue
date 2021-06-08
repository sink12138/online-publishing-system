<template>
  <div class="reviewer">
    <div>
      <h1>所有作者名单</h1>
    </div>
    <div class="table">
      <el-table
        :data="
          authorData.slice((currentPage - 1) * pagesize, current * pagesize)
        "
        :header-cell-style="{ height: '0px' }"
        style="height: 100%;width: 100%;padding-top:10px;scoped"
      >
        <el-table-column
          prop="authorId"
          label="作者编号"
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
        <el-table-column
          prop="researchInterests"
          label="研究方向"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="articleCount"
          label="绑定的文章数量"
          align="center"
        ></el-table-column>
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
      url: "http://82.156.190.251:80/apis/editor/authors",
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
          authorId: 0,
          realName: "",
          institution: "",
          researchInterests: "",
          articleCount: 0,
        },
      ],
      pagesize: 5,
      currentPage: 1,
      authorData: "",
      total: 0,
    };
  },
};
</script>
