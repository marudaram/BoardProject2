import SignIn from "@/views/SignIn.vue";
import BoardList from "@/views/BoardList.vue";
import BoardWrite from "@/views/BoardWrite.vue";
import BoardDetail from "@/views/BoardDetail.vue";
import BoardModify from "@/views/BoardModify.vue";
import MyBoard from "@/views/MyBoard.vue";
import BoardDetailPrac from "@/views/BoardDetailPrac.vue";
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
    component: BoardList,
    children: [
      {
        //게시판 리스트 화면
        path: "/boardList/:criteriaObj",
        name: "listParam",
        component: BoardList
      }
    ]
  },
  {
    //게시글 쓰기
    path: "/boardWrite",
    name: "boardWrite",
    component: BoardWrite
  },
  {
    //게시글 상세보기
    path: "/boardDetail/:boardNum",
    name: "boardDetail",
    component: BoardDetail
  },
  {
    //게시글 수정하기
    path: "/boardModify/:boardNum",
    name: "boardModify",
    component: BoardModify
  },
  {
    //내가 쓴 게시글 보기
    path: "/myBoard/:id",
    name: "myBoard",
    component: MyBoard,
    children: [
      {
        path: "/myBoard/:obj",
        name: "myListParam",
        component: MyBoard
      }
    ]
  },
  {
    //댓글 연습용
    path: "/boardDetailPrac/:boardNum",
    name: "boardDetailPrac",
    component: BoardDetailPrac
  }
];

const router = new VueRouter({
  routes,
  mode: "hash"
});

export default router;
