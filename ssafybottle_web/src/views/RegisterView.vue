<template>
    <div class="login-container">
    <div class="center">
      <h1>회원가입</h1>
      <form>
        <div class="txt_field">
          <input id="input-1" v-model="form.id" type="text" required>
          <span></span>
          <label>아이디</label>
        </div>
        <div class="txt_field">
          <input id="input-2" v-model="form.name" type="text" required>
          <span></span>
          <label>이름</label>
        </div>
        <div class="txt_field">
          <input id="input-3" v-model="form.pass" type="password" required>
          <span></span>
          <label>비밀번호</label>
        </div>

        <b-button class="button" @click="register">회원가입</b-button>

        <div class="signup_link">
          계정이 있으신가요? <a href="javascript:void(0);" @click="movePage('/login')">로그인</a>
        </div>
      </form>
    </div>
    </div>
</template>

<script>
import http from '@/util/http-common'
export default {
    name: 'register-view',
    data() {
        return {
            form: {
                id: '',
                name: '',
                pass: ''
            }
        }
    },
    methods: {
        register() {
            if (!this.form.id) {
                alert('아이디를 입력하세요.');
                return;
            }

            if (!this.form.name) {
                alert('이름을 입력하세요.');
                return;
            }

            if (!this.form.pass) {
                alert('비밀번호를 입력하세요.');
                return;
            }

            http
                .post("/user", JSON.stringify(this.form))
                .then(( response ) => {
                    if (response.status == 200) {
                    alert("회원가입에 성공했습니다.");
                    this.movePage("/login");
                    } else {            
                    alert("회원가입에 실패했습니다.");
                    }
                })
                .catch(() => {
                    alert("회원가입에 실패했습니다.");
                })        
        },
        movePage(url) {
            this.$router.push(url);
        }
    }
}
</script>

<style scoped>
.error {
    color: red;
}
</style>