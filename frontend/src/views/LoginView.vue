<template>
    <div class="login d-flex flex-wrap  align-item-center justify-content-center" style="height: 60%" v-on:change="checkLogin">
        <div class="container m-5 align-item-center" style="border: 3px solid black">
            <h1 class="m-5">로그인</h1>

            <blockquote class="blockquote">
                <p>신문물의 회원 기능을 사용하려면 로그인이 필요합니다.</p>
            </blockquote>

            <form>
                <input type="hidden" name="_token" value="{{ csrf_token() }}">
                <div class="input-group mb-3" style="padding: 5% 5% 0% 5%">
                    <input type="text" class="form-control" v-model="emailData" placeholder="아이디를 입력하세요(이메일)" aria-label="Username" aria-describedby="basic-addon1">
                </div>
                <div class="input-group mb-3" style="padding: 0% 5% 0% 5%">
                    <input type="password" class="form-control" v-model="passwdData" placeholder="비밀번호를 입력하세요" aria-label="Username" aria-describedby="basic-addon1">
                </div>

                <button type="button" class="btn btn-primary m-2" v-on:click='loginBtn'>로그인</button>
                <button type="button" class="btn btn-light m-2" to='/'>취소</button>
                <p class="m-2">
                    아직 회원이 아니신가요? <router-link to="/register">회원가입</router-link>으로 계정을 만들어주세요.
                </p>
                <!-- social login -->
                <div class="social-login m-5">
                    <h3>소셜 로그인</h3>
                    <section>
                        <img src="../../public/img/kakao_login_medium_narrow.png" class="rounded" alt="카카오 로그인" v-on:click="kakaoLogin">
                    </section>
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
    name: "login",
    data() {
        return{
            authToken: localStorage.getItem('authToken') || null,
            emailData: '',
            passwdData: '',
            kakaoToken: '',
            kakaoEmail: '',
        }
    },
    mounted() {
        if(this.authToken) {
            this.$router.push('/')
        }
    },
    methods: {
        loginBtn() {
            console.log("login triggerd ");

            if(this.emailData != null && this.passwdData !="") {
                console.log('login data : ', this.emailData, this.passwdData);
                axios.post(`${API_SERVER}/user/login`, {
                        userEmail : this.emailData,
                        userPwd : this.passwdData,
                })
                .then((res) =>{
                    console.log(res);
                    if(res.status === 200){
                        localStorage.setItem('authToken', res.data.data.accessToken);
                        this.$store.userSeq = res.data.data.userSeq;
                        // this.$router.push({ name: 'home'});
                        this.$router.go()
                    } else if (res.status === 202) {
                        alert("가입 정보가 없습니다.")
                    }

                }).catch((e) => {
                    console.log(e);
                })
            } else {
                alert("로그인 정보를 입력해주세요");
            }
        },

        kakaoLogin:function() {
            window.Kakao.Auth.login({
                scope: 'account_email, gender',
                success: this.getInfo
            });
        },
        getInfo(authObj){
            console.log(authObj.access_token);
            axios.post(`${API_SERVER}/user/kakao/login`, {}, {
                headers: {
                    accessToken: authObj.access_token,
                }
            }).then((res) => {
                if(res.status === 200){
                    localStorage.setItem('authToken', res.data.data.accessToken);
                    this.$router.go()
                } else if (res.status === 202) {
                    alert("등록되지 않은 계정입니다. 회원가입 페이지로 이동합니다.");
                    this.$router.push({
                        name: 'register',
                        params: {
                            email: res.data.data.email,
                            gender: res.data.data.gender,
                            usersgType: "kakao"
                        }
                    })
                }
            })
        }
    }
}
</script>
