<template>
  <v-container style="position:relative">
    <v-col style="textAlign:center">
      <div style="marginBottom:100px">
        <v-text-field
          hide-details
          prepend-icon="mdi-magnify"
          single-line
          style="float:left; marginRight:10px"
          v-model="searchKeyword"
        ></v-text-field>
        <v-btn variant="tonal" @click="getBoardList" style="float:left">
          검색
        </v-btn>
        <v-select
          :items="searchOption"
          outlined
          style="float:left"
          v-model="searchOptionSelected"
        ></v-select>
      </div>

      <v-btn
        variant="tonal"
        @click="toBoardWrite"
        style="display:block; position:absolute; top:2%; left: 90%"
      >
        글 등록
      </v-btn>

      <!-- 테이블 타이틀부분 -->
      <v-simple-table style="marginTop:30px; position:relative">
        <thead>
          <tr>
            <th class="text-left no" style="paddingLeft:2%">
              No
            </th>
            <th class="text-left subject" style="width:40%; paddingLeft:18%">
              제목
            </th>
            <th class="text-left" style="paddingLeft:60px">
              작성자
            </th>
            <th class="text-left" style=" paddingLeft:13%">
              날짜
            </th>
            <th class="text-left" style="paddingLeft: 2%">
              조회수
            </th>
          </tr>
        </thead>
        <!-- 테이블 내용부분 -->
        <tbody>
          <tr
            v-for="(row, idx) in listData"
            :key="idx"
            @click="detail(row.boardNum)"
          >
            <td>{{ row.boardNum }}</td>
            <td style="textAlign:center">{{ row.title }}</td>
            <td>{{ row.id }}</td>
            <td>{{ $moment(row.regDate).format("YYYY-MM-DD HH:MM") }}</td>
            <td>{{ row.hit }}</td>
          </tr>
        </tbody>
      </v-simple-table>
    </v-col>
    <v-col>
      <!-- 페이지네이션 부분 -->
      <v-pagination
        v-model="page"
        :length="totalPages < 10 ? totalPages : 10"
        circle
        @input="routePage({ page: page })"
      ></v-pagination>
    </v-col>
  </v-container>
</template>

<script>
export default {
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
      totalPages: 0,

      //검색 관련
      searchOption: ["SELECT", "TITLE", "CONTENT", "ID"], // 검색 옵션
      searchKeyword: "", //검색 키워드
      searchOptionSelected: "SELECT" //검색 옵션값 받아오기, 기본값은 본문으로 지정
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

      console.log(this.$route.params.criteriaObj);
    }
    this.getBoardList();
  },
  methods: {
    toBoardWrite() {
      this.$router.push({ path: "/boardWrite" });
    },
    async getBoardList() {
      const { status, data } = await this.$axios.get("/board/list", {
        params: {
          page: this.page > 0 ? this.page - 1 : this.page,
          amount: this.amount,
          keyword: this.searchKeyword,
          searchOption: this.searchOptionSelected
        }
      });

      if (status == 200) {
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

        // if (this.$route.path !== `/boardList`) {
        //   this.$router.push(`/boardList`);
        // }
      }
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
  position: relative;
}

.wrapBox2 .writeBtn {
  width: 70px;
  float: right;

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
</style>
