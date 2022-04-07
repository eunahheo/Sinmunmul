<template>
  <div class="register" style="margin-left: 19%">
    <div class="m-5 align-item-center;" style="text-align: center; width: 70%">
      <h1 class="m-3">회원가입</h1>

      <form>
        <div class="input-group mb-3" style="padding: 0% 5% 0% 5%">
          <input
            type="text"
            class="form-control"
            v-model="emailData"
            style="width: 50%"
            placeholder="아이디를 입력하세요(이메일)"
          />
          <input
            type="button"
            class="form-control"
            v-on:click="emailVerify"
            value="인증"
          />
          <input
            type="text"
            class="form-control"
            v-model="emailCheckCodeData"
            name="emailCheckCode"
            style="width: 20%"
            placeholder="인증번호"
          />
          <input
            type="button"
            class="form-control"
            v-on:click="verifyCodeCheck"
            v-if="!isEmailChecked"
            value="확인"
          />
        </div>
        <div class="input-group mb-3" style="padding: 1% 5% 0% 5%">
          <input
            type="password"
            class="form-control"
            v-model="passwdData"
            placeholder="비밀번호를 입력하세요"
          />
        </div>
        <div class="input-group mb-3" style="padding: 0% 5% 0% 5%">
          <input
            type="password"
            class="form-control"
            v-model="passwdDataCheck"
            placeholder="비밀번호를 다시 입력하세요"
          />
        </div>
        <div>
          <span v-if="(passwdData == '') | (passwdDataCheck == '')"></span>
          <span
            v-else-if="passwdData == passwdDataCheck"
            v-on:change="console.log('aasd')"
            >패스워드가 일치합니다.</span
          >
          <span v-else>패스워드가 일치하지 않습니다.</span>
        </div>
        <div class="btn-group px-5" role="group" style="width: 100%">
          <span class="h5" style="margin: 0% 5% 0% 5%">성별</span>
          <input
            type="radio"
            class="btn-check"
            v-model="genderValue"
            name="btnradio"
            id="btnradio1"
            autocomplete="off"
            value="male"
            checked
          />
          <label class="btn btn-outline-primary" for="btnradio1">남자</label>

          <input
            type="radio"
            class="btn-check"
            v-model="genderValue"
            name="btnradio"
            id="btnradio2"
            autocomplete="off"
            value="female"
          />
          <label class="btn btn-outline-primary" for="btnradio2">여자</label>

          <span class="h5" style="margin: 0% 5% 0% 5%">출생연도</span>
          <input
            type="text"
            class="form-control"
            v-model="birthDate"
            name="birthDate"
            style="width: 30%"
            placeholder="출생연도"
          />
        </div>
        <div class="m-3" style="border: 1px solid black; border-radius: 20px">
          <h2 class="m-3">관심분야</h2>

          <h3 class="m-3">정치</h3>
          <div class="btn-group" role="group">
            <input
              type="checkbox"
              class="btn-check"
              v-model="this.politicsList"
              id="pol1"
              value="264"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="pol1">청와대</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.politicsList"
              id="pol2"
              value="265"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="pol2">국회/정당</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.politicsList"
              id="pol3"
              value="268"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="pol3">북한</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.politicsList"
              id="pol4"
              value="266"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="pol4">행정</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.politicsList"
              id="pol5"
              value="267"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="pol5">국방/외교</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.politicsList"
              id="pol6"
              value="269"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="pol6">정치일반</label>
          </div>

          <h3 class="m-3">경제</h3>
          <div class="btn-group" role="group">
            <input
              type="checkbox"
              class="btn-check"
              v-model="this.economyList"
              id="eco1"
              value="259"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="eco1">금융</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.economyList"
              id="eco2"
              value="258"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="eco2">증권</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.economyList"
              id="eco3"
              value="261"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="eco3">산업/재계</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.economyList"
              id="eco4"
              value="771"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="eco4">중기/벤쳐</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.economyList"
              id="eco5"
              value="260"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="eco5">부동산</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.economyList"
              id="eco6"
              value="262"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="eco6"
              >글로벌 경제</label
            >

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.economyList"
              id="eco7"
              value="310"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="eco7">생활경제</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.economyList"
              id="eco8"
              value="263"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="eco8">경제 일반</label>
          </div>

          <h3 class="m-3">사회</h3>
          <div class="btn-group" role="group">
            <input
              type="checkbox"
              class="btn-check"
              v-model="this.socialList"
              id="social1"
              value="249"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="social1"
              >사건사고</label
            >

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.socialList"
              id="social2"
              value="250"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="social2">교육</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.socialList"
              id="social3"
              value="251"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="social3">노동</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.socialList"
              id="social4"
              value="254"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="social4">언론</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.socialList"
              id="social5"
              value="252"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="social5">환경</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.socialList"
              id="social6"
              value="59"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="social6"
              >인권/복지</label
            >

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.socialList"
              id="social7"
              value="255"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="social7"
              >식품/의료</label
            >

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.socialList"
              id="social8"
              value="256"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="social8">지역</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.socialList"
              id="social9"
              value="276"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="social9">인물</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.socialList"
              id="social10"
              value="257"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="social10"
              >사회 일반</label
            >
          </div>

          <h3 class="m-3">생활/문화</h3>
          <div class="btn-group" role="group">
            <input
              type="checkbox"
              class="btn-check"
              v-model="this.livingCultureList"
              id="living1"
              value="241"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="living1"
              >건강정보</label
            >

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.livingCultureList"
              id="living2"
              value="239"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="living2"
              >자동차/시승기</label
            >

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.livingCultureList"
              id="living3"
              value="240"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="living3"
              >도로/교통</label
            >

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.livingCultureList"
              id="living4"
              value="237"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="living4"
              >여행/레저</label
            >

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.livingCultureList"
              id="living5"
              value="238"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="living5"
              >음식/맛집</label
            >

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.livingCultureList"
              id="living6"
              value="376"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="living6"
              >패션/뷰티</label
            >

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.livingCultureList"
              id="living7"
              value="242"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="living7"
              >공연/전시</label
            >

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.livingCultureList"
              id="living8"
              value="243"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="living8">책</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.livingCultureList"
              id="living9"
              value="244"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="living9">종교</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.livingCultureList"
              id="living10"
              value="248"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="living10">날씨</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.livingCultureList"
              id="living11"
              value="245"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="living11"
              >생활문화 일반</label
            >
          </div>

          <h3 class="m-3">IT/과학</h3>
          <div class="btn-group" role="group">
            <input
              type="checkbox"
              class="btn-check"
              v-model="this.itScienceList"
              id="it1"
              value="731"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="it1">모바일</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.itScienceList"
              id="it2"
              value="226"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="it2">인터넷/SNS</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.itScienceList"
              id="it3"
              value="227"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="it3"
              >통신/뉴미디어</label
            >

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.itScienceList"
              id="it4"
              value="230"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="it4">IT일반</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.itScienceList"
              id="it5"
              value="732"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="it5">보안/해킹</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.itScienceList"
              id="it6"
              value="283"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="it6">컴퓨터</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.itScienceList"
              id="it7"
              value="229"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="it7">게임/리뷰</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.itScienceList"
              id="it8"
              value="228"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="it8">과학 일반</label>
          </div>

          <h3 class="m-3">세계</h3>
          <div class="btn-group" role="group">
            <input
              type="checkbox"
              class="btn-check"
              v-model="this.worldList"
              id="world1"
              value="231"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="world1"
              >아시아/호주</label
            >

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.worldList"
              id="world2"
              value="232"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="world2"
              >미국/중남미</label
            >

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.worldList"
              id="world3"
              value="233"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="world3">유럽</label>

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.worldList"
              id="world4"
              value="234"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="world4"
              >중동/아프리카카</label
            >

            <input
              type="checkbox"
              class="btn-check"
              v-model="this.worldList"
              id="world5"
              value="322"
              autocomplete="off"
            />
            <label class="btn btn-outline-primary" for="world5">세계일반</label>
          </div>
        </div>
        <div class="m-3">
          <button
            type="button"
            class="btn btn-primary m-2"
            v-on:click="registerBtn"
          >
            회원가입
          </button>
          <button type="button" class="btn btn-light m-2" to="/">취소</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
