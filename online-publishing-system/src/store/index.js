import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate"

Vue.use(Vuex)

export default new Vuex.Store({
  state:  {
    role: 7,
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
      state.role += 4;
    },
    setReviewer(state){
      state.role += 2;
    },
    setEditor(state){
      state.role += 1;
    },
    login(state){
      state.isLogin = true;
    },
    logout(state){
      state.isLogin = false;
      state.role = 0;
    },
  },
  actions: {
  },
  modules: {
  },
  plugins: [createPersistedState({
    storage: window.sessionStorage,
    reducer(data) {
      return {
      adminLogin: data.adminLogin
      }
    }
  })]
})
