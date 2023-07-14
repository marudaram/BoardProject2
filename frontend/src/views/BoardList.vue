<template>
  <v-app class="wrapBox">
    <v-btn elevation="2" small class="writeBtn">글쓰기</v-btn>
    <v-toolbar-title style="fontSize:40px; marginBottom:15px"
      >Board List</v-toolbar-title
    >

    <div class="searchArea">
      <v-toolbar style="height:50px">
        <input type="text" class="inputBox" />

        <v-btn icon class="hidden-xs-only">
          <v-icon>mdi-magnify</v-icon>
        </v-btn>
      </v-toolbar>
    </div>

    <select class="selectBox">
      <option>본문</option>
      <option>제목</option>
      <option>작성자</option>
    </select>

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
.header {
  height: 20px;
}
.wrapBox {
  margin-top: 100px;
  position: relative;
}

.writeBtn {
  width: 70px;
  position: absolute;
  top: -1%;
  left: 60%;
}

.searchArea {
  width: 300px;
  position: absolute;
  top: -3%;
  left: 68%;
}

.inputBox {
  font-size: 15px;
  width: 250px;
}

.selectBox {
  border: 1px solid lightgray;
  position: absolute;
  top: -1%;
  left: 95%;
  text-align: center;
}
</style>
