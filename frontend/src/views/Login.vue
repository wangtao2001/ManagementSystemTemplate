<template>
<div class="page">
  <router-view/>
  <div class="reg">
    没有账户?
    <a href="/register">立即注册</a>
  </div>
</div>
</template>

<script>
export default {
  name: "Login"
}
</script>

<style scoped>
  .page {
    width: 100%;
    height: 100vh;
    background-color: #fafafa;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
  }

  .reg {
    width: 370px;
    height: 80px;
    margin-top: 20px;
    background-color: #fff;
    border: 1px solid #dbdbdb;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .reg a {
    color: #409EFF;
    font-weight: 600;
  }
</style>