<template>
  <div class="reviewer">
    <div>
      <h1>您待审阅的文章如下</h1>
      <router-link to="/">返回主页</router-link>
    </div>
    <div class="articles">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="文章ID">
                <span>{{ props.row.articleID }}</span>
              </el-form-item>
              <el-form-item label="文章标题">
                <span>{{ props.row.title }}</span>
              </el-form-item>
              <el-form-item label="关键字">
                <span>{{ props.row.keywords }}</span>
              </el-form-item>
              <el-form-item label="第一作者">
                <span>{{ props.row.firstAuthor }}</span>
              </el-form-item>
              <el-form-item label="其他作者">
                <span>{{ props.row.otherAuthors }}</span>
              </el-form-item>
              <el-form-item label="处理状态">
                <span>{{ props.row.status }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column label="文章 ID" prop="articleID"> </el-table-column>
        <el-table-column label="文章标题" prop="title"> </el-table-column>
        <el-table-column label="文章状态" prop="status"> </el-table-column>
        <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="scope"
            ><el-select v-model="value" placeholder="是否通过">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
            <el-button
              type="text"
              size="small"
              @click="downloadarticle(scope.row)"
              >下载<i class="el-icon-download el-icon--right"></i
            ></el-button>
            <el-button @click="open(scope.row)" type="text" size="small"
              >评价</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<style>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>

<script>
const axios = require("axios");
export default {
  data() {
    return {
      tableData: [
        {
          ID: 12345,
          Name: "test",
          keyword: "test,abstract",
          firstAuthor: "abc",
          otherAuthors: "123,456",
          state: "审核中",
        },
      ],
      options: [
        {
          value: true,
          label: "通过",
        },
        {
          value: false,
          label: "不通过",
        },
      ],
      value: false,
      comments: "",
    };
  },
  created() {
    this.convert();
  },
  methods: {
    downloadarticle(row) {
      axios({
        method: "get",
        url: "/download",
        params: JSON.stringify({ articleID: row.articleID }),
      }).then(
        (response) => {
          console.log(response);
        },
        (err) => {
          alert(err);
        }
      );
      alert("下载中");
    },
    convert: function () {
      axios.get("/reviewer/articles").then((res) => {
        this.tableData = res.data;
      });
    },
    open(row) {
      this.$prompt("请输入评论", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: "",
        showclose: true,
      })
        .then(({ value }) => {
          this.$message({
            type: "success",
            message: "通过意见:" + this.value + ",你的评论是: " + value,
          });
          this.comments = value;
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消输入",
          });
        });
      console.log(row);
      axios({
        method: "post",
        url: "/reviewer/review/submit",
        params: JSON.stringify({
          articleID: row.articleID,
          pass: this.value,
          comments: this.comments,
        }).then(
          (response) => {
            console.log(response);
          },
          (err) => {
            alert(err);
          }
        ),
      });
    },
  },
};
</script>