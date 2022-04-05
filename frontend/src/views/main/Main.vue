<template>
<div>


<news-modal  v-bind:visible="newsVisible" :news="newsData2" @close='visible=newsInit()'></news-modal>

  <div class="row">

    <div class="col-1"> </div>




    <div class="col-10">

      <div class="row"  style="text-align: left;">

       <h5 > 오늘의 뉴스 현황 &nbsp&nbsp&nbsp  {{todayNews}} </h5>
       <hr>
      </div>

      <div class="row mb-2">


      <div class="col-6 mb-4">



        <div>
       <h3>주요 키워드</h3>


       <div class="mb-2"> <button :class="{'main-btn-check': item.flag===1 , 'main-btn-uncheck': item.flag===0}"  v-for="item in codeGroup" :key="item.number"  @click="wordcloud(item.number)" >
         {{ item.name }}
       </button></div>
      <div  id="word-cloud"   style="width:100%; height:100%; text-align:center">
        </div>
    </div>



      </div>




      <div class="col-6 mb-3">

<div class="spinner-div" v-if="this.newsData==null">
<button class="btn">
    <span class="spinner-border spinner-border-m"></span>
    조회중입니다.
  </button>
</div>
              <div else>
<div v-for="item in newsData" :key="item.news_seq"  class="mb-1 main-news-list" @click="modal(item.news_seq)">
<div class="card">
  <div class="row">
  <div class="col-4 ">
    <img  class="card-img main-card-img" v-bind:src="item.news_photo" alt="Card image"  @error="replaceDefault" >
    </div>

    <div class="col-8">
    <div class="card-body">
      <div class="mb-2">
      <h5 class="card-title "  style="text-align: left;">{{item.news_Title}}
      </h5>
      </div>
      <hr>
      <div>
      <p class="card-text  main-card"  style="text-align: left; font-size:12px;">{{item.news_desc}}</p>
      </div>
    </div>
    </div>
    </div>
  </div>
  </div>
    </div>




      </div>

  <hr>
      </div>


<div class= "row mb-2">

  <div class="col-6 mb-3" >
    <div >
       <h3>최고 빈도 키워드 뉴스</h3>
       <!-- <bar-chart :data="chartData"></bar-chart> -->
       <bar-chart :data="chartData" :colors="['#0b6e00', '#006ca2', '#10379c']"></bar-chart>
    </div>

     </div>




<div class="col-6 mb-3" >

    <div >
      <h3>키워드 언급량 추이 그래프</h3>
      <line-chart :data="lineData" ></line-chart>
    </div>

</div>
<hr>
</div>



<div class="row mb-2">

<div class="col-6 mb-3">
<div>
      <h2>추천 기사 자리 1</h2>
    </div>

</div>
<div class="col-6 mb-3">



    <div>
      <h2>추천기사 자리 2</h2>
    </div>



</div>

</div>




































    </div>

    <div class="col-1"> </div>


  </div>

</div>
</template>

<script>
import axios from 'axios'
import VueWordCloud from 'vuewordcloud'
import wordcloud from 'vue-wordcloud'
import img from '@/assets/default.png'
import newsModal from '@/components/MyPage/newsModal.vue'
// const LOCAL_HOST = 'http://localhost:3030/api'
const SERVER_HOST = 'https://j6a406.p.ssafy.io/api'

