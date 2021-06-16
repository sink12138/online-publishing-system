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
  background-color: #ff8686;
}
.articles {
  margin-left: 60px;
  margin-top: 60px;
  width: 400px;
  height: 200px;
  background-color: #fff386;
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
      load: false,
    }
  },
  mounted() {
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
    })
    this.$axios({
      method:'get',
      url: 'http://82.156.190.251:80/apis/admin/select/reviewers',
    }).then(res =>{
      this.reviewerNums = res.data[0].results
    })
    this.$axios({
      method:'get',
      url: 'http://82.156.190.251:80/apis/admin/select/editors',
    }).then(res =>{
      this.editorNums = res.data[0].results
    })
    var ctx = document.getElementById('myChart');
      var myChart = new Chart(ctx, {
        type: 'pie',
        data: {
          labels: ['作者','审稿人','编辑'],
          datasets: [{
            label: 'User Composition',
            data: [15,13,14],
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
}
</script>