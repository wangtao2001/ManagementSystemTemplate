<template>
<div class="page">
  <div class="box">
    <div class="title">登录</div>
    <div class="form">
      <input type="text" v-model="form.username" placeholder="请输入账号">
      <input type="password" v-model="form.password" placeholder="请输入密码">
      <button :disabled="disabled" @click="login">登录</button>
    </div>
    <div class="divi">
      <div class="hr"/>
      <p>或</p>
      <div class="hr"/>
    </div>
    <div class="third">
      <a href="#">使用手机号登录</a>
    </div>
  </div>
  <div class="reg">
    没有账户?
    <a href="#">立即注册</a>
  </div>
</div>
</template>

<script>
import {mapMutations} from "vuex";
import axios from "axios"; // 使用axios库进行请求
export default {
  name: "Login",
  data() {
    return {
      form: {},
      userToken: ''
    }
  },
  methods: {
    ...mapMutations(['changeLogin']),
    login() {
      axios({
        method: 'post',
        url: '/api/user/login',
        data:this.form
      }).then(res => {
        console.log(res.data);
        this.userToken = 'Bearer ' + res.data.token;
        // 将用户token保存到vuex中
        this.changeLogin({ Authorization:this.userToken });
        this.$router.push('/');
      }).catch(err => {
        console.log("err",err);
      })
    }
  },
  computed: {
    disabled: function () {
      // 验证账户名与密码都输入且密码长度在5-10之间才能登陆
      return !(this.form.username != null
          && this.form.password != null
          && this.form.password.length >= 5
          && this.form.password.length <= 10);
    }
  }
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
  .box {
    height: 400px;
    width: 370px;
    background-color: #fff;
    border: 1px solid #dbdbdb;
    display: flex;
    align-items: center;
    flex-direction: column;
  }

  .form {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 65%;
  }

  .form * {
    margin-top: 15px;
    width: 100%;
    height: 35px;
  }

  .form input {
    background-color: #fafafa;
    border-radius: 4px;
    border: 1px solid #dbdbdb;
    padding: 0 10px;
  }

  .form input:focus {
    outline: none;
    border-color: #c7c1c1;
  }

  .form button {
    background-color: #409EFF;
    color: white;
    border: none;
    border-radius: 4px;
    width: calc(100% + 20px);
  }

  .form button:active {
    background-color: #b2dffc;
  }

  .form button:disabled {
    background-color: #b2dffc;
  }

  .divi {
    margin-top: 20px;
    display: flex;
    align-items: center;
    width: calc(65% + 20px);
  }

  .divi p {
    text-align: center;
    color: #8e8e8e;
    margin: 0 10px;
  }

  .divi .hr {
    flex: 1;
    height: 0;
    border-top: 1px solid #8e8e8e;
  }

  .third {
    margin-top: 20px;
    font-weight: 600;
  }

  .third a {
    color: #409EFF;
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
  .title {
    margin: 50px 0;
    font-size: 23px;
    color: #262626;
  }
</style>