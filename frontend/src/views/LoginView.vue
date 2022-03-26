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
                    <input type="password" name="password" v-model="pswdData" placeholder="password">
                </div>
                <input type="submit" value="login">

                <router-link to="/register">register</router-link>
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
            pswdData: '',
            kakaoToken: '',
        }
    },
    methods: {
        kakaoLogin:function() {
            window.Kakao.init('864650259e852266a14e98b75eedc985')

            if (window.Kakao.Auth.getAccessToken()) {
                window.Kakao.API.request({
                    url: '/v1/user/unlink',
                    sucess: function(reponse) {
                        console.log(reponse)
                    },
                    fail: function(error) {
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
