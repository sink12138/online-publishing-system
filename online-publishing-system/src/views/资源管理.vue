<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin/home' }">后台首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/admin/documents' }">资源管理</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="table">
      <el-table
        :data="accountData.slice((currentPage-1)*pagesize,currentPage*pagesize)"
        :header-cell-style="{height:'60px'}"
        style="height: 100%;width: 100%;padding-top:10px;scoped">
        <el-table-column
          prop="articleId"
          label="文章编号"
          width="180"
          height="10">
        </el-table-column>
        <el-table-column
          prop="title"
          label="文章标题"
          width="180"
          height="10">
        </el-table-column>
        <el-table-column
          prop="submitterId"
          label="提交作者编号"
          width="180">
        </el-table-column>
        <el-table-column
          prop="editorId"
          label="责任编辑编号"
          width="180">
        </el-table-column>
        <el-table-column
          prop="status"
          label="文章出版状态"
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
          url: '/1.json',
        }).then(res =>{
          console.log(res.data)
          this.accountData = res.data
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
        total:100,
      }
    }
  }
</script>