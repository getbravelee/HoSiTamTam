<template>
  <div>
    <SearchBar :value="query" @results="updateResults" :backgroundColor="'#293A67'" />
    <div class="result-list">
      <ListItem
          v-for="item in aptList"
          :key="item.aptId"
          :item="item"
          @toggleFavorite="toggleFavorite"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from "vue";
import axios from "axios";
import ListItem from "@/components/ListItem.vue";
import { useUserStore } from "@/stores/user";

const userStore = useUserStore();
const aptList = ref([]);

const fetchAptList = async () => {
  try {
    const response = await axios.get(`/region/${region}`, {
      params: {
        query: query.value
      }
    });
    aptList.value = response.data;

    // 로그인한 상태라면 즐겨찾기 상태를 반영
    if (userStore.isLogin) {
      for (let apt of aptList.value) {
        const favoriteResponse = await axios.get(`/favorites/isFavorite/${apt.aptId}`, {
          headers: { Authorization: `Bearer ${userStore.authToken}` }
        });
        apt.isFavorite = favoriteResponse.data;
      }
    }
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
      headers: { Authorization: `Bearer ${userStore.authToken}` }
    });

    if (response.data) {
      await axios.delete(`/favorites/remove/${item.aptId}`, {
        headers: { Authorization: `Bearer ${userStore.authToken}` }
      });
      item.isFavorite = false; // 상태 변경
    } else {
      await axios.post(`/favorites/add/${item.aptId}`, {}, {
        headers: { Authorization: `Bearer ${userStore.authToken}` }
      });
      item.isFavorite = true; // 상태 변경
    }
  } catch (error) {
    console.error('즐겨찾기 상태 변경 실패:', error);
  }
};

watch([() => query.value], fetchAptList);

onMounted(() => {
  fetchAptList();
});
</script>

<style scoped>
/* 스타일 추가 */
table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 10px;
  text-align: left;
}

tr:hover {
  background-color: #f2f2f2;
}
</style>
