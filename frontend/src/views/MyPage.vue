<template>
 <div id="mypage" class="container-fluid">
   <div class="row">
      <div class="col-2"><my-page-sidebar></my-page-sidebar></div>
      <div class="col-10"><router-view/></div>
   </div>
 </div>

</template>

<script>
import MyPageSidebar from '@/components/MyPage/MyPageSidebar.vue'

export default {
  name: 'MyPage',
  components: { MyPageSidebar }

}
</script>

<style>
.row {height: 100%;}
#app {height: 100%;}
html,body {height: 100%; overflow-y: hidden}
#mypage {
  height: 100%;
}
a { text-decoration: none;
}
a:link {
  color : black;
}
a:visited {
  color : black;
}
a:hover {
  color : black;
}
a:active {
  color : black
}
</style>
