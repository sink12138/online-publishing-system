<template>
    <div class="chart">
      <canvas id="myChart"></canvas>
    </div>
</template>

<style scoped>
.chart {
  height: 600px;
  width: 600px;
  font-size: 13px;
  color: rgb(57, 214, 175);
}
</style>

<script>
import Chart from 'chart.js/auto'
Chart.defaults.font.size = 18;
export default {
  mounted() {
    var ctx = document.getElementById('myChart');
    var myChart = new Chart(ctx, {
      type: 'pie',
      data: {
        labels: ['读者','作者','审稿人','编辑'],
        datasets: [{
          label: 'User Composition',
          data: [12,19,3,10],
          backgroundColor: [
            'rgba(65, 167, 70, 0.9)',
            'rgba(57, 214, 175, 0.9)',
            'rgba(80, 238, 185, 0.9)',
            'rgba(75, 192, 192, 0.9)',
          ],
          hoverOffset: 4,
          borderWidth: 0
        }],
      },
    })
  }
}
</script>