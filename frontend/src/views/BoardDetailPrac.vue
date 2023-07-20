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
              '날짜: ' + this.boardDetailData.hit,
              '조회수: ' + this.boardDetailData.hit,
              '작성자: ' + this.boardDetailData.id
            ]"
            error
            disabled
            class="writingInfo"
          >
          </v-input>
          <v-card-text
            v-model="boardDetailData.content"
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
      <v-btn
        variant="tonal"
        @click="boardDelete"
        v-if="sessionId === boardDetailData.id"
      >
        삭제
      </v-btn>
      <v-btn
        variant="tonal"
        @click="modifyBoard"
        v-if="sessionId === boardDetailData.id"
      >
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

        <v-btn
          variant="tonal"
          @click="comSubmit"
          style="marginLeft:95%; marginBottom:20px;"
        >
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
        <!-- 댓글 작성하는 곳 -->
        <div
          class="writtenComment"
          v-html="row.comContent"
          v-if="readMode"
        ></div>
        <v-textarea
          label="댓글 수정"
          filled
          auto-grow
          rows="4"
          row-height="30"
          v-model="row.comContent"
          v-else
        >
        </v-textarea>
        <div class="btnWrapBox">
          <button v-if="!readMode" class="modiBtn1">
            취소
          </button>

          <button v-if="!readMode" class="modiBtn2">
            수정하기
          </button>
        </div>

        <div class="commentInfoBox">
          <v-input
            error-count="3"
            :error-messages="row.regDate"
            error
            disabled
            class="writingInfo"
          >
          </v-input>
          <div class="btnBox">
            <button @click="comDeleteBtn(row.comNum)" v-if="readMode">
              삭제
            </button>
            <button @click="wantToModifyBtn" v-if="readMode">
              수정
            </button>
          </div>
        </div>
      </v-row>
      <template> </template>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      sessionId: JSON.parse(sessionStorage.getItem("sessionId")),
      boardDetailData: {
        boardNum: "",
        title: "",
        content: "",
        id: JSON.parse(sessionStorage.getItem("sessionId")),
        regDate: "",
        hit: ""
      },
      commentDetailData: [],
      readMode: true
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
        this.commentDetailData = res.data.map(d => {
          console.log(" ==>> ", d.comWriter);
          return {
            ...d,
            showModal: false
          };
        });
        console.log(res.data, this.commentDetailData);
        console.log("댓작성자" + this.commentDetailData[0].comWriter);
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
            console.log(res.data);
          })
          .catch(error => {
            console.log(error);
          });
      }
    },
    comSubmit() {
      this.$axios
        .post("/comment/comSave", {
          comWriter: JSON.parse(sessionStorage.getItem("sessionId")),
          comContent: this.commentDetailData.comContent,
          id: this.boardDetailData.id,
          boardNum: this.boardDetailData.boardNum
        })
        .then(res => {
          if (res.status === 200) {
            this.comLoad();
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    comDeleteBtn(comNum) {
      console.log("컴넘은?" + comNum);

      if (confirm("삭제하시겠습니까?")) {
        this.$axios
          .get(`/comment/comDelete`, {
            params: { comNum: comNum }
          })
          .then(res => {
            // self.$router.go(self.$router.currentRoute);
            console.log(res);
            this.commentDetailData = this.commentDetailData.filter(
              c => c.comNum != comNum
            );
          })
          .catch(error => {
            console.log(error);
          });
      }
    },
    comModifyBtn(comment) {
      const comNum = comment.comNum;

      this.$axios
        .put(`/comment/comModify/${comNum}`, {
          comWriter: comment.comWriter,
          comContent: comment.comContent
        })
        .then(res => {
          const { status, data } = res;
          if (status !== 200) {
            alert("에러가 발생했습니다");
          }
          const { boardNum, comContent, comNum, comWriter, id, regDate } = data;
          comment.boardNum = boardNum;
          comment.comContent = comContent;
          comment.comNum = comNum;
          comment.comWriter = comWriter;
          comment.id = id;
          comment.regDate = regDate;
          comment.showModal = false;
        });
    },
    comLoad() {
      const boardNum = this.boardDetailData.boardNum;
      this.$axios
        .get(`/comment/comList/${boardNum}`)
        .then(res => {
          this.commentDetailData = res.data.map(d => ({
            ...d
          }));
          console.log(res.data);
          console.log("댓작성자" + this.commentDetailData[0].comWriter);
        })
        .catch(error => {
          console.log(error);
        });
    },
    wantToModifyBtn() {
      this.readMode = false;
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
  width: 83.9%;
}

.commentInfoBox {
  position: absolute;
  right: 11%;
}

.commentInfoBox .btnBox {
  margin-top: 100px;
}

.commentInfoBox button {
  font-size: 10px;
  color: gray;
}

.btnWrapBox {
  position: relative;
  width: 150px;
}

.modiBtn1 {
  position: absolute;
  top: 30%;
  right: 100px;
  font-size: 10px;
  color: gray;
}

.modiBtn2 {
  position: absolute;
  top: 30%;
  right: 150px;
  font-size: 10px;
  color: gray;
}
</style>
