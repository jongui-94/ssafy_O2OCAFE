<template>
    <div class="container">
        <b-alert variant="info" show="show">
            <h3 class="title">상품상세</h3>
            <div>
                <b-card-group>
                    <img
                        :src="require('@/assets/menu/' + productDetail[0].img)"
                        imt="imt"
                        width="300"
                        img="img"
                        height="300"/>
                    <b-col class="content">
                        <b-card>상품명:
                            {{ productDetail[0].name }}</b-card>
                        <b-card>상품단가:
                            {{ productDetail[0].price }}</b-card>
                        <b-card>총주문수량:
                            {{ productDetail[0].sells }}</b-card>
                        <b-card>평가회수:
                            {{ productDetail[0].commentCnt == null ? 0 : productDetail[0].commentCnt }}</b-card>
                        <b-card>평균평점:
                            {{ productDetail[0].avg == null ? 0 : productDetail[0].avg }}</b-card>
                    </b-col>
                </b-card-group>
                <b-button class="b-button" v-b-modal.modal-prevent-closing v-show="userId">
                    한줄평 남기기
                </b-button>

                <b-modal
                    id="modal-prevent-closing"
                    ref="modal"
                    title="한줄평 남기기"
                    @show="initReview"
                    @hidden="initReview"
                    @ok="handleOk">
                    <form ref="form" @submit.stop.prevent="handleSubmit">
                        <b-form-group
                            label="평점"
                            label-for="rating-input"
                            invalid-feedback="0~10 사이 값만 가능합니다.">                        
                            <b-form-input
                                id="rating-input"
                                v-model="review.rating"
                                required>
                            </b-form-input>
                        </b-form-group>
                         <b-form-group
                            label="한줄평"
                            label-for="review-input"
                            invalid-feedback="한줄평">                        
                            <b-form-input
                                id="name-input"
                                v-model="review.comment"
                                required>
                            </b-form-input>
                        </b-form-group>
                    </form>
                </b-modal>
                
                <hr class="divider">
                </div>
                <p class="content"> 자신이 남긴 평가만 수정 삭제할 수 있습니다.</p>    

                <table class="table-content">
                    <thead>
                        <tr>
                            <th>사용자</th>
                            <th>평점</th>
                            <th>한줄평</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(item, idx) in productDetail" :key="idx">
                            <td>{{item.userName}}</td>
                            <td>{{item.rating}}</td>
                            <td>{{item.comment}}</td>
                            <td>
                                <div v-if="item.user_id === userId">
                                    <b-button variant="success" @click="updateModal(item)">
                                        수정
                                    </b-button>
                                    <b-button variant="danger" @click="deleteModal(item)">
                                        삭제
                                    </b-button>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table> 
            </b-alert> 

            <b-modal title="한줄평 삭제" id="delete-modal" @ok="deleteOk">{{deleteReview.comment}} 을(를) 삭제하시겠습니까?</b-modal>
            <b-modal
                    id="update-modal"
                    ref="modal"
                    title="한줄평 수정하기"
                    @show="resetUpdateModal"
                    @hidden="resetUpdateModal"
                    @ok="updateOk">
                    <form ref="form" @submit.stop.prevent="updateSubmit">
                        <b-form-group
                            label="평점"
                            label-for="rating-input"
                            invalid-feedback="0~10 사이 값만 가능합니다.">                        
                            <b-form-input
                                id="rating-input"
                                v-model="updateReview.rating"
                                required>
                            </b-form-input>
                        </b-form-group>
                         <b-form-group
                            label="한줄평"
                            label-for="review-input"
                            invalid-feedback="한줄평">                        
                            <b-form-input
                                id="name-input"
                                v-model="updateReview.comment"
                                required>
                            </b-form-input>
                        </b-form-group>
                    </form>
            </b-modal>
        </div>
    </template>

    <script>
    import http from '@/util/http-common'
        export default {
            name: 'product-detail-view',
            created() {
                this.$store.dispatch('getProductDetail', this.$store.getters.getProductId);
            },            
            computed: {
                productDetail() {
                    let productDetail = this.$store.getters.getProductDetail;
                    return productDetail;
                },
                userId() {
                    let userId = this.$store.getters.getUserId;
                    return userId;
                },
                productId() {
                    let productId = this.$store.getters.getProductId;
                    return productId;
                }
            },
            data() {
                return {
                    review: {
                        comment: '',
                        id: 0,
                        productId: 0,
                        rating: 0,
                        userId: ''
                    },
                    deleteReview: {
                        id: 0,
                        comment: ''
                    },
                    updateReview: {
                        comment: '',
                        id: 0,
                        productId: 0,
                        rating: 0,
                        userId: ''
                    }
                }
            },
            methods: {
                deleteModal(productDetail) {
                    this.deleteReview.id = productDetail.commentId;
                    this.deleteReview.comment = productDetail.comment;
                    this.$bvModal.show('delete-modal');
                },
                deleteOk() {
                    http
                    .delete('/comment/' + this.deleteReview.id)
                    .then((body) => {
                        if (body.status === 200) {
                            alert("상품평을 삭제했습니다.");
                            this.$store.dispatch('getProductDetail', this.$store.getters.getProductId);
                        }
                    })
                    .catch(() => {
                        alert("상품평을 삭제하는데 실패했습니다.");
                    })
                },
                updateModal(productDetail) {
                    this.updateReview.comment = productDetail.comment;
                    this.updateReview.id = productDetail.commentId;
                    this.updateReview.productId = this.productId;
                    this.updateReview.userId = productDetail.user_id;
                    this.updateReview.rating = productDetail.rating;

                    this.$bvModal.show('update-modal');
                },
                updateOk(bvModalEvt) {
                    bvModalEvt.preventDefault();
                    this.updateSubmit();
                },
                updateSubmit() {
                    if(this.updateReview.rating < 0 || this.updateReview.rating > 10) {
                        alert("평점이 유효하지 않습니다.");
                        return;
                    }
                    if(this.updateReview.review === '') {
                        alert("상품평이 비어있습니다.");
                        return;
                    }

                    http
                    .put('/comment', JSON.stringify(this.updateReview))
                    .then(( response ) => {
                        if(response.status == 200) {
                            alert("상품평을 수정했습니다.");
                            this.$store.dispatch('getProductDetail', this.$store.getters.getProductId);
                        } else {
                            alert("상품평을 수정하는데 실패했습니다.");
                        }
                    })
                    .catch(() => {
                        alert("상품평을 수정하는데 실패했습니다.");
                    })                              

                    this.$nextTick(() => {
                        this.$bvModal.hide('update-modal');
                    })
                },
                initReview() {
                    this.review = {
                        comment: '',
                        id: 0,
                        productId: 0,
                        rating: 0,
                        userId: ''
                    };
                },
                handleOk(bvModalEvt) {
                    bvModalEvt.preventDefault();
                    this.handleSubmit();
                },
                handleSubmit() {
                    if(this.review.rating < 0 || this.review.rating > 10) {
                        alert("평점이 유효하지 않습니다.");
                        return;
                    }
                    if(this.review.comment === '') {
                        alert("상품평이 비어있습니다.");
                        return;
                    }
                    
                    this.review.userId = this.userId;
                    this.review.productId = this.productId;

                    console.log(this.review);

                    http
                    .post('/comment', JSON.stringify(this.review))
                    .then(( response ) => {
                        if(response.status == 200) {
                            alert("상품평을 등록했습니다.");
                            this.$store.dispatch('getProductDetail', this.$store.getters.getProductId);
                        } else {
                            alert("상품평을 등록하는데 실패했습니다.");
                        }
                    })
                    .catch(() => {
                        alert("상품평을 등록하는데 실패했습니다.");
                    })

                    this.$nextTick(() => {
                        this.$bvModal.hide('modal-prevent-closing');
                    })
                }
            }
        }
    </script>

    <style scoped="scoped">
        .container {
            padding: 20px;
        }

        table {
            margin-top: 15px;
            width: 100%;
            border: 1px solid black;
            border-collapse: collapse;
            text-align: center;
            table-layout: fixed;
        }

        th {
            background-color: rgb(56, 54, 54);
            color: white;
        }

        td,
        th {
            padding: 10px 5px;
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

.b-button {
    margin: 15px;
    float: right;
    background-color:#00a1dd;
}

.table-content {

  color: #555;
  font-size: 16px;
  line-height: 1.6;
  font-weight: 400;
  font-family: Noto Sans KR,sans-serif;
}
    </style>