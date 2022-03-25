<template>
  <section class="container">
    <div class="columns">
      <div class="column">
        <h3>Bar Chart test</h3>
        <bar-chart :data="chartData"></bar-chart>
      </div>
    </div>
  </section>
</template>

<script>
  export default {
    name: 'TempChart',
    data () {
      return {
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
        ]
      }
    }
  }
</script>
