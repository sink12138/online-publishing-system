<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin/home' }">后台首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/admin/reviewers' }">查看审稿人</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="table">
      <el-table
        :data="accountData.slice((currentPage-1)*pagesize,currentPage*pagesize)"
        :header-cell-style="{height:'60px'}"
        style="height: 100%;width: 100%;padding-top:10px;scoped">
        <el-table-column
          prop="accountId"
          label="账号编号"
          width="100"
          height="10">
        </el-table-column>
        <el-table-column
          prop="reviewerId"
          label="审稿人编号"
          width="100"
          height="10">
        </el-table-column>
        <el-table-column
          prop="email"
          label="账号邮箱"
          width="250">
        </el-table-column>
        <el-table-column
          prop="password"
          label="账号密码"
          width="100">
        </el-table-column>
        <el-table-column
          prop="realName"
          label="真实姓名"
          width="100">
        </el-table-column>
        <el-table-column
          prop="organization"
          label="学术组织"
          width="180">
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
        background
        layout="prev, pager, next, jumper"
        :total="total"
        :page-size="10"
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
          url: 'http://82.156.190.251:80/apis/admin/select/reviewers',
        }).then(res =>{
          console.log(res.data)
          this.total = res.data[0].results
          this.accountData = res.data.slice(1)
        })
    },
    methods: {
      handleCurrentChange(val) {
        this.currentPage = val;
      },
    },
    data() {
      return {
        pagesize:10,
        currentPage:1,
        accountData:'',
        total:0
      }
    }
  }
</script>