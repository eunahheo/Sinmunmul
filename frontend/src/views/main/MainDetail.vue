<template>
<div class="container">

<div>
  <div class="input-group input-group-lg m-5 mt-4 mb-4 ">
  <span class="input-group-text" id="inputGroup-sizing-lg">검색어</span>
  <input type="text" class="form-control " v-model="searchWord" @keyup.enter="search">
</div>

 <ul class="nav nav-tabs m-3" >
  <li class="nav-item container row" style="float: none; margin:100 auto;">
   <h3 class="">검색 결과</h3>
  </li>
 </ul>
  
  <main class="m-3">
    <div class="plan span--2 long--2 ">
       <h3>이슈 - 워드 클라우드</h3>
        <div id="word-cloud">
        </div>
    </div>

    <div class="plan span--2 long--2">
      <h3>키워드 언급량 추이 그래프</h3>
      <line-chart :data="lineData" ></line-chart>
    </div>
  </main>
  
<ul class="nav nav-tabs m-3" >
  <li class="nav-item container row" style="float: none; margin:100 auto;">
   <h3 class="">관련 뉴스 목록</h3>
   
   <br>
   <hr><hr>
  
   
  </li>
 </ul>
</div>

</div>
</template>
<script>
import axios from 'axios'
// const LOCAL_HOST = 'http://localhost:3030/api'
const SERVER_HOST = 'https://j6a406.p.ssafy.io/api'

export default {
  data() {
    return {
      searchWord : null,
      searchedData : []
    }
  },
  created() {
      this.searchWord = this.$route.params.searchWord;
      this.search();
  },

  methods: {
    search() {
      console.log("검색 키워드 확인 : "+this.searchWord);
      if(this.searchWord != null && this.searchWord !="") {
        axios.get(`${SERVER_HOST}/news/keyword`, {
          params: {
            keyword : this.searchWord,
            page : 1,
            size : 3
          }
        })
        .then((res) =>{
         
          // console.log(res.data);
          this.searchedData = res.data;
          console.log(this.searchedData);
          console.log("=========================");
          //console.log(this.searchedData.data);

          console.log("길이 ? : "+this.searchedData.length);

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
@media (min-width: 700px) {
  main {
    display: grid;
    grid-template-columns: repeat(3, 1fr) 25%;
    grid-template-rows: repeat(3, auto);
    grid-gap: 1.2rem;
  }
}

.span--2 {
  grid-column: span 2;
}

.long--2 {
  grid-row: span 2;
}

.long--4 {
  grid-row: span 4;
}

</style>