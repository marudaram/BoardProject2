<template>
  <v-container>
    <v-col>
      <v-simple-table style="marginTop:30px">
        <thead>
          <tr>
            <th class="text-left no">
              No
            </th>
            <th class="text-left subject">
              제목
            </th>
            <th class="text-left">
              작성자
            </th>
            <th class="text-left">
              날짜
            </th>
            <th class="text-left">
              조회수
            </th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(row, idx) in myListData"
            :key="idx"
            @click="detail(row.boardNum)"
          >
            <td><input type="checkbox" readonly /></td>
            <td>{{ row.boardNum }}</td>
            <td>{{ row.title }}</td>
            <td>{{ row.id }}</td>
            <td>{{ row.regDate }}</td>
            <td>{{ row.hit }}</td>
          </tr>
        </tbody>
      </v-simple-table>
    </v-col>
    <v-col>
      <v-btn variant="tonal">
        목록으로
      </v-btn>
      <v-btn variant="tonal" @click="toBoardWrite">
        글 등록
      </v-btn>

      <!-- <v-pagination
        v-model="page"
        :length="10"
        circle
        @input="routePage({ page: page }), getBoardList()"
      ></v-pagination> -->
    </v-col>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      myListData: {
        id: JSON.parse(sessionStorage.getItem("sessionId")),
        title: "",
        content: "",
        regDate: "",
        hit: 0
      }
    };
  },
  beforeCreate() {
    const id = JSON.parse(sessionStorage.getItem("sessionId"));
    this.$axios
      .get(`/board/myBoard/${id}`)
      .then(res => {
        this.myListData = res.data;
        console.log(res.data);
      })
      .catch(error => {
        console.log(error);
      });
  },
  methods: {
    detail(idx) {
      this.$router.push({
        name: "boardDetail",
        params: {
          boardNum: idx
        }
      });
    }
  }
};
</script>
