<template>
  <div>
    <h1>个人信息页面</h1>
    <div class="myinfo">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
        tab-position="left"
        type="border-card"
      >
        <el-tab-pane label="个人信息" name="first">
          <div class="author">
            <router-link to="/author/certify">
              <el-button v-if="$store.state.role < 4"> 认证作者 </el-button>
            </router-link>
            <router-link to="/author/cancel">
              <el-button v-if="$store.state.role >= 4"> 注销作者 </el-button>
            </router-link>
            <router-link to="/main">
              <el-button> 返回主页 </el-button>
            </router-link>
          </div>
          <div class="articleinfo">
            <el-table
              :show-header="false"
              :data="tableData"
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
              <el-table-column width="180px" prop="name"></el-table-column>
              <el-table-column prop="amount1"></el-table-column>
              <el-table-column width="180px" prop="amount2"></el-table-column>
              <el-table-column prop="amount3"></el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
        <el-tab-pane label="修改信息" name="second">
          <div class="modifyEmail">
            <el-form
              :inline="true"
              :model="formInline"
              class="demo-form-inline"
            >
              <el-form-item label="Email">
                <el-input
                  v-model="formInline.email"
                  placeholder="Email"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="success" plain @click="submit()"
                  >提交修改</el-button
                >
              </el-form-item>
            </el-form>
          </div>
          <el-divider></el-divider>
          <div class="modify">
            <el-form
              ref="form"
              :model="form"
              class="modify-form"
              label-width="120px"
            >
              <el-form-item label="password">
                <el-input
                  v-model="formInline.password"
                  prefix-icon="el-icon-lock"
                  show-password
                  placeholder="password"
                ></el-input>
              </el-form-item>
              <el-form-item label="realName">
                <el-input
                  v-model="formInline.realName"
                  placeholder="realName"
                ></el-input>
              </el-form-item>
              <el-form-item label="institution">
                <el-input
                  v-model="formInline.institution"
                  placeholder="研究机构"
                  v-if="$store.state.role >= 4"
                ></el-input>
              </el-form-item>
              <el-form-item label="researchInterests">
                <el-input
                  v-model="formInline.researchInterests"
                  placeholder="研究方向"
                  
                ></el-input>
              </el-form-item>
              <el-form-item label="organization">
                <el-input
                  v-model="formInline.organization"
                  placeholder="学术组织"
                  v-if="
                    $store.state.role == 2 ||
                    $store.state.role == 3 ||
                    $store.state.role == 6 ||
                    $store.state.role == 7
                  "
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="success" plain @click="submit2()"
                  >提交修改</el-button
                >
              </el-form-item>
            </el-form>
          </div>
        </el-tab-pane>
      </el-tabs>
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
      activeName: "first",
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
          amount2: "密码",
          amount3: this.dataForm.password,
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
          if (res.data.success == true) {
            this.$message({
              message: "邮箱修改成功！",
              type: "success",
            });
          } else {
            this.$message({
              message: res.data.message,
            });
          }
        });
      } else {
        this.$message({
          message: "邮箱不能为空！",
        });
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
          if (res.data.success == true) {
            this.$message({
              message: "信息修改成功！",
              type: "success",
            });
          } else {
            this.$message({
              message: res.data.message,
            });
          }
        });
      } else {
        this.$message({
          message: "密码不能为空！",
        });
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
.el-tabs__item.is-active {
  color: rgb(73, 178, 82);
}
.el-tabs__active-bar {
  background-color: rgb(73, 178, 82);
}
.myinfo {
  display: flex;
  justify-content: center;
  align-items: center;
}
.author {
  text-align: right;
  margin-right: 10px;
  text-decoration: none;
}
.el-tabs {
  width: 1200px;
}
.el-table {
  width: 60px;
}
.modify-form .el-form-item {
  width: 600px;
  margin: 0 auto;
  margin-bottom: 20px;
}
.el-input {
  width: 400px;
  height: 20px;
}
a {
  text-decoration: none;
  color: black;
}
.router-link-active {
  text-decoration: none;
  color: yellow;
}
</style>