<template>
  <div>
    <div class="titleArea">
      <v-card>
        <v-responsive :aspect-ratio="18 / 1">
          <v-card-text style="fontSize: 25px; lineHeight: 30px">
            {{ boardDetailData.title }}
          </v-card-text>
        </v-responsive>
      </v-card>
    </div>
    <div>
      <v-card>
        <v-responsive :aspect-ratio="20 / 7">
          <v-input
            error-count="3"
            :error-messages="[
              '날짜: ' + this.boardDetailData.regDate,
              '조회수: ' + this.boardDetailData.hit,
              '작성자: ' + this.boardDetailData.id
            ]"
            error
            disabled
            class="writingInfo"
          >
          </v-input>
          <v-card-text
            v-html="boardDetailData.content"
            style="marginTop:100px;"
          ></v-card-text>
        </v-responsive>
      </v-card>
    </div>
    <div>
      <v-card>
        <v-responsive :aspect-ratio="10 / 1">
          <v-card-text> </v-card-text>
        </v-responsive>
      </v-card>
    </div>
    <div class="btnBox">
      <v-btn variant="tonal" @click="toBoardList"> 목록으로 </v-btn>
      <v-btn variant="tonal" @click="boardDelete"> 삭제 </v-btn>
      <v-btn variant="tonal" @click="modifyBoard">
        수정
      </v-btn>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      boardDetailData: {
        boardNum: "",
        title: "",
        content: "",
        id: JSON.parse(sessionStorage.getItem("sessionId")),
        regDate: "",
        hit: ""
      }
    };
  },
  beforeCreate() {
    console.log(JSON.parse(sessionStorage.getItem("sessionId")));
    const boardNum = this.$route.params.boardNum;
    this.$axios
      .get(`board/detail/${boardNum}`)
      .then(res => {
        const { status, data } = res;
        if (status !== 200) alert("에러가 발생했습니다.");
        this.boardDetailData = data;
      })
      .catch(error => {
        console.log(error);
      });
  },
  methods: {
    toBoardList() {
      this.$router.push({ path: "/boardList" });
    },
    modifyBoard() {
      this.$router.push({
        name: `boardModify`,
        params: {
          boardNum: this.$route.params.boardNum
        }
      });
    },
    boardDelete() {
      const boardNum = this.$route.params.boardNum;
      if (confirm("삭제하시겠습니까?")) {
        this.$axios
          .post(`board/detail/${boardNum}`, {
            boardNum: this.$route.params.boardNum
          })
          .then(res => {
            this.$router.push(`/boardList`);
          })
          .catch(error => {
            console.log(error);
          });
      }
    }
  }
};
</script>

<style>
.titleArea {
  margin-top: 70px;
}

button {
  margin-top: 20px;
  float: right;
  margin-right: 10px;
}

.btnBox {
  margin-bottom: 100px;
}

.writingInfo {
  float: right;
  margin-right: 30px;
  margin-top: 10px;
}
</style>
