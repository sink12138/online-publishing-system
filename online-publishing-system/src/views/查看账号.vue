<template>
  <div>
    <div class="breadcrumb">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin' }">后台首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/account' }">查看账号</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="table">
      <el-table
        :data="accountData.slice((currentPage-1)*pagesize,currentPage*pagesize)"
        :header-cell-style="{height:'60px'}"
        style="height: 100%;width: 100%;padding-top:10px;scoped">
        <el-table-column
          prop="accountId"
          label="账号编号"
          width="180"
          height="10">
        </el-table-column>
        <el-table-column
          prop="email"
          label="账号邮箱"
          width="180">
        </el-table-column>
        <el-table-column
          prop="password"
          label="账号密码"
          width="180">
        </el-table-column>
        <el-table-column
          prop="realName"
          label="真实姓名"
          width="180">
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
        background
        layout="prev, pager, next, jumper"
        :total="total"
        :page-size="3"
        @current-change="handleCurrentChange"
        :current-page="currentPage">
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
    mounted:function() {
      this.$axios({
          method:'get',
          url: '/1.json',
        }).then(res =>{
          console.log(res.data)
          this.accountData = res.data
        })
    },
    methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      },
      handleCurrentChange(val) {
        this.currentPage = val;
      },
    },
    data() {
      return {
        pagesize:3,
        currentPage:1,
        accountData:'',
        total:7
      }
    }
  }
</script>