import axios from "axios";
const API_SERVER = "https://j6a406.p.ssafy.io/api";

export default {
  name: "register",
  conponent: {},
  data() {
    return {
      emailData: this.$route.params.email ? this.$route.params.email : "",
      emailCheckCode: "",
      emailCheckCodeData: "",
      isEmailChecked: false,
      genderValue: this.$route.params.gender
        ? this.$route.params.gender
        : "male",
      passwdData: "",
      passwdDataCheck: "",
      isPasswdChecked: false,
      birthDate: "",
      usersgType: this.$route.params.usersgType
        ? this.$route.params.usersgType
        : "email",
      politicsList: [],
      economyList: [],
      socialList: [],
      livingCultureList: [],
      itScienceList: [],
      worldList: [],
      authToken: localStorage.getItem("authToken") || null,
    };
  },
  mounted() {
    if (this.authToken) {
      this.$router.push("/");
    }
  },
  methods: {
    emailVerify() {
      axios
        .post(`${API_SERVER}/user/info`, {
          params: {
            userEmail: this.emailData,
            usersgType: this.usersgType,
          },
        })
        .then((res) => {
          console.log(res);
          if (res.status === 200) {
            alert("이미 가입된 이메일입니다.");
          } else if (res.status === 202) {
            axios
              .get(`${API_SERVER}/user/cert`, {
                params: {
                  email: this.emailData,
                },
              })
              .then((res) => {
                console.log(res.data.data);
                this.emailCheckCode = res.data.data;
              });
            alert("이메일로 인증번호를 보냈습니다.");
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },

    verifyCodeCheck() {
      if (this.emailCheckCode == this.emailCheckCodeData) {
        this.isEmailChecked = true;
        alert("인증되었습니다.");
      } else {
        alert("인증번호가 일치하지 않습니다.");
      }
    },

    registerBtn() {
      if (this.isEmailChecked == true) {
        const curDate = new Date();
        axios
          .post(`${API_SERVER}/user/signin`, {
            userEmail: this.emailData,
            userGender: this.genderValue,
            userPwd: this.passwdData,
            userAge: (curDate.getFullYear() - this.birthDate) * 1,
            usersgType: this.usersgType,
          })
          .then((res) => {
            console.log(res);
            if (res.status === 200) {
              alert("회원가입 성공");
              axios
                .post(`${API_SERVER}/user/login`, {
                  userEmail: this.emailData,
                  userPwd: this.passwdData,
                })
                .then((res) => {
                  console.log(res);
                  if (res.status === 200) {
                    alert("로그인 성공");
                    localStorage.setItem(
                      "authToken",
                      res.data.data.accessToken
                    );
                    localStorage.setItem("userSeq", res.data.data.userSeq);
                    this.$router.go();
                  } else {
                    alert("로그인 실패");
                  }
                });
            } else {
              alert(res.status);
            }
          })
          .catch((e) => {
            console.log(e);
          });
      } else {
        alert("이메일 인증을 진행해주세요.");
      }
    },
  },
};
</script>
