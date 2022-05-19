import Vue from 'vue'
import Vuex from 'vuex'
import http from '@/util/http-common'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    products: [],
    productId: 0,
    productDetail: [],

    userId: '',
    user: {},
    userInfo: {},

    orderId: 0,
    orderDetail: []
  },
  getters: {
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
    }
  },
  mutations: {
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
    }
  },
  actions: {
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
    }
  },
  modules: {
  }
})