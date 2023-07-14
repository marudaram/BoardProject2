<template>
  <v-app class="wrapBox">
    <v-toolbar-title>게시판 리스트</v-toolbar-title>
    <v-data-table
      :headers="headers"
      :items="contents"
      :items-per-page="5"
      class="elevation-1"
    ></v-data-table>
  </v-app>
</template>

<script>
export default {
  name: "VuetifyTable",
  data() {
    return {
      headers: [
        {
          align: "start",
          sortable: false,
          value: "name"
        },
        { text: "No", value: "mon" },
        { text: "제목", value: "tue" },
        { text: "작성자", value: "wed" },
        { text: "날짜", value: "tur" },
        { text: "조회수", value: "fri" }
      ],
      contents: [
        {
          name: "1조",
          mon: "94.7%",
          tue: "-",
          wed: "-",
          tur: "-",
          fri: "-",
          sat: "-",
          sun: "-",
          percent: "94.7%"
        },
        {
          name: "2조",
          mon: "-",
          tue: "92.3%",
          wed: "-",
          tur: "-",
          fri: "-",
          sat: "-",
          sun: "-",
          percent: "92.3%"
        },
        {
          name: "3조",
          mon: "91.9%",
          tue: "90.3%",
          wed: "-",
          tur: "-",
          fri: "-",
          sat: "-",
          sun: "-",
          percent: "91.1%"
        },
        {
          name: "4조",
          mon: "100.0%",
          tue: "96.4%",
          wed: "-",
          tur: "-",
          fri: "-",
          sat: "-",
          sun: "-",
          percent: "98.4%"
        },
        {
          name: "평균",
          mon: "95.9%",
          tue: "92.8%",
          wed: "-",
          tur: "-",
          fri: "-",
          sat: "-",
          sun: "-",
          percent: "94.3%"
        }
      ]
    };
  }
};
</script>

<style scoped>
.wrapBox {
  margin-top: 100px;
}
</style>
