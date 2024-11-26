<script setup>
import {defineProps, ref, defineEmits} from "vue";
// import axios from "axios";
// import {useUserStore} from "@/stores/user";

// const userStore = useUserStore();
// 부모로부터 전달받은 item
const props = defineProps({
  item: {
    type: Object,
    required: true
  }
});

const isHeartClicked = ref(props.item.isFavorite);

// const toggleHeartColor = async () => {
//   isHeartClicked.value = false;
//   try {
//     const response = await axios.post(`/remove/${props.item.aptId}`, {
//       headers: {
//         Authorization: `Bearer ${userStore.authToken}`,
//       }
//     });
//     console.log('즐겨찾기 취소 완료', response.data);
//   } catch (error) {
//     console.error('즐겨찾기 상태 변경 실패', error);
//     isHeartClicked.value = true;
//   }
// };


// 하트 클릭 시, 부모로 이벤트 전달
const emit = defineEmits({
  toggleFavorite: (item) => {
    if (item && item.aptId) return true; // 아이템이 유효한지 확인 (optional)
    return false; // 유효하지 않으면 false 반환
  }
});
const handleHeartClick = () => {
  // 부모 컴포넌트에서 처리할 수 있도록 이벤트 전달
  emit("toggleFavorite", props.item);
};
</script>

<template>
  <div class="item-box">
    <div class="item-pic">
      <img
          src="https://images.unsplash.com/photo-1489936724440-afaf5a115ede?q=80&w=2051&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
          alt="AptImg"
          style="width: 150px; height: 100%; border-radius: 10px;"/>
      <font-awesome-icon :icon="['fas', 'heart']" size="lg" class="heart-icon"
                         :style="{ color: isHeartClicked ? 'red' : 'white' }"
                         @click="handleHeartClick" />
    </div>
    <div class="item-info">
      <div class="dong">{{ props.item.local3 }}</div>
      <div class="name">{{ props.item.aptName }}</div>
      <div class="price">매매 {{ props.item.maxSalesPrice.toLocaleString() }}</div>
      <div class="apt-detail">총 세대수: {{ props.item.totalHome }} 세대</div>
      <div class="apt-detail">가구 당 주차 대수: {{ props.item.parkingPerHome }}</div>
    </div>
  </div>
</template>

<style scoped>
.item-box {
  width: 100%;
  height: 140px;
  padding: 10px;
  margin-bottom: 10px;
  background-color: #FFFFFF;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
  display: flex;
  flex-direction: row;
  cursor: pointer;
}

.item-pic {
  position: relative;
}

.heart-icon {
  position: absolute;
  top: 8px;
  right: 8px;
  color: red;
}

.item-info {
  text-align: left;
  line-height: 1.2;
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-left: 10px;
}

.dong {
  font-size: 14px;
}

.name {
  font-size: 16px;
  font-weight: 600;
}

.price {
  font-size: 20px;
  font-weight: bolder;
}

.apt-detail {
  font-size: 13px;
  color: #9f9e9e;
}

</style>