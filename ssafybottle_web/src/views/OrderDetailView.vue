<template>
    <div class="container">
        <b-card-group deck="deck">
            <b-card class="title" header="주문상세">
                <b-alert variant="info" show>
                    <h5 class="content">주문일시: {{orderDetail.orders[0].order_time}} </h5>
                    <hr class="divider">
                    <div v-for="(item, idx) in orderDetail.orders" :key="idx">
                        <b-card class="item"
                            :img-src="require('@/assets/menu/' + item.img)"
                            img-left="img-left"
                            img-height="90"
                            img-width="90">
                        <b-card-text style="display: inline" class="content">
                            품명: {{item.name}}, 단가: {{item.unitprice}}, {{item.quantity}}잔</b-card-text>
                    <b-button style="float: right">{{item.totalprice}}원</b-button>
                    </b-card>
                </div>
                    <hr class="divider">
                    <p class="content">총 비용: {{orderDetail.totalPrice}}원, 스탭프 적립: {{orderDetail.orders[0].stamp}}</p>
                </b-alert>
            </b-card>                      
        </b-card-group>
    </div>
</template>

<script>
export default {
    name: 'order-detail-view',
    created() {
        this.$store.dispatch('getOrderDetail', this.$store.state.orderId);
    },
    computed: {
        orderDetail() {
            let orderDetail = this.$store.getters.getOrderDetail;
            return orderDetail;
        }
    }
}
</script>

<style scoped>
.container {
    padding: 10px;
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

.item {
    vertical-align: middle;    
}
</style>