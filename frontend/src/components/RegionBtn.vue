<script setup>
import {useRegionStore} from "@/stores/Region";
import {useRouter} from "vue-router";

const regionStore = useRegionStore();

const router = useRouter();
const goToApartmentList = (regionName) => {
  router.push({
    name: 'aptList',
    params: { region: regionName },
  });
};
</script>

<template>
  <div>
    <button class="bottom-btn" @click="goToApartmentList(regionStore.regionName)">
      <font-awesome-icon :icon="['fas', 'list-ul']" />
      {{regionStore.regionName}}
    </button>
  </div>
</template>

<style scoped>
.bottom-btn {
  all: unset;
  position: fixed;
  bottom: 14px;
  left: 50%;
  transform: translateX(-50%);
  background-color: #3b5998;
  color: white;
  padding: 12px 18px;
  border-radius: 25px;
  font-size: 18px;
  cursor: pointer;
  z-index: 1000;
}
</style>