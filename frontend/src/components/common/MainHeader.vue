<template>
<nav class="py-2">
<div class="px-3 py-0 mb-0">
      <div class="container d-flex flex-wrap justify-content-center">
        <form class="col-12 col-lg-auto mb-2 mb-lg-0 me-lg-auto">
          <input type="search" class="form-control" placeholder="Search..." aria-label="Search">
        </form>

      <router-link to="/temp" class="navbar-brand col-12 col-lg-auto mb-2 mb-lg-0 me-lg-auto">
      <img src="@/assets/img/titleLogo.png" alt="" width="120" height="60">
      </router-link>


        <div class="text-end">
           <button type="button" class="btn btn-outline-secondary m-2">로그인</button>
          <button type="button" class="btn btn-outline-secondary m-2">회원가입</button>
        </div>
      </div>
    </div>
</nav>
 <aside class="d-flex flex-wrap align-items-center justify-content-center py-3 mb-3 " >
      <ul class="nav col-10 col-md-auto mb-2 justify-content-center mb-md-0">

         <li class="nav-item dropdown">
          <a class="nav-link link-dark" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false">카테고리</a>
          <ul class="dropdown-menu">
        <li><a class="dropdown-item" href="#">상세 카테고리 1</a></li>
        <li><a class="dropdown-item" href="#">상세 카테고리 2</a></li>
        <li><a class="dropdown-item" href="#">상세 카테고리 3</a></li>
          </ul>
         </li>

        <li><a href="#" class="nav-link px-20 link-dark">오피니언</a>
        </li>
        <li><a href="#" class="nav-link px-20 link-dark">정치</a>
        </li>
        <li><a href="#" class="nav-link px-20 link-dark">경제</a>
        </li>
        <li><a href="#" class="nav-link px-20 link-dark">국제</a>
        </li>
        <li><a href="#" class="nav-link px-20 link-dark">사회</a>
        </li>
        <li><a href="#" class="nav-link px-20 link-dark">문화</a>
        </li>
        <li><a href="#" class="nav-link px-20 link-dark">연예</a>
        </li>
        <li><a href="#" class="nav-link px-20 link-dark">스포츠</a>
        </li>
        <li><a href="#" class="nav-link px-20 link-dark">매거진</a>
        </li>
      </ul>

 </aside>

</template>

<style scoped>

aside {
  text-align: center;
  border-bottom: solid;
  border-color: black;
  border-width: 2px 0;
  text-transform: uppercase;
  letter-spacing: 2px;
}
</style>
