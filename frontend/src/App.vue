<template>
  <main-header />
  <router-view />
</template>

<script>
import MainHeader from "./components/common/MainHeader.vue";

export default {
  name: "App",
  components: {
    "main-header": MainHeader,
  },
};
</script>

<style>
#app {
  font-family: "NanumBarunGothic";

  font-weight: 100;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}

@font-face {
  font-family: "NanumBarunGothic";
  src: url("assets/fonts/NanumBarunGothic.ttf") format("truetype");
  font-weight: bold;
}
</style>
