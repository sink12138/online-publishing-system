<template>
  <div class="reviewer">
    <div>
      <h1>所有审稿人名单</h1>
    </div>
    <div class="authors">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column label="作者 ID" prop="authorId"> </el-table-column>
        <el-table-column label="真实姓名" prop="realName"> </el-table-column>
        <el-table-column label="所属机构" prop="institution"></el-table-column>
        <el-table-column label="研究方向" prop="researchInterests"></el-table-column>
        <el-table-column label="绑定的文章数量" prop="articleCount"></el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        tableData: [{
          authorId:"",
          realName:"",
          institution:"",
          researchInterests:"",
          articleCount:""
        }]
      }
    }
  }
</script>
