<template>
  <v-container style="position:relative; ">
    <v-col style="textAlign:center">
      <div style="marginBottom:100px">
        <v-text-field
          hide-details
          prepend-icon="mdi-magnify"
          single-line
          style="float:left; marginRight:10px"
          v-model="searchKeyword"
          @keydown.enter="getBoardList"
        ></v-text-field>
        <v-btn
          variant="tonal"
          @click="getBoardList"
          style="float:left; backgroundColor:rgb(6, 58, 81); color:white;"
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
          class="writeBtn"
          variant="tonal"
          @click="toBoardWrite"
          style="backgroundColor:rgb(6, 58, 81); color:white"
        >
          글 등록
        </v-btn>
      </div>
      <!-- 로딩화면 -->
      <LoadingSpinner v-if="isLoading"></LoadingSpinner>
      <!-- 테이블 타이틀부분 -->
      <v-simple-table v-else style="marginTop:30px; position:relative">
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
        <!-- 테이블 내용부분 -->
        <tbody>
          <tr
            v-for="(row, idx) in listData"
            :key="idx"
            @click="detail(row.boardNum)"
          >
            <td style="cursor:pointer">{{ row.boardNum }}</td>
            <td style="textAlign:center; cursor:pointer">
              {{ row.title
              }}<span
                v-if="row.comCount != 0"
                style="fontWeight:bold; color:gray"
              >
                ( {{ row.comCount }} )</span
              >
            </td>
            <td style="cursor:pointer">{{ row.id }}</td>
            <td style="cursor:pointer">
              {{ $moment(row.regDate).format("YYYY-MM-DD HH:MM") }}
            </td>
            <td style="cursor:pointer">{{ row.hit }}</td>
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
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
export default {
  components: {
    LoadingSpinner
  },
  data() {
    return {
      listData: {
        boardNum: "",
        title: "",
        content: "",
        regDate: "",
        hit: 0,
        comCount: ""
      },
      isLoading: false,
      comTotal: "",
      //페이지 이동에 필요한 초기값
      page: 1,
      amount: 10,
      totalElements: 0,
      totalPages: 0,

      //검색 관련
      searchOption: ["CONTENT", "TITLE", "ID"], // 검색 옵션
      searchKeyword: "", //검색 키워드
      searchOptionSelected: "CONTENT" //검색 옵션값 받아오기, 기본값은 본문으로 지정
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
    //글쓰기 페이지로 이동하는 메서드
    toBoardWrite() {
      this.$router.push({ path: "/boardWrite" });
    },
    //게시글 리스트 가져오는 메서드
    async getBoardList() {
      this.isLoading = true;
      const { status, data } = await this.$axios.get("/board/list", {
        params: {
          page: this.page > 0 ? this.page - 1 : this.page,
          amount: this.amount,
          keyword: this.searchKeyword,
          searchOption: this.searchOptionSelected
        }
      });
      if (status != 200) {
        console.error();
        return;
      } else {
        this.isLoading = false;
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
      }
    },
    //게시글 상세페이지 이동 메서드
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
  border: 1px solid lightgrey;
  float: right;
  text-align: center;
  margin-top: 10px;
}
</style>
