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
    token: 'authToken',
  },
  plugins: [
    createPersistedState({
      paths: ['token']
    })
  ]
})
