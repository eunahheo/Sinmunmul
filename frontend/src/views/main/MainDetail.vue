<template>
  <div class="container">
    <div>
      <div class="input-group input-group-lg m-5 mt-4 mb-4">
        <span class="input-group-text" id="inputGroup-sizing-lg">검색어</span>
        <input
          type="text"
          class="form-control"
          v-model="searchWord"
          @keyup.enter="search"
        />
      </div>

      <ul class="nav nav-tabs m-3">
        <li
          class="nav-item container row"
          style="float: none; margin: 100 auto"
        >
          <h3 class="">검색 결과</h3>
        </li>
      </ul>

      <main class="m-3">
        <div class="plan span--2 long--2">
          <span style="font-size: calc(1.3rem + 0.6vw)"> 연관 키워드 </span>

          <span style="float: right"
            ><ul class="wrapper">
              <li class="icon facebook">
                <span class="tooltip"
                  >검색 키워드가 언급된 기사들을 Word2Vec을 활용하여 연관어
                  분석을 수행합니다. <br />(연관도) X (키워드 언급횟수) 를
                  계산하여 상위 20개의 키워드를 워드클라우드 형태로 제공됩니다.
                </span>
                <span><i class="fab">i</i></span>
              </li>
            </ul></span
          >

 <div style=" width = 600; height = 300;">
              <div class="spinner-div temp" v-show="this.wordcloudState === 1">
                  <span class="spinner-border spinner-border-m"  ></span>
                  조회중입니다.
              </div>

          <div v-show="this.wordcloudState === 2" id="word-cloud"></div>

          <div v-show="this.wordcloudState === 3" class="temp"  ><small style="font-size:20px">연관 키워드를 불러올 수 없습니다. </small></div>
</div>
</div>
        <div class="plan span--2 long--2">
          <span style="font-size: calc(1.3rem + 0.6vw)">
            키워드 주간 기사량
          </span>

          <span style="float: right"
            ><ul class="wrapper">
              <li class="icon facebook">
                <span class="tooltip"
                  >검색 키워드의 주간 기사량 추이를 나타내는 그래프입니다.
                </span>
                <span><i class="fab">i</i></span>
              </li>
            </ul></span
          >

          <line-chart :data="chartData"></line-chart>
        </div>
      </main>

      <ul class="nav nav-tabs m-4">
        <li
          class="nav-item container row"
          style="float: none; margin: 100 auto"
        >
          <h3 class="">관련 뉴스 목록</h3>
        </li>
      </ul>

      <div
        v-if="loading"
        class="spinner-border text-center"
        style="width: 3rem; height: 3rem"
        role="status"
      >
        <span class="visually-hidden">Loading...</span>
      </div>
      <div>
        <div
          class="card m-3"
          style="max-width: 100%; height: 410px"
          v-for="news in searchedData"
          :key="news.news_seq"
        >
          <div class="cards">
            <div @click="detail(news.news_seq)" style="cursor: pointer">
              <div class="card__image-holder">
                <img
                  v-if="news.news_photo !== ''"
                  class="card-img-top"
                  v-bind:src="news.news_photo"
                  alt="Card image"
                  style="object-fit: cover"
                  @error="replaceDefault"
                />
                <img
                  v-else
                  src="../../../src/assets/shin_logo.png"
                  class="card-img-top"
                  style="object-fit: fill"
                  @error="replaceDefault"
                />
              </div>
              <div class="m-2 card-title">
                <h2>
                  <div style="height: 50px">
                    {{
                      news.news_Title.length > 25
                        ? news.news_Title.substring(0, 25) + "..."
                        : news.news_Title
                    }}
                  </div>
                  <hr />
                  <small class="m-2 detail-card-desc mb-1">{{
                    news.news_desc
                  }}</small>
                </h2>
              </div>
            </div>
          </div>
        </div>
      </div>

      <nav aria-label="..." class="d-flex justify-content-center mb-4">
        <ul class="pagination d-flex justify-content-between">
          <li v-if="start" class="page-item">
            <a class="page-link" href="#" @click="thisPage(1)">«</a>
          </li>
          <li v-else class="page-item disabled">
            <a class="page-link" href="#" tabindex="-1" aria-disabled="true"
              >«</a
            >
          </li>

          <li v-if="pre" li class="page-item">
            <a class="page-link" href="#" @click="thisPage(nowPage - 5)">‹</a>
          </li>
          <li v-else li class="page-item disabled">
            <a class="page-link" href="#" tabindex="-1" aria-disabled="true"
              >‹</a
            >
          </li>
          <li
            v-for="pageitem in pageNumbers"
            v-bind:id="'p' + pageitem"
            v-bind:class="{ ' active': pageitem == nowPage }"
            :key="pageitem"
            class="page-item"
          >
            <a class="page-link" href="#" @click="thisPage(pageitem)">{{
              pageitem
            }}</a>
          </li>
          <li v-if="next" class="page-item">
            <a class="page-link" href="#" @click="thisPage(nowPage + 5)">›</a>
          </li>
          <li v-else li class="page-item disabled">
            <a class="page-link" href="#" tabindex="-1" aria-disabled="true"
              >›</a
            >
          </li>

          <li v-if="end" class="page-item">
            <a class="page-link" href="#" @click="thisPage(totalPage)">»</a>
          </li>
          <li v-else li class="page-item disabled">
            <a class="page-link" href="#" tabindex="-1" aria-disabled="true"
              >»</a
            >
          </li>
        </ul>
      </nav>
      <news-modal
        v-bind:visible="newsVisible"
        :news="newsData"
        @close="visible = newsInit()"
      ></news-modal>
    </div>
  </div>
