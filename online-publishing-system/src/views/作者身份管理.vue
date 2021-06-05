<template>
    <div class="editor/authors">
        <h1>作者身份管理</h1>
        <router-link to="/editor/cancel/authors">
          <el-row>
            <el-button type="primary">撤销作者身份</el-button>
          </el-row>
        </router-link>
        <router-link to="/editor/check/authors">
            <el-row>
                <el-button type="primary">查看所有作者</el-button>
            </el-row>
        </router-link>
    </div>
</template>
<script>
  export default {
    methods: {
      }
    }
</script>
