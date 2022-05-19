<template>
    <div class="login-container">
    <div class="center">
      <h1>로그인</h1>
      <form>
        <div class="txt_field">
          <input id="input-1" v-model="form.id" type="text" required>
          <span></span>
          <label>아이디</label>
        </div>
        <div class="txt_field">
          <input id="input-2" v-model="form.pass" type="password" required>
          <span></span>
          <label>비밀번호</label>
        </div>
        <div class="pass">비밀번호를 잊으셨나요?</div>
        <b-button class="button" @click="login">로그인</b-button>
        <div class="signup_link">
          회원이 아니신가요? <a href="javascript:void(0);" @click="movePage('/register')">회원가입</a>
        </div>
      </form>
    </div>
    </div>
</template>

<script>
import http from '@/util/http-common'
export default {
    data() {
        return {
            form: {
                id: '',
                pass: ''
            }
        }
    },
    methods: {
        login() {
            if (!this.form.id) {
                alert('아이디를 입력하세요.');
                return;
            }
            if (!this.form.pass) {
                alert('비밀번호를 입력하세요.');
                return;
            }

            http
                .post("/user/login", JSON.stringify(this.form))
                .then(( response ) => {
                    if(response.status == 200) {
                        if(response.data === '') {
                            alert("로그인에 실패했습니다. 아이디와 비밀번호를 확인하세요.");
                        } else {
                            alert("로그인에 성공했습니다.");
                            this.$store.dispatch('setUserId', response.data.id);
                            this.$cookies.set('userId', response.data.id);
                            this.movePage("/");
                        }
                    }
                })
                .catch(() => {
                    alert("로그인에 실패했습니다.");
                })        
        },
        movePage(url) {
            this.$router.push(url);
        },
    }
}
</script>