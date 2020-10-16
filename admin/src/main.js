import Vue from 'vue'
import app from './app.vue'
import router from './router'
import axios from 'axios'

Vue.config.productionTip = false;
Vue.prototype.$ajax = axios;

new Vue({
  router,
  render: h => h(app),
}).$mount('#app');
