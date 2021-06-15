<template>
  <div class="about">
    <h1>作者主页</h1>
    <router-link to="/author/submit">
      <el-button type="info" plain>
        提交新文章
      </el-button>
    </router-link >
    <router-link to="/author/claim">
      <el-button type="info" plain>
        认领文章
      </el-button>
    </router-link>
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
};
</script>