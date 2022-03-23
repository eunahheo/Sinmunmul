<template>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid text-left">
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <form class="d-flex">
        <input class="form-control me-3" type="search" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-success" type="submit">search</button>
      </form>

    </div>

    <div class="container-fluid text-center">
    <a class="navbar-brand" href="#">
      <img src="@/assets/img/titleLogo.png" alt="" width="120" height="60" class="d-inline-block ">
    </a>
  </div>

  <div class="container-fluid col-md-3 text-end">
    <button type="button" class="btn btn-outline-secondary m-2">로그인</button>

     <button type="button" class="btn btn-outline-secondary m-2">회원가입</button>
  </div>
  </div>
</nav>

<nav>
<div class="px-3 py-2 border-bottom mb-3">
      <div class="container d-flex flex-wrap justify-content-center">
        <form class="col-12 col-lg-auto mb-2 mb-lg-0 me-lg-auto">
          <input type="search" class="form-control" placeholder="Search..." aria-label="Search">
        </form>


      <a class="navbar-brand col-12 col-lg-auto mb-2 mb-lg-0 me-lg-auto" href="#">
      <img src="@/assets/img/titleLogo.png" alt="" width="120" height="60">
    </a>


        <div class="text-end">
          <button type="button" class="btn btn-light text-dark me-2">Login</button>
          <button type="button" class="btn btn-primary">Sign-up</button>
        </div>
      </div>
    </div>
</nav>
 <header class="d-flex flex-wrap align-items-center justify-content-center py-3 mb-4 border-bottom" >
      <ul class="nav col-10 col-md-auto mb-2 justify-content-center mb-md-0">
         <li class="nav-item dropdown">
          <a class="nav-link link-dark" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false">카테고리</a>
          <ul class="dropdown-menu">
        <li><a class="dropdown-item" href="#">상세 카테고리 1</a></li>
        <li><a class="dropdown-item" href="#">상세 카테고리 2</a></li>
        <li><a class="dropdown-item" href="#">상세 카테고리 3</a></li>
      </ul>
      </li>

        <li><a href="#" class="nav-link px-20 link-dark">오피니언</a></li>
        <li><a href="#" class="nav-link px-20 link-dark">정치</a></li>
        <li><a href="#" class="nav-link px-20 link-dark">경제</a></li>
        <li><a href="#" class="nav-link px-20 link-dark">국제</a></li>
        <li><a href="#" class="nav-link px-20 link-dark">사회</a></li>
        <li><a href="#" class="nav-link px-20 link-dark">문화</a></li>
        <li><a href="#" class="nav-link px-20 link-dark">연예</a></li>
        <li><a href="#" class="nav-link px-20 link-dark">스포츠</a></li>
        <li><a href="#" class="nav-link px-20 link-dark">매거진</a></li>
      </ul>

    </header>

</template>

<style scoped>

</style>
