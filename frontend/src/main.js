import Vue from "vue";
import App from "./App.vue";
import vuetify from "./plugins/vuetify";
import router from "./router";
import axios from "axios";
import VueResource from "vue-resource";
import session from "vue-session";
import store from "@/assets/store";
import moment from "moment";
import Vuex from "vuex";

Vue.config.productionTip = false;
Vue.prototype.$session = session;
Vue.prototype.$store = store;
Vue.prototype.$axios = axios;
Vue.prototype.$moment = moment;
new Vue({
  vuetify,
  router,
  store,
  moment,
  Vuex,
  render: h => h(App)
}).$mount("#app");

Vue.use(VueResource);
Vue.use(store);
