<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin/home' }">后台首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/admin/settings' }">网站设置</el-breadcrumb-item>
    </el-breadcrumb>
    
    <router-view/>
  </div>
</template>

<style scoped>

</style>

<script>
  export default {
    data() {
      return {
        articleId : 5
      }
    },
    methods: {
      
      /*handleDown() {
        var data = {
          articleId : this.articleId
        }
        this.$axios({
          method: 'get',
          url: 'http://82.156.190.251:80/apis/download',
          params: data,
          responseType: 'blob',
        }).then(res => {
          console.log(res)
          const filename = decodeURIComponent(res.headers['content-disposition'].split(';')[1].split('=')[1]);
          console.log(filename)
          this.download(res.data, filename)
        }).catch(err => console.log(err))
      },
      download (data, filename) {
        if (! data) {
          return
        }
        let url = window.URL.createObjectURL(new Blob([data],{ type:'application/force-download;charset=utf-8'}))
        let link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.download = filename
        document.body.appendChild(link)
        link.click()
        document.body.removeChild(link)
        window.URL.revokeObjectURL(url)
      }*/
    }
  }
</script>