import { Store } from "vuex";
import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    //전역으로 사용할 변수들을 여기서 정의(변수들의 집합소)
    id: "",
    password: ""
  },
  getters: {
    //state 변수들을 사용하는 getter 함수 정의
    getId(state) {
      return state.id;
    },
    getPassword(state) {
      return state.password;
    }
  },
  mutations: {
    //state 변수들을 새로 정의할 때 사용
    setId(state, id) {
      state.id = id;
    },
    setPassword(state, password) {
      state.password = password;
    }
  }
});
