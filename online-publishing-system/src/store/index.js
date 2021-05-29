import Vue from 'vue'
import Vuex from 'vuex'
//import createPersistedState from "vuex-persistedstate"

Vue.use(Vuex)

export default new Vuex.Store({
  state: {

  },
  mutations: {
    adminLogin() {
      sessionStorage.setItem("admin","login");
    },
    adminLogout() {
      sessionStorage.setItem("admin","logout");
    }
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
