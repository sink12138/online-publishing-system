<template>
  <div class="authorinfo">
    <h1>作者信息页面</h1>
    <div class="authorform">
      <el-table
        :show-header="false"
        :data="tableData"
        :span-method="objectSpanMethod"
        border
        :cell-style="columnStyle"
        style="width: 100%; margin-top: 20px"
      >
        <el-table-column prop="id" label="ID" width="200">
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
    <div>
      <el-table class="myarticles" :data="tableData1" style="width: 100%">
        <el-table-column label="文章 ID" prop="articleId"> </el-table-column>
        <el-table-column label="文章标题" prop="title"> </el-table-column>
        <el-table-column label="第一作者" prop="firstAuthor"> </el-table-column>
        <el-table-column label="其他作者" prop="otherAuthors"> </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  name: "authorinfo",
  data() {
    return {
      authorId: 0,
      dataForm: {},
      tableData1: [],
    };
  },
  computed: {
    tableData() {
      return [
        {
          id: this.dataForm.headImg,
          name: "账号邮箱",
          amount1: this.dataForm.email,
          amount2: "真实姓名",
          amount3: this.dataForm.realName,
        },
        {
          id: this.dataForm.headImg,
          name: "所在机构",
          amount1: this.dataForm.institution,
          amount2: "研究方向",
          amount3: this.dataForm.researchInterests,
        },
      ];
    },
  },
  created() {
    this.convert();
  },
  methods: {
    convert: function () {
      this.$axios({
        method: "get",
        url: "http://82.156.190.251:80/apis/infos",
        data: {
          authorId: JSON.stringify(sessionStorage.getItem("authorId")),
        },
      }).then((res) => {
        var array = new Array();
        array = res.data;
        this.dataForm = array[0];
        this.tableData1 = array.slice(1);
      });
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
