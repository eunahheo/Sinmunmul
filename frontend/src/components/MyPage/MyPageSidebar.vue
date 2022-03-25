<template>

    <!-- <div id="sidebar">
    <div id="mypageheader"><h2>마이페이지</h2></div>
    <hr>
 <div> <router-link :to="{ name: 'MyProfile'}">프로필 수정</router-link> </div>
 <div> <router-link :to="{ name: 'ScrapArticle'}">스크랩한 기사 </router-link> </div>
  </div> -->

<div class="d-flex flex-column flex-shrink-0 p-3 bg-white" id="sidebar">
      <div id="mypageheader"><h3>마이페이지</h3></div>
      <hr>
    <ul class="nav nav-pills flex-column mb-auto">
      <li class="nav-item">
        <router-link :to="{ name: 'MyProfile'}" class="nav-link" >프로필 수정</router-link>
      </li>
      <li>
        <router-link :to="{ name: 'ScrapNews'}"  class="nav-link">스크랩한 기사 </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'MyPageSidebar'
}
</script>

<style scoped>

#mypageheader{
  height: 30px;
}

a { text-decoration: none;
}
#sidebar {
  -webkit-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
  -moz-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
  box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
  width: 100%;
  height: 100%;

  }

</style>
