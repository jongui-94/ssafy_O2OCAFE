<template>
    <div style="text-align: center">
         <b-button class="order-sheet" v-b-toggle.sidebar-right v-show="userId">주문서 보기</b-button>
            <b-sidebar id="sidebar-right" title="주문서 작성중" right shadow>
                <div v-for="(item, idx) in orders" :key="idx" >
                    <b-card
                    :img-src="require('@/assets/menu/' + products[item].img)"
                    img-left="img-left"
                    img-height="90"
                    img-width="90">           
                    <b-card-text class="b-button-text" style="display:inline" >{{products[item].name}}</b-card-text>
                    <b-card-text class="b-button-text" style="display:inline" >{{products[item].count}}</b-card-text>
                    </b-card>
                </div>
            <b-button style="width: 100%; background-color:#00a1dd" variant="primary" @click="orderProduct">주문하기</b-button>
        </b-sidebar>

        <div id="container">
            <b-row>
                <b-col id="b-col" cols="3" v-for="(item, idx) in products" :key="idx">
                    <div class="product">
                    <img class="img" :src="require('@/assets/menu/' + item.img)" @click="moveProductDetail(item.id)"/>
                        <b-card-text class="product-name">{{item.name}}</b-card-text>
                        <b-card-text class="product-price">₩ {{ item.price }}</b-card-text>
                        <b-button-group v-show="userId">
                            <b-button class="b-button" variant="none" @click="minusProduct(idx)"> - </b-button>
                            <b-button class="b-button-text" variant="none" >{{ item.count }}</b-button>
                            <b-button class="b-button" variant="none" @click="plusProduct(idx)"> + </b-button>
                        </b-button-group>
                    </div>
                </b-col>
            </b-row>
        </div>
    </div>
</template>

<script>
    import http from '@/util/http-common'
    export default {
        name: 'product-list-view',
        data() {
            return {
                orders: []
            }
        },
        created() {
            this.$store.dispatch('getProducts');
        },
        computed: {
            products() {
                return this.$store.getters.getProducts;
            },
            userId() {
                let userId = this.$store.getters.getUserId;
                return userId;
            }
        },
        methods: {
            plusProduct(idx) {
                if(this.products[idx].count == 0) {
                    this.orders.push(idx);
                }
                this.products[idx].count++;
            },
            minusProduct(idx) {
                if (this.products[idx].count > 0) {
                    this.products[idx].count--;

                    if(this.products[idx].count == 0) {
                        for(var i = 0; i<this.orders.length; i++) {
                            if(idx === this.orders[i]) {
                                this.orders.splice(i, 1);
                                i--;
                            }
                        }
                    }
                }
            },
            orderProduct() {
                let order = {
                    details: [],
                    orderTable: 'table01',
                    userId: this.userId
                }

                for(let i = 0; i < this.orders.length; i++) {
                    let orderDetail = { 
                        productId: this.products[this.orders[i]].id,
                        quantity: this.products[this.orders[i]].count
                    }
                    order.details.push(orderDetail);
                }
                
                http
                    .post('/order', JSON.stringify(order))
                    .then(( response ) => {
                        if(response.status == 200) {
                            alert("주문을 완료했습니다.");
                            this.movePage('user-info');
                        } else {
                            alert("주문에 실패했습니다.");
                        }
                    })
                    .catch(() => {
                        alert("주문에 실패했습니다.");
                    })
            },
            moveProductDetail(productId) {
                this.$store.dispatch('setProductId', productId);
                this.movePage('product-detail');
            },
            movePage(url) {
                this.$router.push(url);
            }
        }
    }
</script>

<style scoped="scoped">
    #container {
        padding-top: 10px;
        padding-left: 200px;
        padding-right: 200px;
    }

    #b-col {
        padding: 20px;
    }

    .buttons {
        text-align: center;
    }

    .buttons > b-button {
        margin: 10px;
    }

    #kakao {
        width: 40px;
        height: 40px;
    }

    .img {
        width: 90%;
        height: 300px;

    }

    .product {
        text-align: center;
    }

    .product-name{
        margin-top: 20px;
        font-family: "Rubik", sans-serif;
        font-weight: 400;
        font-size: 16px;
    }

    .product-price{
        color: darkgrey;
        margin-top: 10px;
        font-family: "Rubik", sans-serif;
        font-weight: 400;
        font-size: 16px;
    }

    .b-button {
        width: 28%;
        margin-left: 10px;
        margin-right: 10px;
        border-radius: 0px;
        text-align: center;
        font-size: 16px;
        color:#01a1dd
    }

    .b-button-text {
        width: 28%;
        margin-left: 10px;
        margin-right: 10px;
        border-radius: 0px;
        text-align: center;
        font-weight: 400;
        font-size: 16px;
        color:black
    }

    .order-sheet {
        width: 200px;
        height: 60px; 
        margin-top: 30px;
        background-color:#00a1dd;
    }
</style>