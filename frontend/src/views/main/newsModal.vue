<template>
<div class="modal__background"  v-if="visible">
 <div class="modal" id="myModal"  style="display:block">
  <div class="modal-dialog modal-lg  modal-dialog-centered" >
    <div class="modal-content p-3">
      <!-- Modal Header -->
      <div class="row" style="height:60px">
        <div class="col-11"></div>
        <div class="col-1 mt-2" ><button type="button" class="btn-close mt-2,mr-2"  @click="$emit('close')" data-bs-dismiss="modal"  ></button></div>
         </div>

      <div  class="row" style="padding-top: 0px;">
        <div class="col-1" > </div>

      <div class="col-10">
           <div  style="text-align: left;"><h3> {{news.newsPress}} </h3></div>
           <div ><h4 class="modal-title" style="text-align: left; " >{{news.newsTitle}}</h4></div>

          <div class="row mt-3" style="height:22px ">
        <div class=" col-4" style="text-align: left; padding-left:10px"
        ><button type="button"  class="btn m-1"  style="border:1px solid; width:75px;  font-size:12px; " @click="redirect(news.newsLink)">기사원문</button>
        <button type="button"  class="btn "  style="border:1px solid; width:75px;  font-size:12px; " @click="scrap(news.newsSeq)"  v-if="this.$store.userSeq != null" >스크랩</button>
        </div>
        <div class="col-8" style="text-align: right; padding-right:10px; padding-top:8px"> {{this.date}}  | {{news.newsAuthor}} 기자</div>
        </div>
         <hr>
         <div><img v-bind:src="news.newsPhoto" class="image mt-4 mb-4" style="width:100%;"></div>
         <div class="modal-body" v-html="desc"  style="text-align: left;  font-size:20px;"></div>
        <div class="modal-footer mb-4"><button type="button" class="btn btn-danger"   @click="$emit('close')"  data-bs-dismiss="modal">닫기</button></div>
      </div>

        <div class="col-1"> </div>

      </div>

      <!-- Modal body -->

      <!-- Modal footer -->
    </div>
  </div>
</div>
</div>
</template>

<script>
import axios from 'axios'
const API_SERVER = 'https://j6a406.p.ssafy.io/api'
export default {
  name: 'newsModal',
  props: ['visible', 'news'],

  data: function () {
    return {
      desc: '',
      date: ''
    }
  },
  watch: {
    news () {
      this.date = this.news.newsRegDt
      const text = this.news.newsDesc
      const tmp = this.date.split(' ')
      this.date = tmp[0]

      this.desc = text
      // console.log(this.desc)

      this.desc = this.desc.replace(/^\s*/, '')
      this.desc = this.desc.replace(/\t/g, '')
      this.desc = this.desc.replace(/\n$/gm, '<br/>')
      console.log('해당문자를 포함하고 있나요? ' + this.desc.indexOf(('\n')))
      if (this.desc.indexOf(('\n')) === -1) {
        this.desc = this.desc.replace(/(?:\.\s)/g, '. <br /> <br />')
      } else {
        this.desc = this.desc.replace(/(?:\.\r\n|\.\r|\.\n|\.\s*\n)/g, '. <br/> <br/>')
        this.desc = this.desc.replace(/(?:\n)/g, '<br/>')
      }
      // this.desc = this.desc.replace(/(?:\r\n|\r|\n|\s\n)/g, '<br />')
    }
  },
  methods: {
    scrap: function (newsSeq) {
      axios({
        method: 'post',
        // /mypage/{user_seq}/updatePassword
        url: `${API_SERVER}/news/${newsSeq}/scrap`,
        params: {
          user_Seq: this.$store.userSeq
        }
      })
        .then((res) => {
          console.log(res)
          this.modalView()
        })
        .catch(err => console.log(err)
        )
    },
    redirect: function (data) { // 기사원문 버튼 클릭
      // window.location.href = data // 바로 이동
      window.open(data) // 새 창 열기
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

.modal-dialog {
width: 100%;
max-width:1000px;
padding: 30px;
margin:0 auto;
}

.row>* {
  padding-right: 1px;
    padding-left: 1px;
}

.modal-body {
 /* white-space: pre-line; */
    padding: 0px;
}
.modal__background {
  position: fixed;
  top:0; left: 0; bottom: 0; right: 0;
  background: rgba(0, 0, 0, 0.8);
  z-index:6;
}

</style>
