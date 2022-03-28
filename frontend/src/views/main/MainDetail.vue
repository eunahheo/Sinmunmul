<template>
<div class="container">

<div>
  <ul class="nav nav-tabs" >

  <li class="nav-item container row" style="float: none; margin:100 auto;">
   <h3 class="">검색 결과</h3>
    
  </li>
 
</ul>


</div>

</div>
</template>
<script>

export default {
  data() {
    return {
      searchResult : Array
    }
  },
  created() {
      console.log("라우터 데이터 확인")
      console.log(this.$route.query.result );
  }
}

</script>