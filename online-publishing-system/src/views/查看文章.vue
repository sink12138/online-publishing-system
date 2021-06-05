<template>
  <div>
    <div class="breadcrumb">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin/home' }">后台首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/documents' }">资源管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/documents/view' }">查看文章</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="document-info">
      <el-card class="info-card">
        <div slot="header" class="clearfix">
          <span>文章信息</span>
          <el-button
            @click="handleDelete(aritcleData.articleId)" 
            plain
            icon="el-icon-delete"
            style="float:right;padding:5px 8px">
          </el-button>
        </div>
        <div class="info-text">
          <p>文章编号：{{this.articleData.articleId}}</p>
          <p>文章标题：{{this.articleData.title}}</p>
          <p>摘要：{{this.articleData.articleAbstract}}</p>
          <p>关键字：{{this.articleData.keywords}}</p>
          <p>第一作者：{{this.articleData.firstAuthor}}</p>
          <p>其他作者：{{this.articleData.otherAuthors}}</p>
          <p>提交者作者编号：{{this.articleData.submitterId}}</p>
          <p>责任编辑编号：{{this.articleData.editorId}}</p>
          <p>文章出版状态：{{this.articleData.status}}</p>
          <p>出版编号：{{this.articleData.identifier}}</p>
          <p>出版日期: {{this.articleData.publishingDate}}</p>
        </div>
      </el-card>
    </div>
  </div>
</template>

<style scoped>
.document-info {
  position: relative;
  margin: 0 auto;
  width: 650px ;
  height: 650px;
}
.info-card {
  background-color: #A3D0C3;
}
.el-button {
  background-color: #FFF;
}
.info-text {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
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
          this.articleData = res.data[(sessionStorage.getItem("adminDocIndex"))+1];
          console.log(this.articleData)
        })
    },
    methods:{
      handleDelete(articleId) {
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
      }
    },
    data() {
      return {
        articleData:''
      }
    }
  }
</script>