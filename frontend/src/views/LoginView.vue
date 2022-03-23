<template>
    <div class="login">
        <img alt="신문물 logo" src="../assets/shin_logo.png" style="width: 10%">
        <h1>신문물</h1>
        <h2 style="height:50px"></h2>
        <div>
            <span>login</span>

            <form>
                <input type="hidden" name="_token" value="{{ csrf_token() }}">
                <input type="text" name="email" placeholder="email">
                <input type="password" name="password" placeholder="password">
                <input type="submit" value="login">

                <!-- <a href="/register">register</a> -->
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
export default {
    name: "login",
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
