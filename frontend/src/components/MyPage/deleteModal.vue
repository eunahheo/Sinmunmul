<template>
<div class="modal__background"  v-if="visible">
 <div class="modal" id="myModal"  style="display:block">
  <div class="modal-dialog  modal-dialog-centered">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">알림창</h4>
        <button type="button" class="btn-close"  @click="$emit('close')" data-bs-dismiss="modal" ></button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">
        삭제 완료
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-danger"   @click="$emit('close')"  data-bs-dismiss="modal">닫기</button>
      </div>

    </div>
  </div>
</div>
</div>
</template>

<script>
export default {
  name: 'deleteModal',
  props: ['visible']
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
