<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin/home' }">后台首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/admin/editors' }">查看编辑</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="editors-add">
      <el-popover
        placement="right"
        width="210"
        trigger="click">
        <el-input placeholder="请输入邮箱" v-model="editorForm.email">
          <el-button 
            slot="append" 
            icon="el-icon-check" 
            @click="handleEditor()">
          </el-button>
        </el-input>
        <el-button slot="reference">添加编辑</el-button>
      </el-popover>
    </div>
    <div class="table">
      <el-table
        :data="editorData.slice((currentPage-1)*pagesize,currentPage*pagesize)"
        :header-cell-style="{height:'60px'}"
        style="height: 100%;width: 100%;padding-top:10px;">
        <el-table-column
          prop="accountId"
          label="账号编号"
          width="100"
          height="10">
        </el-table-column>
        <el-table-column
          prop="editorId"
          label="编辑编号"
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
          width="180">
        </el-table-column>
        <el-table-column
          prop="realName"
          label="真实姓名"
          width="180">
        </el-table-column>
        <el-table-column
          label="删除编辑"
          width="100">
          <template slot-scope="scope">
            <el-button 
              @click="handleDelete(scope.$index, editorData, scope.row.editorId)" 
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
        :page-size="8"
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
.editors-add .el-button{
  position: fixed;
  left: 240px;
  bottom: 40px;
  height: 50px;
  width: 100px;
}
.table {
  position: fixed;
  text-align: center;
}
.table .el-button {
  height: 30px;
  width: 45px;
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
          url: 'http://82.156.190.251:80/apis/admin/select/editors',
        }).then(res =>{
          console.log(res.data)
          this.total = res.data[0].results
          this.editorData = res.data.slice(1)
        })
    },
    methods: {
      handleCurrentChange(val) {
        this.currentPage = val;
      },
      handleDelete(index, rows, editorId){
        this.$prompt('请输入管理员密码', '确认删除账号', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(({ value }) => {
          if (value == sessionStorage.getItem("adminPassword")) {
            var JsonEditor = {"editorId":editorId};
            this.$axios({
              method:'post',
              url:'http://82.156.190.251:80/apis/admin/delete/editor',
              data:JsonEditor,
            }).then(res =>{
              console.log(res)
              if (res.data.success == true) {
                this.$message({
                  type: 'success',
                  message: '删除账号成功',
                })
                rows.splice(index,1);
                console.log(editorId);
              } else {
                this.$message.error(res.data.message)
              }
            })
            .catch(error => console.log(error))
          }
          else this.$message.error('密码错误');
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消删除'
          });       
        });
      },
      handleEditor() {
        console.log(this.editorForm);
        let JsonEmail = JSON.stringify(this.editorForm);
        console.log(JsonEmail);
        this.$axios({
            method:"post",
            url:'http://82.156.190.251:80/apis/admin/insert/editor',
            data:JsonEmail,
        })
        .then((res)=>{
            console.log(res);
            if (res.data.success == true) {
              this.$message({
                showClose: true,
                message: '添加编辑成功',
                type: 'success'
              });
            }
            else {
              this.$message({
                showClose: true,
                message: res.data.message,
                type: 'error'
              });
            }
        })
        .catch(error => console.log(error))
      }
    },
    data() {
      return {
        pagesize:8,
        currentPage:1,
        editorData:'',
        total:0,
        editorForm:{
          email:'',
        }
      }
    }
  }
</script>