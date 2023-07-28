<template>
  <v-app id="inspire">
    <v-app-bar class="px-3" color="light-gray" flat density="compact">
      <v-avatar color="grey-darken-1" size="32"></v-avatar>
      <v-spacer></v-spacer>
      <!-- 메뉴바 -->
      <v-tabs centered color="grey-darken-2" class="header">
        <v-tab @click="toBoardList">
          <p>Board List</p>
        </v-tab>
        <v-tab @click="toMyPage">
          <p>My Page</p>
        </v-tab>
        <v-tab @click="toWriting">
          <p>Writing</p>
        </v-tab>
        <v-tab @click="logOut">
          <p>Logout</p>
        </v-tab>
      </v-tabs>

      <v-spacer></v-spacer>

      <v-avatar
        class="hidden-sm-and-down"
        color="grey-darken-1"
        size="32"
      ></v-avatar>
    </v-app-bar>
    <div class="miniBox"><span style="lineHeight:40px">Maru Daram</span></div>

    <v-main class="bg-grey-lighten-3">
      <v-container>
        <router-view />
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
export default {
  data: () => ({
    id: JSON.parse(sessionStorage.getItem("sessionId"))
  }),
  methods: {
    //메뉴바 클릭시 해당하는 화면으로 이동하는 메서드
    //세션 스토리지가 비어있을 경우 alert
    //현재 페이지가 게시판 리스트가 아니라면 해당 페이지로 이동 / 현재 페이지라면 reload
    toBoardList() {
      if (JSON.parse(sessionStorage.getItem("sessionId")) == null) {
        alert("로그인 먼저 해주세요.");
        this.$router.go(this.$router.currentRoute);
      } else {
        const path = `/boardList`;
        if (this.$route.path !== path) {
          this.$router.push(path);
        } else {
          window.location.reload(true);
        }
      }
    },
    toMyPage() {
      if (JSON.parse(sessionStorage.getItem("sessionId")) == null) {
        alert("로그인 먼저 해주세요.");
        this.$router.go(this.$router.currentRoute);
      } else {
        const id = this.id;
        const path = `/myBoard/${id}`;
        if (this.$route.path !== path) {
          this.$router.push(path);
        } else {
          window.location.reload(true);
        }
      }
    },
    toWriting() {
      if (JSON.parse(sessionStorage.getItem("sessionId")) == null) {
        alert("로그인 먼저 해주세요.");
        this.$router.go(this.$router.currentRoute);
      } else {
        const path = `/boardWrite`;
        if (this.$route.path !== path) {
          this.$router.push(path);
        }
      }
    },
    //저장돼있는 세션을 지우고 로그인 화면으로 이동
    logOut() {
      sessionStorage.clear();
      this.$router.push(`/`);
      window.location.reload(true);
    }
  }
};
</script>

<style>
.miniBox {
  height: 40px;
  background-color: rgb(6, 58, 81);
  color: white;
  text-align: center;
}
</style>
