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
                <span>{{ props.row.articleId }}</span>
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
        <el-table-column label="文章 ID" prop="articleId"> </el-table-column>
        <el-table-column label="文章标题" prop="title"> </el-table-column>
        <el-table-column label="文章状态" prop="status"> </el-table-column>
        <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="scope"
            ><el-select v-model="scope.row.value" placeholder="是否通过">
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
            <el-button
              @click="submit(scope.row)"
              type="text"
              size="small"
              v-if="
                scope.row.comments !== undefined && scope.row.comments !== ''
              "
              >提交</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<style>
.Reviewer {
  background-color: #fff;
  opacity: 80%;
}
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
export default {
  data() {
    return {
      tableData: [],
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
      options: [{
          value: '选项1',
          label: '通过'
        }, {
          value: '选项2',
          label: '不通过'
        }],
        value: ''
    };
  },
  created() {
    this.convert();
  },
  methods: {
    downloadarticle(row) {
      this.$axios({
        method: "get",
        url: "http://82.156.190.251:80/apis/download",
        params: { articleId: row.articleId },
        responseTpe: "blob",
      }).then(
        (response) => {
          console.log(response);
          const filename = decodeURIComponent(
            response.headers["content-disposition"].split(";")[1].split("=")[1]
          );
          console.log(filename);
          this.load(response.data, filename);
        },
        (err) => {
          alert(err);
        }
      );
    },
    load(data,filename){
      if (! data) {
          return
        }
        let url = window.URL.createObjectURL(new Blob([data],{ type:'application/force-download;charset=utf-8'}))
        let link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.download = filename
        document.body.appendChild(link)
        link.click()
        document.body.removeChild(link)
        window.URL.revokeObjectURL(url)
    },
    submit(row) {
      console.log(row.articleId);
      this.$axios({
        method: "post",
        url: "http://82.156.190.251:80/apis/reviewer/review/submit",
        data: JSON.stringify({
          articleId: Number(row.articleId),
          pass: row.value,
          comments: row.comments,
        }),
      }).then(
        (response) => {
          console.log(response);
        },
        (err) => {
          alert(err);
        }
      );
      location.reload();
    },
    convert: function () {
      this.$axios
        .get("http://82.156.190.251:80/apis/reviewer/articles")
        .then((res) => {
          this.tableData = res.data.slice(1);
          console.log(this.tableData);
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
            message: "通过意见:" + row.value + ",你的评论是: " + value,
          });
          row.comments = value;
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消输入",
          });
        });
      console.log(row);
    },
  },
};
</script>
