<template>
  <div class="container">

    <form id="password-form">
  <div class="mb-3">
    <input type="password" class="form-control password" id="exampleInputEmail1" placeholder="현재 비밀번호" v-model="password" >
  </div>
  <div class="mb-3">
    <input type="password" class="form-control password" id="exampleInputPassword1" placeholder="새 비밀번호" v-model="newpassword" >
  </div>
  <div class="mb-3">
    <input type="password" class="form-control password" id="exampleCheck1" placeholder="새 비밀번호 확인" v-model="newpasswordcon">
  </div>

     <div v-bind:class="alertState"  role="alert" v-bind:style="alertStyle">
    {{text}}
</div>

  <div class=" mt-5" style="width">
  <button type="button" @click="passwordUpdate"   class="btn btn-primary">변경</button>
  </div>

</form>

  </div>

</template>

<script>
import axios from 'axios'
// const SERVER_HOST = process.env.VUE_APP_SERVER_HOST
const SERVER_HOST = 'https://j6a406.p.ssafy.io/api'

export default {
  name: 'MyInfo',
  data: function () {
    return {
      flag: false,
      delay: 3000,
      password: '',
      newpassword: '',
      newpasswordcon: '',
      seq: this.$store.userSeq, // 유저의 기본키
      text: '비밀번호를 입력하세요', // alert창의 글자
      alertState: 'alert alert-danger',
      alertStyle: {
        visibility: 'hidden',
        width: '600px'
      }
    }
  },

  methods: {
    passwordUpdate: function () {
      const check = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/ // 정규표현식
      if (this.password === '') {
        this.alertChange('비밀번호를 입력하세요', 'visible', 'alert alert-danger')
      } else if (this.newpassword === '') {
        this.alertChange('새 비밀번호를 입력하세요', 'visible', 'alert alert-danger')
        // window.alert('새 비밀번호를 입력하세요')
      } else if (this.newpasswordcon === '') {
        this.alertChange('새 비밀번호 확인을 입력하세요', 'visible', 'alert alert-danger')
        // window.alert('새 비밀번호 확인을 입력하세요')
      } else {
        //  1. 현재 입력한 비밀번호와 db에 저장되어 있는 비밀번호가 일치하는지 확인하는 부분을 추가해야함
        if (!(check.test(this.newpassword))) {
          this.alertChange('8~16자의 영문 소문자와 숫자, 특수문자를 사용할 수 있습니다', 'visible', 'alert alert-danger')
          // window.alert('8~16자의 영문 소문자와 숫자, 특수문자를 사용할 수 있습니다')// 어떤 말을 출력할건지..?
          this.newpassword = ''
          this.newpasswordcon = ''
        } else if (!(this.newpassword === this.newpasswordcon)) {
          this.alertChange('새 비밀번호와 비밀번호 확인이 일치하지 않습니다', 'visible', 'alert alert-danger')
          // window.alert('새 비밀번호와 비밀번호 확인이 일치하지 않습니다')
          this.newpasswordcon = ''
        } else {
          // 모든 조건이 충족하면 새로운 비밀번호 변경을 서버에 요청한다  (토큰 추가?? )
          axios({
            method: 'put',
            // /mypage/{user_seq}/updatePassword
            url: `${SERVER_HOST}/mypage/${this.seq}/updatePassword`,
            data: {
              newUserPwd: this.newpassword,
              userPwd: this.password
            }

          }).then((res) => {
            this.alertChange('비밀번호 변경 완료', 'visible', 'alert alert-primary')
            this.password = ''
            this.newpassword = ''
            this.newpasswordcon = ''
            console.log(res)
          }).catch((err) => {
            this.alertChange('비밀번호가 올바르지 않습니다', 'visible', 'alert alert-danger')
            this.password = ''
            this.newpassword = ''
            this.newpasswordcon = ''
            console.log(err)
          })
        }
      }
    },
    alertChange: function (text, visibility, alertState) {
      this.text = text
      this.alertStyle.visibility = visibility
      this.alertState = alertState
      this.flag = true
      clearTimeout(this.delay) //  버튼이 다시 눌리면 기존 실행 중지 후 다시 3초 시작
      this.init()
    },
    init: function () {
      this.delay = setTimeout(() => {
        this.alertStyle.visibility = 'hidden'
      }, 3000)
    }
  }
}
</script>

<style scoped>

#password-form{
  margin-left: 260px;
  padding: 40px;
  margin-top:150px;
  max-width: 680px;
  height: 400px;
  -webkit-border-radius: 10px;
  -moz-border-radius: 10px;
   border-radius: 10px;
 -webkit-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
  -moz-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
  box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
}

.password {
  width: 600px;
}

.btn {
  width: 600px;
}

</style>
