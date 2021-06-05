<template>
    <div class="editor/reviewers">
        <h1>审稿人身份管理</h1>
        <router-link to="/editor/cancel/reviewers">
          <el-row>
            <el-button type="primary" >撤销审稿人身份</el-button>
          </el-row>
        </router-link>
        <router-link to="/editor/check/reviewers">
            <el-row>
                <el-button type="primary">查看所有审稿人</el-button>
            </el-row>
        </router-link>
        <router-link to="/editor/certify/reviewers">
            <el-row>
                <el-button type="primary">认证审稿人身份</el-button>
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
