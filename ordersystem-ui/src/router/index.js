import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
import BlogLogin from '@/components/BlogLogin.vue'
import BlogIndex from '@/components/BlogIndex.vue'
import BlogRegister from '@/components/BlogRegister.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      // path: '/',
      // name: 'HelloWorld',
      // component: HelloWorld
      path: '/',
      redirect: '/login'
    },
    {
      path: '/index',
      name: 'BlogIndex',
      component: BlogIndex
    },
    {
      path: '/login',
      name: 'BlogLogin',
      component: BlogLogin
    },
    {
      path: '/register',
      name: 'BlogRegister',
      component: BlogRegister
    }
  ]
})