</template>
<script>
import img from "@/assets/default.png";
import newsModal from "./newsModal.vue";
import axios from "axios";
// const LOCAL_HOST = 'http://localhost:3030/api'
const SERVER_HOST = "https://j6a406.p.ssafy.io/api";

export default {
  data() {
    return {
      words: [
        { text: "글자1", size: 40, color: "#f6535d" },
        { text: "글자2", size: 36, color: "#377cc4" },
        { text: "글자3", size: 32, color: "#b168e0" },
        { text: "글자4", size: 28, color: "#1a9a9f" },
        { text: "글자5", size: 24, color: "#fac302" },
        { text: "글자6", size: 24, color: "#d86898" },
        { text: "글자7", size: 24, color: "#f9870e" },
        { text: "글자8", size: 18, color: "#6E6E6E" },
        { text: "글자9", size: 18, color: "#6E6E6E" },
        { text: "글자10", size: 18, color: "#6E6E6E" },
        { text: "글자11", size: 18, color: "#6E6E6E" },
        { text: "글자12", size: 18, color: "#6E6E6E" },
        { text: "글자13", size: 18, color: "#6E6E6E" },
        { text: "글자14", size: 18, color: "#6E6E6E" },
        { text: "글자15", size: 18, color: "#6E6E6E" },
        { text: "글자16", size: 18, color: "#6E6E6E" },
        { text: "글자17", size: 18, color: "#6E6E6E" },
        { text: "글자18", size: 18, color: "#6E6E6E" },
        { text: "글자19", size: 18, color: "#6E6E6E" },
        { text: "글자20", size: 18, color: "#6E6E6E" },
      ],
      preWord: null,
      searchWord: null,
      newsData: {},
      newsVisible: false,
      searchedData: [],
      pageNumbers: [1, 2, 3, 4, 5],
      page: 1, // 기본 1페이지
      totalPage: "",
      totalCount: "",
      nowPage: 1,
      startPage: "",
      endPage: "",
      pre: false,
      next: false,
      start: false,
      end: false,
      chartData: [{ name: "", data: {} }],
      wordcloudState: null // 1은 조회 중  ,2 관련 키워드 조회 성공 , 3 관련 키워드 조회 실패
    };
  },
  components: {
    newsModal,
  },
  created() {
    this.searchWord = this.$route.params.searchWord;
    this.preWord = this.searchWord;
    this.wordcloud();
    this.chartMake(this.searchWord);
    this.search();
  },

  methods: {
    genLayout() {
      const cloud = require("d3-cloud");
      cloud()
        .words(this.words)
        .padding(1)
        .rotate(0)
        .font("serif")
        .fontSize(function (d) {
          return d.size;
        })
        .on("end", this.wordcloudend)
        .spiral("archimedean")
        .start()
        .stop();
    },
    wordcloudend(words) {
      console.log(words);
      const d3 = require("d3");
      const width = 600;
      const height = 300;
      d3.select("#word-cloud")
        .html("")
        .append("svg")
        .attr("width", width)
        .attr("height", height)
        .style("background", "white")
        .append("g")
        .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")") // g를 중심으로 단어그림 svg 중심으로 이동
        .selectAll("text")
        .data(words)
        .enter()
        .append("text")
        .attr("class", (d) => {
          return d.text;
        })
        .style("font-size", (d) => {
          return d.size + "px";
        })
        .style("cursor", "pointer")
        .style("fill", (d) => {
          return d.color;
        }) // 색지정
        .style("fill-opacity", 1) // 투명도 조절
        .style("font-family", "Impact")
        .style("font-weight", "bold")
        .attr("text-anchor", "middle")
        .attr("transform", (d) => {
          return (
            "translate(" +
            [d.x * 1.2, d.y * 1.2] +
            ") rotate (" +
            d.rotate +
            ")"
          );
        })
        .text((d) => d.text);
    },

    wordcloud() {
      // 키워드 연관어 받아오는 api 호출
      this.wordcloudState = 1
      axios({
        method: "get",
        url: "https://j6a406.p.ssafy.io/fapi/news/search/wordcloud",
        params: { keyword: this.searchWord },
      })
        .then((res) => {
          console.log(res);

            if(res.data.statusCode === 200)
            {

          console.log('여기 오는거 아닌가요?')
            this.wordcloudState = 2
                 console.log(this.wordcloudState)
                  const data = res.data.data;

          for (let index = 0; index < 20; index++) {
            this.words[index].text = data[index].keyword;
          }
          this.genLayout();
            }

        else {
          this.wordcloudState = 3
        }

        })
        .catch((err) => {
          console.log(err);
        });
    },
    detail(seq) {
      // console.log("검색 시퀀스 : "+seq);
      axios
        .get(`${SERVER_HOST}/news/detail`, {
          params: {
            newsSeq: seq,
          },
        })
        .then((res) => {
          //  console.log(res.data.data);
          this.newsData = res.data.data;
          this.newsVisible = !this.newsVisible;
        })
        .catch((err) => {
          console.log("에러");
          console.log(err);
        });
    },
    newsInit: function () {
      this.newsVisible = false;
    },
    search() {
      if (this.searchWord !== this.preWord) {
        this.preWord = this.searchWord;
        this.nowPage = 1;
        this.wordcloud();
      }
      this.searchedData = [];
      // console.log("검색 키워드 확인 : "+this.searchWord);
      if (this.searchWord != null && this.searchWord != "") {
        this.loading = true;
        axios
          .get(`${SERVER_HOST}/news/keyword`, {
            params: {
              keyword: this.searchWord,
              page: this.nowPage,
              size: 9,
            },
          })
          .then((res) => {
            console.log(res.data);
            this.searchedData = res.data.data;
            const totalElements = res.data.data[0].totalElements;
            this.pagination(totalElements);
            this.chartMake(this.searchWord);
            this.loading = false;
          })
          .catch((err) => {
            console.log("에러");
            alert("검색 결과가 없습니다.");
            console.log(err);
            this.loading = false;
          });
      }
    },
    pagination: function (total) {
      this.totalPage = parseInt(total / 9);
      if (total % 9 !== 0) {
        this.totalPage = this.totalPage + 1;
      }
      this.startPage = parseInt((this.nowPage - 1) / 5) * 5 + 1;
      this.endPage = this.startPage + 4;

      if (this.endPage > this.totalPage) {
        this.endPage = this.totalPage;
      }
      this.pre = false;
      this.start = false;

      if (this.nowPage - 5 >= 1) {
        this.pre = true;
      }
      if (this.startPage > 5) {
        this.start = true;
      }
      this.next = false;
      this.end = false;

      if (this.endPage < this.totalPage) {
        this.next = true;
        this.end = true;
      }

      const s = this.startPage;
      const e = this.endPage;
      this.pageNumbers = [];
      for (let i = s; i <= e; i++) {
        this.pageNumbers[i - s] = i;
      }
    },
    thisPage: function (num) {
      if (num > this.totalPage) {
        num = this.totalPage;
      }

      this.nowPage = num;
      this.search();
    },

    replaceDefault: function (e) {
      e.target.src = img;
    },

    chartMake(keyword) {
      // console.log("차트 검색 키워드 "+keyword);
      axios
        .get(`${SERVER_HOST}/news/keyword/trend/week`, {
          params: {
            keywords: keyword,
          },
        })
        .then((res) => {
          this.chartData = [{ name: "", data: {} }]; //할당 안해주면 data 표시 안됨
          var values = Object.values(res.data.data[0].stat); //받은 result value들만 따로 정제
          var temp = {}; //value를 속성, 값으로 만들어줄 객체

          for (var i = 0; i < values.length; i++) {
            let label = values[i].label;
            temp[label] = values[i].count; //temp 객체에 label 속성과 count 값 할당
          }

          //차트 데이터 할당
          this.chartData[0].name = keyword;
          this.chartData[0].data = temp;

          console.log(this.chartData);
        })
        .catch((err) => {
          console.log("에러");
          alert("검색 결과가 없습니다.");
          console.log(err);
        });
    },
  },
};
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
.card-img-top {
  height: 200px;
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
#word-cloud {
  /* border  : 1px solid blue; */
  -webkit-border-radius: 10px;
  -moz-border-radius: 10px;
  border-radius: 10px;
  -webkit-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.1);
  -moz-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.1);
  box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.1);
}

