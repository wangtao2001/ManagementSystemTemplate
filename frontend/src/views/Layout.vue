<template>
  <!--这个组件作为后台管理的展示页面，包含顶部、侧边栏以及数据(路由)-->
  <div>
    <Header></Header>
    <!--主体-->
    <div style="display: flex">
      <!--侧边栏-->
      <Aside></Aside>
      <!--内容-->
      <router-view style="flex: 1"></router-view>
    </div>
  </div>
</template>

<script>
import Header from "../components/layout/Header.vue";
import Aside from "../components/layout/Aside.vue";
export default {
  name: "Layout",
  components: {
    Header, Aside
  }
}
</script>

<style scoped>

</style>