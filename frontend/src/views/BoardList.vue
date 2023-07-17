<template>
  <v-container>
    <v-col>
      <v-simple-table>
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
            v-for="(row, idx) in listData"
            :key="idx"
            @click="detail(row.boardNum)"
          >
            <td>{{ row.boardNum }}</td>
            <td>{{ row.title }}</td>
            <td>{{ row.writer }}</td>
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

      <v-pagination
        v-model="page"
        :length="10"
        circle
        @input="routePage({ page: page }), getBoardList()"
      ></v-pagination>
    </v-col>
  </v-container>
</template>

<script>
export default {
  name: "VuetifyTable",
  data() {
    return {
      listData: {
        title: "",
        content: "",
        regDate: "",
        hit: 0
      },

      //페이지 이동에 필요한 초기값
      page: 1,
      amount: 10,
      totalElements: 0,
      totalPages: 0
    };
  },
  computed: {
    param() {
      return this.$route.params.criteriaObj;
    }
  },
  watch: {
    param(criteriaObj) {
      const { page, amount } = JSON.parse(criteriaObj);
      this.page = page;
      this.amount = amount;
      this.getBoardList();
    }
  },
  mounted() {
    if (this.$route.name == "listParam") {
      const param = this.$route.params.criteriaObj;
      const { page, amount } = JSON.parse(param);
      this.page = page;
      this.amount = amount;
    }
    this.getBoardList();
  },
  methods: {
    toBoardWrite() {
      this.$router.push({ path: "/boardWrite" });
    },
    getBoardList() {
      this.$axios
        .get("/board/list", {
          params: {
            page: this.page > 0 ? this.page - 1 : this.page,
            amount: this.amount
          }
        })
        .then(res => {
          const { status, data } = res;
          if (status !== 200) alert("에러가 발생했습니다!");
          const {
            content: list,
            number: page,
            totalElements,
            size: amount,
            totalPages
          } = data;
          this.listData = list;
          this.page = page + 1;
          this.amount = amount;
          this.totalElements = totalElements;
          this.totalPages = totalPages;
        })
        .catch(error => {
          console.log(error);
        });
    },
    detail(idx) {
      this.$router.push({
        name: "boardDetail",
        params: {
          boardNum: idx
        }
      });
    },
    routePage({ page = 1, amount = this.amount }) {
      this.$router.push({
        name: "listParam",
        params: {
          criteriaObj: JSON.stringify({ page, amount })
        }
      });
    }
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

.wrapBox2 .writeBtn {
  width: 70px;
  float: right;
  margin-top: 10px;
  margin-right: 15px;
}

.wrapBox2 .searchArea {
  width: 250px;
  height: 90px;
  float: right;
  margin-right: 15px;
}

.wrapBox2 .inputBox {
  font-size: 15px;
  width: 250px;
  float: right;
}

.selectBox {
  border: 1px solid lightgray;
  float: right;
  text-align: center;
  margin-top: 10px;
}

.v-simple-table {
  min-width: 50px !important;
}
</style>
