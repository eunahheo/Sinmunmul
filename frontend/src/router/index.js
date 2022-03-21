import { createRouter, createWebHashHistory } from 'vue-router'
import MainView from '../views/main/Main.vue'
import Temp from '../views/main/Temp.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: MainView
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
    path: '/temp',
    name: 'temp',
    component: Temp
  },
  {
    path: '/tempchart',
    name: 'tempchart',
    component: () => import('../views/main/TempChart.vue')
  }

]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
