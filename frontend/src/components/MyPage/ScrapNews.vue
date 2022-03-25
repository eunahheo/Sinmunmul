<template>
<div class="container">
<div id="mypageheader" style="text-align: left"> <h3>스크랩기사</h3>  </div>
<hr>
<div class="row" >
  <div class="col-4 mb-5" v-for="news in newsList" :key="news.scrapSeq">
    <div class="card" style="width: 20rem;  cursor: pointer" >
      <div style="text-align : right
    ;"> <svg  xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x" viewBox="0 0 16 16" @click="deleteNews">
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
      newsList: []
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
          page: 1
        }
      })
        .then((res) => {
          console.log(res)
          this.newsList = res.data.data.content
        })
        .catch((err) => {
          console.log(err)
        })
      window.alert(' api통신을 통해 스크랩 뉴스 리스트 가져오기 ')
    },
    deleteNews: function () {
      window.alert('삭제합니다')
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
