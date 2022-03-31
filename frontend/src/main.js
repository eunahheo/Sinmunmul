import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap'
import VueChartkick from 'vue-chartkick'
import 'chartkick/chart.js'
import 'vuex-persistedstate'

const app = createApp(App);

app.use(store);
app.use(router);
app.use(VueChartkick);

app.mount('#app');
