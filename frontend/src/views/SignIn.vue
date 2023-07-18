<template>
  <v-card class="mx-auto" max-width="344">
    <v-card-text>
      <p style="fontSize:20px; color:black; marginBottom:40px">LogIn</p>
      <div>ID</div>
      <v-text-field v-model="userInfo.id"> </v-text-field>
      <div>Password</div>
      <v-text-field v-model="userInfo.password"> </v-text-field>
    </v-card-text>
    <v-card-actions>
      <v-btn
        text
        color="deep-purple accent-4"
        style="marginLeft:250px"
        @click="logIn"
      >
        LOGIN
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import { defineComponent } from "vue";

export default defineComponent({
  name: "SignIn",
  data() {
    return {
      userInfo: {
        id: "",
        password: ""
      }
    };
  },
  methods: {
    logIn() {
      console.log("로그인버튼클릭");
      this.$axios
        .post(`/user/logIn`, {
          id: this.userInfo.id,
          password: this.userInfo.password
        })
        .then(res => {
          //sessionStorage에 id 추가
          sessionStorage.setItem(`sessionId`, JSON.stringify(this.userInfo.id));
          this.$store.commit(`setId`, sessionStorage.getItem(`sessionId`));
          this.$router.push(`/boardList`);
          console.log(res);
        })
        .catch(error => {
          console.log(error);
        })
        .finally(() => console.log("로그인 시도"));
    }
  }
});
</script>
