<script setup>
import {useRegionStore} from "@/stores/Region";

const regionStore = useRegionStore();
</script>

<template>
  <div>
    {{regionStore.regionName}}
  </div>
</template>

<style scoped>

</style>