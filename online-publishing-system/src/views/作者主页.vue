<template>
  <div class="about">
    <h1>作者主页</h1>
    <router-link to="/author/submit">提交新文章</router-link> |
    <router-link to="/author/claim">认领文章</router-link>
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
