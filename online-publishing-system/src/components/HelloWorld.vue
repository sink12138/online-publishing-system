<template>
  <div class="hello">
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
    <h1>{{ msg }}</h1>
    <p>
      如果您想使用网上出版系统的更多功能,请您先<a href="/login">登录</a>.<br />
      如果您还未注册，请点击<a href="/register">此处</a>。
    </p>
  </div>
</template>

<script>
const axios = require("axios");
export default {
  name: "HelloWorld",
  props: {
    msg: String,
  },
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
a {
  color: #42b983;
}
.table {
  padding: 50px 250px 100px 250px;
}
</style>
