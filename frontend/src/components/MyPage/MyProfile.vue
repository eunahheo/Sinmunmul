<template>

<div>
<ul class="nav p-3 " id="mypageheader" >

  <li class="nav-item">
   <router-link :to="{ name: 'MyInterest'}" class="nav-link " > <h5>관심사 수정</h5> </router-link>

  </li>
  <li class="nav-item">
    <router-link :to="{name: 'MyInfo'}"  class="nav-link" > <h5> 비밀번호 변경 </h5> </router-link>
  </li>
</ul>
</div>
<div>
  <hr>
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
  height: 45px;
}

</style>
