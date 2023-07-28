<template>
  <v-container>
    <v-col class="wrapBox">
      <!-- 제목 -->
      <input
        type="text"
        placeholder="제목"
        class="titleArea"
        v-model="boardChangeData.title"
      />
      <!-- 에디터 -->
      <div class="example">
        <quill-editor
          class="editor"
          style="height:300px;"
          ref="myTextEditor"
          :disabled="false"
          :value="content"
          :options="editorOption"
          v-model="boardChangeData.content"
        />
      </div>
    </v-col>

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
        @click="modifyBoard"
        style="backgroundColor:rgb(6, 58, 81); color:white"
      >
        수정
      </v-btn>
    </v-col>
  </v-container>
</template>

<script>
import hljs from "highlight.js";
import debounce from "lodash/debounce";
import { quillEditor } from "vue-quill-editor";
//css 커스터마이징
// import "src/assets/sass/overrides.sass";

// highlight.js style
import "highlight.js/styles/tomorrow.css";

// import theme style
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";

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
      boardChangeData: {
        boardNum: "",
        title: "",
        content: "",
        id: "",
        regDate: "",
        hit: ""
      },
      content: ""
    };
  },
  mounted() {
    const boardNum = this.$route.params.boardNum;
    this.$axios
      .get(`board/detail/${boardNum}`)
      .then(res => {
        this.boardChangeData = res.data;
        console.log(res.data);
      })
      .catch(error => {
        console.log(error);
      });
  },
  methods: {
    async modifyBoard() {
      const { status, data } = await this.$axios.put(
        `board/detail/${this.boardChangeData.boardNum}`,
        {
          title: this.boardChangeData.title,
          content: this.boardChangeData.content,
          id: this.boardChangeData.id
        }
      );
      if (status != 200) {
        console.error();
        return;
      } else {
        this.boardChangeData = data;
        this.$router.push({
          name: `boardDetail`,
          params: {
            boardNum: this.$route.params.boardNum
          }
        });
        if (
          this.$route.path !== `/boardDetail/${this.$route.params.boardNum}`
        ) {
          this.$router.push(`/boardDetail/${this.$route.params.boardNum}`);
        }
      }
    },
    toBoardList() {
      this.$router.push({ path: `/boardList` });
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
