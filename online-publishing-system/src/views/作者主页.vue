<template>
  <div class="about">
    <h1>我的文章</h1>
    <Article class="article"></Article>
    <el-button type="ops" plain @click="dialogTableVisible = true">
      查看编辑
    </el-button>
    <el-dialog title="编辑信息" :visible.sync="dialogTableVisible" :modal-append-to-body='false'>
      <el-table :data="gridData">
        <el-table-column
          property="name"
          label="姓名"
          width="200"
        ></el-table-column>
        <el-table-column property="email" label="邮箱"></el-table-column>
      </el-table>
    </el-dialog>
    <br />
    <router-view></router-view>
  </div>
</template>

<style scoped>
.about {
  opacity: 0.7;
}
.el-button--ops.is-active,
.el-button--ops:active {
  background: #000000;
  border-color: #000000;
  color: #fff;
}
.el-button--ops:focus,
.el-button--ops:hover {
  background: #ababab;
  border-color: #ababab;
  color: #000000;
}
.el-button--ops {
  color: #fff;
  background: #000000;
  border-color: #000000;
  position: absolute;
  bottom: 60px;
  left: 150px;
  margin-right: 20px;
}
</style>

<script>
import articles from "../components/我的文章.vue";
export default {
  data() {
    return {
      activeName: "first",
      gridData: [],
      dialogTableVisible: false,
      results: 0,
    };
  },
  mounted() {},
  components: {
    Article: articles,
  },
  created: function () {
    this.$axios({
      method: "get",
      url: "http://82.156.190.251:80/apis/author/editors",
      responseTpe: "blob",
    })
      .then((response) => {
        console.log(response);
        var arraylist = new Array();
        arraylist = response.data;
        this.success = arraylist[0].success;
        if (this.success == true) {
          this.results = arraylist[0].results;
          this.gridData = arraylist.slice(1);
          console.log(this.gridData);
        }
      })
      .catch((err) => console.log(err));
  },
};
</script>