<template>
    <div>
    <header class="header">
      <a href="javascript:void(0);">
        <img class="logo" alt="SSAFYBOTTLE" src="@/assets/ic_logo.png" @click="movePage('/')" />
      </a>

      <nav class="main-nav">
        <ul class="main-nav-list">
          <li><a class="main-nav-link" href="javascript:void(0);" @click="movePage('/')">Home</a></li>
          <li><a class="main-nav-link" href="javascript:void(0);" @click="movePage('/about')">About</a></li>
          <li><a class="main-nav-link" href="javascript:void(0);" @click="movePage('/product-list')">Menu</a></li>
          <li><a class="main-nav-link" href="javascript:void(0);" @click="moveMyPage()">MyPage</a></li>
          <li ><a class="main-nav-link nav-cta" href="javascript:void(0);" v-show="!userId" @click="movePage('/login')" >Login</a></li>
          <li ><a class="main-nav-link nav-cta" href="javascript:void(0);" v-show="userId" @click="logout" >Logout</a></li>
        </ul>
      </nav>
    </header>
    <hr>
    </div>

</template>

<script>
export default {
    name: 'header-nav',
    methods: {
        movePage(url) {
            this.$router.push(url);
        },
        moveMyPage() {
          if(this.userId) {
            this.movePage('/user-info');
          } else {
            this.movePage('/login');
          }
        },
        logout() {
            this.$store.dispatch('logout');
            this.$cookies.remove('userId');
            this.$router.go();
        }
    },
    computed: {
      userId() {
        let userId = this.$store.getters.getUserId;
        return userId;
      }
    }
}
</script>

<style scope>
#logo {
    width: 30px;
    height: 30px;
}
</style>