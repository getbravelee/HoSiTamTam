<script setup>
import { onMounted, ref } from "vue";
import ListItem from "@/components/ListItem.vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";
import axios from "axios";

const tab = ref('apt');
const router = useRouter();
const goBack = () => {
  router.back();
};

const goToMap = () => {
  router.push({ name: 'map' });
};

// 즐겨찾기 목록 불러오기
const userStore = useUserStore();
const favoriteList = ref([]);

// 즐겨찾기 취소 함수
const toggleFavorite = async (item) => {
  try {
    const response = await axios.get(`/favorites/isFavorite/${item.aptId}`, {
      headers: {
        Authorization: `Bearer ${userStore.authToken}`,
      },
    });

    if (response.data) {
      // 즐겨찾기 취소
      await axios.delete(`/favorites/remove/${item.aptId}`, {
        headers: { Authorization: `Bearer ${userStore.authToken}` },
      });
      item.isFavorite = false;
    } else {
      // 즐겨찾기 추가
      await axios.post(`/favorites/add/${item.aptId}`, {}, {
        headers: { Authorization: `Bearer ${userStore.authToken}` },
      });
      item.isFavorite = true;
    }

    // 상태 변경 후, 바로 반영
    const index = favoriteList.value.findIndex(favItem => favItem.aptId === item.aptId);
    if (index !== -1) {
      favoriteList.value[index].isFavorite = item.isFavorite;
    }
  } catch (error) {
    console.error('즐겨찾기 상태 변경 실패:', error);
  }
};

const fetchFavorites = async () => {
  try {
    const response = await axios.get('/favorites', {
      headers: {
        Authorization: `Bearer ${userStore.authToken}`,
      },
    });
    favoriteList.value = response.data;
  } catch (error) {
    console.error('즐겨찾기 목록 조회 실패:', error);
  }
};

onMounted(() => {
  fetchFavorites();
});

// 상세 페이지로 이동하는 함수
const goToApartmentDetail = (item) => {
  router.push({
    name: 'aptDetail',
    params: {
      aptId: item.aptId,
      aptName: item.aptName,
      lat: item.lat,
      lng: item.lng,
    },
  });
};
</script>

<template>
  <div>
    <div class="top-bar">
      <font-awesome-icon :icon="['fas', 'arrow-left']" size="lg" @click="goBack()" />
      즐겨찾기 목록
      <font-awesome-icon :icon="['fas', 'xmark']" size="xl" @click="goToMap()" />
    </div>

    <div class="body-container">
      <div class="tab-option">
        <input id="tab-1" type="radio" v-model="tab" value="region" class="option" />
        <label for="tab-1" class="tab">지역</label>
        <input id="tab-2" type="radio" v-model="tab" value="apt" class="option" />
        <label for="tab-2" class="tab">아파트</label>
      </div>

      <div class="favorite-list">
        <ListItem v-for="(item) in favoriteList" :key="item.aptId" :item="item" @toggleFavorite="toggleFavorite" @click="goToApartmentDetail(item)" />
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