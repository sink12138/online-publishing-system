<template>
  <div>
    <div class="accounts-header">
      <div class="breadcrumb">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/admin/home' }">后台首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/accounts' }">查看账号</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="accounts-add">
        <router-link to="/admin/accounts/add">
          <el-button 
          icon="el-icon-circle-plus-outline">
          </el-button>
        </router-link>
      </div>
    </div>
    <div class="table">
      <el-table
        :data="accountData.slice((currentPage-1)*pagesize,currentPage*pagesize)"
        :header-cell-style="{height:'60px'}"
        style="height: 100%;width: 100%;padding-top:10px;">
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
        <el-table-column
          label="删除账号"
          width="100">
          <template slot-scope="scope">
            <el-button 
              @click="handleDelete(scope.$index, accountData, scope.row.accountId)" 
              type="danger"
              icon="el-icon-delete" >
            </el-button>
          </template>
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
    </div>
    <router-view></router-view>
  </div>
</template>

<style>
.breadcrumb {
  position: relative;
  text-align: center;
}
.accounts-add .el-button{
  position: fixed;
  right: 120px;
  top: 120px;
  height: 90px;
  width: 90px;
  font-size: 60px;
  background-color: #A3D0C3;
}
.accounts-add .el-button i{
  position: relative;
  left: -5px;
}
.table {
  position: fixed;
  text-align: center;
}
.table .el-button {
  height: 30px;
  width: 45px;
  opacity: 40%;
}
.table .el-button i{
  position: relative;
  left: -5px;
  top: -4px;
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
      handleDelete(index, rows, accountId){
        this.$prompt('请输入管理员密码', '确认删除账号', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(({ value }) => {
          if (value == sessionStorage.getItem("adminPassword")) {
            this.$message({
              type: 'success',
              message: '删除账号成功',
            })
            rows.splice(index,1);
            console.log(accountId);
          }
          else this.$message.error('密码错误');
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消删除'
          });       
        });
      }
    },
    data() {
      return {
        pagesize:8,
        currentPage:1,
        accountData:'',
        total:7
      }
    }
  }
</script>