import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './assets/css/global.css'
import axios from 'axios' ;
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.config.productionTip = false
Vue.use(ElementUI);

// 添加请求拦截器，在请求头中加token
axios.interceptors.request.use(
    config => {
      if (localStorage.getItem('Authorization')) {
        config.headers.Authorization = localStorage.getItem('Authorization');
      }

      return config;
    },
    error => {
      return Promise.reject(error);
    });


new Vue({
  router,
  store: store,
  render: h => h(App)
}).$mount('#app')
