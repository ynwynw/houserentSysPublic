import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);
const store = new Vuex.Store({
  state: {
    token: localStorage.getItem('token') ? localStorage.getItem('token') : ''
  },
  mutations: {
    setToken (state,token) {
      state.token =token;
      localStorage.setItem("token",token.token);     //存储token
    },
    delToken (state) {
      state.token = '';
      localStorage.removeItem("token");    //删除token
    }
  }
});

export default store;
