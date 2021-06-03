<template>
  <div class="myinfo">
    <h1>个人信息页面</h1>
    <div class="author">
      <router-link to="/author/certify">认证作者</router-link> |
      <router-link to="/author/cancel">注销作者</router-link> |
      <router-link to="/">返回主页</router-link>
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
    <br />
    <hr />
    <div class="modifyEmail">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="Email">
          <el-input v-model="formInline.email" placeholder="Email"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submit()">提交修改</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
 
<script>
const axios = require("axios");
export default {
  name: "myinfo",
  data() {
    return {
      formInline: {
        email: "",
      },
      dataForm: {},
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
        {
          id: this.dataForm.headImg,
          name: "学术组织",
          amount1: this.dataForm.organization,
          amount2: "",
          amount3: "",
        },
      ];
    },
  },
  created() {
    this.convert();
  },
  methods: {
    submit() {
      axios({
        method: "post",
        url: "/home/modify/email",
        data: JSON.stringify({
          email: this.formInline.email,
        }),
      }).then((res) => {
        console.log(res);
      });
      console.log("submit!");
    },
    convert: function () {
      axios.get("/home").then((res) => {
        this.dataForm = res.data;
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

<style scoped>
.author {
  text-align: right;
  margin-right: 10px;
}
</style>