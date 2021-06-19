<template>
  <div class="about">
    <Article class="article"></article>
    <router-view></router-view>
  </div>
</template>

<script>
import articles from "../views/查看所有负责的文章.vue";
export default {
    data() {
        return {
            activeName: 'first',
        }
    },
    mounted () {},
      components: {
            'Article': articles,
      }
}
</script>
