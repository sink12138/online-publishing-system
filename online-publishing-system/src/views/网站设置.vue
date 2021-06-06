<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin/home' }">后台首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/admin/settings' }">网站设置</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="clean">
      <el-button
        plain
        @click="handleClean">
        清除无效信息
      </el-button>
    </div>
    <div class="log">
      <el-button
        plain
        @click="handleLog">
        下载Log
      </el-button>
    </div>
    <div class="download">
      <el-button
        plain
        @click="handleDown">
        下载
      </el-button>
    </div>
    <router-view/>
  </div>
</template>

<style scoped>
.clean {
  position: fixed;
  top: 180px;
  left: 400px;
}
.log {
  position: fixed;
  top: 360px;
  left: 400px;
}
.el-button {
  background-color: #79b6fb;
  height: 50px;
  width: 160px;
  font-size: 18px;
}
</style>

<script>
  export default {
    data() {
      return {
        articleId : 5
      }
    },
    methods: {
      handleClean() {
        this.$axios.post('http://82.156.190.251:80/apis/admin/clean')
        .then((res) =>{
          if (res.data.success == true) {
            this.$notify.success({
              title: '成功',
              message: '清除无效信息成功',
              showClose: false
            });
          } else {
            this.$notify.warning({
              title: '请重试',
              message: res.data.message,
              showClose: false
            });
          }
        })
      },
      handleLog() {
        this.$axios({
          method: 'get',
          url: 'http://82.156.190.251:80/apis/admin/logs',
          responseType: 'blob',
        }).then(res => {
          console.log(res)
          const filename = decodeURI(res.headers['content-disposition'].split(';')[1].split('=')[1]);
          console.log(filename)
          this.download(res.data, filename)
        }).catch(err => console.log(err))
      },
      handleDown() {
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
      }
    }
  }
</script>