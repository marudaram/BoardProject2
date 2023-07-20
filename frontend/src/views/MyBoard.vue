<template>
  <v-container>
    <v-col>
      <v-simple-table style="marginTop:30px">
        <thead>
          <tr>
            <th style="text-align: center; width: 50px">
              <input
                type="checkbox"
                v-model="allSelected"
                @click="selectAll($event.target.checked)"
              />
            </th>
            <th class="text-left no">
              No
            </th>
            <th class="text-left subject">
              제목
            </th>
            <th class="text-left">
              작성자
            </th>
            <th class="text-left">
              날짜
            </th>
            <th class="text-left">
              조회수
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(row, idx) in myListData" :key="idx">
            <td>
              <input
                type="checkbox"
                :id="'check_' + row.boardNum"
                :value="row.boardNum"
                v-model="row.selected"
                @change="selected($event)"
              />
            </td>
            <td @click="detail(row.boardNum)">{{ row.boardNum }}</td>
            <td @click="detail(row.boardNum)">{{ row.title }}</td>
            <td @click="detail(row.boardNum)">{{ row.id }}</td>
            <td @click="detail(row.boardNum)">{{ row.regDate }}</td>
            <td @click="detail(row.boardNum)">{{ row.hit }}</td>
          </tr>
        </tbody>
      </v-simple-table>
    </v-col>
    <v-col>
      <v-btn variant="tonal">
        목록으로
      </v-btn>
      <v-btn variant="tonal" @click="toBoardWrite">
        글 등록
      </v-btn>

      <v-pagination v-model="page" :length="10" circle></v-pagination>
    </v-col>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      myListData: {
        id: JSON.parse(sessionStorage.getItem("sessionId")),
        title: "",
        content: "",
        regDate: "",
        hit: 0
      },
      boardNums: [],
      allSelected: false
    };
  },
  beforeCreate() {
    const id = JSON.parse(sessionStorage.getItem("sessionId"));
    this.$axios
      .get(`/board/myBoard/${id}`)
      .then(res => {
        this.myListData = res.data;
        console.log(res.data);
      })
      .catch(error => {
        console.log(error);
      });
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
    toBoardWrite() {
      this.$router.push("/boardWrite");
    },
    selectAll(checked) {
      this.allSelected = checked;
      for (let i in this.myListData) {
        this.myListData[i].selected = this.allSelected;
      }
      console.log(this.boardNums[0]);
    },
    selected(e) {
      for (let i in this.myListData) {
        if (!this.myListData[i].selected) {
          this.allSelected = false;
          return;
        } else {
          this.allSelected = true;
        }
      }
    },
    getSelected() {
      let boardNums = [];
      for (let i in this.myListData) {
        if (this.myListData[i].selected) {
          boardNums.push(this.myListData[i].boardNum);
        }
      }
    }
  }
};
</script>
