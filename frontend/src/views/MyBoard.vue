<template>
  <v-container>
    <v-col style="textAlign:center">
      <div style="height: 70px">
        <v-text-field
          hide-details
          prepend-icon="mdi-magnify"
          single-line
          style="float:left; marginRight:10px"
          v-model="searchKeyword"
        ></v-text-field>
        <v-btn
          variant="tonal"
          @click="getMyBoardList"
          style="float:left; backgroundColor:rgb(6, 58, 81); color:white"
        >
          검색
        </v-btn>
        <v-select
          :items="searchOption"
          outlined
          style="float:left"
          v-model="searchOptionSelected"
        ></v-select>
        <v-btn
          variant="tonal"
          @click="toBoardWrite"
          class="writeBtn"
          style="backgroundColor:rgb(6, 58, 81); color:white"
        >
          글 등록
        </v-btn>
      </div>
      <LoadingSpinner v-if="isLoading" style="marginRight:50%"></LoadingSpinner>
      <v-simple-table style="marginTop:30px" v-else>
        <thead style="backgroundColor:rgb(169, 212, 244)">
          <tr>
            <th class="text-left no" style="paddingLeft:3%">
              No
            </th>
            <th class="text-left subject" style="width:40%; paddingLeft:19%">
              제목
            </th>
            <th class="text-left" style="paddingLeft:8%">
              작성자
            </th>
            <th class="text-left" style=" paddingLeft:11%">
              날짜
            </th>
            <th class="text-left" style="paddingLeft: 3%">
              조회수
            </th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="(row, idx) in myListData" :key="idx">
            <td @click="detail(row.boardNum)">{{ row.boardNum }}</td>
            <td @click="detail(row.boardNum)">{{ row.title }}</td>
            <td @click="detail(row.boardNum)">{{ row.id }}</td>
            <td @click="detail(row.boardNum)">
              {{ $moment(row.regDate).format("YYYY-MM-DD HH:MM") }}
            </td>
            <td @click="detail(row.boardNum)">{{ row.hit }}</td>
          </tr>
        </tbody>
      </v-simple-table>
    </v-col>
    <v-col>
      <v-btn
        variant="tonal"
        style="backgroundColor:rgb(6, 58, 81); color:white"
      >
        목록으로
      </v-btn>
      <v-btn
        variant="tonal"
        @click="toBoardWrite"
        style="backgroundColor:rgb(6, 58, 81); color:white"
      >
        글 등록
      </v-btn>

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
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
export default {
  components: {
    LoadingSpinner
  },
  data() {
    return {
      myListData: {
        id: JSON.parse(sessionStorage.getItem("sessionId")),
        title: "",
        content: "",
        regDate: "",
        hit: 0
      },
      isLoading: false,

      //페이지 이동에 필요한 초기값
      page: 1,
      amount: 10,
      totalElements: 0,
      totalPages: 0,

      //검색 관련
      searchOption: ["CONTENT", "TITLE"], // 검색 옵션
      searchKeyword: "", //검색 키워드
      searchOptionSelected: "CONTENT" //검색 옵션값 받아오기, 기본값은 본문으로 지정
    };
  },
  computed: {
    param() {
      return this.$route.params.obj;
    }
  },
  watch: {
    param(obj) {
      const { page, amount } = JSON.parse(obj);
      this.page = page;
      this.amount = amount;

      this.getMyBoardList();
    }
  },
  mounted() {
    this.getMyBoardList();
  },
  methods: {
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
        name: "myListParam",
        params: {
          obj: JSON.stringify({ page, amount })
        }
      });
    },
    toBoardWrite() {
      this.$router.push("/boardWrite");
    },
    async getMyBoardList() {
      this.isLoading = true;
      const id = JSON.parse(sessionStorage.getItem("sessionId"));
      const { status, data } = await this.$axios.get(`/board/myBoard/${id}`, {
        params: {
          page: this.page > 0 ? this.page - 1 : this.page,
          amount: this.amount,
          keyword: this.searchKeyword,
          searchOption: this.searchOptionSelected
        }
      });

      if (status == 200) {
        this.isLoading = false;
        const {
          content: list,
          number: page,
          totalElements,
          size: amount,
          totalPages
        } = data;
        this.myListData = list;
        this.page = page + 1;
        this.amount = amount;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
      }
    }
  }
};
</script>

<style>
header {
  background-color: rgb(169, 212, 244);
}
</style>
