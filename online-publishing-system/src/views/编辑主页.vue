<template>
  <div class="about">
    <el-tabs tab-position="left" v-model="activeName" >
        <el-tab-pane label="管理作者" name="first">
            <div class="come-to">
                <router-link to='/editor/authors'>
                    <el-button>
                        前往该页面
                    </el-button>
                </router-link>
            </div>
            <author class="author"></author>
        </el-tab-pane>
        <el-tab-pane label="管理审稿人" name="second">
            <div class="come-to">
                <router-link to='/editor/reviewers'>
                    <el-button>
                        前往该页面
                    </el-button>
                </router-link>
            </div>
            <reviewer class="reviewer"></reviewer>
        </el-tab-pane>
        <el-tab-pane label="管理文章" name="third">
            <div class="come-to">
                <router-link to='/editor/articles'>
                    <el-button>
                        前往该页面
                    </el-button>
                </router-link>
            </div>
            <Article class="article"></article>
        </el-tab-pane>
    </el-tabs>
    <router-view></router-view>
  </div>
</template>

<style scoped>
.come-to {
    position: fixed;
}
</style>

<script>
import authors from "../views/查看所有作者.vue";
import reviewers from "../views/查看所有审稿人.vue";
import articles from "../views/查看所有负责的文章.vue";
export default {
    data() {
        return {
            activeName: 'first',
        }
    },
    mounted () {},
      components: {
            'Author': authors,
            'Reviewer': reviewers,
            'Article': articles,
      }
}
</script>
