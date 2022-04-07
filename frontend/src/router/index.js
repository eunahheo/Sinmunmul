import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/main/Main.vue'
import HomeView from '../views/HomeView.vue'
import MyPage from '../views/MyPage.vue'
import MyProfile from '@/components/MyPage/MyProfile.vue'
import MyInterest from '@/components/MyPage/MyInterest.vue'
import MyInfo from '@/components/MyPage/MyInfo.vue'
import ScrapNews from '@/components/MyPage/ScrapNews.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: MainView
  },

  {
    path: '/mypage',
    name: 'MyPage',
    component: MyPage,
    redirect: '/mypage/profile',
    children: [
      {
        path: 'profile',
        component: MyProfile,
        name: 'MyProfile',
        redirect: '/mypage/profile/interest',
        children: [
          {
            path: 'interest',
            component: MyInterest,
            name: 'MyInterest'
          },
          {
            path: 'myinfo',
            component: MyInfo,
            name: 'MyInfo'
          }
        ]
      },
      {
        path: 'scrapnews',
        name: 'ScrapNews',
        component: ScrapNews
      }
    ]
  },

  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/search',
    name: 'search',
    component: () => import('../views/main/MainDetail.vue')
  },
 
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/LoginView.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/RegisterView.vue')
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
