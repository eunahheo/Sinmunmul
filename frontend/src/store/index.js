import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate'
import {authToken} from './authToken.js'

export default createStore({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    token: AuthToken,
  },
  plugins: [
    createPersistedState({
      paths: ['token']
    })
  ]
})
