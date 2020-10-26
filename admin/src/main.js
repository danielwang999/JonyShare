import Vue from 'vue'
import app from './app.vue'
import router from './router'
import axios from 'axios'
import filter from './filter/filter'

Vue.config.productionTip = false;
Vue.prototype.$ajax = axios;

/**
 * axios拦截器
 */
axios.interceptors.request.use(function (config) {
  console.log("请求：", config);
  // let token = Tool.getLoginUser().token;
  // if (Tool.isNotEmpty(token)) {
  //   config.headers.token = token;
  //   console.log("请求headers增加token:", token);
  // }
  return config;
}, error => {
});
axios.interceptors.response.use(function (response) {
  console.log("返回结果：", response);
  return response;
}, error => {
});

/**
 * 全局过滤器
 */
Object.keys(filter).forEach(key => {
  Vue.filter(key, filter[key])
});

new Vue({
  router,
  render: h => h(app),
}).$mount('#app');

