<template>
  <el-table
    :data="tableData3"
    height="500"
    border
    style="width: 100%">
    <el-table-column
      prop="authorId"
      label="作者编号"
      width="360">
    </el-table-column>
    <el-table-column
      prop="realName"
      label="真实姓名"
      width="360">
    </el-table-column>
    <el-table-column
      prop="Institution"
      label="所属机构"
      width="360">
    </el-table-column>
    <el-table-column
      prop="researchInterests"
      label="研究方向"
      width="360">
    </el-table-column>
    <el-table-column
      prop="articleCount"
      label="绑定的文章数量"
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