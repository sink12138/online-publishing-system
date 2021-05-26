import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

const Admin = () => import("../views/Admin.vue");
const 管理员登录 = () => import("../views/管理员登录.vue");
const 查看账号 = () => import("../views/查看账号.vue");
const 查看作者账号 = () => import("../views/查看作者账号.vue");
const 查看审稿人账号 = () => import("../views/查看审稿人账号.vue");
const 查看编辑账号 = () => import("../views/查看编辑账号.vue");
const 增加账号 = () => import("../views/增加账号.vue");
const 修改身份 = () => import("../views/修改身份.vue");
const 资源管理 = () => import("../views/资源管理.vue");
const 查看文章 = () => import("../views/查看文章.vue");
const 编号查找 = () => import("../views/编号查找.vue");
const 关键字查找 = () => import("../views/关键字查找.vue");
const 清除无效信息 = () => import("../views/清除无效信息.vue");
const 网站设置 = () => import("../views/网站设置.vue");

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
  },
  {
    path:"/admin/account",
    name:"查看账号",
    component:查看账号,
  },
  {
    path:"/admin/writer",
    name:"查看作者账号",
    component:查看作者账号,
  },
  {
    path:"/admin/referee",
    name:"查看审稿人账号",
    component:查看审稿人账号,
  },
  {
    path:"/admin/editor",
    name:"查看编辑账号",
    component:查看编辑账号,
  },
  {
    path:"/admin/documents",
    name:"资源管理",
    component:资源管理,
  },
  {
    path:"/admin/account/add",
    name:"增加账号",
    component:增加账号,
  },
  {
    path:"/admin/account/change",
    name:"修改身份",
    component:修改身份,
  },
  {
    path:"/admin/documents/view",
    name:"查看文章",
    component:查看文章,
  },
  {
    path:"/admin/documents/number-search",
    name:"编号查找",
    component:编号查找,
  },
  {
    path:"/admin/documents/key-search",
    name:"关键字查找",
    component:关键字查找,
  },
  {
    path:"/admin/clean",
    name:"清除无效信息",
    component:清除无效信息,
  },
  {
    path:"/admin/settings",
    name:"网站设置",
    component:网站设置,
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
