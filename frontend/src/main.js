import { createApp } from "vue";
import Vuex from "vuex";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";
import VueChartkick from "vue-chartkick";
import "chartkick/chart.js";

// import Vue from "vue";
import VueNumber from "vue-number-animation";

window.Kakao.init('864650259e852266a14e98b75eedc985')

const app = createApp(App);
app.use(VueNumber);
app.use(store);
app.use(router);
app.use(VueChartkick);
app.use(Vuex);
app.mount("#app");
