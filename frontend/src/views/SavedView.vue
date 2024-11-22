<script setup>
import {ref} from "vue";
import ListItem from "@/components/ListItem.vue";

const tab = ref('apt');
</script>

<template>
  <div>
    <div class="top-bar">
      <font-awesome-icon :icon="['fas', 'arrow-left']" size="lg"/>
      즐겨찾기 목록
      <font-awesome-icon :icon="['fas', 'xmark']" size="xl"/>
    </div>

    <div class="body-container">
      <div class="tab-option">
        <input id="tab-1" type="radio" v-model="tab" value="region" class="option"/>
        <label for="tab-1" class="tab">지역</label>
        <input id="tab-2" type="radio" v-model="tab" value="apt" class="option"/>
        <label for="tab-2" class="tab">아파트</label>
      </div>
      <div class="favorite-list">
        <ListItem />
        <ListItem />
        <ListItem />
      </div>
    </div>
  </div>
</template>

<style scoped>
.top-bar {
  position: fixed;
  top: 0px;
  left: 80px;
  width: 375px;
  padding: 15px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #293A67;
  color: #FFFFFF;
  font-size: 18px;
  z-index: 10;
}

.body-container {
  position: fixed;
  top: 57px;
  left: 80px;
  padding: 20px 10px;
  width: 375px;
  z-index: 10;
  background-color: #EBF2FC;
  height: calc(100vh - 57px);
  overflow-y: auto;
}

input[type=radio] {
  display: none;
}

.tab-option {
  display: flex;
  flex-direction: row;
  margin-bottom: 10px;
}

.tab {
  font-size: 16px;
  width: 68px;
  padding: 2px 0;
  margin-right: 10px;
  color: #4E5E77;
  border-radius: 25px;
  border: 2px #4E5E77 solid;
  background-color: #FFFFFF;
  cursor: pointer;
}

.tab:hover {
  color: #fff;
  background-color: #4E5E77;
}

.option:checked + .tab {
  color: #fff;
  background-color: #4E5E77;
  box-shadow: inset 0 4px 6px rgba(0, 0, 0, 0.3);
}
</style>