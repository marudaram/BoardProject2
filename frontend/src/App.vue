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
        <v-tab>
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
      const path = `/boardList`;
      if (this.$route.path !== path) this.$router.push(path);
      window.location.reload(true);
    },
    toMyPage() {
      const id = this.id;
      const path = `/myBoard/${id}`;
      if (this.$route.path !== path) this.$router.push(path);
      window.location.reload(true);
    },
    toWriting() {
      const path = `/boardWrite`;
      if (this.$router.path !== path) this.$router.push(path);
      window.location.reload(true);
    }
  },
  beforeCreate() {}
};
</script>

<style></style>
