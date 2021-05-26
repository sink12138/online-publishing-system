<template>
  <div class="myinfo">
    <router-link to="/author/certify">认证作者</router-link> |
    <router-link to="/author/cancel">注销作者</router-link>
    <br>
    <button @click="convert">个人信息</button>
    <div>
      {{title}}
    </div>
    <hr>
  </div>
</template>
 
<script>
  const axios = require('axios');
  export default {
    name: 'myinfo',
    data() {
      return {
        title: "个人信息"
      }
    },
    created(){
      this.convert();
    },
    methods: {
      convert: function () {
        axios.get("/home").then(res => {
          this.title = res.data;
        })
      }
    }
  }
</script>