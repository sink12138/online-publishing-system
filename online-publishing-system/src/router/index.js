import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/main',
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
    path: '/login',
    name: '登录',
    component: () => import('../views/用户登录.vue')
  },
  {
    path: '/register',
    name: '注册',
    component: () => import('../views/注册.vue')
  },
  {
    path: '/home',
    name: '个人信息',
    component: () => import('../views/个人信息.vue')
  },
  {
    path: '/search',
    name: '搜索',
    component: () => import('../views/搜索.vue')
  },
  {
    path: '/article',
    name: '文章信息',
    component: () => import('../views/文章信息.vue')
  },
  {
    path: '/infos',
    name: '作者信息',
    component: () => import('../views/作者信息.vue')
  },
  {
    path: '/download',
    name: '下载',
    component: () => import('../views/下载.vue')
  },
  {
    path: '/author',
    name: '作者主页',
    component: () => import('../views/作者主页.vue')
  },
  {
    path: '/author/certify',
    name: '作者认证',
    component: () => import('../views/认证作者.vue')
  },
  {
    path: '/author/cancel',
    name: '作者注销',
    component: () => import('../views/注销作者.vue')
  },
  {
    path: '/author/new/upload',
    name: '上传文章',
    component: () => import('../views/上传文章.vue')
  },
  {
    path: '/author/new/submit',
    name: '提交文章',
    component: () => import('../views/提交文章.vue')
  },
  {
    path: '/author/confirm/draft',
    name: '确认终稿',
    component: () => import('../views/确认终稿.vue')
  },
  {
    path: '/author/claim',
    name: '认领文章',
    component: () => import('../views/认领文章.vue')
  },
  {
    path: '/author/articles',
    name: '我的文章',
    component: () => import('../views/我的文章.vue')
  },
  {
    path: '/reviewer',
    name: '审稿人主页',
    component: () => import('../views/审稿人主页.vue')
  },
  {
    path: '/editor',
    name: '编辑主页',
    component: () => import('../views/编辑主页.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
