<script setup>
import {onMounted, ref} from "vue";
import ListItem from "@/components/ListItem.vue";
import {useRouter} from "vue-router";
import {useUserStore} from "@/stores/user";
import axios from "axios";

const tab = ref('apt');

// 이전 페이지로 이동하기
const router = useRouter();
const goBack = () => {
  router.back();
}

const goToMap = () => {
  router.push({ name: 'map' });
};

// 즐겨찾기 목록 불러오기
const userStore = useUserStore();
const favoriteList = ref([]);


// 즐겨찾기 취소 함수
const toggleFavorite = async (item) => {
  try {
    // 즐겨찾기 취소 API 호출
    const response = await axios.post(`/favorites/remove/${item.aptId}`, {}, {
      headers: {
        Authorization: `Bearer ${userStore.authToken}`,
      },
    });

    // API 응답 성공 시, 리스트에서 해당 아이템 제거
    if (response.status === 200) {
      favoriteList.value = favoriteList.value.filter(favItem => favItem.aptId !== item.aptId);
      console.log('즐겨찾기 취소 완료');
    }
  } catch (error) {
    console.error('즐겨찾기 취소 실패:', error);
  }
};


const fetchFavorites = async () => {
  try {
    const response = await axios.get('/favorites', {
      headers: {
        // "Content-Type": "application/json",
        Authorization: `Bearer ${userStore.authToken}`,
      },
    });
    console.log(response.data);
    favoriteList.value = response.data;
  } catch (error) {
    console.error('즐겨찾기 목록 조회 실패:', error);
  }
};

onMounted(() => {
  fetchFavorites();
});
</script>

<template>
  <div>
    <div class="top-bar">
      <font-awesome-icon :icon="['fas', 'arrow-left']" size="lg" @click="goBack()"/>
      즐겨찾기 목록
      <font-awesome-icon :icon="['fas', 'xmark']" size="xl" @click="goToMap()"/>
    </div>

    <div class="body-container">
      <div class="tab-option">
        <input id="tab-1" type="radio" v-model="tab" value="region" class="option"/>
        <label for="tab-1" class="tab">지역</label>
        <input id="tab-2" type="radio" v-model="tab" value="apt" class="option"/>
        <label for="tab-2" class="tab">아파트</label>
      </div>
      <div class="favorite-list">
        <ListItem v-for="(item) in favoriteList" :key="item.aptId" :item="item" @toggle-favorite="toggleFavorite"/>
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