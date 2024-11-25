<script setup>
import {ref} from "vue";

const isHeartClicked = ref(false);

const toggleHeartColor = () => {
  isHeartClicked.value = !isHeartClicked.value;
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
                         :style="{ color: isHeartClicked ? 'white' : 'red' }"
                         @click="toggleHeartColor" />
    </div>
    <div class="item-info">
      <div class="dong">명지동</div>
      <div class="name">명지오션시티삼정그린코아</div>
      <div class="price">매매 4억 8,000</div>
      <div class="apt-detail">84m²</div>
      <div class="apt-detail">109동 6층</div>
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