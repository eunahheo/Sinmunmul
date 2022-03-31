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
      <!-- <line-chart :data="lineData" ></line-chart> -->
    </div>
  </main>
  
<ul class="nav nav-tabs m-4" >
  <li class="nav-item container row" style="float: none; margin:100 auto;">
   <h3 class="">관련 뉴스 목록</h3>
  </li>
 </ul>

<div v-if="loading" class="spinner-border text-center" style="width: 3rem; height: 3rem" role="status">
  <span class="visually-hidden">Loading...</span>
</div>

<div class="card m-2 " style="max-width: 1040px; max-height : 180px" v-for="news in searchedData" :key="news.news_seq">
  <div class="row g-0">
    <div class="col-md-4">
      <img v-bind:src="news.news_photo" class="card-img-top" style="max-height : 180px"  @error="replaceDefault">
      <!-- <img v-bind:src="news.news.newsPhoto" class="img-fluid rounded-start"  @error="replaceDefault"> -->
    </div>
    <div class="col-md-7">
      <div class="card-body">
        <h5 class="card-title">{{news.news_Title}} </h5>
        <!-- <p class="card-text"><small class="text-muted"> 작은 글씨</small></p> -->
      </div>
    </div>
    <div class="col-md-1">
      <!-- <button @click="detail">자세히 보기 </button> -->
      <!-- <a :href="detail" class="btn btn-primary" style="display:block; width:100%; height:100%; vertical-align: middle;">자세히 보기</a> -->

      <button @click="detail(news.news_seq)" class="btn btn-info" 
      style="display:block; width:100%; height:100%; vertical-align: middle;"
      >자세히 보기</button>
    </div>

  </div>
 
</div>
  <nav aria-label="..." class="d-flex justify-content-center mb-4 ">
      <ul class="pagination d-flex justify-content-between">
        <li v-if="start" class="page-item">
          <a class="page-link" href="#" @click="thisPage(1)">«</a>
        </li>
        <li v-else class="page-item disabled">
          <a class="page-link" href="#" tabindex="-1" aria-disabled="true">«</a>
        </li>

        <li v-if="pre" li class="page-item">
          <a class="page-link" href="#" @click="thisPage(nowPage-5)">‹</a>
        </li>
        <li v-else li class="page-item disabled">
          <a class="page-link" href="#" tabindex="-1" aria-disabled="true">‹</a>
        </li>
        <li
         v-for="pageitem in pageNumbers"
          v-bind:id="'p'+pageitem"
          v-bind:class="{' active': pageitem == nowPage}"
          :key="pageitem"
         class="page-item"><a class="page-link"  href="#" @click="thisPage(pageitem)">{{pageitem}}</a></li>
        <li v-if="next" class="page-item">
          <a class="page-link" href="#" @click="thisPage(nowPage+5)">›</a>
        </li>
        <li v-else li class="page-item disabled">
          <a class="page-link" href="#" tabindex="-1" aria-disabled="true">›</a>
        </li>

        <li v-if="end" class="page-item">
          <a class="page-link" href="#" @click="thisPage(totalPage)">»</a>
        </li>
        <li v-else li class="page-item disabled">
          <a class="page-link" href="#" tabindex="-1" aria-disabled="true">»</a>
        </li>
      </ul>
    </nav>
    <news-modal v-bind:visible="newsVisible" :news="newsData" @close='visible=newsInit()'></news-modal>
</div>

</div>
</template>
<script>
import img from '@/assets/default.png'
import newsModal from './newsModal.vue'
import axios from 'axios'
// const LOCAL_HOST = 'http://localhost:3030/api'
const SERVER_HOST = 'https://j6a406.p.ssafy.io/api'

export default {
  data() {
    return {
      searchWord : null,
      newsData : {},
      newsVisible: false,
      searchedData : [],
      pageNumbers: [1, 2, 3, 4, 5],
      page: 1, // 기본 1페이지
      totalPage: '',
      totalCount: '',
      nowPage: 1,
      startPage: '',
      endPage: '',
      pre: false,
      next: false,
      start: false,
      end: false,
      loading: false,
    }
  },
    components: {
    newsModal
  },
  created() {
      this.searchWord = this.$route.params.searchWord;
      this.search();
  },

  methods: {
      detail (seq) {
      console.log("검색 시퀀스 : "+seq);
      axios.get(`${SERVER_HOST}/news/detail`, {
        params: {
          newsSeq : seq,           
        }
      }).then((res) =>{
        //  console.log(res.data.data);
        this.newsData = res.data.data;
        this.newsVisible = !this.newsVisible;
      }).catch((err) => {
        console.log("에러");
        console.log(err);
      });

    },
    newsInit: function () {
       this.newsVisible = false;
    },
    search() {
      console.log("검색 키워드 확인 : "+this.searchWord);
      if(this.searchWord != null && this.searchWord !="") {
        this.loading = true;
        axios.get(`${SERVER_HOST}/news/keyword`, {
          params: {
            keyword : this.searchWord,
            page : this.nowPage,
            size : 5
          }
        })
        .then((res) =>{
          this.loading = false;
          console.log(res.data);
          this.searchedData = res.data.data;
          const totalElements = res.data.data[0].totalElements;
          this.pagination(totalElements);

          
        }).catch((err) => {
            console.log("에러");
            console.log(err);
          })
      }
    },

    pagination: function (total) {
      this.totalPage = parseInt(total / 6)
      if ((total % 6) !== 0) {
        this.totalPage = this.totalPage + 1
      }
      this.startPage = (parseInt((this.nowPage - 1) / 5)) * 5 + 1
      this.endPage = this.startPage + 4

      if (this.endPage > this.totalPage) {
        this.endPage = this.totalPage
      }
      this.pre = false
      this.start = false

      if ((this.nowPage - 5) >= 1) {
        this.pre = true
      }
      if (this.startPage > 5) {
        this.start = true
      }
      this.next = false
      this.end = false

      if (this.endPage < this.totalPage) {
        this.next = true
        this.end = true
      }

      const s = this.startPage
      const e = this.endPage
      for (let i = s; i <= e; i++) {
        this.pageNumbers[i - s] = i
       
      }
    },
    thisPage: function (num) {
      if (num > this.totalPage) {
        num = this.totalPage
      }

      this.nowPage = num
      this.search();
    },

    replaceDefault: function (e) {
      e.target.src = img
    },

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