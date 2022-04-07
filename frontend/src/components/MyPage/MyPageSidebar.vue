<template>

<div class="d-flex flex-column flex-shrink-0 p-3 bg-white" id="sidebar">
      <div id="mypageheader"><h3 style="font-weight: bold">마이페이지</h3></div>
      <hr>
    <ul class="nav nav-pills flex-column mb-auto">
      <li class="nav-item mt-1 mb-3">
        <router-link :to="{ name: 'MyProfile'}" :class="{
                    'nav-link-check':   click[0].flag === true,
                    'nav-link-uncheck': click[0].flag === false,
                  }"  @click="check(0)">프로필 수정</router-link>
      </li>
      <li>
        <router-link :to="{ name: 'ScrapNews'}"  :class="{
                    'nav-link-check':   click[1].flag === true,
                    'nav-link-uncheck': click[1].flag === false,
                  }"  @click="check(1)">스크랩 기사 </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'MyPageSidebar',
  data: function () {
    return {
      click: [
        { flag: true },
        { flag: false }
      ]
    }
  },
  methods: {
    check: function (data) {
      for (let index = 0; index < 2; index++) {
        if (data === index) {
          this.click[index].flag = true
        } else {
          this.click[index].flag = false
        }
      }
    }
  }
}
</script>

<style scoped>

#mypageheader{
  height: 30px;
}

a { text-decoration: none;
}
#sidebar {
  border-radius: 8px;
  -webkit-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
  -moz-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
  box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
  width: 100%;
  height: 100%;

  }

  .nav-link-check{
       color : blue;
  }

.nav-link-uncheck{
   color :black;
  }
</style>
