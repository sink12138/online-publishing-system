<template>
  <div class="article">
    <div>
      <h1>文章信息页面</h1>
    </div>
    <div class="articleinfo">
      <el-table
        :show-header="false"
        :data="tableData"
        :span-method="objectSpanMethod"
        border
        :cell-style="columnStyle"
        style="width: 100%; margin-top: 20px"
      >
        <el-table-column prop="id" label="ID" width="240">
          <template slot-scope="scope">
            <div>
              <img :src="scope.row.id | setPicUrl" />
            </div>
          </template>
        </el-table-column>
        <el-table-column width="180" prop="name"></el-table-column>
        <el-table-column prop="amount1"></el-table-column>
        <el-table-column width="180" prop="amount2"></el-table-column>
        <el-table-column prop="amount3"></el-table-column>
      </el-table>
    </div>
    <br />
    <div class="download">
      <el-button type="primary" @click="downloadarticle(scope.row)"
        >点击下载本篇文章<i class="el-icon-download el-icon--right"></i
      ></el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "article",
  data() {
    return {
      dataForm: {},
    };
  },
  created() {
    this.convert();
  },
  computed: {
    tableData() {
      return [
        {
          id: this.dataForm.headImg,
          name: "文章标题",
          amount1: this.dataForm.title,
          amount2: "文章摘要",
          amount3: this.dataForm.articleAbstract,
        },
        {
          id: this.dataForm.headImg,
          name: "关键字",
          amount1: this.dataForm.keywords,
          amount2: "第一作者",
          amount3: this.dataForm.firstAuthor,
        },
        {
          id: this.dataForm.headImg,
          name: "其他作者",
          amount1: this.dataForm.otherAuthors,
          amount2: "作者编号",
          amount3: "",
        },
        {
          id: this.dataForm.headImg,
          name: "出版编号",
          amount1: this.dataForm.identifier,
          amount2: "出版日期",
          amount3: this.dataForm.publishingDate,
        },
      ];
    },
  },
  methods: {
    convert() {
      this.$axios.get("/test.json").then((res) => {
        console.log(JSON.stringify(res));
        this.dataForm = res.data;
      });
    },
    downloadarticle(row) {
      this.$axios({
        method: "get",
        url: "http://82.156.190.251:80/apis/download",
        data: {"articleID":row.articleID},
      }).then(
        (response) => {
          console.log(response);
        },
        (err) => {
          alert(err);
        }
      );
      console.log("下载中");
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