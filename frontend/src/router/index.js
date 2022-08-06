import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [{
    path: '/',
    name: 'layout',
    redirect: '/user',
    component: () => import('@/views/Layout.vue'),
    children: [{
        path: '/user',
        name: 'user',
        component: () => import('@/components/layout/User.vue')
    },
        {
            path: '/about',
            name: 'about',
            component: () => import('@/components/layout/About.vue')
        }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/Login.vue'),
    children: [{
        name: 'password',
        path: '/',
        component: () => import('@/components/login/Password.vue'),
    }, {
        path: '/login/phone',
        name: 'phoneNumber',
        component: () => import('@/components/login/PhoneNumber.vue'),
    }
    ]
  },
    {
        path: '/register',
        name: 'register',
        component: () => import('@/views/Register.vue'),
    }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

//路由守卫
//使用 router.beforeEach 注册一个全局前置守卫，判断用户是否登陆
router.beforeEach((to, from, next) => {
  let token = localStorage.getItem('Authorization');
  let hasLogin = !(token === null || token === ''); // 已经登陆过
  let isLoginPage = (to.path === '/login' || to.path === "/login/phone" || to.path === "/register");  // 是否去往登陆注册页面
  if (isLoginPage && !hasLogin) { // 去登陆页面，但是没有登陆(注意有两种登陆)
    next();
  } else if (isLoginPage && hasLogin) { // 去登陆页面，但是已经登陆
    next('/');
  } else { // 其他页面
    if (hasLogin) { // 已经登陆
      next();
    } else { // 没有登陆
        next('/login');
    }
  }
});

export default router
