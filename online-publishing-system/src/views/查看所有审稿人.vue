<template>
  <el-table
    :data="tableData3"
    height="500"
    border
    style="width: 100%">
    <el-table-column
      prop="reviewerId"
      label="审稿人编号"
      width="360">
    </el-table-column>
    <el-table-column
      prop="name"
      label="真实姓名"
      width="360">
    </el-table-column>
    <el-table-column
      prop="authors"
      label="所属组织"
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