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