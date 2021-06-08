<template>
  <div class="reviewer">
    <div>
      <h1>文章评论如下</h1>
    </div>
    <div>
      <el-input v-model="articleId">查找的文章编号 </el-input>
      <template
        ><el-button type="text" size="small" @click="searchArticle"
          >查找</el-button
        >
      </template>
    </div>
    <div class="articles">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column label="评论内容" prop="comments"> </el-table-column>
        <el-table-column label="是否通过" prop="pass"> </el-table-column>
        <el-table-column label="评论时间" prop="date"> </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      search: {
        articleId: 0,
      },
      tableData: [
        {
          comments: "",
          pass: "",
          date: "",
        },
      ],
    };
  },
  methods: {
    searchArticle() {
      this.$axios({
        method: "get",
        url: "http://82.156.190.251:80/apis/editor/reviews",
        params: this.search,
      })
        .then((response) => {
          console.log(this.search);
          console.log(response);
          var arraylist = new Array();
          arraylist = response.data;
          this.success = arraylist[0].success;
          if (this.success == true) {
            this.results = arraylist[0].results;
            this.tableData = arraylist.slice(1);
            console.log(this.tableData);
          }
        })
        .catch((err) => console.log(err));
    },
  },
};
</script>
