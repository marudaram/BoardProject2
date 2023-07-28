<template>
  <div class="d-flex py-1">
    <div class="test1 d-flex align-center justify-center">
      {{ row.comWriter }}
    </div>
    <div
      class="d-flex align-center justify-center px-2"
      style="paddingTop:30px; paddingLeft:20px; white-space:pre; overflow:auto;"
      v-html="row.comContent"
    ></div>
  </div>
</template>

<style lang="scss" scoped>
.test1 {
  min-height: 138px;
  background-color: rgb(169, 212, 244);
  width: 190px;
}
</style>

<script>
export default {
  props: ["row"]
};
</script>
