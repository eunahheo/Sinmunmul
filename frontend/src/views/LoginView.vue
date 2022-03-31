<template>
    <div class="login">
        <img alt="신문물 logo" src="../assets/shin_logo.png" style="width: 10%">
        <h1>신문물</h1>
        <h2 style="height:50px"></h2>
        <div>
            <h2>login</h2>

            <form>
                <input type="hidden" name="_token" value="{{ csrf_token() }}">
                <div>
                    <input type="text" name="email" v-model="emailData" placeholder="email">
                    <input type="password" name="password" v-model="passwdData" placeholder="password">
                </div>
                <button v-on:click='loginBtn'>로그인</button>
                <button>
                    <router-link to="/register">register</router-link>
                </button>
                <!-- social login -->
                <div class="social-login">
                    <section>
                        <div v-on:click="kakaoLogin">카카오 연동</div>
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
            emailData: '',
            passwdData: '',
            kakaoToken: '',
            kakaoEmail: '',
        }
    },
    methods: {
        loginBtn() {
            console.log("login triggerd ");
            
            if(this.emailData != null && this.passwdData !="") {
                console.log('login data : ', this.emailData, this.passwdData);
                axios.get(`${API_SERVER}/user/login`, {
                params: {
                    userEmail : this.emailData,
                    userPwd : this.passwdData,
                }
                })
                .then((res) =>{
                console.log(res);
                }).catch((e) => {
                    console.log(e);
                })
            } else {
                alert("로그인 정보를 입력해주세요");
            }
        },

        kakaoLogin:function() {
            window.Kakao.init('864650259e852266a14e98b75eedc985')

            if (window.Kakao.Auth.getAccessToken()) {
                window.Kakao.API.request({
                    url: '/v1/user/unlink',
                    sucess: function(reponse) {
                        console.log("unlink")
                        console.log(reponse)
                    },
                    fail: function(error) {
                        console.log("unlink")
                        console.log(error)
                    },
                })
                window.Kakao.Auth.setAccessToken(undefined)
            }

            window.Kakao.Auth.login({
                success: function(authObj) {
                    window.Kakao.API.request({
                        url: '/v2/user/me',
                        data: {
                            property_keys: ["kaccount_email"]
                        },
                        success: async function(res) {
                            console.log(res);
                            axios.post(`${API_SERVER}/user/kakao/login`, {
                                accessToken: res.id,
                            }).then((res) => {
                                console.log(res);
                                if(res.status == 200) {
                                    alert("로그인 성공");
                                    window.location.href = "/";
                                } else {
                                    alert("로그인 실패");
                                }
                            }).catch((e) => {
                                console.log(e);
                            })
                        },
                        fail: function(error) {
                            console.log(error);
                        }
                    });
                },
                fail: function(err) {
                    console.log(err);
                }
            })
        }
    }
}
</script>
