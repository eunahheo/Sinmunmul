<template>
  <div id="word-cloud">
    <h2> 워드 클라우드 테스트</h2>

  </div>
</template>

<script>

export default {
  data() {
    return {
      words: [
        {text : "AAA", size : 20},
        {text : "BBB", size : 20},
        {text : "CCC", size : 20},
        {text : "DDD", size : 20},
        {text : "EEE", size : 20},
        {text : "fff", size : 20},
        {text : "GGG", size : 20},
        {text : "hHHH", size : 70},
        {text : "iiiii", size : 80},
        {text : "JJJ", size : 80},
        {text : "KK", size : 22},
        {text : "LL", size : 40},
        {text : "MMMMM", size : 50},
        


      ],
    };
  },
  mounted() {
    this.genLayout();
  },
  methods : {
    genLayout() {
      const cloud = require("d3-cloud");
      cloud()
      .words(this.words)
      .padding(1)
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
      const width = 500;
      const height = 250;
      
      d3.select("#word-cloud")
      .append("svg")
      .attr("width", width)
      .attr("height", height)
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
  },
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
fontSize([fontSize]): font 크기를 결정한다.
rotate([rotate]): 각 단어의 회전각을 결정. 
text([text]): words에서 넣은 값에서 표시할 것을 명시한다. ex {text: 'word', value: 30 }
spiral([spiral]): 단어를 위치시킬 떄 사용하는 나선형의 형태를 결정할 수 있는데 archimedean(원형) 또는 rectangular를 선택할 수 있으며 함수로 임의로 만들 수도 있다. default로 내장된 archimedean을 사용한다.
padding([padding]): 각 단어의 padding을 결정. default는 1.

random([random]): 내부적으로 단어를 초기 위치와 시계, 반시계 방향 각도를 결정할 때 사용하는 랜덤 숫자 생성기. 0 이상 1 미만 숫자가 들어가야하며 default는 Math.random

canvas([canvas]): 캔버스 생성기.