<template>
  <v-card class="mx-auto" style="max-width: 500px;">
    <v-form ref="form" v-model="form" class="pa-4 pt-6">
      <div>
        <p>SignUp</p>
      </div>
      <v-text-field
        v-model="user.id"
        :rules="[rules.idRule]"
        filled
        color="deep-purple"
        label="ID"
      ></v-text-field>
      <v-text-field
        v-model="user.password"
        :rules="[rules.passwordRule, rules.length(4)]"
        filled
        color="deep-purple"
        counter="4"
        label="Password"
        style="min-height: 96px"
        type="password"
      ></v-text-field>
      <v-text-field
        v-model="user.name"
        :rules="[rules.nameRule]"
        filled
        color="deep-purple"
        label="Name"
      ></v-text-field>
    </v-form>
    <v-divider></v-divider>
    <v-card-actions>
      <v-btn text @click="$refs.form.reset()">
        Clear
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn
        :disabled="!form"
        :loading="isLoading"
        class="white--text"
        color="deep-purple accent-4"
        depressed
        @click="join"
      >
        Submit
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  data() {
    return {
      dialog: false,
      user: {
        id: "",
        password: "",
        name: ""
      },
      form: false,
      isLoading: false,
      rules: {
        idRule: v => !!v || "ID를 입력해주세요.",
        length: len => v =>
          (v || "").length >= len ||
          `Invalid character length, required ${len}`,
        passwordRule: v =>
          !!(v || "").match(
            /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*(_|[^\w])).+$/
          ) || "비밀번호는 대문자, 소문자, 숫자를 포함해야 합니다.",
        nameRule: v => !!v || "이름을 입력해주세요."
      }
    };
  },

  methods: {
    async join() {
      console.log("join 실행");
      await this.$axios
        .post(`/user/join`, {
          id: this.user.id,
          password: this.user.password,
          name: this.user.name
        })
        .then(res => {
          this.$router.push(`/`);
          console.log(res.data);
          alert("회원가입이 성공적으로 되었습니다!");
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};
</script>
