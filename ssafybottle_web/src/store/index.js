import Vue from 'vue'
import Vuex from 'vuex'
import { getTime } from "@/util/time.js"
import http from '@/util/http-common'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    /*
    Api
    */
    // 상품 리스트
    products: [],
    productId: 0,
    productDetail: [],

    userId: '',
    user: {},
    userInfo: {},

    orderId: 0,
    orderDetail: [],

    /*
    LocalDatabase
    */

    loginUser: {
      id: '',
      name: '',
      pass: '',
      stamps: 0
    },    
    myOrders: [],
    orders: [],
    reviews: []
  },
  getters: {
    /*
    Api
    */
    getProducts(state) {
      return state.products;
    },
    getUserId(state) {
      return state.userId;
    },
    getUser(state) {
      return state.user;
    },
    getUserInfo(state) {
      return state.userInfo;
    },
    getOrderDetail(state) {
      return state.orderDetail;
    },
    getProductId(state) {
      return state.productId;
    },
    getProductDetail(state) {
      return state.productDetail;
    },

    /*
    LocalDatabase
    */
    getLoginUser(state) {
      return state.loginUser;
    },
    getMyOrders(state) {
      return state.myOrders.reverse();
    },
    getReviews(state) {
      return state.reviews;
    }
  },
  mutations: {
    /*
    Api
    */
    
    // 서버에서 가져온 상품리스트 저장
    setProducts(state, payload) {
      state.products = payload;
    },
    setUserId(state, payload) {
      state.userId = payload;
    },
    setUser(state, payload) {
      state.user = payload;
    },
    logout(state) {
      state.userId = '';
      state.user = {};
    },
    setUserInfo(state, payload) {
      state.userInfo = payload;
    },
    setOrderId(state, payload) {
      state.orderId = payload;
    },
    setOrderDetail(state, payload) {
      state.orderDetail = payload;
    },
    setProductId(state, payload) {
      state.productId = payload;
    },
    setProductDetail(state, payload) {
      state.productDetail = payload;
    },

    

    /*
    LocalDatabase
    */
    SELECT_PRODUCT(state, products) {
      state.products = products;
    },
    INSERT_ORDER_DETAIL(state, orderDetail) {
      state.orderDetail = orderDetail;
    },
    INSERT_ORDER(state, orders) {
      state.orders = orders;
    },
    SELECT_MY_ORDER(state, orders) {
      let myOrders = [];
      for (let i = 0; i < orders.length; i++) {
        if (orders[i].user_id === state.loginUser.id) {
          myOrders.push(orders[i]);
        }
      }
      state.myOrders = myOrders;
    },
    INSERT_PRODUCT_DETAIL(state, productDetail) {
      state.productDetail = productDetail;
    },
    SELECT_REVIEWS(state, reviews) {
      state.reviews = reviews;
    },
    INSERT_REVIEWS(state, reviews) {
      state.reviews = reviews;
    },
    INSERT_STAMP(state, stamps) {
      state.loginUser.stamps += stamps;
    }
  },
  actions: {
    /*
    Api
    */
    // 상품 정보 가져오기
    getProducts(context) {
      http
        .get("/product")
        .then((body) => {
          let products = body.data;

          for (let p of products) {
            p.count = 0;
          }

          context.commit("setProducts", products);
        })
        .catch(() => {
          alert("상품 정보를 가져오는데 실패했습니다.");
      })
    },
    setUserId(context, userId) {
      context.commit('setUserId', userId);
    },
    setUser(context, user) {
      context.commit('setUser', user);
    },
    logout(context) {
      context.commit('logout');
    },
    getUserInfo(context, userId) {
      http
        .post('/user/info?id=' + userId)
        .then(({ data }) => {
          if (data == '') {
            alert("유저 정보를 가져오는데 실패했습니다.");
          } else {
            context.commit('setUserInfo', data);
          }
        })
        .catch(() => {
          alert("유저 정보를 가져오는데 실패했습니다.");
        })
    },
    setOrderId(context, orderId) {
      context.commit('setOrderId', orderId);
    },
    getOrderDetail(context, orderId) {
      http
        .get('/order/' + orderId)
        .then(({ data }) => {
          let orderDetail = {};
          orderDetail.orders = data;
          orderDetail.totalPrice = 0;
          orderDetail.totalStamp = 0;
          for (let o of data) {
            orderDetail.totalPrice += o.totalprice;
            orderDetail.totalStamp += o.stamp;
          }

          context.commit('setOrderDetail', orderDetail);
        })
        .catch(() => {
          alert("주문 상세 정보를 가져오는데 실패했습니다.");
      })
    },
    setProductId(context, productId) {
      context.commit('setProductId', productId);
    },
    getProductDetail(context, productId) {
      http
        .get('/product/' + productId)
        .then(({ data }) => {
          console.log(data);
          context.commit('setProductDetail', data);
        }).catch(() => {
          alert("상품 상세 정보를 가져오는데 실패했습니다.");
      })
    },

    /*
    LocalDatabase
    */


    insertOrder({ commit }, order) {
      let t_order_string = localStorage.getItem('t_order');
      let t_order = JSON.parse(t_order_string);

      order.id = t_order.length + 1;
      order.where = '웹주문';
      order.time = getTime();

      t_order.push(order);
      localStorage.setItem('t_order', JSON.stringify(t_order));

      let t_user_string = localStorage.getItem('t_user');
      let t_user = JSON.parse(t_user_string);

      let stamps = 0;
      for (let i = 0; i < order.products.length; i++) {
        stamps += order.products[i].quantity;
      }
      for (let i = 0; i < t_user.length; i++) {
        if (t_user[i].id === order.user_id) {
          t_user[i].stamps = stamps;
        }
      }
      localStorage.setItem('t_user', JSON.stringify(t_user));

      commit('INSERT_ORDER', t_order);
      commit('INSERT_STAMP', stamps);
    },
    selectMyOrders({ commit }) {
      let t_order_string = localStorage.getItem('t_order');
      let t_order = JSON.parse(t_order_string);

      commit("SELECT_MY_ORDER", t_order);
    },
    insertOrderDetail({ commit }, order) {
      let t_product_string = localStorage.getItem('t_product');
      let t_product = JSON.parse(t_product_string);

      let orderDetail = {
        time: order.time,
        where: order.where,
        products: [],
        total_cost: 0,
        stamp: 0
      };

      for (let i = 0; i < order.products.length; i++) {
        for (let j = 0; j < t_product.length; j++) {
          if (order.products[i].id === t_product[j].id) {
            orderDetail.products.push({
              id: t_product[j].id,
              name: t_product[j].name,
              type: t_product[j].type,
              price: t_product[j].price,
              img: t_product[j].img,
              quantity: order.products[i].quantity,
            });
          }
        }
      }

      for (let i = 0; i < orderDetail.products.length; i++) {
        orderDetail.stamp += orderDetail.products[i].quantity;
        orderDetail.total_cost += (orderDetail.products[i].quantity * orderDetail.products[i].price);
      }

      commit("INSERT_ORDER_DETAIL", orderDetail);
    },
    insertProductDetail({ commit }, product) {
      // product = { id: 1, name: 'coffee1', type: 'coffee' , price: 1000, img: 'coffee1.png'}
      // productDetail = {	name:	,price: ,	total_order_quantity:,	evaluation_count:,	average_rating:,	img:}

      let t_order_string = localStorage.getItem('t_order');
      let t_order = JSON.parse(t_order_string);

      let t_review_string = localStorage.getItem('t_review');
      let t_review = JSON.parse(t_review_string);

      let productDetail = {
        id: product.id,
        name: product.name,
        price: product.price,
        type: product.type,
        total_order_quantity: 0,
        evaluation_count: 0,
        average_rating: 0,
        img: product.img
      }

      for (let i = 0; i < t_order.length; i++) {
        for (let j = 0; j < t_order[i].products.length; j++) {
          if (t_order[i].products[j].id === product.id) {
            productDetail.total_order_quantity += t_order[i].products[j].quantity;
          }
        }
      }

      let ratingSum = 0;
      for (let i = 0; i < t_review.length; i++) {
        if (t_review[i].product_id === product.id) {
          productDetail.evaluation_count += 1;
          ratingSum += Number(t_review[i].rating);
        }
      }

      if (productDetail.evaluation_count > 0) {
        productDetail.average_rating = ratingSum / productDetail.evaluation_count;
      }

      commit('INSERT_PRODUCT_DETAIL', productDetail);
    },
    selectProductDetail({ commit }) {
      let t_order_string = localStorage.getItem('t_order');
      let t_order = JSON.parse(t_order_string);

      let t_review_string = localStorage.getItem('t_review');
      let t_review = JSON.parse(t_review_string);

      this.state.productDetail.total_order_quantity = 0;

      for (let i = 0; i < t_order.length; i++) {
        for (let j = 0; j < t_order[i].products.length; j++) {
          if (t_order[i].products[j].id === this.state.productDetail.id) {
            this.state.productDetail.total_order_quantity += t_order[i].products[j].quantity;
          }
        }
      }

      this.state.productDetail.evaluation_count = 0;

      let ratingSum = 0;
      for (let i = 0; i < t_review.length; i++) {
        if (t_review[i].product_id === this.state.productDetail.id) {
          this.state.productDetail.evaluation_count += 1;
          ratingSum += Number(t_review[i].rating);
        }
      }

      if (this.state.productDetail.evaluation_count > 0) {
        this.state.productDetail.average_rating = ratingSum / this.state.productDetail.evaluation_count;
      }

      commit('INSERT_PRODUCT_DETAIL', this.state.productDetail);
    },
    selectReviews({ commit }) {
      let t_review_string = localStorage.getItem('t_review');
      let t_review = JSON.parse(t_review_string);

      let reviews = [];

      for (let i = 0; i < t_review.length; i++) {
        if (t_review[i].product_id === this.state.productDetail.id) {
          reviews.push(t_review[i])
        }
      }
      commit('SELECT_REVIEWS', reviews);
    },
    insertReview({ commit }, review) {
      let t_review_string = localStorage.getItem('t_review');
      let t_review = JSON.parse(t_review_string);

      review.id = t_review.length + 1;
      t_review.push(review);
      localStorage.setItem('t_review', JSON.stringify(t_review));

      commit('INSERT_REVIEWS', t_review)
    },
    deleteReview({ commit }, reviewId) {
      let t_review_string = localStorage.getItem('t_review');
      let t_review = JSON.parse(t_review_string);

      for (let i = 0; i < t_review.length; i++) {
        if (t_review[i].id === reviewId) {
          t_review.splice(i, 1);
          i--;
        }
      }

      localStorage.setItem('t_review', JSON.stringify(t_review));
      commit('SELECT_REVIEWS', t_review);
    },
    updateReview({ commit }, review) {
      let t_review_string = localStorage.getItem('t_review');
      let t_review = JSON.parse(t_review_string);

      for (let i = 0; i < t_review.length; i++) {
        if (t_review[i].id === review.id) {
          t_review[i] = review;
        }
      }

      localStorage.setItem('t_review', JSON.stringify(t_review));
      commit('INSERT_REVIEWS', t_review)
    }
  },
  modules: {
  }
})