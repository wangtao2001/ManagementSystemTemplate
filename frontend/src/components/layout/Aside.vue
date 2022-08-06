<template>
  <div>
        <el-menu
            style="width: 200px; height: calc(100vh - 60px);"
            default-active="user"
            class="el-menu-vertical-demo"
            @open=""
            router
            @close="">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>系统管理</span>
            </template>
              <el-menu-item index="user">用户管理</el-menu-item>
          </el-submenu>
          <el-menu-item index="about">
            <i class="el-icon-copy-document"></i>
            <span slot="title">关于</span>
          </el-menu-item>
        </el-menu>
  </div>
</template>

<script>
export default {
    name: 'Aside',
}
</script>

<style scoped>
</style>