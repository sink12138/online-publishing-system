<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin/home' }">后台首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/admin/documents' }">资源管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/admin/documents/number-search' }">编号查找</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="keyinput">
      <el-input
        placeholder="编号检索..."
        prefix-icon="el-icon-search"
        v-model="input">
      </el-input>
    </div>
    <div class="table">
      <el-table
        :data="items.slice((currentPage-1)*pagesize,currentPage*pagesize)"
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
        <el-table-column
          label="查看文章"
          width="100">
          <template slot-scope="scope">
            <el-button
              @click="handleInfo(scope.$index)" 
              type="info"
              icon="el-icon-document" >
            </el-button>
          </template>
        </el-table-column>
        <el-table-column
          label="删除文章"
          width="100">
          <template slot-scope="scope">
            <el-button
              @click="handleDelete(scope.$index, articleData, scope.row.articleId)" 
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
        :page-size="6"
        @current-change="handleCurrentChange"
        :current-page="currentPage">
        </el-pagination>
      </div>
    </div>
    <router-view></router-view>
  </div>
</template>

<style scoped>
.breadcrumb {
  position: relative;
  text-align: center;
}
.el-input {
  position: relative;
  left: -400px;
  text-align: left;
  margin: 40px;
  width: 400px;
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
          url: 'http://82.156.190.251:80/apis/admin/select/articles',
        }).then(res =>{
          console.log(res.data)
          this.total = res.data[0].results
          this.articleData = res.data.slice(1)
        })
    },
    methods: {
      handleCurrentChange(val) {
        this.currentPage = val;
      },
      handleDelete(index, rows, articleId){
        this.$prompt('请输入管理员密码', '确认删除文章', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(({ value }) => {
          if (value == sessionStorage.getItem("adminPassword")) {
            var JsonArticle = {"articleId":articleId};
            this.$axios({
              method:'post',
              url:'http://82.156.190.251:80/apis/admin/delete/article',
              data:JsonArticle,
            }).then(res =>{
              if (res.data.success == true) {
                this.$message({
                  type: 'success',
                  message: '删除文章成功',
                })
                rows.splice(index,1);
                console.log(articleId);
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
      handleInfo(index) {
        sessionStorage.setItem("adminDocIndex",index);
        window.location.href='../admin/documents/view'
      }
    },
    data() {
      return {
        pagesize:6,
        currentPage:1,
        articleData:'',
        input:'',
        total:0,
      }
    },
    computed: {
      items:function() {
        var number = parseInt(this.input);
        if (number) {
          return this.articleData.filter(function(e){
            return Object.keys(e).some(function(){
              return e.articleId.is(number);
            })
          })
        }
        return this.articleData;
      }
    }
  }
</script>