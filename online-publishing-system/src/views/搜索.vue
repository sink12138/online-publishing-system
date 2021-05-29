<template>
  <div class="Search">
    <div>
      <input type="text" style="height:21.5px;width:400px">
      <router-link to="/search"><el-button type="primary" icon="el-icon-search" size="mini">搜索</el-button></router-link>
      <router-view />
    </div>
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
            type="selection"
            width="55"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="id"
            label="文章ID"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="name"
            label="标题"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="sex"
            label="关键词"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="college"
            label="第一作者"
            align="center"
          ></el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope" class="active">
              <el-button
                @click="editData(scope.row)"
                type="primary"
                icon="el-icon-edit"
                circle
              ></el-button>
              <el-button
                @click="removeData(scope.row.id)"
                type="danger"
                icon="el-icon-delete"
                circle
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
                :page-size="pageSize"
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
      currentPage: 1, //当前页数
      pageSize: 10, //每页获取条数（页面大小）
      tableData: [], //存放从后端传来的数据
    };
  },
  mounted() {
    this.fetchdata();
  },
  methods: {
    //获取后端数据
    fetchdata() {
      axios.get("url").then((response) => {
        this.tableData = response.data;
      });
    },
  },
};
</script>

<style scoped>
.table {
  padding: 50px 250px 100px 250px;
}
</style>
