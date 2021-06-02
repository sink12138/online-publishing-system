import Vue from 'vue'
import Vuex from 'vuex'
//import createPersistedState from "vuex-persistedstate"

Vue.use(Vuex)

export default new Vuex.Store({
  state:  {
    role: 'N',
    isLogin: false,
  },
  mutations: {
    adminLogin() {
      sessionStorage.setItem("admin","login");
    },
    adminLogout() {
      sessionStorage.setItem("admin","logout");
    },
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
  },
  /*plugins: [createPersistedState({
    storage: window.sessionStorage,
    reducer(data) {
      return {
      adminLogin: data.adminLogin
      }
    }
  })]*/
})
