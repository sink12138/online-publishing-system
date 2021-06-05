<template>
  <div class="reviewer">
    <div>
      <h1>负责的文章如下</h1>
    </div>
    <div class="articles">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column label="文章 ID" prop="articleId"> </el-table-column>
        <el-table-column label="文章名称" prop="title"> </el-table-column>
        <el-table-column label="关键词" prop="keywords"></el-table-column>
        <el-table-column label="作者" prop="authors"> </el-table-column>
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="第一作者">
                <span>{{ props.row.firstAuthor }}</span>
              </el-form-item>
              <el-form-item label="其他作者">
                <span>{{ props.row.otherAuthors }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column label="文章状态" prop="status"> </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        tableData: [{
          articleId:" ",
          title:"",
          keywords:" ",
          authors:" ",
          status:" "
        }]
      }
    }
  }
</script>
