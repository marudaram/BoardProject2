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
        v-show="sessionId === boardDetailData.id"
        style="backgroundColor:rgb(6, 58, 81); color:white"
      >
        삭제
      </v-btn>
      <v-btn
        variant="tonal"
        @click="modifyBoard"
        v-show="sessionId === boardDetailData.id"
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
      <Test v-for="(row, index) in commentDetailData" :key="index" :row="row" />

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
  mounted() {
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
    // 게시판 리스트로 이동하는 메서드
    toBoardList() {
      this.$router.push({ path: "/boardList" });
    },
    //게시글 수정 메서드
    modifyBoard() {
      this.$router.push({
        name: `boardModify`,
        params: {
          boardNum: this.$route.params.boardNum
        }
      });
    },
    //게시글 삭제 메서드
    async boardDelete() {
      const boardNum = this.$route.params.boardNum;

      if (confirm("삭제하시겠습니까?")) {
        const { status } = await this.$axios.delete(
          `/board/detail/${boardNum}`
        );
        if (status != 200) {
          console.error();
          return;
        } else {
          this.$router.push(`/boardList`);
        }
      }
    },
    //댓글 작성 메서드
    async comSubmit() {
      const { status } = await this.$axios.post("/comment/comSave", {
        comWriter: JSON.parse(sessionStorage.getItem("sessionId")),
        comContent: this.commentDetailData.comContent,
        id: this.boardDetailData.id,
        boardNum: this.boardDetailData.boardNum
      });
      if (status != 200) {
        console.error();
        return;
      } else {
        this.comLoad();
      }
    },
    //댓글 삭제 메서드
    async comDeleteBtn(comNum) {
      if (confirm("삭제하시겠습니까?")) {
        const { status } = await this.$axios.get(`/comment/comDelete`, {
          params: { comNum: comNum }
        });
        if (status != 200) {
          console.error();
          return;
        } else {
          this.commentDetailData = this.commentDetailData.filter(
            c => c.comNum != comNum
          );
        }
      }
    },
    //댓글 수정 메서드
    async comModifyBtn(comment) {
      const comNum = comment.comNum;
      const { status, data } = await this.$axios.put(
        `/comment/comModify/${comNum}`,
        {
          comWriter: comment.comWriter,
          comContent: comment.comContent
        }
      );
      if (status != 200) {
        console.error();
        return;
      } else {
        confirm("수정하시겠습니까?");
        const { boardNum, comContent, comNum, comWriter, id, regDate } = data;
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
    },
    //댓글 불러오는 메서드
    async comLoad() {
      const boardNum = this.boardDetailData.boardNum;
      const { status, data } = await this.$axios.get(
        `/comment/comList/${boardNum}`
      );
      if (status != 200) {
        console.error();
        return;
      } else {
        this.commentDetailData = data.map(d => {
          d.readMode = "true";
          return {
            ...d
          };
        });
      }
    },
    //댓글 읽기 모드일 때 수정 모드로 바꾸는 메서드
    wantToModifyBtn(row) {
      row.readMode = false;
    },
    //댓글 수정 모드일 때 읽기 모드로 바꾸는 메서드
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
  height: 138px;
  width: 190px;
  float: left;
  border: 1px solid white;
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
