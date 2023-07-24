<template>
  <div>
    <!-- 제목부분 -->
    <div class="titleArea">
      <v-card>
        <v-responsive :aspect-ratio="18 / 1">
          <v-card-text
            style="fontSize: 25px; lineHeight: 30px; backgroundColor:rgb(169, 212, 244); color:white"
          >
            {{ boardDetailData.title }}
          </v-card-text>
        </v-responsive>
      </v-card>
    </div>
    <!-- 작성정보 부분 -->
    <div>
      <v-card>
        <v-responsive :aspect-ratio="20 / 7">
          <v-input
            error-count="3"
            :error-messages="[
              '날짜: ' +
                $moment(this.boardDetailData.regDate).format(
                  'YYYY-MM-DD HH:MM'
                ),
              '조회수: ' + this.boardDetailData.hit,
              '작성자: ' + this.boardDetailData.id
            ]"
            error
            disabled
            class="writingInfo"
          >
          </v-input>
          <!-- 내용부분 -->
          <v-card-text
            v-html="boardDetailData.content"
            style="marginTop:100px;"
          ></v-card-text>
        </v-responsive>
      </v-card>
    </div>
    <!-- 글 관련 버튼 -->
    <div class="btnBox">
      <v-btn
        variant="tonal"
        @click="toBoardList"
        style="backgroundColor:rgb(6, 58, 81); color:white"
      >
        목록으로
      </v-btn>
      <v-btn
        variant="tonal"
        @click="boardDelete"
        v-if="sessionId === boardDetailData.id"
        style="backgroundColor:rgb(6, 58, 81); color:white"
      >
        삭제
      </v-btn>
      <v-btn
        variant="tonal"
        @click="modifyBoard"
        v-if="sessionId === boardDetailData.id"
        style="backgroundColor:rgb(6, 58, 81); color:white"
      >
        수정
      </v-btn>
    </div>
    <!-- 댓글 작성하는 부분 -->
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

        <!-- 댓글 작성 버튼 -->
        <v-btn
          variant="tonal"
          @click="comSubmit"
          style="marginLeft:95%; marginBottom:20px; backgroundColor:rgb(6, 58, 81); color:white"
        >
          작성
        </v-btn>
      </v-row>

      <!-- 쓴 댓글 나오는 부분 -->
      <v-row
        id="readMode"
        cols="auto"
        sm="12"
        v-for="(row, index) in commentDetailData"
        :key="index"
      >
        <!-- 댓쓴이 -->
        <div class="commentMiniBox">
          <p class="commentTitle">
            {{ row.comWriter }}
          </p>
        </div>
        <!-- 댓글내용 -->
        <div
          class="writtenComment"
          id=""
          v-html="row.comContent"
          v-if="row.readMode"
        ></div>
        <!-- 수정버튼 누른 후 수정할 때 -->
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
        <!-- 댓글 읽기 모드일 때 -->
        <div class="commentInfoBox">
          <v-input
            error-count="3"
            :error-messages="$moment(row.regDate).format('YYYY-MM-DD HH:MM')"
            error
            disabled
            class="writingInfo"
            v-if="row.readMode"
          >
          </v-input>
          <!-- 수정하기 모드일 때 버튼 -->
          <div class="btnBox1">
            <button
              v-if="!row.readMode"
              class="modiBtn1"
              @click="cancelBtn(row)"
            >
              취소
            </button>

            <button
              v-if="!row.readMode"
              class="modiBtn2"
              @click="comModifyBtn(row)"
            >
              수정하기
            </button>
          </div>
          <!-- 읽기 모드일 때 버튼 -->
          <div class="btnBox2">
            <button @click="comDeleteBtn(row.comNum)" v-if="row.readMode">
              삭제
            </button>
            <button @click="wantToModifyBtn(row)" v-if="row.readMode">
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
      commentDetailData: []
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
          d.readMode = "true";
          return {
            ...d
          };
        });
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
      if (confirm("삭제하시겠습니까?")) {
        this.$axios
          .get(`/comment/comDelete`, {
            params: { comNum: comNum }
          })
          .then(res => {
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

          if (status == 200) {
            confirm("수정하시겠습니까?");
            const {
              boardNum,
              comContent,
              comNum,
              comWriter,
              id,
              regDate
            } = data;
            console.log("데이터:" + data);
            comment.boardNum = boardNum;
            comment.comContent = comContent;
            comment.comNum = comNum;
            comment.comWriter = comWriter;
            comment.id = id;
            comment.regDate = regDate;
            comment.showModal = false;
            comment.readMode = true;
          }
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
        })
        .catch(error => {
          console.log(error);
        });
    },
    wantToModifyBtn(row) {
      row.readMode = false;
    },
    cancelBtn(row) {
      row.readMode = true;
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
  background-color: rgb(169, 212, 244);
  height: 145px;
  width: 190px;
  float: left;
  border: 1px solid rgb(169, 212, 244);
}

.commentMiniBox .commentTitle {
  text-align: center;
  margin-top: 30%;
  color: white;
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
  margin-right: 30px;
}
</style>
