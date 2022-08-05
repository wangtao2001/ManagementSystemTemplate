import Vue from 'vue'
import VueRouter from 'vue-router'
import el from "element-ui/src/locale/lang/el";

Vue.use(VueRouter)

const routes = [{
    path: '/',
    name: 'layout',
    component: () => import('@/views/Layout.vue'),
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/Login.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 路由守卫
// 使用 router.beforeEach 注册一个全局前置守卫，判断用户是否登陆
router.beforeEach((to, from, next) => {
  let token = localStorage.getItem('Authorization');
  let isLogin = !(token === null || token === '');
  if (to.path === '/login' && !isLogin) { // 去登陆页面，但是没有登陆
    next();
  } else if (to.path === '/login' && isLogin) { // 去登陆页面，但是已经登陆
    next('/');
  } else { // 其他页面
    if (isLogin) { // 已经登陆
      next();
    } else { // 没有登陆
        next('/login');
    }
  }
});

export default router
