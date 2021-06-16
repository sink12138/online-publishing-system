<template>
  <div class="about">
    <h1>作者主页</h1>
    <router-link to="/author/submit">
      <el-button type="info" plain> 提交新文章 </el-button>
    </router-link>
    <router-link to="/author/claim">
      <el-button type="info" plain> 认领文章 </el-button>
    </router-link>
    <el-button type="info" plain @click="dialogTableVisible = true">
      查看编辑
    </el-button>
    <el-dialog title="编辑信息" :visible.sync="dialogTableVisible">
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
    <div class="articles">
      <child title="我的文章"></child>
    </div>
  </div>
</template>

<script>
import child from "../components/我的文章.vue";
export default {
  name: "Search",
  components: {
    child,
  },
  data() {
    return {
      gridData: [],
      dialogTableVisible: false,
      results: 0,
    }
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