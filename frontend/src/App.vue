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
  /* font-family: "ChosunCentennial"; */
  font-family: "ChosunSm";
  /* font-family: "ChosunNm"; */
  /* font-family: "ChosunKm"; */
  font-weight: 100;
  /* font-family: Avenir, Helvetica, Arial, sans-serif; */
  /* background-color: rgb(251, 251, 251); */
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
  font-family: "ChosunNm";
  src: url("assets/fonts/ChosunNm.ttf") format("truetype");
  font-weight: 700;
}

@font-face {
  font-family: "ChosunSm";
  src: url("assets/fonts/ChosunSm.ttf") format("truetype");
  font-weight: bold;
}
@font-face {
  font-family: "ChosunKm";
  src: url("assets/fonts/ChosunKm.ttf") format("truetype");
  font-weight: bold;
}
</style>
