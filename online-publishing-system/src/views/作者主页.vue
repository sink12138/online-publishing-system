<template>
  <div class="about">
    <el-tabs tab-position="left" v-model="activeName">
      <el-tab-pane label="我的文章" name="first">
        <Article class="article"></Article>
      </el-tab-pane>
      <el-tab-pane label="提交文章" name="second">
        <div class="come-to">
          <router-link to="/author/submit">
            <el-button> 前往该页面 </el-button>
          </router-link>
        </div>
        <submit class="submit"></submit>
      </el-tab-pane>
      <el-tab-pane label="认领文章" name="third">
        <div class="come-to">
          <router-link to="/author/claim">
            <el-button> 前往该页面 </el-button>
          </router-link>
        </div>
        <claim class="claim"></claim>
      </el-tab-pane>
    </el-tabs>
    <el-button type="info" plain @click="dialogTableVisible = true">
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
.come-to {
  position: fixed;
}
.button {
  position: fixed;
  left: 60px;
  bottom: 60px;
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
  position: relative;
  margin-right: 20px;
}
</style>

<script>
import articles from "../components/我的文章.vue";
import claim from "../views/认领文章.vue";
import submit from "../views/提交文章.vue";
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
    Claim: claim,
    Submit: submit,
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