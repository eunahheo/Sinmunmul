<template>
<div>
    <aside class="cssgrid-collection"> 
     
    </aside>

 <main>
   <div class="title--large main-title plan"> 
     <h2 class="title--large main-title">오늘의 뉴스 현황 &nbsp {{todayNews}}
     </h2>
  
     
   </div>
    
     
       
    <div class="plan span--2 long--2 m-3 ">
       <h3>이슈 - 워드 클라우드</h3>
      <div id="word-cloud">
        </div>
    </div>

    <div class="plan span--2 long--2 m-3">
    <a class="terrarium" href="" target="_blank">
      <figure><img src=""/>
        <figcaption>해당 토픽에 대한 관련 기사 자리</figcaption>
      </figure>
    </a>
    </div>

    <div class="plan span--2 long--2 ">
       <h3>최고 빈도 키워드 뉴스</h3>
       <!-- <bar-chart :data="chartData"></bar-chart> -->
       <bar-chart :data="chartData" :colors="['#0b6e00', '#006ca2', '#10379c']"></bar-chart>
    </div>
    
    <div class="plan span--2 long--2 ">
      <h3>키워드 언급량 추이 그래프</h3>
      <line-chart :data="lineData" ></line-chart>
    </div>


    <div class="span--2 long--2">
      <h2>추천 기사 자리 1</h2>
    </div>

    <div class="span--2 long--2">
      <h2>추천기사 자리 2</h2>
    </div>

    <div class="item-with-image cssgrid-collection">
        <a class="cssgrid-collection__image" href="#" target="_blank"><img src=""/></a>
      <h1>임시 푸터 자리</h1>
      <div class="cssgrid-collection__content">
      </div>
    </div>

    <div class="sidebar">
      <h2 class="title--big">사이드 바</h2>
      <h3>필요한지는 모름</h3>
      <h5> test text </h5>
    </div>
  </main>

</div>
</template>

<script>
import axios from 'axios'
// const LOCAL_HOST = 'http://localhost:3030/api'
const SERVER_HOST = 'https://j6a406.p.ssafy.io/api'

