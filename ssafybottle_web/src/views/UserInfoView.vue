<template>
    <div class="container">
        <hr>
        <b-alert variant="info" show>
            <h3 class="title">우리 {{ userInfo.user.name }}님은요~</h3>
            <hr class="divider">
            <p class="content">로그인 할 때 아이디는 {{userInfo.user.id}}을 사용합니다.<br>
                현재 모은 스탬프는 총 {{userInfo.user.stamps}}개로 {{userInfo.grade.title}} {{userInfo.grade.step}} 단계입니다.<br>
                앞으로 {{userInfo.grade.to}}개만 더 모으면 다음 단계 입니다!!🚀
            </p>
        </b-alert>
        <hr>
       <b-card-group deck="deck">
            <b-card class="title" header="이제까지 주문 내역은">
                <p class="content">
                    주문 정보를 클릭하면 주문 내역을 살펴볼 수 있습니다.
                </p>
                <b-list-group class="content" v-for="(item, idx) in userInfo.order" :key="idx">
                    <b-list-group-item href="#" @click="setOrderId(item.id)">
                        언제? {{item.orderTime}}
                    </b-list-group-item>       
                </b-list-group>
            </b-card>
        </b-card-group>
    </div>
</template>

<script>
export default {
    name: 'user-info-view',
    created() {
        this.$store.dispatch('getUserInfo', this.$store.state.userId);
    },
    computed: {
        userInfo() {
            let userInfo = this.$store.getters.getUserInfo;
            return userInfo;
        }
    },
    methods: {
        setOrderId(orderId) {
            this.$store.dispatch('setOrderId', orderId);
            this.movePage("/order-detail");
        },
        movePage(url) {
            this.$router.push(url);
        },
    }
}
</script>

<style scoped>
.container {
    padding: 10px
}

.content{
  display: block;
  margin-block-start: 1em;
  margin-block-end: 1em;
  margin-inline-start: 0px;
  margin-inline-end: 0px;
  margin: 0 0 10px;
  -webkit-text-size-adjust: 100%;
  color: #555;
  font-size: 16px;
  line-height: 1.6;
  font-weight: 400;
  font-family: Noto Sans KR,sans-serif;
}

.title {
  margin: 10px 0;
    font-family: inherit;
    line-height: 20px;
    color: inherit;
    text-rendering: optimizelegibility;
    line-height: 40px;
    font-size: 31.5px;
    margin-block-start: 0.83em;
    margin-block-end: 0.83em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    display: block;
}

.divider {
    margin-bottom: 10px;
}
</style>