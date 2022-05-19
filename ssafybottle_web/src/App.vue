<template>
  <div id="app">
    <header-nav/>
    <router-view/>
    <footer-main/>
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav.vue";
import Footer from "@/components/Footer.vue";

export default {
  name: 'app',
  components: {
    'header-nav' : HeaderNav,
    'footer-main' : Footer    
  },
  created() {
    let userId = this.$cookies.get('userId');

    if(userId !== null) {
      this.$store.dispatch('setUserId', userId);
    }

    let t_order = [
      { id: 1, user_id: 'ssafy01', time: '2022-03-19 01:57:30', where: '웹주문', products: [ {id: 1, quantity: 3}, {id: 2, quantity: 5}, {id: 3, quantity: 1}]},
      { id: 2, user_id: 'ssafy01', time: '2022-03-20 02:47:50', where: '웹주문', products: [ {id: 4, quantity: 4}, {id: 5, quantity: 4}, {id: 6, quantity: 2}]},
      { id: 3, user_id: 'ssafy01', time: '2022-03-21 03:27:40', where: '웹주문', products: [ {id: 7, quantity: 5}, {id: 8, quantity: 3}, {id: 9, quantity: 3}]}
    ];
    localStorage.setItem("t_order", JSON.stringify(t_order));

    let t_review = [
      { id: 1, user_id: 'ssafy01', user_name: "싸피01", product_id: 1, rating: 10, review: '먹을만 하긴 합니다.'},
      { id: 2, user_id: 'ssafy02', user_name: "싸피02", product_id: 1, rating: 5, review: '그냥 그래요.'},
    ];
    localStorage.setItem("t_review", JSON.stringify(t_review));
  }
}
</script>