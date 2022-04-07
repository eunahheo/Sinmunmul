<template>
<div class="container">

<div class="row">

 <div class="col-12" id="interest-form">
   <div  v-for="items in state" :key="items">

  <div style="text-align:left"  class="mt-3"><h2>{{items.name}}</h2></div>

<div class="form-check mb-3" style="text-align:left" >
   <span  v-for="item in items.data" :key="item.code">
    <input type="checkbox" class="form-check-input"  v-bind:id="item.code"  v-bind:name="item.code" value="something" v-bind:checked="item.state" @click="change(item)">
      <label class=" m-1 btn btn-primary" v-bind:for="item.code" >{{item.name}}</label>
     </span>

    </div>
</div>
<div class="mt-5 mb-3" style="text-align:center" >
<button @click="update" class="btn btn-primary" style="width:250px; height:50px" >변경</button>
</div>
    </div>

</div>
<interest-modal v-bind:visible="visible" @close='visible=init()'></interest-modal>
</div>
</template>

<script>
import axios from 'axios'
import InterestModal from './InterestModal.vue'
// const SERVER_HOST = process.env.VUE_APP_SERVER_HOST
const SERVER_HOST = 'https://j6a406.p.ssafy.io/api'
export default {
  name: 'MyInterest',
  data: function () {
    return {
      visible: false,
      hashMap: new Map(),
      state: [
        { name: '정치', data: [] }, // 정치
        { name: '경제', data: [] }, // 경제
        { name: '사회', data: [] }, // 사회
        { name: '생활/문화', data: [] }, // 생활/문화
        { name: '세계', data: [] }, // 세계
        { name: 'IT/과학', data: [] } // IT/과학
      ],
      updateData: [],
      politics: [],
      economy: [],
      society: [],
      culture: [],
      international: [],
      it: [],
      noInterest: [],
      yesInterest: [],
      category: [
        // 대분류
        { code: 100, name: 0 },
        { code: 101, name: 1 },
        { code: 102, name: 2 },
        { code: 103, name: 3 },
        { code: 104, name: 4 },
        { code: 105, name: 5 },

        // 소분류
        { code: 264, name: '청와대' },
        { code: 265, name: '국회/정당' },
        { code: 268, name: '북한' },
        { code: 266, name: '행정' },
        { code: 267, name: '국방/외교' },
        { code: 269, name: '정치일반' },
        { code: 259, name: '금융' },
        { code: 258, name: '증권' },
        { code: 261, name: '산업/재계' },
        { code: 771, name: '중기/벤쳐' },
        { code: 260, name: '부동산' },
        { code: 262, name: '글로벌 경제' },
        { code: 310, name: '생활경제' },
        { code: 263, name: '경제 일반' },
        { code: 249, name: '사건사고' },
        { code: 250, name: '교육' },
        { code: 251, name: '노동' },
        { code: 254, name: '언론' },
        { code: 252, name: '환경' },
        { code: 59, name: '인권/복지' },
        { code: 255, name: '식품/식료' },
        { code: 256, name: '지역' },
        { code: 276, name: '인물' },
        { code: 257, name: '사회/일반' },
        { code: 241, name: '건강정보' },
        { code: 239, name: '자동차/시승기' },
        { code: 240, name: '도로교통' },
        { code: 237, name: '여행/레저' },
        { code: 238, name: '음식/맛집' },
        { code: 376, name: '패션/뷰티' },
        { code: 242, name: '공연/전시' },
        { code: 243, name: '책' },
        { code: 244, name: '종교' },
        { code: 248, name: '날씨' },
        { code: 245, name: '생활문화 일반' },
        { code: 731, name: '모바일' },
        { code: 226, name: '인터넷/SNS' },
        { code: 227, name: '통신/뉴미디어' },
        { code: 230, name: 'IT일반' },
        { code: 732, name: '보안/해킹' },
        { code: 283, name: '컴퓨터' },
        { code: 229, name: '게임/리뷰' },
        { code: 228, name: '과학 일반' },
        { code: 231, name: '아시아/호주' },
        { code: 232, name: '미국/중남미' },
        { code: 233, name: '유럽' },
        { code: 234, name: '중동/아프리카' },
        { code: 322, name: '세계일반' }
      ]
    }
  },
  components: {
    InterestModal
  },
  created () {
    axios({
      method: 'get',
      // /mypage/{user_seq}/updatePassword
      url: `${SERVER_HOST}/mypage/7/searchInterest`
    }).then((res) => {
      this.yesInterest = res.data.data.yesInterest
      this.noInterest = res.data.data.noInterest
      console.log(res)
      // 해시맵에 카테고리 번호와 이름 설정
      for (let index = 0; index < this.category.length; index++) {
        this.hashMap.set(this.category[index].code, this.category[index].name)
      }
      for (let index = 0; index < this.yesInterest.length; index++) {
        const value = this.yesInterest[index]
        console.log(this.hashMap.get(value.code))
        this.state[this.hashMap.get(value.codeGroup)].data.push({ codeGroup: value.codeGroup, code: value.code, name: this.hashMap.get(value.code), state: true })
      }
      for (let index = 0; index < this.noInterest.length; index++) {
        const value = this.noInterest[index]
        this.state[this.hashMap.get(value.codeGroup)].data.push({ codeGroup: value.codeGroup, code: value.code, name: this.hashMap.get(value.code), state: false })
      }
      this.numberSort()
      console.log(this.state[0].data)
      console.log(this.state[1].data)
      console.log(this.state[2].data)
      console.log(this.state[3].data)
      console.log(this.state[4].data)
      console.log(this.state[5].data)
    }).catch((err) => {
      console.log(err)
    })
  },
  methods: {
    update: function () {
      for (let i = 0; i < this.state.length; i++) {
        for (let j = 0; j < this.state[i].data.length; j++) {
          const val = this.state[i].data[j]
          if (val.state) {
            this.updateData.push({ codeGroup: val.codeGroup, code: val.code })
          }
        }
      }
      axios({
        method: 'put',
        // /mypage/{user_seq}/updatePassword
        url: `${SERVER_HOST}/mypage/7/updateInterest`,
        data: this.updateData
      })
        .then((res) => {
          console.log(res)
          this.modalView()
        })
        .catch(err => console.log(err)
        )
    },
    change: function (item) {
      if (item.state) {
        item.state = false
      } else {
        item.state = true
      }
    },
    numberSort: function () {
      for (let num = 0; num <= 5; num++) {
        this.state[num].data.sort(function (a, b) {
          return a.code - b.code
        })
      }
    },
    modalView: function () {
      this.visible = !this.visible
    },
    init: function () {
      this.visible = false
      this.updateData.length = 0
    }
  }
}
</script>

<style scoped>
#interest-form {
  margin-bottom: 50px;
  padding: 20px;
  margin-top: 50px;
   -webkit-border-radius: 10px;
  -moz-border-radius: 10px;
   border-radius: 10px;
  -webkit-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
  -moz-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
  box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15)
}

.form-check input{
    display: none;
}
.form-check label {
   color: black;
  background-color: white;
   border-radius: 1rem;
   cursor: pointer;
}
.form-check input:checked + label {
  color: white;
  background-color: #0d6efd;
}

</style>
