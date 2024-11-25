<script setup>
import {useRoute, useRouter} from "vue-router";
import {ref, watch} from "vue";
import SearchBar from "@/components/SearchBar.vue";

const router = useRouter();
const route = useRoute();
const query = ref(route.query.query || '');
const results = ref(history.state.results || '[]');
const favorites = ref(results.value.map(() => false));

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

const toggleFavorite = (index) => {
  favorites.value[index] = !favorites.value[index];
};


// 지역을 클릭하면 아파트 리스트로 이동
const goToApartmentList = (region) => {
  const data = results.value.map(item => item);
  router.push({
    name: 'aptList',
    params: { region },
    query: { query: query.value },
    state: { results: data }
  });
};

// 이전 페이지로 이동하기
const goBack = () => {
  router.back();
}

const goToMap = () => {
  router.push({ name: 'map' });
};
</script>

<template>
  <div>
    <SearchBar :value="query" @results="updateResults" :backgroundColor="'#293A67'"/>
    <div class="result-bar">
      <font-awesome-icon :icon="['fas', 'arrow-left']" size="lg" @click="goBack()"/>
      {{ query }} 검색 결과
      <font-awesome-icon :icon="['fas', 'xmark']" size="xl" @click="goToMap()"/>
    </div>

    <div class="body-container">
      <div class="result-list">
        <div v-for="(result, index) in results" :key="result" class="result-item" @click="goToApartmentList(result)">
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
  top: 118px;
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
