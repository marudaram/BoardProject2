import Vue from "vue";
import App from "./App.vue";
import vuetify from "./plugins/vuetify";
import router from "./router";
import axios from "axios";
import VueResource from "vue-resource";

Vue.config.productionTip = false;
Vue.prototype.$axios = axios;
new Vue({
  vuetify,
  router,
  render: h => h(App)
}).$mount("#app");

Vue.use(VueResource);

// App.config.globalProperties.$axios = axios;
