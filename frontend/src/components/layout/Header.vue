<template>
    <div class="header">
        <div class="left">后台管理</div>
        <div style="flex: 1"></div>
        <div class="right">
          <el-dropdown>
          <span class="el-dropdown-link">
              {{currentUser.nickname}}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <span @click="logout">退出登陆</span>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
    </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Header",
  methods: {
   logout() {
     this.$confirm('您需要重新登陆', '正在退出', {
       confirmButtonText: '确定',
       cancelButtonText: '取消',
       type: 'warning'
     }).then(() => {
       localStorage.removeItem('Authorization');
       this.$router.push("/login")
     }).catch()
   }
  },
  data() {
    return {
      currentUser: {
      }
    }
  },
  created() {
    axios({
      method: 'get',
      url: '/api/user/current'
    }).then(res => {
      res = res.data
      console.log(res)
      if (res.code === "200") {
        this.currentUser = res.data
      }
    })
  }
}
</script>

<style scoped>
    .header {
        height: 60px;
        line-height: 60px;
        border-bottom: 1px solid #ccc;
        display: flex;
    }

    .left{
        margin-left: 30px;
        font-weight: bold;
        color: #409EFF;
    }
    .right{
        margin-right: 30px;
        display: flex;
        align-items: center;
    }
</style>