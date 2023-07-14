import SignIn from "@/views/SignIn.vue";
import BoardList from "@/views/BoardList.vue";
import BoardWrite from "@/views/BoardWrite.vue";
import Vue from "vue";
import VueRouter from "vue-router";
Vue.use(VueRouter);

const routes = [
  {
    //로그인 화면
    path: "/",
    name: "SignIn",
    component: SignIn
  },
  {
    //게시판 리스트
    path: "/boardList",
    name: "boardList",
    component: BoardList
  },
  {
    //게시글 쓰기
    path: "/boardWrite",
    name: "boardWrite",
    component: BoardWrite
  }
];

const router = new VueRouter({
  routes,
  mode: "hash"
});

export default router;
