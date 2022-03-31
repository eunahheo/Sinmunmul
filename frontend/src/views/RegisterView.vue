<template>
    <div class="register">
        <img alt="신문물 logo" src="../assets/shin_logo.png" style="width: 10%">
        <h1>신문물</h1>
        <h2 style="height:50px"></h2>
        <div>
            <span>register</span>

            <form>
                <div>
                    <input type="text" v-model="emailData" name="email" placeholder="email">
                    <input type="button" v-on:click="emailVerify" value="인증">
                    <input type="text" v-model="emailCheckCode" name="emailCheckCode" placeholder="인증번호">
                </div>
                <div>
                    <input type="password" v-model="passwdData" name="password" placeholder="enter password"><br>
                    <input type="password" v-model="passwdDataCheck" name="password" placeholder="enter password again">
                    <div>
                        <span v-if="passwdData == '' | passwdDataCheck == ''"></span>
                        <span v-else-if="passwdData == passwdDataCheck" v-on:change="console.log('aasd')">패스워드가 일치합니다.</span>
                        <span v-else>패스워드가 일치하지 않습니다.</span>
                    </div>
                </div>
                <div>
                    <span>gender</span>
                    <input type="radio" v-model="genderValue" value="male">
                    <input type="radio" v-model="genderValue" value="female">
                    {{genderValue}}
                </div>
                <div>
                    <span>연령대</span>
                    <select class="form-select" aria-label="Default select example">
                        <option selected>연령대를 선택해주세요</option>
                        <option value="1">10대</option>
                        <option value="2">20대</option>
                        <option value="3">30대</option>
                    </select>
                </div>
                <div>
                    <div>
                        <h2>정치</h2>
                        <button type="button" class="btn btn-secondary btn-sm">청와대</button>
                        <button type="button" class="btn btn-secondary btn-sm">국회/정당</button>
                        <button type="button" class="btn btn-secondary btn-sm">북한</button>
                        <button type="button" class="btn btn-secondary btn-sm">행정</button>
                        <button type="button" class="btn btn-secondary btn-sm">국방/외교</button>
                        <button type="button" class="btn btn-secondary btn-sm">정치일반</button>
                    </div>
                    <div>
                        <h2>경제</h2>
                        <button type="button" class="btn btn-secondary btn-sm">금융</button>
                        <button type="button" class="btn btn-secondary btn-sm">증권</button>
                        <button type="button" class="btn btn-secondary btn-sm">산업/재계</button>
                        <button type="button" class="btn btn-secondary btn-sm">중기/벤쳐</button>
                        <button type="button" class="btn btn-secondary btn-sm">부동산</button>
                        <button type="button" class="btn btn-secondary btn-sm">글로벌 경제</button>
                        <button type="button" class="btn btn-secondary btn-sm">생활경제</button>
                        <button type="button" class="btn btn-secondary btn-sm">경제 일반</button>
                    </div>
                    <div>
                        <h2>사회</h2>
                        <button type="button" class="btn btn-secondary btn-sm">사건사고</button>
                        <button type="button" class="btn btn-secondary btn-sm">교육</button>
                        <button type="button" class="btn btn-secondary btn-sm">노동</button>
                        <button type="button" class="btn btn-secondary btn-sm">언론</button>
                        <button type="button" class="btn btn-secondary btn-sm">환경</button>
                        <button type="button" class="btn btn-secondary btn-sm">인권/복지</button>
                        <button type="button" class="btn btn-secondary btn-sm">식품/의료</button>
                        <button type="button" class="btn btn-secondary btn-sm">지역</button>
                        <button type="button" class="btn btn-secondary btn-sm">인물</button>
                        <button type="button" class="btn btn-secondary btn-sm">사회 일반</button>
                    </div>
                    <div>
                        <h2>생활/문화</h2>
                        <button type="button" class="btn btn-secondary btn-sm">건강정보</button>
                        <button type="button" class="btn btn-secondary btn-sm">자동차/시승기</button>
                        <button type="button" class="btn btn-secondary btn-sm">도로/교통</button>
                        <button type="button" class="btn btn-secondary btn-sm">여행/레져</button>
                        <button type="button" class="btn btn-secondary btn-sm">음식/맛집</button>
                        <button type="button" class="btn btn-secondary btn-sm">패션/뷰티</button>
                        <button type="button" class="btn btn-secondary btn-sm">공연/전시</button>
                        <button type="button" class="btn btn-secondary btn-sm">책</button>
                        <button type="button" class="btn btn-secondary btn-sm">종교</button>
                        <button type="button" class="btn btn-secondary btn-sm">날씨</button>
                        <button type="button" class="btn btn-secondary btn-sm">생활문화 일반</button>
                    </div>
                    <div>
                        <h2>IT/과학</h2>
                        <button type="button" class="btn btn-secondary btn-sm">모바일</button>
                        <button type="button" class="btn btn-secondary btn-sm">인터넷/SNS</button>
                        <button type="button" class="btn btn-secondary btn-sm">통신/뉴미디어</button>
                        <button type="button" class="btn btn-secondary btn-sm">IT일반</button>
                        <button type="button" class="btn btn-secondary btn-sm">보안/해킹</button>
                        <button type="button" class="btn btn-secondary btn-sm">컴퓨터</button>
                        <button type="button" class="btn btn-secondary btn-sm">게임/리뷰</button>
                        <button type="button" class="btn btn-secondary btn-sm">과학 일반</button>
                    </div>
                    <div>
                        <h2>세계</h2>
                        <button type="button" class="btn btn-secondary btn-sm">아시아/호주</button>
                        <button type="button" class="btn btn-secondary btn-sm">미국/중남미</button>
                        <button type="button" class="btn btn-secondary btn-sm">유럽</button>
                        <button type="button" class="btn btn-secondary btn-sm">중동/아프리카</button>
                        <button type="button" class="btn btn-secondary btn-sm">세계일반</button>
                    </div>
                </div>
                <div>
                    <input type="submit" v-on:click="registerBtn" value="회원가입">
                    <input type="submit" value="취소">
                </div>
            </form>
        </div>
    </div>
