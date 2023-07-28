<template>
  <v-container>
    <v-col class="wrapBox">
      <!-- 제목 -->
      <input
        type="text"
        placeholder="제목"
        class="titleArea"
        v-model="boardDetailData.title"
      />
      <!-- 에디터 -->
      <div class="example">
        <quill-editor
          class="editor"
          style="height:300px;"
          ref="myTextEditor"
          :disabled="false"
          :options="editorOption"
          v-model="boardDetailData.content"
        />
      </div>
    </v-col>
    <!-- 버튼 -->
    <v-col style="marginBottom:150px">
      <v-btn
        variant="tonal"
        @click="toBoardList"
        style="backgroundColor:rgb(6, 58, 81); color:white"
      >
        목록으로 </v-btn
      >&nbsp;
      <v-btn
        variant="tonal"
        @click="onSubmit"
        style="backgroundColor:rgb(6, 58, 81); color:white"
      >
        글 등록
      </v-btn>
    </v-col>
  </v-container>
</template>

<script>
import hljs from "highlight.js";
import debounce from "lodash/debounce";
import { quillEditor } from "vue-quill-editor";

// highlight.js style
import "highlight.js/styles/tomorrow.css";

// import theme style
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
// import { reactive } from "vue";

export default {
  name: "quill-example-snow",
  title: "Theme: snow",
  components: {
    quillEditor
  },
  data() {
    return {
      editorOption: {
        placeholder: "place holder test",
        modules: {
          // 에디터 툴바
          toolbar: [
            ["bold", "italic", "underline", "strike"], // <strong>, <em>, <u>, <s>
            ["blockquote", "code-block"], // <blockquote>, <pre class="ql-syntax" spellcheck="false">
            [{ header: 1 }, { header: 2 }], // <h1>, <h2>
            [{ list: "ordered" }, { list: "bullet" }],
            [{ script: "sub" }, { script: "super" }], // <sub>, <sup>
            [{ indent: "-1" }, { indent: "+1" }], // class제어
            [{ direction: "rtl" }], //class 제어
            [{ size: ["small", false, "large", "huge"] }], //class 제어 - html로 되도록 확인
            [{ header: [1, 2, 3, 4, 5, 6, false] }], // <h1>, <h2>, <h3>, <h4>, <h5>, <h6>, normal
            [{ font: [] }], // 글꼴 class로 제어
            [{ color: [] }, { background: [] }], //style="color: rgb(230, 0, 0);", style="background-color: rgb(230, 0, 0);"
            [{ align: [] }], // class
            // ["clean"],
            ["link", "image", "video"]
          ],
          syntax: {
            highlight: text => hljs.highlightAuto(text).value
          }
        }
      },
      boardDetailData: {
        title: "",
        content: "",
        id: ""
      }
    };
  },
  computed: {
    editor() {
      return this.$refs.myTextEditor.quill;
    },
    contentCode() {
      return hljs.highlightAuto(this.content).value;
    }
  },
  methods: {
    // 글 등록 메서드
    async onSubmit() {
      if (
        this.boardDetailData.title == "" ||
        this.boardDetailData.content == ""
      ) {
        alert("제목과 내용은 필수입니다.");
      } else {
        await this.$axios
          .post("/board/save", {
            title: this.boardDetailData.title,
            content: this.boardDetailData.content,
            id: JSON.parse(sessionStorage.getItem("sessionId"))
          })
          .then(res => {
            if (res.status === 200) {
              this.$router.push({ path: "/boardList" });
            }
          })
          .catch(error => {
            console.log(error);
          });
      }
    },
    // 게시판 화면으로 이동하는 메서드
    toBoardList() {
      this.$router.push({ path: "/boardList" });
    }
  }
};
</script>

<style scoped>
.titleArea {
  border: 2px solid lightgrey;
  height: 40px;
  font-size: 20px;
  width: 100%;
  margin-bottom: 10px;
}

button {
  margin-top: 100px;
}
</style>
