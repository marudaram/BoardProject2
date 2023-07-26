<template>
  <v-card class="mx-auto" max-width="344">
    <v-card-text>
      <p style="fontSize:20px; color:black; marginBottom:40px">LogIn</p>

      <v-text-field label="ID" v-model="userInfo.id"> </v-text-field>
      <v-text-field
        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="[rules.required]"
        :type="show1 ? 'text' : 'password'"
        name="input-10-1"
        label="password"
        counter
        @click:append="show1 = !show1"
        v-model="userInfo.password"
      ></v-text-field>
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
      },
      show1: false,
      show2: true,
      show3: false,
      show4: false,
      rules: {
        required: value => !!value || "Required."
      }
    };
  },
  methods: {
    async logIn() {
      console.log("로그인버튼클릭");
      await this.$axios
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
          alert("로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.");
          window.location.reload(true);

          console.log(error);
        })
        .finally(() => console.log("로그인 시도"));
    }
  }
});
</script>
