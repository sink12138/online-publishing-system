<template>
  <div class="Search">
    <div class="table">
      <el-card class="box-card">
        <el-table
          :data="tableData"
          border
          stripe
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
          <el-table-column
            prop="articleId"
            label="文章ID"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="title"
            label="标题"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="keywords"
            label="关键词"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="firstAuthor"
            label="第一作者"
            align="center"
          ></el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope" class="active">
              <el-button
                @click="download(scope.row)"
                type="text"
                icon="el-icon-download"
              ></el-button>
            </template>
          </el-table-column>
          <el-table-column
            label="认领"
            align="center"
            v-if="$store.state.role >= 4"
          >
            <template slot-scope="scope" class="active">
              <el-button
                @click="claim(scope.row.articleId)"
                type="text"
                icon="el-icon-success"
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-row :gutter="20">
          <el-col :span="6" :offset="12">
            <div class="block">
              <el-pagination
                background
                @current-change="handleCurrentChange"
                :current-page.sync="currentPage"
                :page-size=8
                layout="total, prev, next, jumper, pager"
                :total="total"
              ></el-pagination>
            </div>
          </el-col>
        </el-row>
      </el-card>
    </div>
  </div>
</template>

<script>
const axios = require("axios");
export default {
  name: "Search",
  data() {
    return {
      search: {
        searchType: "title",
        searchString: "",
      },
      success: false,
      message: "",
      results: 0,
      currentPage: 1, //当前页数
      pageSize: 10, //每页获取条数（页面大小）
      tableData: [{
        articleId: "",
        title: "",
        keywords: "",
        firstAuthor: "",
      }], //存放从后端传来的数据
    };
  },
  props: ["table"],
  mounted() {
    this.fetchdata();
  },
  methods: {
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
    },
    claim(articleId) {
      axios({
        method: "post",
        url: "http://82.156.190.251:80/apis/author/claim",
        data: JSON.stringify({
          articleId: articleId,
        }),
      }).then((res) => {
        console.log(res);
      });
      console.log("submit!");
    },
    fetchdata() {
      axios({
        methods: "get",
        url: "http://82.156.190.251:80/apis/search",
        params: JSON.stringify({
          searchType: this.search.searchType,
          searchString: this.search.searchString,
        }),
      }).then(
        (response) => {
          var arraylist = new Array();
          arraylist = response.data;
          this.success = response.success;
          if (this.success == true) {
            this.tableData = arraylist.slice(0);
          }
        },
        (err) => {
          alert(err);
        }
      );
    },
  },
};
</script>

<style scoped>
.table {
  padding: 50px 250px 100px 250px;
}
</style>
