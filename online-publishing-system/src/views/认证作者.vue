<template>
  <div class="certify">
    <h1>认证作者页面</h1>
    <br />
    <div id="certifyauthor">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="认证机构">
          <el-input
            v-model="formInline.institution"
            placeholder="institution"
          ></el-input>
        </el-form-item>
        <el-form-item label="研究方向">
          <el-input
            v-model="formInline.researchInterests"
            placeholder="researchInterests"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">提交</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div>
      <br />
      <router-link to="/">返回主页</router-link>
    </div>
  </div>
</template>

<script>
const axios = require('axios');
export default {
  data() {
    return {
      formInline: {
        institution: "",
        researchInterests: "",
      },
    };
  },
  methods: {
    onSubmit() {
      console.log("submit!");
      axios({
        method: "post",
        url: "/author/certify",
        params: JSON.stringify(this.formInline),
      }).then(
        (response) => {
          console.log(response);
        },
        (err) => {
          alert(err);
        }
      );
    },
  },
};
</script>