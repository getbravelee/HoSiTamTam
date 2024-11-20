<script setup>
import {useRoute} from "vue-router";

const route = useRoute();
const query = route.query.query || '';
const results = history.state.results || '[]';
</script>

<template>
  <div class="body-container">
    <h4>검색 결과</h4>
    <p>검색어: {{ query }}</p>
    <ul>
      <li v-for="result in results" :key="result">{{ result }}</li>
    </ul>
  </div>
</template>

<style scoped>
.body-container {
  position: fixed;
  top: 71px;
  left: 80px;
  padding: 20px 19px;
  width: 375px;
  z-index: 10;
  background-color: #EBF2FC;
  height: calc(100vh - 65px);
  overflow-y: auto;
}
</style>