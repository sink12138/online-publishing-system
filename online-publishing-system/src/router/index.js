import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

const Admin = () => import("../views/Admin.vue");
const 管理员登录 = () => import("../views/管理员登录.vue");
const 管理员主页 = () => import("../views/管理员主页.vue");
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
    component: Home,
    children:[
      {
        path: 'search',
        name: '搜索',
        component: () => import('../views/搜索.vue')
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
        path: '/author',
        name: '作者主页',
        component: () => import('../views/作者主页.vue'),
        children:[
          {
            path: 'articles',
            name: '我的文章',
            component: () => import('../components/我的文章.vue')
          },
        ]
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
        path: '/author/submit',
        name: '提交文章',
        component: () => import('../views/提交文章.vue')
      },
      {
        path: '/author/claim',
        name: '认领文章',
        component: () => import('../views/认领文章.vue')
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
    meta: {
      adminLoginRequest: true
    },
    children: [
      {
        path:"home",
        name:"管理员主页",
        component:管理员主页,
        meta: {
          adminLoginRequest: true
        }
      },
      {
        path:"accounts",
        name:"查看账号",
        component:查看账号,
        meta: {
          adminLoginRequest: true
        }
      },
      {
        path:"authors",
        name:"查看作者账号",
        component:查看作者账号,
        meta: {
          adminLoginRequest: true
        }
      },
      {
        path:"reviewers",
        name:"查看审稿人账号",
        component:查看审稿人账号,
        meta: {
          adminLoginRequest: true
        }
      },
      {
        path:"editors",
        name:"查看编辑账号",
        component:查看编辑账号,
        meta: {
          adminLoginRequest: true
        }
      },
      {
        path:"documents",
        name:"资源管理",
        component:资源管理,
        meta: {
          adminLoginRequest: true
        }
      },
      {
        path:"accounts/add",
        name:"增加账号",
        component:增加账号,
        meta: {
          adminLoginRequest: true
        }
      },
      {
        path:"accounts/change",
        name:"修改身份",
        component:修改身份,
        meta: {
          adminLoginRequest: true
        }
      },
      {
        path:"documents/view",
        name:"查看文章",
        component:查看文章,
        meta: {
          adminLoginRequest: true
        }
      },
      {
        path:"/admin/documents/number-search",
        name:"编号查找",
        component:编号查找,
        meta: {
          adminLoginRequest: true
        }
      },
      {
        path:"/admin/documents/key-search",
        name:"关键字查找",
        component:关键字查找,
        meta: {
          adminLoginRequest: true
        }
      },
      {
        path:"/admin/clean",
        name:"清除无效信息",
        component:清除无效信息,
        meta: {
          adminLoginRequest: true
        }
      },
      {
        path:"/admin/settings",
        name:"网站设置",
        component:网站设置,
        meta: {
          adminLoginRequest: true
        }
      },
    ]
  },
  {
    path:"/admin/login",
    name:"管理员登录",
    component:管理员登录,
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  if ((to.meta.adminLoginRequest == true)){
    var admin = sessionStorage.getItem("admin");
    console.log(admin)
    if (admin != "login") {
      next({
        path : '/admin/login'
      })
    } else {
      next()
    }
  } else {
    next()
  }
})

export default router