export default {
  data() {
    return {
      words: [
        {text : "Vue", size : 40},
        {text : "Hi", size : 25},
        {text : "신문물", size : 50},
        {text : "뉴스빅", size : 45},
        {text : "ㅋㅋㅋ", size : 31},
        {text : "ㄴㄴㄴ", size : 32},
        {text : "ㅇㅇㅇ", size : 37},
        {text : "Bye", size : 42},
        {text : "good", size : 38},
        {text : "bad", size : 36},
        
      ],
      chartData: [
          ['검색어 1', 422],
          ['검색어 2', 1092],
          ['검색어 3', 1192],
          ['검색어 4', 1292],
          ['검색어 5', 1392],
          ['검색어 6', 1492],
          ['검색어 7', 1552],
          ['검색어 8', 1592],
          ['검색어 9', 2692]
        ],
       lineData : [ {name: '검색어1',  data: {
      '1월': 3, 
      '2월': 4,
      '3월': 14,
      '4월': 24,
      '5월': 34,
      '6월': 44,
      '7월': 54,
      '8월': 36,
      '8월': 26,
      '9월': 16,
      '10월': 36,
      '11월': 246,
      '12월': 146
      }},

      {name: '검색어2',  data: {
      '1월': 31, 
      '2월': 41,
      '3월': 134,
      '4월': 241,
      '5월': 341,
      '6월': 441,
      '7월': 514,
      '8월': 316,
      '8월': 216,
      '9월': 126,
      '10월': 136,
      '11월': 246,
      '12월': 146
      }},

      {name: '검색어3',  data: {
      '1월': 311, 
      '2월': 14,
      '3월': 14,
      '4월': 214,
      '5월': 314,
      '6월': 414,
      '7월': 514,
      '8월': 316,
      '8월': 26,
      '9월': 16,
      '10월': 36,
      '11월': 246,
      '12월': 146
      }},

      {name: '검색어4',  data: {
      '1월': 32, 
      '2월': 34,
      '3월': 214,
      '4월': 324,
      '5월': 134,
      '6월': 244,
      '7월': 654,
      '8월': 326,
      '8월': 6,
      '9월': 16,
      '10월': 36,
      '11월': 546,
      '12월': 146
      }},

      {name: '검색어5',  data: {
      '1월': 3, 
      '2월': 4,
      '3월': 14,
      '4월': 254,
      '5월': 344,
      '6월': 414,
      '7월': 524,
      '8월': 336,
      '8월': 246,
      '9월': 156,
      '10월': 636,
      '11월': 746,
      '12월': 846
      }}
  ], //line data

    todayNewsData : [],
    todayNews : null,
    };//return

  },//data

  mounted() {
    this.genLayout();
    this.getTodayNews();

  }, //mounted

  methods : {
    genLayout() {
      const cloud = require("d3-cloud");
      cloud()
      .words(this.words)
      .padding(1.5)
      .font("Impact")
      .fontSize(function(d) {
        return d.size;
      })
      .on("end", this.end)
      .spiral("archimedean")
      .start();
      //.stop();
    },
    end(words) {
      const d3 = require("d3");
      const width = 400;
      const height = 180;

      d3.select("#word-cloud")
      .append("svg")
      .attr("width", width)
      .attr("height", height)
      // .attr("class", "span--2 long--2")
      .style("background", "white")
      .append("g")
      .attr("transform", "translate(" + width /2 + "," +height /2 +")") //g를 중심으로 단어그림 svg 중심으로 이동
      .selectAll("text")
      .data(words)
      .enter()
      .append("text")
      .style("font-size", (d)=>{return d.size +"px";})
      .style("font-family", "middle")
      .attr("text-anchor", "middle")
      .attr("transform", (d)=>{return "translate(" + [d.x, d.y] +") rotate (" +d.rotate +")";})
      .text((d)=>d.text);
    },

    getTodayNews() {
      axios.get(`${SERVER_HOST}/news/today`)
        .then((res) =>{
          //console.log(res.data.data);
          this.todayNewsData = res.data.data;

          var keys = Object.keys(this.todayNewsData);
          var values = Object.values( this.todayNewsData);
          var news ="";
          for(var i=0; i < keys.length-1; i++) {
            
            news += keys[i];
            const per = values[i].percent;
            news += " "+per.toFixed(1)+"% ";
          }

          this.todayNews = news;

          
          
        }).catch((err) => {
            console.log("에러");
            console.log(err);
        });
    },
  }, //methods

};
</script>

