<script setup>
import {ref, watch} from "vue";
import {useRoute, useRouter} from "vue-router";
import SearchBar from "@/components/SearchBar.vue";

const route = useRoute();
const router = useRouter();
const region = route.params.region;
const query = ref(route.query.query || '');
const results = ref(history.state.results || '[]');

console.log(region);
console.log(results);

watch(() => route.query.query, (newQuery) => {
  query.value = newQuery || '';
});

const updateResults = (newResults) => {
  results.value = newResults;
};

const apartments = ref([
  { id: 1, name: `${region} 아파트 1` },
  { id: 2, name: `${region} 아파트 2` },
  { id: 3, name: `${region} 아파트 3` },
]);

const goToApartmentDetail = (apartmentId) => {
  router.push({ name: 'aptDetail', params: { region, apartmentId } });
};
</script>

<template>
  <div>
    <SearchBar :value="query" @results="updateResults" :backgroundColor="'#293A67'"/>
    <div class="result-bar">
      <font-awesome-icon :icon="['fas', 'arrow-left']" size="lg"/>
      {{ region }}
      <font-awesome-icon :icon="['fas', 'xmark']" size="xl"/>
    </div>

    <div class="body-container">
      <div class="result-list">
        <div v-for="apartment in apartments" :key="apartment.id" @click="goToApartmentDetail(apartment.id)">
          <span>{{ apartment.name }}</span>
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