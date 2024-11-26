<script setup>
import {defineProps, defineEmits} from "vue";

// 부모로부터 전달받은 item
const props = defineProps({
  item: {
    type: Object,
    required: true
  }
});

// const isHeartClicked = ref(props.item.isFavorite);

// 하트 클릭 시, 부모로 이벤트 전달
const emit = defineEmits(["toggleFavorite"]);
const handleHeartClick = () => {
  emit("toggleFavorite", props.item);
};


// const aptImage = 'https://ic.zigbang.com/vp/BigData/16067/c2802a66d041e62f9b86b0fa748d37268272835b.jpg';

</script>

<template>
  <div class="item-box">
    <div class="item-pic">
      <img
          :src="props.item.image ? `${props.item.image}?w=750&q=80&a=1` : ''"
          alt="AptImg"
          style="width: 100%; height: 100%; border-radius: 5px"/>
      <font-awesome-icon :icon="['fas', 'heart']" size="lg" class="heart-icon"
                         :style="{ color: props.item.isFavorite ? 'red' : 'white' }"
                         @click.stop="handleHeartClick" />
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