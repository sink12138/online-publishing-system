<template>
  <el-table
    :data="tableData3"
    height="500"
    border
    style="width: 100%">
    <el-table-column
      prop="articleId"
      label="认领的文章编号"
      width="360">
    </el-table-column>
    <el-table-column
      prop="authorId"
      label="认领文章的作者编号"
      width="360">
    </el-table-column>
    <el-table-column
      prop="operation"
      label="文章操作"
      width="360">
    </el-table-column>
  </el-table>
</template>
<script>
  export default {
    data() {
      return {
        tableData: []
      }
    }
  }
</script>