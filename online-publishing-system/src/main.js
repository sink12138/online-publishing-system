import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.prototype.$axios = axios
axios.defaults.headers.post['Content-Type'] = 'application/json'
Vue.config.productionTip = false
//axios.defaults.withCredentials = true;

Vue.use(ElementUI);

new Vue({
  router,
  store,
  axios,
  render: h => h(App)
}).$mount('#app')