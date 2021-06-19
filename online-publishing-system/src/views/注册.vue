<template>
  <div class="register">
    <h1>注册页面</h1>
    <br />
    <el-card class="box-card">
      <el-form
        :model="ruleForm"
        status-icon
        label-position="top"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="ruleForm"
      >
        <div class="inputForm">
          <el-form-item label="邮箱" prop="email" class="item">
            <el-input v-model="ruleForm.email"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="pass" class="item">
            <el-input
              type="password"
              v-model="ruleForm.pass"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPass" class="item">
            <el-input
              type="password"
              v-model="ruleForm.checkPass"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </div>
        <el-form-item>
          <div class="button">
            <el-button type="success" @click="submitForm()">提交</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </div>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<style scoped>
.box-card {
  background-color: #f5f5f5;
  height: 380px;
  width: 550px;
  text-align: center;
  margin: 0 auto;
  opacity: 0.8;
  border: 0;
}
.item >>> .el-form-item__label{
  padding: 0;
}
.el-form-item {
  width: 500px;
  margin: 0 auto;
  margin-top: 5px;
}
.el-input {
  width: 400px;
  height: 40px;
}
.button {
  position: relative;
  top: 30px;
}
</style>

<script>
export default {
  data() {
    var checkEmail = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("邮箱不能为空"));
      } else {
        callback();
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (value.length < 6 || value.length > 20) {
          callback(new Error("请输入六至二十位"));
        }
        var regx = /^(?!([a-zA-Z]+|\d+)$)[a-zA-Z\d]{6,20}$/;
        if (!this.ruleForm.pass.match(regx)) {
          callback(new Error("请同时包含字母数字"));
        }
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        pass: "",
        checkPass: "",
        email: "",
      },
      rules: {
        email: [{ validator: checkEmail, trigger: "blur" }],
        pass: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
      },
    };
  },
  methods: {
    submitForm() {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          var registerdata = {
            email: this.ruleForm.email,
            password: this.ruleForm.pass,
          };
          var data1 = JSON.stringify(registerdata);
          console.log(data1);
          this.$axios({
            method: "post",
            url: "http://82.156.190.251:80/apis/register",
            data: data1,
          })
            .then((res) => {
              console.log(res);
              if (res.data.success == false) {
                this.$message({
                  showClose: true,
                  message: res.data.message,
                });
              } else {
                this.$message({
                  showClose: true,
                  message: "注册完毕，请查看邮箱验证账号",
                  type: "success",
                });
              }
            })
            .catch((error) => {
              console.log(error);
            });
          console.log("submit!");
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>
