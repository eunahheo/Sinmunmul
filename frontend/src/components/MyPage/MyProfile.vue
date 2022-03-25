<template>

<div>
<ul class="nav nav-tabs" id="mypageheader" >

  <li class="nav-item">
   <router-link :to="{ name: 'MyInterest'}" class="nav-link " > <h3>관심사 수정</h3> </router-link>

  </li>
  <li class="nav-item">
    <router-link :to="{name: 'MyInfo'}"  class="nav-link" > 비밀번호 변경 </router-link>
  </li>
</ul>
</div>
<div>
<router-view></router-view>
</div>
</template>

<script>
export default {
  name: 'MyProfile'
}
</script>

<style>

 #mypageheader{
  height: 60px;
}

</style>
