<template>
  <div class="about">
    <h1>作者主页</h1>
<<<<<<< HEAD
    <router-link to="/author/upload">上传文章</router-link> |
    <router-link to="/author/submit">提交文章</router-link> |
=======
    <router-link to="/author/submit">提交新文章</router-link> |
>>>>>>> origin/frontend
    <router-link to="/author/claim">认领文章</router-link> |
    <router-link to="/">返回主页</router-link>
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
