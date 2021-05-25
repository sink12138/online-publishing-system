import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

const Admin = () => import("../views/Admin.vue");
const 管理员登录 = () => import("../views/管理员登录.vue");

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path:"/admin",
    name:"Admin",
    component:Admin,
  },
  {
    path:"/admin/login",
    name:"管理员登录",
    component:管理员登录,
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
