import Vue from "vue";
import App from "./App.vue";
import vuetify from "./plugins/vuetify";
import router from "./router";
import axios from "axios";
import VueResource from "vue-resource";
import session from "vue-session";
import store from "@/assets/store";

Vue.config.productionTip = false;
Vue.prototype.$session = session;
Vue.prototype.$store = store;
Vue.prototype.$axios = axios;
new Vue({
  vuetify,
  router,
  store,
  render: h => h(App)
}).$mount("#app");

Vue.use(VueResource);
Vue.use(store);
// App.config.globalProperties.$axios = axios;
