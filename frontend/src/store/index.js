import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate'
import {authToken} from './modules/authToken.js'

export default createStore({
  state: {
    userSeq: null
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    authToken: authToken
  },
  plugins: [
    createPersistedState({
      paths: ['authToken']
    })
  ]
})
