<template>
  <el-table
    :data="tableData3"
    height="500"
    border
    style="width: 100%">
    <el-table-column
      prop="articleBufferId"
      label="文章临时编号"
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