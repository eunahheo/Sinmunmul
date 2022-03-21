import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap'
import VueChartkick from 'vue-chartkick'
import 'chartkick/chart.js'

createApp(App).use(store).use(VueChartkick).use(router).mount('#app')
