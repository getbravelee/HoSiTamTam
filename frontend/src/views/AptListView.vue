<script setup>
import { onMounted, reactive, ref, watch} from "vue";
import {useRoute, useRouter} from "vue-router";
import SearchBar from "@/components/SearchBar.vue";
import ListItem from "@/components/ListItem.vue";
import {MultiSlider} from 'vue3-multi-slider';
import {usePlaceStore} from "@/stores/place";
import axios from "axios";
import {useUserStore} from "@/stores/user";
import {useAptListStore} from "@/stores/apt";
import {eventBus} from "@/eventBus/eventBus";

const route = useRoute();
const router = useRouter();
const region = route.params.region;
const query = ref(route.query.query || '');
const results = ref(history.state.results || '[]');
const dongName = ref(route.query.dongName || '');
const tab = ref('all');

watch(() => route.query.query, (newQuery) => {
  query.value = newQuery || '';
});

const updateResults = (newResults) => {
  results.value = newResults;
};

const price = reactive({
  'Category 1': {
    'value': 0,
    'background_color': '#E6E3E3',
    'text_color': '#ffffff'
  },
  'Category 2': {
    'value': 40,
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

// 상권 정보(카테고리) 검색에 필요한 것
const placesStore = usePlaceStore();
const categories = ref([
  {id: 'MT1', name: '마트', icon: 'mart'},
  {id: 'PM9', name: '약국', icon: 'pharmacy'},
  {id: 'SW8', name: '지하철', icon: 'subway'},
  {id: 'HP8', name: '병원', icon: 'hospital'},
  {id: 'PS3', name: '유치원', icon: 'store'},
  {id: 'SC4', name: '학교', icon: 'school'},
]);

const searchPlaces = (category, lat, lng) => {
  const ps = new window.kakao.maps.services.Places();
  const location = new window.kakao.maps.LatLng(lat, lng);
  ps.categorySearch(category, placesSearchCB, {
    location: location, // 경도, 위도를 기준으로 검색
    radius: 5000, // 검색 범위 (5km)
  });
};

const placesSearchCB = (data, status) => {
  if (status === window.kakao.maps.services.Status.OK) {
    placesStore.addPlaceData(data);  // Pinia에 상권 정보 저장
  } else if (status === window.kakao.maps.services.Status.ZERO_RESULT) {
    // 검색 결과가 없는 경우 처리
  } else if (status === window.kakao.maps.services.Status.ERROR) {
    // 에러 처리
  }
};

const goToApartmentDetail = (apartmentId, aptName, lat, lng) => {
  // 카테고리 별 정보 가져오기
  placesStore.resetPlaceData();

  for (let category of categories.value) {
    searchPlaces(category.id, lat, lng);
  }

  console.log(placesStore.placesData);

  // const data = results.value.map(item => item);
  // router.push({
  //   name: 'aptDetail',
  //   params: {query, region, aptId: apartmentId},
  //   state: {aptName: aptName, results: data}
  // });

  router.push({
    name: 'aptDetail',
    params: {query, region, aptId: apartmentId},
    state: {aptName: aptName}
  });
};

// 이전 페이지로 이동하기
const goBack = () => {
  router.back();
}

const goToMap = () => {
  router.push({name: 'map'});
};

// 아파트 리스트
const userStore = useUserStore();
const aptListStore = useAptListStore();
const aptList = ref([]);
const fetchAptList = async () => {
  try {
    const params = {
      areaMin: area["Category 1"].value * 100000,
      areaMax: (area["Category 2"].value * 100000) + (area["Category 1"].value * 100000),
      priceMin: (price["Category 1"].value * 100000),
      priceMax: (price["Category 2"].value * 100000) + (price["Category 1"].value * 100000),
    };

    if (tab.value !== 'all') {
      params.type = tab.value;
    }

    const response = await axios.get(`/region/${region}`, {params});
    const apartmentList = response.data;
    eventBus.emit('aptList', aptList.value); // 'aptList' 이벤트 발생
    aptListStore.setAptList(response.data);
    console.log(response.data);
    // 각 아파트의 즐겨찾기 상태 확인
    if (userStore.isLogin) {
      for (let apt of apartmentList) {
        const favoriteResponse = await axios.get(`/favorites/isFavorite/${apt.aptId}`, {
          headers: {Authorization: `Bearer ${userStore.authToken}`,}
        });
        console.log("좋아요 여부 응답" + favoriteResponse.data);
        apt.isFavorite = favoriteResponse.data;
      }
    }
    aptList.value = apartmentList;
  } catch (error) {
    console.error('아파트 목록 조회 실패:', error);
  }
};

// 즐겨찾기 상태 변경
const toggleFavorite = async (item) => {
  if (!userStore.isLogin) {
    alert('로그인이 필요합니다.');
    return;
  }

  try {
    const response = await axios.get(`/favorites/isFavorite/${item.aptId}`, {
      headers: {Authorization: `Bearer ${userStore.authToken}`,}
    });

    if (response.data) {
      await axios.delete(`/favorites/remove/${item.aptId}`, {
        headers: {Authorization: `Bearer ${userStore.authToken}`,}
      });
      item.isFavorite = false;
    } else {
      await axios.post(`/favorites/add/${item.aptId}`, {}, {
        headers: {Authorization: `Bearer ${userStore.authToken}`,}
      });
      item.isFavorite = true;
    }

    // await fetchAptList();
  } catch (error) {
    console.error('즐겨찾기 상태 변경 실패:', error);
  }
};

// 필터링 변경 시 실행
watch([() => price["Category 1"].value, () => price["Category 2"].value,
  () => area["Category 1"].value, () => area["Category 2"].value,
  () => tab.value], () => {
  fetchAptList();
});

onMounted(() => {
  fetchAptList();
});
</script>

<template>
  <div>
    <SearchBar :value="query" @results="updateResults" :backgroundColor="'#293A67'"/>
    <div class="result-bar">
      <font-awesome-icon :icon="['fas', 'arrow-left']" size="lg" @click="goBack()"/>
      {{ dongName }}
      <font-awesome-icon :icon="['fas', 'xmark']" size="xl" @click="goToMap()"/>
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
            <div class="slider-values">{{ price["Category 1"].value }} ~
              {{ price["Category 2"].value + price["Category 1"].value }}억
            </div>
          </div>
          <MultiSlider
              v-model="price"
              :min="0"
              :max="40"
              :step="1"
              unit="억"
              :show-inputs="false"
              :ticks="[0, 10, 20, 30, 40]"
          ></MultiSlider>
        </div>
        <div class="area-filter">
          <div class="slider-info">
            <div class="slider-label">평형</div>
            <div class="slider-values">{{ area["Category 1"].value }} ~
              {{ area["Category 2"].value + area["Category 1"].value }}평
            </div>
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
        <hr/>
      </div>

      <div class="result-list">
        <ListItem v-for="(item) in aptList" :key="item.aptId" :item="item"
                  @click="goToApartmentDetail(item.aptId, item.aptName, item.lat, item.lng)"
                  @toggleFavorite="toggleFavorite"/>
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
  height: calc(100vh - 118px);
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
  box-shadow: inset 0 4px 6px rgba(0, 0, 0, 0.3);
}

.price-filter, .area-filter {
  text-align: left;
  display: block;
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
  margin: 10px 0 !important;
}
</style>