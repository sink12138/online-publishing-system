<template>
  <div class="home">
    <div class="info">
      <el-card class="accounts">
        <div slot="header" class="clearfix">
          <span>注册用户数量</span>
        </div>
        <div class="text item">
         {{this.accountNums}}
        </div>
      </el-card>
      <el-card class="articles">
        <div slot="header" class="clearfix">
          <span>提交文章数量</span>
        </div>
        <div class="text item">
         {{this.articleNums}}
        </div>
      </el-card>
    </div>
    <div class="chart">
      <canvas id="myChart"></canvas>
    </div>
    <div class="clean">
      <el-popconfirm
        @confirm="handleClean" 
        confirm-button-text='确认'
        cancel-button-text='取消'
        title="确认删除信息？">
        <el-button plain slot="reference">
        清除无效信息
        </el-button>
      </el-popconfirm>
    </div>
    <div class="log">
      <el-button
        plain
        @click="handleLog">
        下载Log
      </el-button>
    </div>
  </div>
</template>

<style scoped>
.home {
  display: flex;
}
.chart {
  margin-left: 200px;
  height: 600px;
  width: 600px;
}
.accounts {
  margin-left: 60px;
  margin-top: 60px;
  width: 400px;
  height: 200px;
  background-color: #6fe2ff;
  font-size: 35px;
  font-weight: 600;
}
.articles {
  margin-left: 60px;
  margin-top: 60px;
  width: 400px;
  height: 200px;
  background-color: #86fff5;
  font-size: 35px;
  font-weight: 600;
}
.clean {
  position: fixed;
  bottom: 20px;
  right: 80px;
}
.log {
  position: fixed;
  bottom: 70px;
  right: 80px;
}
.el-button {
  background-color: #79b6fb;
  height: 40px;
  width: 140px;
  font-size: 16px;
}
</style>

<script>
import Chart from 'chart.js/auto'
Chart.defaults.font.size = 18;
export default {
  data() {
    return {
      accountNums: 0,
      articleNums: 0,
      authorNums: 0,
      reviewerNums: 0,
      editorNums: 0,
      chartData: [],
      myChart: null,
      canvas: null
    }
  },
  watch:{
    canvas: function () {
      console.log('init')
      this.initchart()
    },
    chartData: {
      handler: function () { 
        this.updatechart()
      },
      deep: true
    },
  },
  mounted() {
    var el = document.getElementById('myChart');
    this.canvas = el;
    this.$axios({
      method:'get',
      url: 'http://82.156.190.251:80/apis/admin/select/articles',
    }).then(res =>{
      this.articleNums = res.data[0].results
    })
    this.$axios({
      method:'get',
      url: 'http://82.156.190.251:80/apis/admin/select/accounts',
    }).then(res =>{
      this.accountNums = res.data[0].results
    })
    this.$axios({
      method:'get',
      url: 'http://82.156.190.251:80/apis/admin/select/authors',
    }).then(res =>{
      this.authorNums = res.data[0].results
      this.chartData.push(this.authorNums)
    })
    this.$axios({
      method:'get',
      url: 'http://82.156.190.251:80/apis/admin/select/reviewers',
    }).then(res =>{
      this.reviewerNums = res.data[0].results
      this.chartData.push(this.reviewerNums)
    })
    this.$axios({
      method:'get',
      url: 'http://82.156.190.251:80/apis/admin/select/editors',
    }).then(res =>{
      this.editorNums = res.data[0].results
      this.chartData.push(this.editorNums)
    })
  },
  methods: {
    initchart:function() {
      console.log(this.chartData)
      var ctx = document.getElementById('myChart');
      this.myChart = new Chart(ctx, {
        type: 'pie',
        data: {
          labels: ['作者','审稿人','编辑'],
          datasets: [{
            label: 'User Composition',
            data: [],
            backgroundColor: [
              'rgba(57, 214, 175, 0.9)',
              'rgba(80, 238, 185, 0.9)',
              'rgba(75, 192, 192, 0.9)',
            ],
            hoverOffset: 4,
            borderWidth: 0
          }],
        },
      })
    },
    updatechart:function() {
      console.log(this.chartData)
      this.myChart.data.datasets[0].data = this.chartData;
      this.myChart.update()
    },
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
  }
}
</script>