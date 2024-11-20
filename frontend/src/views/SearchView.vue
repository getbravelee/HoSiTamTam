<script setup>
import {useRoute} from "vue-router";
import SearchBar from "@/components/SearchBar.vue";
import {ref, watch} from "vue";

const route = useRoute();
const query = ref(route.query.query || '');
const results = ref(history.state.results || '[]');

watch(() => route.query.query, (newQuery) => {
  query.value = newQuery || '';
});

const updateResults = (newResults) => {
  results.value = newResults;
};

const highlightText = (text, query) => {
  if (!query) return text; // query가 비어 있으면 원본 텍스트를 반환

  const regex = new RegExp(`(${query})`, 'gi'); // 대소문자 구분 없이 정규식으로 검색어 찾기
  return text.replace(regex, '<span style="color: #0a53be">$1</span>'); // 검색어 부분을 <span>으로 감쌈
}

const favorites = ref(results.value.map(() => false));
const toggleFavorite = (index) => {
  favorites.value[index] = !favorites.value[index];
};
</script>

<template>
  <div>
    <SearchBar :value="query" @results="updateResults" :backgroundColor="'#293A67'"/>
    <div class="result-bar">
      <font-awesome-icon :icon="['fas', 'arrow-left']" size="lg"/>
      {{ query }} 검색 결과
      <font-awesome-icon :icon="['fas', 'xmark']" size="xl"/>
    </div>

    <div class="body-container">
      <div class="result-list">
        <div v-for="(result, index) in results" :key="result" class="result-item">
          <span v-html="highlightText(result, query)"></span>
          <font-awesome-icon
              :icon="['fas', 'star']"
              size="lg"
              :style="{ color: favorites[index] ? '#FFD43B' : '#dad7d7' }"
              @click="toggleFavorite(index)"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.result-bar {
  position: fixed;
  top: 71px;
  left: 80px;
  width: 375px;
  padding: 10px 20px;
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
  top: 119px;
  left: 80px;
  padding: 20px 0px;
  width: 375px;
  z-index: 10;
  background-color: #EBF2FC;
  height: calc(100vh - 65px);
  overflow-y: auto;
}

.result-item {
  padding: 15px 19px;
  border-bottom: 1px #959595 solid;
  font-size: 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.result-item:hover {
  background-color: rgba(255, 255, 255, 0.77);
}
</style>