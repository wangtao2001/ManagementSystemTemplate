<template>
  <div class="page">
    <div class="box">
      <div class="title">注册</div>
      <div class="form">
        <input type="text" v-model="form.username" placeholder="用户名需要包含1-10位字符">
        <input type="password" v-model="form.password" placeholder="密码需包含5-10位大小写字母与数字">
        <input type="password" v-model="password2" placeholder="请重复密码">
        <button @click="register" :disabled="disabled">注册</button>
      </div>
      <div class="policy">
        注册即表示你同意我们的
        <a href="#">条款</a>|
        <a href="#">隐私政策</a>|
        <a href="#">Cookie政策</a>
      </div>
    </div>
    <div class="reg">
      已有账户?
      <a href="/login">立即登录</a>
    </div>
  </div>
</template>

<script>

import axios from "axios";

export default {
  name: "Register",
  data() {
    return {
      form: {
        username: "",
        password: "",
      },
      password2: ""
    }
  },
  computed: {
    disabled: function () {
      return !(this.form.username.length !== 0
        && this.form.password.length >=5
        && this.password2 === this.form.password
        && this.form.username.length >= 1
        && this.form.username.length <= 10
      )
    }
  },
  methods: {
    register() {
      let reg = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{5,10}$/;
      if (!reg.test(this.form.password)) {
          this.$message.warning("密码不符合规则")
          this.form.password = ""
          this.password2 = ""
          return
      }
      axios({
        method: 'post',
        url: '/api/user/register',
        data:this.form
      }).then(res => {
        res = res.data
        if (res.code === "200") {
          this.$router.push('/login');
          this.$message.success("注册成功，请登录")
        } else {
          // 相应提示
          this.form.username = ""
          this.$message.error(res.msg);
        }
      })
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
  padding-left: 10px;
}

.form input:focus {
  outline: none;
  border-color: #c7c1c1
}

.form button {
  background-color: #409EFF;
  color: white;
  border: none;
  border-radius: 4px;
  width: calc(100% + 10px);
}

.form button:active {
  background-color: #76b0ea;
}

.form button:disabled {
  background-color: #b2dffc;
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
  text-decoration: none;
  color: #409EFF;
  font-weight: 600;
}
.title {
  margin: 50px 0;
  font-size: 23px;
  color: #262626;
}

.policy {
  margin-top: 30px;
  font-size: 10px;
}

.policy a {
  font-weight: 600;
  text-decoration: none;
  color: black;
}
</style>