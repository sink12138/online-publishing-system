<template>
  <div class="reviewer">
    <div>
      <h1>所有审稿人名单</h1>
    </div>
    <div class="reviewers">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column label="审稿人 ID" prop="reviewerId"> </el-table-column>
        <el-table-column label="真实姓名" prop="realName"> </el-table-column>
        <el-table-column label="所属组织" prop="institution"></el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        tableData: [{
          reviewerId:"",
          realName:"",
          institution:""
        }]
      }
    }
  }
</script>