export default {

  components: { VueWordCloud, wordcloud ,newsModal },
  data() {
    return {
      newsData2: {},
        newsVisible: false,
        newsData: null,
      codeGroup: [
          {number : 0 , name : '전체', flag:1},
          {number : 100 , name : '정치', flag:0},
          {number : 101 , name : '경제',flag:0},
          {number : 102 , name : '사회',flag:0},
          {number : 103 , name : '생활/문화',flag:0},
          {number : 104 , name : '세계',flag:0},
          {number : 105 , name : 'IT/과학',flag:0}


      ],

      words: [
        {text : "글자1", size : 40, color: "#f6535d"},
        {text : "글자2", size : 36, color: "#377cc4" },
        {text : "글자3", size : 32, color: "#b168e0" },
        {text : "글자4", size : 28, color: "#1a9a9f" },
        {text : "글자5", size : 24, color: "#fac302"},
        {text : "글자6", size : 24, color: "#d86898"},
        {text : "글자7", size : 24, color: "#f9870e"},
        {text : "글자8", size : 18, color: "#6E6E6E"},
        {text : "글자9", size : 18, color: "#6E6E6E"},
        {text : "글10", size : 18, color: "#6E6E6E"},
        {text : "글11", size : 18, color: "#6E6E6E"},
        {text : "글12", size : 18, color: "#6E6E6E"},
        {text : "글13", size : 18, color: "#6E6E6E"},
        {text : "글14", size : 18, color: "#6E6E6E"},
        {text : "글15", size : 18, color: "#6E6E6E"},
        {text : "글16", size : 18, color: "#6E6E6E"},
        {text : "글17", size : 18, color: "#6E6E6E"},
        {text : "글18", size : 18, color: "#6E6E6E"},
        {text : "글19", size : 18, color: "#6E6E6E"},
        {text : "글20", size : 18, color: "#6E6E6E"},
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



  created (){

     this.wordcloud(0)
  },

  mounted () {
    this.getTodayNews();

  }, //mounted

  methods: {
     newsInit: function () {
      this.newsVisible = false
    },

    modal:function (seq) {
      //seq로  뉴스 상세정보 조회
       axios({
        method: 'get',
        url: `${SERVER_HOST}/news/detail`,
         params: {
            newsSeq : seq,
          }
      })
        .then((res) => {
          console.log(res.data.data)
           this.newsData2 = res.data.data
          this.newsVisible = !this.newsVisible


        })
        .catch((err) => {
          console.log(err)
        })

    },


      replaceDefault: function (e) {
      e.target.src = img
    },
    wordcloud(number)
    {
          for (let index = 0; index < this.codeGroup.length; index++) {
                if(this.codeGroup[index].number===number)
                {
                  this.codeGroup[index].flag=1;
                }
                else{
                  this.codeGroup[index].flag=0;
                }

          }



        console.log(number)
        axios({
        method: 'get',
        url: `${SERVER_HOST}/news/main/wordcloud`,
        params: {
          codeGroup: number
        }
      })
        .then((res) => {
          console.log(res)
          const data = res.data.data
          let flag = false

            if(this.words[0].text==='글자1')
            {
              flag=true
            }

          for (let index = 0; index < 20; index++) {



            this.words[index].text = data[index].keyword
          }

          if(flag)
          {
  axios({
        method: 'get',
        url: `${SERVER_HOST}/news/keyword`,
         params: {
            keyword : this.words[0].text,
            page : 1,
            size : 3
          }
      })
        .then((res) => {
          console.log(res)
         this.newsData = res.data.data

        })
        .catch((err) => {
          console.log(err)
        })
          }


          console.log(this.words)
        this.genLayout()

        })
        .catch((err) => {
          console.log(err)
        })
    },
    check (d,d3) {

          this.newsData = null;

      console.log(d)
      d3.selectAll("text").style("fill-opacity", 0.5)
      d3.select("." + d.text).style("fill-opacity", 1)
      console.log(d.text)

         axios({
        method: 'get',
        url: `${SERVER_HOST}/news/keyword`,
         params: {
            keyword : d.text,
            page : 1,
            size : 3
          }
      })
        .then((res) => {
          console.log(res)
         this.newsData = res.data.data

        })
        .catch((err) => {
          console.log(err)
        })




    },

    genLayout () {
      const cloud = require('d3-cloud')
      cloud()
        .words(this.words)
        .padding(2)
        .rotate(0)
        .font('serif')
        .fontSize(function (d) {
          return d.size
        })
        .on('end', this.end)
        .spiral("archimedean")
        .start()
        // .stop();
    },
    end(words) {
      console.log(words)
      const d3 = require("d3");
      const width = 500;
      const height = 400;
      const text ='';
     d3.select("#word-cloud")
     .html('')
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
      .attr("class",(d)=>{return d.text})
      .style("font-size", (d)=>{return d.size +"px";})
      .style("cursor","pointer")
      .style("fill",(d)=>{return d.color;}) //색지정
      .style("fill-opacity", 1) //투명도 조절
      .style("font-family", "Impact")
      .style("font-weight","bold")
      .attr("text-anchor", "middle")
      .attr("transform", (d)=>{return "translate(" + [d.x, d.y] +") rotate (" + d.rotate +")";})
      .text((d)=>d.text)
      .on('click',(ev,d) =>  {
            this.check(d,d3)
      })
      .on('mouseover', function() {
          d3.select(this).style("font-size", (d)=>{return d.size+3 +"px";})

      })
       .on('mouseout', function() {
          d3.select(this).style("font-size", (d)=>{return d.size-3 +"px";})

      })
    },

    handleMouseOver(d) {

        d.style("fill","blue")


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


.main-news-list{
cursor:pointer;

}

.spinner-div {
line-height: 470px;
text-align: center;

}


.main-btn-check {
border: none;
 border-bottom : 3px solid blue;

 margin-right : 10px;
 background-Color:white;


}

.main-btn-uncheck {

  border:none;
  margin-right : 10px;
  background-Color:white;

}

.main-card-img {
  height:156px;
  object-fit: cover ;
  margin:0px;
}

.main-card {
 display:-webkit-box;
  word-wrap:break-word;
  -webkit-line-clamp:3;
  -webkit-box-orient:vertical;
  overflow:hidden;
  text-overflow:ellipsis;
    height:54px
}


#word-cloud {
  /* border  : 1px solid blue; */
   -webkit-border-radius: 10px;
  -moz-border-radius: 10px;
   border-radius: 10px;
  -webkit-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.1);
  -moz-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.1);
  box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.1)

}

</style>


