<template>
  <el-table
    :data="tableData"
    style="width: 100%">
    <el-table-column type="expand">
      <template slot-scope="props">
        <el-form label-position="left" inline class="demo-table-expand">
          <el-form-item label="文章ID">
            <span>{{ props.row.ID }}</span>
          </el-form-item>
          <el-form-item label="文章标题">
            <span>{{ props.row.Name }}</span>
          </el-form-item>
          <el-form-item label="关键字">
            <span>{{ props.row.keyword }}</span>
          </el-form-item>
          <el-form-item label="第一作者">
            <span>{{ props.row.firstAuthor }}</span>
          </el-form-item>
          <el-form-item label="其他作者">
            <span>{{ props.row.otherAuthors }}</span>
          </el-form-item>
          <el-form-item label="文章状态">
            <span>{{ props.row.state }}</span>
          </el-form-item>
        </el-form>
      </template>
    </el-table-column>
    <el-table-column
      label="文章 ID"
      prop="ID">
    </el-table-column>
    <el-table-column
      label="文章标题"
      prop="Name">
    </el-table-column>
    <el-table-column
      label="文章状态"
      prop="state">
    </el-table-column>
    <el-table-column
      fixed="right"
      label="操作"
      width="100">
      <template slot-scope="scope">
        <el-button type="text" size="small" @click="withdraw(scope.row)">撤稿</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<style>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
</style>

<script>
const axios = require('axios');
  export default {
    data() {
      return {
        tableData: [{
          ID: '',
          Name: '',
          keyword: '',
          firstAuthor: '',
          otherAuthors: '',
          state: '',
        }]
      }
    },
    created(){
      this.convert();
    },
    methods: {
        convert: function () {
        axios.get("/author/articles").then(res => {
          this.tableData = res.data;
        })
        },
        withdraw(row) {
        axios.post("/author/withdraw",row.ID);
        console.log(row);
      }
    }
  }
</script>