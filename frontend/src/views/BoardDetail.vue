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
    <div>
      <v-row cols="auto" sm="12">
        <div class="commentMiniBox">
          <p class="commentTitle">댓글</p>
        </div>
        <v-textarea
          filled
          auto-grow
          label="댓글 작성"
          rows="4"
          row-height="30"
          v-model="commentDetailData.comContent"
        ></v-textarea>
        <v-btn variant="tonal" @click="comSubmit" style="marginTop:100px">
          작성
        </v-btn>
      </v-row>

      <v-row
        cols="auto"
        sm="12"
        v-for="(row, index) in commentDetailData"
        :key="index"
      >
        <div class="commentMiniBox">
          <p class="commentTitle">{{ row.comWriter }}</p>
        </div>
        <div
          class="writtenComment"
          filled
          auto-grow
          label="댓글 작성"
          rows="4"
          row-height="30"
          v-html="row.comContent"
          disabled
        ></div>
      </v-row>
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
      },
      commentDetailData: {
        id: "",
        comWriter: "",
        comContent: "",
        regDate: "",
        boardNum: ""
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

    this.$axios
      .get(`/comment/comList/${boardNum}`)
      .then(res => {
        this.commentDetailData = res.data;
        console.log(res.data);
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
    },
    comSubmit() {
      let self = this;
      this.$axios
        .post("/comment/comSave", {
          comWriter: JSON.parse(sessionStorage.getItem("sessionId")),
          comContent: this.commentDetailData.comContent,
          id: this.boardDetailData.id,
          boardNum: this.boardDetailData.boardNum
        })
        .then(res => {
          const id = this.boardDetailData.id;
          if (res.status === 200) {
            self.$router.push({ path: `/boardDetail/${id}` });
          }
        })
        .catch(error => {
          console.log(error);
        });
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

.commentMiniBox {
  background-color: lightgray;
  height: 145px;
  width: 190px;
  float: left;
  border: 1px solid gray;
}

.commentMiniBox .commentTitle {
  text-align: center;
  margin-top: 30%;
}

.writtenComment {
  border: 1px solid lightgray;
  width: 78%;
  padding-top: 10px;
  padding-left: 10px;
}
</style>
