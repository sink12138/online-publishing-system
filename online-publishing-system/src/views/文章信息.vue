<template>
  <div class="article">
    <div>
      <h1>文章信息页面</h1>
    </div>
    <div class="articleinfo">
      <el-card class="info-card">
        <div slot="header" class="clearfix">
          <span>文章信息</span>
        </div>
        <div class="info-text">
          <p>文章标题：{{ this.articleData.title }}</p>
          <p>摘要：{{ this.articleData.articleAbstract }}</p>
          <p>关键字：{{ this.articleData.keywords }}</p>
          <p>第一作者：{{ this.articleData.firstAuthor }}</p>
          <p>其他作者：{{ this.articleData.otherAuthor }}</p>
          <p>出版编号：{{ this.articleData.identifier }}</p>
          <p>出版日期: {{ this.articleData.publishingDate }}</p>
        </div>
      </el-card>
    </div>
    <br />
    <div class="button">
      <el-button type="info" @click="downloadarticle()" v-if="this.$store.state.isLogin"
        >点击下载本篇文章<i class="el-icon-download el-icon--right"></i
      ></el-button>
    </div>
    <div>
      <el-table class="myarticles" :data="authorData" style="width: 100%">
        <el-table-column label="作者姓名" prop="Name"> </el-table-column>
        <el-table-column label="ID" prop="Id"> </el-table-column>
        <el-table-column fixed="right" label="操作" width="400">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="AuthorInfo(scope.row)"
              >查看作者</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<style scoped>
.article {
  opacity: 0.7;
}
</style>

<script>
export default {
  name: "article",
  data() {
    return {
      name: "",
      Id: 0,
      articleData: {},
      otherAuthorName: "",
      authorData: [],
      key: [],
    };
  },
  mounted: function () {
    this.articleId = sessionStorage.getItem("articleId");
    console.log(this.articleId);
    this.$axios({
      method: "get",
      url: "http://82.156.190.251:80/apis/article",
      params: { articleId: this.articleId },
    }).then((res) => {
      console.log(res.data);
      this.key=Object.keys(res.data.authorMap);
      var tmp=0;
      for (tmp;tmp<this.key.length;tmp++) {
        var temp = {};
        this.name=this.key[tmp];
        this.Id=res.data.authorMap[this.name];
        temp = {
          Name: this.name,
          Id: this.Id,
        };
        this.authorData.push(temp);
      }
      console.log(this.authorData);
      var index = Number(sessionStorage.getItem("articleId"));
      this.articleData = res.data;
      console.log(this.articleData);
      var str = "";
      for (var i = 0; i < this.articleData.otherAuthors.length - 1; i++) {
        str += this.articleData.otherAuthors[i] + ",";
      }
      str +=
        this.articleData.otherAuthors[this.articleData.otherAuthors.length - 1];
      this.articleData.otherAuthor = str;
    });
  },
  methods: {
    downloadarticle() {
      this.$axios({
        method: "get",
        url: "http://82.156.190.251:80/apis/download",
        params: { articleId: this.articleId },
        responseTpe: "blob",
      }).then(
        (response) => {
          console.log(response);
          const filename = decodeURIComponent(
            response.headers["content-disposition"].split(";")[1].split("=")[1]
          );
          console.log(filename);
          this.load(response.data, filename);
          console.log("下载中");
        },
        (err) => {
          alert(err);
        }
      );
    },
    AuthorInfo(row) {
      sessionStorage.setItem("authorId", row.Id);
      window.location.href = "../infos";
    },
    load(data, filename) {
      if (!data) {
        return;
      }
      let url = window.URL.createObjectURL(
        new Blob([data], { type: "application/force-download;charset=utf-8" })
      );
      let link = document.createElement("a");
      link.style.display = "none";
      link.href = url;
      link.download = filename;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      window.URL.revokeObjectURL(url);
    },
    columnStyle({ columnIndex }) {
      if (columnIndex == 1 || columnIndex == 3) {
        return "background:#f3f6fc;";
      } else {
        return "background:#ffffff;";
      }
    },
    objectSpanMethod({ rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        if (rowIndex % 4 === 0) {
          return {
            rowspan: 4,
            colspan: 1,
          };
        } else {
          return {
            rowspan: 0,
            colspan: 0,
          };
        }
      }
    },
  },
};
</script>
