<template>
  <div class="myinfo">
    <h1>个人信息页面</h1>
    <div class="author">
      <router-link to="/author/certify" v-if="$store.state.role < 4"
        >认证作者</router-link
      >
      |
      <router-link to="/author/cancel" v-if="$store.state.role >= 4"
        >注销作者</router-link
      >
      |
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
    <div class="modify">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="password">
          <el-input
            v-model="formInline.password"
            placeholder="Email"
          ></el-input>
        </el-form-item>
        <el-form-item label="realName">
          <el-input
            v-model="formInline.realName"
            placeholder="Email"
          ></el-input>
        </el-form-item>
        <el-form-item label="institution">
          <el-input
            v-model="formInline.institution"
            placeholder="Email"
          ></el-input>
        </el-form-item>
        <el-form-item label="researchInterests">
          <el-input
            v-model="formInline.researchInterests"
            placeholder="Email"
          ></el-input>
        </el-form-item>
        <el-form-item label="organization">
          <el-input
            v-model="formInline.organization"
            placeholder="Email"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submit2()">提交修改</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
 
<script>
export default {
  name: "myinfo",
  data() {
    return {
      formInline: {
        email: "",
        password: "",
        realName: "",
        institution: "",
        researchInterests: "",
        organization: "",
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
      console.log(JSON.stringify({ email: this.formInline.email }));
      if (this.formInline.email !== "") {
        this.$axios({
          method: "post",
          url: "http://82.156.190.251:80/apis/home/modify/email",
          data: JSON.stringify({
            email: this.formInline.email,
          }),
        }).then((res) => {
          console.log(res);
        });
        console.log("submit!");
      } else {
        console.log("email cant be void!");
      }
    },
    submit2() {
      console.log(
        JSON.stringify({
          password: this.formInline.password,
          realName: this.formInline.realName,
          institution: this.formInline.institution,
          researchInterests: this.formInline.researchInterests,
          organization: this.formInline.organization,
        })
      );
      if (this.formInline.password !== "") {
        this.$axios({
          method: "post",
          url: "http://82.156.190.251:80/apis/home/modify",
          data: JSON.stringify({
            password: this.formInline.password,
            realName: this.formInline.realName,
            institution: this.formInline.institution,
            researchInterests: this.formInline.researchInterests,
            organization: this.formInline.organization,
          }),
        }).then((res) => {
          console.log(res);
        });
        console.log("submit!");
      } else {
        console.log("password cant be void!");
      }
    },
    convert: function () {
      this.$axios.get("http://82.156.190.251:80/apis/home").then((res) => {
        console.log(res);
        this.dataForm = res.data;
        this.formInline.realName = res.data.realName;
        this.formInline.institution = res.data.institution;
        this.formInline.researchInterests = res.data.researchInterests;
        this.formInline.organization = res.data.organization;
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