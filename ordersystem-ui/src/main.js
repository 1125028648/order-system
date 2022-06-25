// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import iview from 'iview'
import 'iview/dist/styles/iview.css'
import VueRouter from 'vue-router'

// 全局引用iview
Vue.use(VueRouter)
Vue.use(iview)

// 引用axios，并设置基础URL为后端服务api地址
var axios = require('axios')
axios.defaults.baseURL = 'http://localhost:8088'

// 将API方法绑定到全局
Vue.prototype.$axios = axios
Vue.config.productionTip = false

// 允许携带cookie信息
axios.defaults.withCredentials = true

/* eslint-disable no-new */

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})