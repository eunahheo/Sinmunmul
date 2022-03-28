<template>
<nav class="py-2">
<div class="px-3 py-0 mb-0">
      <div class="container d-flex flex-wrap justify-content-center">
        <form class="col-12 col-lg-auto mb-2 mb-lg-0 me-lg-auto">
          <input type="search" class="form-control" placeholder="Search..." aria-label="Search" v-model="searchWord" @keyup.enter="search">
        </form>

      <router-link to="/" class="navbar-brand col-12 col-lg-auto mb-2 mb-lg-0 me-lg-auto">
      <img src="@/assets/img/titleLogo.png" alt="" width="120" height="60">
      </router-link>

        <div class="text-end">
           <button type="button" class="btn btn-outline-secondary m-2">로그인</button>
          <button type="button" class="btn btn-outline-secondary m-2">회원가입</button>
          <button type="button" class="btn btn-outline-secondary m-2"> 
            <router-link to="/mypage">마이페이지</router-link>
            </button>
         
        </div>
      </div>
    </div>
</nav>
 <aside class="d-flex flex-wrap align-items-center justify-content-center py-3 mb-3 " >
      <ul class="nav col-10 col-md-auto mb-2 justify-content-center mb-md-0">

         <li class="nav-item dropdown">
          <a class="nav-link link-dark" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false">카테고리</a>
          <ul class="dropdown-menu">
        <li><a class="dropdown-item" href="#">상세 카테고리 1</a></li>
        <li><a class="dropdown-item" href="#">상세 카테고리 2</a></li>
        <li><a class="dropdown-item" href="#">상세 카테고리 3</a></li>
          </ul>
         </li>

        <li>
          <router-link to="/temp"  class="nav-link px-20 link-dark">
            워드클라우드
          </router-link>
        </li>
        <li>
          <router-link to="/tempchart"  class="nav-link px-20 link-dark">
            차트
          </router-link>
        </li>
        <li>
          <router-link to="/tempgraph"  class="nav-link px-20 link-dark">
            그래프
          </router-link>
        </li>
        <li><a href="#" class="nav-link px-20 link-dark">연예</a>
        </li>
        <li><a href="#" class="nav-link px-20 link-dark">스포츠</a>
        </li>
        <li><a href="#" class="nav-link px-20 link-dark">매거진</a>
        </li>
      </ul>

 </aside>

</template>
<script>
import axios from 'axios'
// const LOCAL_HOST = 'http://localhost:3030/api'
const SERVER_HOST = 'https://j6a406.p.ssafy.io/api'

export default {
  data() {
    return {
      searchWord : null
    }
  },
  methods : {
    search(event) {
      console.log("검색어 : "+this.searchWord);
     
      if(this.searchWord != null && this.searchWord !="") {
        axios.get(`${SERVER_HOST}/news/keyword`, {
          params: {
            keyword : this.searchWord,
            page : 1,
            size : 4
          }
        })
        .then((res) =>{
          //메인페이지 이동
          console.log(res);
          this.$router.push({
            name: 'search',
            query: { result : res }
            });

        }).catch((err) => {
            console.log("에러");
            console.log(err);
          })
      }
    }
  }
}

</script>


<style scoped>

aside {
  text-align: center;
  border-bottom: solid;
  border-color: black;
  border-width: 2px 0;
  text-transform: uppercase;
  letter-spacing: 2px;
}
</style>
