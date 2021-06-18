<template>
  <el-form :inline="true" :model="formInline" class="demo-form-inline">
    <el-form-item label="待认证审稿人的邮箱">
      <el-input
        v-model="formInline.email"
        placeholder="待认证审稿人的邮箱"
      ></el-input>
    </el-form-item>
    <el-form-item label="审稿人所属组织">
      <el-input
        v-model="formInline.organization"
        placeholder="审稿人所属组织"
      ></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="info" @click="CertifySuccess">确定</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="info" @click="Return">返回</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  data() {
    return {
      formInline: {
        email:"",
        organization: "",
      },
    };
  },
  methods: {
    CertifySuccess() {
      if (this.formInline.email === "") {
        alert("请输入待认证的邮箱！");
        return;
      } else {
        if (this.formInline.organization === "") {
          alert("请输入待认证的用户的组织！");
          return;
        }
      }
      let JsonCertifyReviewer = JSON.stringify({
        email: this.formInline.email,
        organization: this.formInline.organization,
      });
      console.log(JsonCertifyReviewer);
      this.$store.commit("Certify");
      this.$axios({
        method: "post",
        url: "http://82.156.190.251:80/apis/editor/certify/reviewer",
        data: JsonCertifyReviewer,
      }).then((res) => {
        console.log(res);
        if (res.data.success == true) {
          this.$message({
            showClose: true,
            message: "认证审稿人成功",
            type: "success",
          });
        } else {
          this.$message({
            showClose: true,
            message: res.data.message,
            type: "error",
          });
        }
      });
      //window.location.href = "../reviewers";
    },
    Return() {
      window.location.href = "../reviewers";
    },
  },
};
</script>
