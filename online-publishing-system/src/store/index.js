import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    state: {
      role: 'N',
      isLogin: false,
    },
  },
  mutations: {
    setWriter(state){
      state.role = 'W';
    },
    setReviewer(state){
      state.role = 'R';
    },
    setEditor(state){
      state.role = 'X';
    },
    login(state){
      state.isLogin = true;
    },
    logout(state){
      state.isLogin = false;
      state.role = 'N';
    },
  },
  actions: {
  },
  modules: {
  }
})
