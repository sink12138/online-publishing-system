<template>
  <div class="home">
    <img alt="logo" src="../assets/logo.png">
    <router-link to='/admin'>Go to Admin</router-link>
    <router-view></router-view>
  </div>
</template>

<style>
.body {
  text-align: center;
}
</style>