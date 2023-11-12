// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import "element-ui/lib/theme-chalk/index.css"
import "element-ui/lib/theme-chalk/reset.css"
import network from "./network";
import store from "./store";
import axios from 'axios';
import {base} from "./config";
// import echarts from 'echarts'

import { VueJsonp } from 'vue-jsonp'
Vue.use(VueJsonp);
Vue.prototype.$network=network;
Vue.prototype.$http= axios
Vue.config.productionTip = false
Vue.use(ElementUI)
// Vue.prototype.$echarts = echarts

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  store: store,
})

// 添加请求拦截器，在请求头中加token
axios.interceptors.request.use(
  config => {
    if (localStorage.getItem('token')) {
      config.headers.token = localStorage.getItem('token');
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  });
