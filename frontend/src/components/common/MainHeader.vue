<template>
  <div class="px-3 py-0 mt-4 mb-2">
    <div class="row">
      <div class="col-4 col-lg-auto mt-1 mb-2 mb-lg-0 me-lg-auto">
        <div class="search-div row mx-3" v-if="showSearch">
          <div class="col-10" style="text-align: left; border-right: 1px solid">
            <input
              type="text"
              class="search-form mt-3"
              placeholder="검색어를 입력하세요."
              aria-label="Search"
              v-model="searchWord"
              @keyup.enter="moveDetail"
              v-if="showSearch"
            />
          </div>
          <div class="col-2 pt-2 mt-1" style="text-align: center">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="23"
              height="23"
              fill="currentColor"
              class="bi bi-search"
              viewBox="0 0 16 16"
              @click="moveDetail"
            >
              <path
                d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"
              />
            </svg>
          </div>
        </div>
      </div>

      <div class="col-4" style="text-align: center">
        <router-link
          to="/"
          v-on:click="renew"
          class="navbar-brand col-12 col-lg-auto mb-2 mb-lg-0 me-lg-auto"
        >
          <img
            src="@/assets/img/titleLogo.png"
            alt=""
            width="120"
            height="60"
          />
        </router-link>
      </div>

      <div class="text-end col-4 mt-2" style="text-align: center">
        <button
          type="button"
          v-on:click="logOut"
          class="btn btn-outline-secondary m-2"
          v-if="authToken !== null"
        >
          로그아웃
        </button>
        <button type="button" class="btn btn-outline-secondary m-2" v-else>
          <router-link to="/login">로그인</router-link>
        </button>
        <button
          type="button"
          class="btn btn-outline-secondary m-2"
          v-if="authToken == null"
        >
          <router-link to="/register">회원가입</router-link>
        </button>
        <button
          type="button"
          class="btn btn-outline-secondary m-2"
          v-if="authToken !== null"
        >
          <router-link to="/mypage">마이페이지</router-link>
        </button>
      </div>
    </div>
  </div>
  <br />
  <aside
    class="d-flex flex-wrap align-items-center justify-content-center py-1 mb-3"
  ></aside>
</template>
<script>
// import axios from 'axios'
// const LOCAL_HOST = 'http://localhost:3030/api'
// const SERVER_HOST = 'https://j6a406.p.ssafy.io/api'

export default {
  data() {
    return {
      authToken: localStorage.getItem("authToken") || null,
      showSearch: true,
      searchWord: null,
      loginCheck: null,
    };
  },
  created() {
    this.showSearch = true;
  },

  mounted() {
    this.showSearch = true;
  },
  methods: {
    renew() {
      this.searchWord = null;
      this.showSearch = true;
      this.$router.push({ name: "home" });
    },
    moveDetail() {
      this.showSearch = false;
      this.$router.push({
        name: "search",
        params: { searchWord: this.searchWord },
      });
    },
    logOut() {
      localStorage.removeItem("authToken");
      localStorage.removeItem("userSeq");

      this.$router.go();
      // this.$router.push({name : 'home'});
    },
  },
};
</script>

<style scoped>
.search-div {
  width: 400px;
  height: 50px;
  -webkit-border-radius: 30px;
  -moz-border-radius: 30px;
  border-radius: 30px;
  border: 1px solid black;
}

.search-form {
  height: 20px;
  border: none;
  display: inline;
  width: 300px;
  margin-right: 10px;
  margin-left: 10px;
  font-size: 20px;
}
input:focus {
  outline: none;
}

.bi-search {
  cursor: pointer;
}

aside {
  text-align: center;
  border-bottom: solid;
  border-color: black;
  border-width: 1px 0;
  /* border-width: 2px 0; */
  text-transform: uppercase;
  letter-spacing: 2px;
}

a {
  text-decoration-line: none;
}
</style>