d3.layout.cloud(): 클라우드 레이아웃 생성
on(type, listner): 이벤트 리스너. 현재는 word와 end만 지원
word 는 단어 하나하나 위치시켜놓을 때마다 발생. 해당 word가 argument로 들어온다.
end 는 모든 단어를 다 위치 시켜놓을 떄 발생. 모든 단어들과 경계선 좌표([{x0, y0}, {x1, y1}])가 argument로 들어온다.
start(): 레이아웃 알고리즘 시작. 단어들을 큰 것부터 넣기 시작하고 충돌을 체크해서 발생할때마다 다른 위치로 넣는다. (만약에 들어갈 자리가 없을 경우 포함되지 않으니 유의)
stop(): 레이아웃 알고리즘 멈춤.
timeInterval([time]): 내부적으로 setInterval 사용하여 event loop가 계속 되도록 해준다. default는 Infinity
words: 클라우드에 띄울 단어들. default는 []
size([size]): 레이아웃의 크기를 결정한다.(width, height) default는 [1, 1]
font([font]): 단어들의 폰트를 결정한다. default는 serif
fonrStyle([fontStyle]): 폰트 스타일을 결정한다. default는 normal
fontWeight([fontWeight]): fontWeight을 결정한다. default는 'normal'
fontSize([fontSize]): font 크기를 결정한다. (예. {text: 'word', value: 30}
rotate([rotate]): 각 단어의 회전각을 결정. 
function() { return (~~(Math.random() * 6) - 3) * 30; }
text([text]): words에서 넣은 값에서 표시할 것을 명시한다. 예를 들어 words에 {text: 'word', value: 30 }를 넣었다면
function(d) { return d.text;}
spiral([spiral]): 단어를 위치시킬 떄 사용하는 나선형의 형태를 결정할 수 있는데 archimedean(원형) 또는 rectangular를 선택할 수 있으며 함수로 임의로 만들 수도 있다. default로 내장된 archimedean을 사용한다.
padding([padding]): 각 단어의 padding을 결정. default는 1.
random([random]): 내부적으로 단어를 초기 위치와 시계, 반시계 방향 각도를 결정할 때 사용하는 랜덤 숫자 생성기. 0 이상 1 미만 숫자가 들어가야하며 default는 Math.random
canvas([canvas]): 캔버스 생성기

<style scoped>


h1 {
  font: 50px/1 "Playfair Display SC";
  text-align: center;
}


h3 {
  font: italic 20px var(--font-title);
  margin-bottom: 1rem;
}

h4 {
  font: 20px/1.2 var(--font-title);
}

h5 {
  font: 700 20px/1 var(--font);
  transition: 0.3s ease;
}

p {
  line-height: 1.3;
}
p a {
  display: inline;
}

.title--large {
  text-align: center;
  font-family: var(--font-title);
  font-size: 32px;
  font-style: normal;
  margin-bottom: 0.8rem;
}
/* .category {
  text-align: right;
} */

@media (min-width: 700px) {
  .title--large {
    font-size: 32px;
    margin: 0;
  }
}

@media (min-width: 700px) {
  main .main-title {
    grid-column: 1/-1;
  }
}
@media (min-width: 1024px) {
  main .main-title {
    grid-column: 1/-2;
  }
}

@media (min-width: 700px) {
  main {
    display: grid;
    grid-template-columns: repeat(4, 1fr) 23%;
    grid-template-rows: repeat(5, auto);
    grid-gap: 1.2rem;
  }
}
main h1,
main aside {
  grid-column: 1/-1;
}

@media (min-width: 700px) {
  main .terrarium {
    grid-column: 1/-1;
  }
}
@media (min-width: 1024px) {
  main .terrarium {
    grid-column: 3/span 2;
  }
}
@media (min-width: 700px) {
  main .main-text {
    grid-column: span 5;
  }
}
@media (min-width: 1024px) {
  main .main-text {
    grid-column: span 2;
  }
}
@media (min-width: 700px) {
  main .sidebar {
    grid-column: 1/-1;
  }
}
@media (min-width: 1024px) {
  main .sidebar {
    /* grid-row: 3/9;
    grid-column: 5/6; */
    grid-row: 1/9;
    grid-column: 5/6;
  }
}

main .article-bar-1 {
  grid-row: span 4;
}

@media (min-width: 700px) {
  main .menu {
    grid-column: 1/-1;
    grid-row: 13;
  }
}
@media (min-width: 1024px) {
  main .menu {
    grid-row: 7/8;
    grid-column: 2/4;
  }
}
@media (min-width: 700px) and (max-width: 1024px) {
  main .toggles {
    grid-column: 3/6;
    grid-row: 10/13;
  }
}
@media (min-width: 700px) and (max-width: 1024px) {
  main .plan {
    grid-column: span 4;
  }
}
@media (min-width: 700px) and (max-width: 1024px) {
  main .style,
main .magazine,
main .pasta {
    grid-column: 1/3;
  }
}
main .cssgrid-collection {
  grid-column: 1/-1;
  grid-row: 9;
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

.with-border {
  border-top: 1px solid;
  padding-top: 0.6rem;
}

img {
  width: 100%;
  filter: grayscale(95%);
  margin-bottom: 0.5rem;
  border: 1px solid var(--black);
  transition: 0.3s ease;
}

figcaption {
  font-style: italic;
  font-size: 90%;
}

aside {
  text-align: center;
  padding: 3px 0;
  border: solid var(--black);
  border-width: 2px 0;
  text-transform: uppercase;
  letter-spacing: 1px;
  margin: 1.5rem 0;
}
@media (min-width: 700px) {
  aside {
    margin: 0;
  }
}
aside > div {
  display: flex;
  align-items: center;
  border: solid var(--black);
  border-width: 1px 0;
}
aside > div > div {
  flex: 1;
  padding: 8px;
}

@media (min-width: 700px) {
  .multi-column {
    column-count: 2;
    column-gap: 1.3rem;
    margin-top: 0.75rem;
  }
  .multi-column-3 {
    column-count: 3;
  }
}
.sidebar {
  margin-top: 3rem;
}
@media (min-width: 700px) and (max-width: 1024px) {
  .sidebar {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    align-items: flex-start;
    margin: 0;
  }
  .sidebar h3 {
    width: 100%;
    text-align: center;
  }
  .sidebar > a {
    flex: 0 1 48%;
    margin: 1.5rem 0;
    padding: 0;
  }
  .sidebar .slack-ui {
    border-top: 0;
  }
 
}
@media (min-width: 1024px) {
  .sidebar {
    display: block;
    border-left: 1px solid;
    padding-left: 1.2rem;
    margin: 0;
  }
}

.main-text.multi-column {
  margin: 0;
}
.main-text.multi-column p {
  margin-bottom: 0.8rem;
}

.terrarium {
  margin: 1.5rem 0;
}
@media (min-width: 700px) {
  .terrarium {
    margin: 0 0 1.5rem;
  }
}
.terrarium figure {
  height: 100%;
}
.terrarium img {
  height: 96%;
  object-fit: cover;
  object-position: right;
}

.sidebar-item {
  margin: 2rem 0;
  padding: 2rem 0 0;
}
.sidebar-item h5 {
  text-align: center;
  width: 100%;
  padding: 0.5rem;
  margin: auto;
}
.sidebar-item p {
  margin-top: 1rem;
}
.sidebar-item:hover h5 {
  transition: 0.3s ease;
  background: var(--black);
  color: #fff;
}

.item-with-image {
  margin-top: 1.5rem;
}
.item-with-image h4 {
  font-size: 24px;
  text-align: left;
  margin-bottom: 0.5rem;
  transition: 0.2s ease;
}
@media (min-width: 700px) {
  .item-with-image {
    margin: 0;
  }
}
.item-with-image:not(.cssgrid-collection):hover h4 {
  color: white !important;
  background: var(--black);
}

.menu {
  margin: 1.5rem 0;
}
@media (min-width: 1024px) {
  .menu {
    margin: 0;
  }
}
.menu figure {
  height: 100%;
}
.menu img {
  height: 90%;
  object-fit: cover;
  object-position: left;
}

.cssgrid-collection {
  display: flex;
  align-content: stretch;
  border-top: 2px solid;
  padding-top: 0.5rem;
}
.cssgrid-collection h4 {
  margin: 0 0 0.8rem;
}
.cssgrid-collection__image {
  flex: 0 0 32%;
  margin-right: 1.5rem;
}
.cssgrid-collection img {
  height: 100%;
  object-fit: cover;
  object-position: left;
}
.cssgrid-collection p a {
  border-bottom: 1px dashed;
}
.cssgrid-collection p a:hover {
  border-bottom: 1px solid;
}

.plan {
  padding-bottom: 1rem;
}
@media (min-width: 700px) and (max-width: 1024px) {
  .plan {
    grid-column: span 3;
    margin: 0 0 1.5rem;
  }
}
@media (min-width: 1024px) {
  .plan {
    border-bottom: 1px solid;
  }
}

.pie:hover img,
.menu:hover img,
.terrarium:hover img,
.plan:hover img,
.toggles:hover img,
.cssgrid-collection__image:hover img {
  filter: grayscale(0);
}

</style>



