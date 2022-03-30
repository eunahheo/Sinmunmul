<template>
<div class="modal__background"  v-if="visible">
 <div class="modal" id="myModal"  style="display:block">
  <div class="modal-dialog modal-lg  modal-dialog-centered">
    <div class="modal-content p-3">
       <div class="row">
          <div class="col-1"></div>
          <div class="col-10">
            <div class="row">
        <div class="col-11"></div>
        <div class="col-1 mt-2" ><button type="button" class="btn-close mt-2,mr-2"  @click="$emit('close')" data-bs-dismiss="modal"  ></button></div>
         </div>

         <div class="row"  >
           <div class="col-3" style="text-align: left; padding-left: 30px;"><h3> {{news.newsPress}} </h3></div>
           <div class="col-11" > </div>
          </div>

      <div  style="padding-top: 0px;">
        <div ><h4 class="modal-title" style="text-align: left;  padding-left: 20px;" >{{news.newsTitle}}</h4></div>
        <div class="mt-3" style="text-align: left;">
           <button type="button"  class="btn"  style="border:1px solid; width:75px;  font-size:12px;" @click="redirect(news.newsLink)">기사원문</button>
      </div>

      <hr>
      </div>

      <div>
      <img v-bind:src="news.newsPhoto" class="image mt-4">
      </div>
      <!-- Modal body -->
      <div class="modal-body" v-html="desc"  style="text-align: left;  font-size:20px;">
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-danger"   @click="$emit('close')"  data-bs-dismiss="modal">닫기</button>
      </div>

    </div>
  </div>
</div>
</div>
            </div>
          <div class="col-1"></div>
       </div>
      <!-- Modal Header -->
</template>

<script>
export default {
  name: 'newsModal',
  props: ['visible', 'news'],

  data: function () {
    return {
      desc: ''
    }
  },
  watch: {
    news () {
      const text = this.news.newsDesc
      // this.desc = text.replace(/\./gi, '.<br/>')
      this.desc = text
      window.alert(this.news.newsDesc)
    }
  },
  methods: {
    redirect: function (data) {
      window.location.href = data // 바로 이동
      // window.open(data) // 새 창 열기
    }
  }
}
</script>
<style scoped>
#myModal {
  position: fixed;
top: 50%;
left: 50%;
-webkit-transform: translate(-50%, -50%);
-moz-transform: translate(-50%, -50%);
-ms-transform: translate(-50%, -50%);
-o-transform: translate(-50%, -50%);
transform: translate(-50%, -50%);

}
.modal__background {
  position: fixed;
  top:0; left: 0; bottom: 0; right: 0;
  background: rgba(0, 0, 0, 0.8);
  z-index:6;
}

</style>