bar-chart {
  height: 400px;
}

/* card */
@import url(https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,200,300,600,700,900);
body {
  background: #dce1df;
  color: #4f585e;
  font-family: "Source Sans Pro", sans-serif;
  text-rendering: optimizeLegibility;
}
a.btn {
  background: #0096a0;
  border-radius: 4px;
  box-shadow: 0 2px 0px 0 rgba(0, 0, 0, 0.25);
  color: #ffffff;
  display: inline-block;
  padding: 6px 30px 8px;
  position: relative;
  text-decoration: none;
  transition: all 0.1s 0s ease-out;
}
.no-touch a.btn:hover {
  background: #00a2ad;
  box-shadow: 0px 8px 2px 0 rgba(0, 0, 0, 0.075);
  transform: translateY(-2px);
  transition: all 0.25s 0s ease-out;
}
.no-touch a.btn:active,
a.btn:active {
  background: #008a93;
  box-shadow: 0 1px 0px 0 rgba(255, 255, 255, 0.25);
  transform: translate3d(0, 1px, 0);
  transition: all 0.025s 0s ease-out;
}

div.card {
  background: #ffffff;
  display: inline-block;
  height: 410px;
  margin: 13px;
  max-width: 300px;
  position: relative;
  text-align: left;
  transition: all 0.3s 0s ease-in;
  width: 300px;
  z-index: 1;
}
div.card img {
  max-width: 300px;
  object-fit: cover;
}
div.card .card__image-holder {
  height: 0;
  padding-bottom: 75%;
  max-width: 300px;
  height: 150px;
}

