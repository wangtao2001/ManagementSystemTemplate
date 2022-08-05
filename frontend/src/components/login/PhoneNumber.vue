<template>
  <div class="box">
    <div class="title">登录</div>
    <div class="form">
      <input type="text" v-model="form.phoneNumber" placeholder="请输入手机号">
      <div class="auth">
        <input type="text" v-model="form.authCode" placeholder="请输入验证码">
        <button>获取验证码</button>
      </div>
      <button :disabled="disabled" @click="login">登录</button>
    </div>
    <div class="divi">
      <div class="hr"/>
      <p>或</p>
      <div class="hr"/>
    </div>
    <div class="third">
      <a href="/login">使用账号密码登录</a>
    </div>
  </div>
</template>

<script>
export default {
  name: "PhoneNumber",
  data() {
    return {
      form:{
        phoneNumber: "",
        authCode: "",
      }
    }
  },
  computed: {
    disabled: function(){
      return !(this.form.phoneNumber.length !== 0 && this.authCode.length !== 0)
    }
  }
}
</script>

<style scoped>
.box {
  height: 400px;
  width: 370px;
  background-color: #fff;
  border: 1px solid #dbdbdb;
  display: flex;
  align-items: center;
  flex-direction: column;
}

.title {
  margin: 50px 0;
  font-size: 23px;
  color: #262626;
}

.form {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 65%;
}

.form>button, .form>input {
  margin-top: 15px;
  width: 100%;
  height: 35px;
}

.form div {
  margin-top: 15px;
  height: 35px;
  width: calc(100% + 20px);
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

.form .auth {
  display: flex;
  height: 37px;
}

.form .auth input {
  width: 60%;
  margin-right: 10px;
}

.form .auth button {
  width: 40%;
  margin-left: 10px;
  background-color: #409EFF;
  color: white;
  border: none;
  border-radius: 4px;
}

.form>button {
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
</style>