</template>

<script>
/* eslint-disable */
import axios from 'axios'
const API_SERVER = 'https://j6a406.p.ssafy.io/api'

export default {
    name: "register",
    conponent: {
    },
    data() {
        return{
            emailData: '',
            emailCheckCode: '',
            isEmailChecked: false,
            genderValue: 'male',
            passwdData: '',
            passwdDataCheck: '',
            isPasswdChecked: false,
            birthDate: '20'

        }
    },
    methods: {
        emailVerify() {
            console.log(this.emailData)

            axios.post(`${API_SERVER}/user/info`, {
                params: {
                    userEmail : this.emailData,
                    usersgType : "email",
                }
            }).then((res) => {
                console.log(res)
                if(res.status === 200){
                    alert("이미 가입된 이메일입니다.")
                } else if (res.status === 202) {
                    axios.get(`${API_SERVER}/user/cert`, {
                        params: {
                            email : this.emailData,
                        }
                    }).then((res) => {
                        console.log(res.data.data)
                        this.emailCheckCode = res.data.data
                    })
                    alert("이메일로 인증번호를 보냈습니다.")
                }
            }).catch((e) => {
                console.log(e)
            })
        },
        registerBtn() {
            console.log(this.emailData, this.genderValue, this.passwdData, this.birthDate)
            axios.post(`${API_SERVER}/user/signin`, {
                params: {
                    userEmail : this.emailData,
                    userGender : this.genderValue,
                    userPwd : this.passwdData,
                    userBirth : (this.birthDate)*1,
                    usersgType : "email",
                }
            }).then((res) => {
                console.log(res)
                if(res.status === 200){
                    alert("회원가입 성공")
                } else {
                    alert(res.status)
                }
            }).catch((e) => {
                console.log(e)
            })
        }
    }
}
</script>