div.card div.card-title {
  background: #ffffff;
  padding: auto;
  position: relative;
  height: 100%;
  z-index: 0;
}

div.card div.card-title h2 {
  font-size: 22px;
  font-weight: 700;
  letter-spacing: -0.05em;
  margin: 0;
  padding: 0;
}
div.card div.card-title h2 small {
  display: -webkit-box;
  word-wrap: break-word;
  -webkit-line-clamp: 4;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
  height: 90px;
}
div.card div.card-description {
  padding: 0 15px 10px;
  position: relative;
  font-size: 14px;
}
div.card div.card-actions {
  box-shadow: 0 2px 0px 0 rgba(0, 0, 0, 0.075);
  padding: 10px 15px 20px;
}

.no-touch div.cards.showing div.card:hover {
  opacity: 0.94;
  transform: scale(0.92);
}
div.card.show {
  opacity: 1 !important;
  transform: scale(1) !important;
}
div.card.show div.card-title a.toggle-info {
  background: #ff6666 !important;
}
div.card.show div.card-title a.toggle-info span {
  top: 15px;
}
div.card.show div.card-title a.toggle-info span.left {
  right: 10px;
}
div.card.show div.card-title a.toggle-info span.right {
  left: 10px;
}
div.card.show div.card-flap {
  background: #ffffff;
  transform: rotateX(0deg);
}
div.card.show div.flap1 {
  transition: all 0.3s 0s ease-out;
}
div.card.show div.flap2 {
  transition: all 0.3s 0.2s ease-out;
}
/* tooltip */
/*
    Auther: Abdelrhman Said
*/

@import url("https://fonts.googleapis.com/css2?family=Poppins&display=swap");

*:focus,
*:active {
  outline: none !important;
  -webkit-tap-highlight-color: transparent;
}

html,
body {
  display: grid;
  height: 100%;
  width: 100%;
  font-family: "Poppins", sans-serif;
  place-items: center;
  background: linear-gradient(315deg, #ffffff, #d7e1ec);
}

.wrapper {
  display: inline-flex;
  list-style: none;
}

.wrapper .icon {
  position: relative;
  background: rgb(255, 254, 254);
  border-radius: 50%;
  padding: 15px;
  margin: 10px;
  width: 10px;
  height: 10px;
  font-size: 18px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.08);
  cursor: pointer;
  transition: all 0.2s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}

.wrapper .tooltip {
  position: absolute;
  top: 0;
  font-size: 14px;
  background: #ffffff;
  color: #ffffff;
  padding: 5px 8px;
  border-radius: 5px;
  width: 300px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.1);
  opacity: 0;
  pointer-events: none;
  transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}

.wrapper .tooltip::before {
  position: absolute;
  content: "";
  height: 8px;
  width: 5px;
  background: #ffffff;
  bottom: -3px;
  left: 50%;
  transform: translate(-50%) rotate(45deg);
  transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}

.wrapper .icon:hover .tooltip {
  top: -45px;
  width: 250px;
  opacity: 1;
  visibility: visible;
  pointer-events: auto;
}

.wrapper .icon:hover span,
.wrapper .icon:hover .tooltip {
  text-shadow: 0px -1px 0px rgba(0, 0, 0, 0.1);
}

.wrapper .facebook:hover,
.wrapper .facebook:hover .tooltip,
.wrapper .facebook:hover .tooltip::before {
  background: #1877f2;
  color: #ffffff;
}

.temp {
  line-height: 300px;
  text-align: center;
}
</style>
