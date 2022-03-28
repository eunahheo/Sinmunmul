<template>
<div class="container">
<div id="mypageheader" style="text-align: left"> <h3>스크랩기사</h3>  </div>
<hr>
<div class="row" >
  <div class="col-4 mb-5" v-for="news in newsList" :key="news.scrapSeq">
    <div class="card" style="width: 20rem;  cursor: pointer" >
      <div style="text-align : right
    ;"> <svg  xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x" viewBox="0 0 16 16" @click="deleteNews(news.news.newsSeq)">
  <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
</svg></div>
<div @click="modal">
<img v-bind:src="news.news.newsPhoto" class="card-img-top image"  @error="replaceDefault" >
  <div class="card-body">
    <div class="card-title" style="text-align: left;"><b>{{news.news.newsTitle}}</b></div>
    <hr>
    <p class="card-text scrap-card" style="text-align: left; font-size:12px;"> {{news.news.newsDesc}}</p>
  </div>
  </div>
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
</div>
</template>

<script>
import img from '@/assets/default.png'
import axios from 'axios'
const SERVER_HOST = process.env.VUE_APP_SERVER_HOST

export default {
  name: 'ScarapNews',
  data: function () {
    return {
      newsList: [],
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
      end: false
    }
  },

  created () {
    this.getScrapNews() // api를 요청하여 데이터를 받아온다
  },

  methods: {
    replaceDefault: function (e) {
      e.target.src = img
    },
    modal: function () {
      window.alert('모달창')
    },
    getScrapNews: function () {
      axios({
        method: 'get',
        // /mypage/{user_seq}/updatePassword
        url: `${SERVER_HOST}/mypage/7/searchScrap`,
        params: {
          page: this.nowPage
        }
      })
        .then((res) => {
          console.log(res)
          this.newsList = res.data.data.content
          this.pagination(res.data.data.totalElements)
        })
        .catch((err) => {
          console.log(err)
        })
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
      console.log(this.startPage)
      console.log(this.endPage)

      const s = this.startPage
      const e = this.endPage
      for (let i = s; i <= e; i++) {
        this.pageNumbers[i - s] = i
        console.log(this.pageNumbers)
      }
    },
    thisPage: function (num) {
      if (num > this.totalPage) {
        num = this.totalPage
      }
      this.nowPage = num
      this.getScrapNews()
    },
    deleteNews: function (seq) { // 유저 시퀀스도 가져오도록 변경?
      axios({
        method: 'delete',
        url: `${SERVER_HOST}/mypage/7/deleteScrap/${seq}`,
        params: {
          news_seq: seq
        }
      })
        .then((res) => {
          console.log(res)
          this.getScrapNews()
        })
        .catch((err) => {
          console.log(err)
        })
    }
  }
}
</script>

<style>
body{
 overflow : auto;
}

.image {
  height: 150px;
  object-fit: cover;

}

.scrap-card {
 display:-webkit-box;
  word-wrap:break-word;
  -webkit-line-clamp:6;
  -webkit-box-orient:vertical;
  overflow:hidden;
  text-overflow:ellipsis;
    height:108px
}

</style>
