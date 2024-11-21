<script setup>
import {reactive, ref, watch} from "vue";
import {useRoute, useRouter} from "vue-router";
import SearchBar from "@/components/SearchBar.vue";
import ListItem from "@/components/ListItem.vue";
import { MultiSlider } from 'vue3-multi-slider';

const route = useRoute();
const router = useRouter();
const region = route.params.region;
const query = ref(route.query.query || '');
const results = ref(history.state.results || '[]');

const tab = ref('all');

watch(() => route.query.query, (newQuery) => {
  query.value = newQuery || '';
});

const updateResults = (newResults) => {
  results.value = newResults;
};

const goToApartmentDetail = (apartmentId) => {
  router.push({ name: 'aptDetail', params: { region, apartmentId } });
};

const price = reactive({
  'Category 1': {
    'value': 0,
    'background_color': '#E6E3E3',
    'text_color': '#ffffff'
  },
  'Category 2': {
    'value': 30,
    'background_color': '#271f7a',
    'text_color': '#ffffff'
  },
})

const area = reactive({
  'Category 1': {
    'value': 0,
    'background_color': '#E6E3E3',
    'text_color': '#ffffff'
  },
  'Category 2': {
    'value': 80,
    'background_color': '#271f7a',
    'text_color': '#ffffff'
  },
})


console.log(price["Category 1"].value);
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
      <div class="filter-option">
        <div class="deal-type">
          <input id="tab-1" type="radio" v-model="tab" value="all" class="option"/>
          <label for="tab-1" class="tab">전체</label>
          <input id="tab-2" type="radio" v-model="tab" value="sale" class="option"/>
          <label for="tab-2" class="tab">매매</label>
          <input id="tab-3" type="radio" v-model="tab" value="rent" class="option"/>
          <label for="tab-3" class="tab">전세</label>
          <input id="tab-4" type="radio" v-model="tab" value="rentM" class="option"/>
          <label for="tab-4" class="tab">월세</label>
        </div>
        <div class="price-filter">
          <div class="slider-info">
            <div class="slider-label">가격</div>
            <div class="slider-values">{{ price["Category 1"].value }} ~ {{ price["Category 2"].value + price["Category 1"].value }}억</div>
          </div>
          <MultiSlider
              v-model="price"
              :min="0"
              :max="40"
              :step="1"
              unit="억"
              :show-inputs="false"
              :ticks="[0, 5, 10, 20, 30, 40]"
          ></MultiSlider>
        </div>
        <div class="area-filter">
          <div class="slider-info">
            <div class="slider-label">평형</div>
            <div class="slider-values">{{ area["Category 1"].value }} ~ {{ area["Category 2"].value + area["Category 1"].value }}평</div>
          </div>
          <MultiSlider
              v-model="area"
              :min="0"
              :max="80"
              :step="1"
              unit="평"
              :show-inputs="false"
              :ticks="[0, 20, 40, 60, 80]"
          ></MultiSlider>
        </div>
        <hr />
      </div>

      <div class="result-list">
        <ListItem @click="goToApartmentDetail(1)"/>
        <ListItem @click="goToApartmentDetail(2)"/>
        <ListItem @click="goToApartmentDetail(3)"/>
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
  padding: 10px 10px;
  width: 375px;
  z-index: 10;
  background-color: #EBF2FC;
  height: calc(100vh - 65px);
  overflow-y: auto;
}

input[type=radio] {
  display: none;
}

.deal-type {
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
  border-radius: 10px;
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
}

.price-filter, .area-filter {
  text-align: left;
  display: block; /* 부모 컨테이너를 블록 요소로 변경 */
  margin: 15px 0;
}

.slider-info {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}

.slider-label {
  font-size: 16px;
}

.slider-values {
  font-size: 14px;
  color: #284261;
}

input[type="range"] {
  width: 100%;
  margin: 10px 0;
}

::v-deep(.slider-value) {
  display: none !important;
}

::v-deep(.range-slider) {
  margin: 10px 0;
}
</style>