<template>
  <v-app id="inspire">
    <v-app-bar class="px-3" color="light-gray" flat density="compact">
      <v-avatar color="grey-darken-1" size="32"></v-avatar>
      <v-spacer></v-spacer>

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
