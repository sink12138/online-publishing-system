import { Pie } from 'vue-chartjs'
//Chart.defaults.font.size = 18;
export default {
  extends: Pie,
  props: {
    chartdata: {
        labels: ['读者','作者','审稿人','编辑'],
        datasets: [{
          label: 'User Composition',
          data: [this.userNums,this.authorNums,this.reviewerNums,this.editorNums],
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
    options: {
      type: Object,
      default: null
    }
  },
  mounted () {
    this.renderChart(this.chartdata, this.options)
  }
}