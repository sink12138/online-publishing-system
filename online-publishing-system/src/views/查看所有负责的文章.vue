<template>
  <el-table
    :data="tableData3"
    height="500"
    border
    style="width: 100%">
    <el-table-column
      prop="articleid"
      label="文章编号"
      width="360">
    </el-table-column>
    <el-table-column
      prop="name"
      label="文章名称"
      width="360">
    </el-table-column>
    <el-table-column
      prop="authors"
      label="作者"
      width="360">
    </el-table-column>
    <el-table-column
      prop="state"
      label="文章状态"
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