<template>
<div class="container">
<div id="mypageheader" style="text-align: left"> <h3>스크랩기사</h3>  </div>
<hr>
<div class="row" >
  <div class="col-4 mb-5" v-for="article in articleList" :key="article.id">
    <div class="card" style="width: 20rem;  cursor: pointer" >
      <div style="text-align : right
    ;"> <svg  xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x" viewBox="0 0 16 16" @click="deleteNews">
  <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
</svg></div>
<div @click="modal">
  <img src="https://imgnews.pstatic.net/image/015/2022/03/22/0004676539_001_20220322110901043.jpg?type=w647" class="card-img-top image" alt="...">

  <div class="card-body">
    <h5 class="card-title" style="text-align: left;">{{article.title}}</h5>
    <hr>
    <p class="card-text scrap-card" style="text-align: left; font-size:12px;"> {{article.summary}}</p>
  </div>
  </div>
</div>
  </div>
</div>

</div>
</template>

<script>

// import axios from 'axios'
// const SERVER_HOST = process.env.VUE_APP_SERVER_HOST

export default {
  name: 'ScarapNews',
  data: function () {
    return {
      articleList: [
        { id: 1, title: '제목1', summary: '일본과 동남아 웹툰 시장에서 치열한 신경전을 벌여온 네이버와 카카오가 이번엔 유럽에서 맞붙는다.김준구 네이버웹툰 대표는 "유럽의 디지털 만화 시장은 이용자가 빠르게 증가하는 잠재력 높은 영역"이라며 "유럽 법인 설립으로 더욱 현지화된 전략을 펼칠 수 있을 것으로 기대한다"고 말했다. 네이버웹툰의 유럽 총괄 법인 설립은 카카오픽코마의 유럽 진출에 대응하려는 차원으로 해석된다된다 해석된다 된다해석된다된다해석된다된다해석된다된다해석된다된다해석된다된다해석된다된다해석된다된다해석된다된다해석된다된다해석된다된다해석된다된다해석된다된다해석된다된다' },
        { id: 2, title: '제목1', summary: '일본과 동남아 웹툰 시장에서 치열한 신경전을 벌여온 네이버와 카카오가 이번엔 유럽에서 맞붙는다.김준구 네이버웹툰 대표는 "유럽의 디지털 만화 시장은 이용자가 빠르게 증가하는 잠재력 높은 영역"이라며 "유럽 법인 설립으로 더욱 현지화된 전략을 펼칠 수 있을 것으로 기대한다"고 말했다. 네이버웹툰의 유럽 총괄 법인 설립은 카카오픽코마의 유럽 진출에 대응하려는 차원으로 해석된다' },
        { id: 3, title: '제목1', summary: '일본과 동남아 웹툰 시장에서 치열한 신경전을 벌여온 네이버와 카카오가 이번엔 유럽에서 맞붙는다.김준구 네이버웹툰 대표는 "유럽의 디지털 만화 시장은 이용자가 빠르게 증가하는 잠재력 높은 영역"이라며 "유럽 법인 설립으로 더욱 현지화된 전략을 펼칠 수 있을 것으로 기대한다"고 말했다. 네이버웹툰의 유럽 총괄 법인 설립은 카카오픽코마의 유럽 진출에 대응하려는 차원으로 해석된다' },
        { id: 4, title: '제목1', summary: '일본과 동남아 웹툰 시장에서 치열한 신경전을 벌여온 네이버와 카카오가 이번엔 유럽에서 맞붙는다.김준구 네이버웹툰 대표는 "유럽의 디지털 만화 시장은 이용자가 빠르게 증가하는 잠재력 높은 영역"이라며 "유럽 법인 설립으로 더욱 현지화된 전략을 펼칠 수 있을 것으로 기대한다"고 말했다. 네이버웹툰의 유럽 총괄 법인 설립은 카카오픽코마의 유럽 진출에 대응하려는 차원으로 해석된다' },
        { id: 5, title: '제목1', summary: '일본과 동남아 웹툰 시장에서 치열한 신경전을 벌여온 네이버와 카카오가 이번엔 유럽에서 맞붙는다.김준구 네이버웹툰 대표는 "유럽의 디지털 만화 시장은 이용자가 빠르게 증가하는 잠재력 높은 영역"이라며 "유럽 법인 설립으로 더욱 현지화된 전략을 펼칠 수 있을 것으로 기대한다"고 말했다. 네이버웹툰의 유럽 총괄 법인 설립은 카카오픽코마의 유럽 진출에 대응하려는 차원으로 해석된다' },
        { id: 6, title: '제목1', summary: '일본과 동남아 웹툰 시장에서 치열한 신경전을 벌여온 네이버와 카카오가 이번엔 유럽에서 맞붙는다.김준구 네이버웹툰 대표는 "유럽의 디지털 만화 시장은 이용자가 빠르게 증가하는 잠재력 높은 영역"이라며 "유럽 법인 설립으로 더욱 현지화된 전략을 펼칠 수 있을 것으로 기대한다"고 말했다. 네이버웹툰의 유럽 총괄 법인 설립은 카카오픽코마의 유럽 진출에 대응하려는 차원으로 해석된다' }
      ]
    }
  },

  created () {
    this.getScrapNews() // api를 요청하여 데이터를 받아온다
  },

  methods: {
    modal: function () {
      window.alert('모달창')
    },
    getScrapNews: function () {
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
