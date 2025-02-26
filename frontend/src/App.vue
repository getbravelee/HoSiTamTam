<script setup>
// import SearchBar from './components/SearchBar.vue';
// import ChatBot from './components/ChatBot.vue'; // ChatBot 컴포넌트 import
// import FilteringWord from "./components/FilteringWord.vue";
import SideMenu from "@/components/SideMenu.vue";
import { RouterView } from "vue-router";
import MapMain from "@/components/MapMain.vue";
import LoginBtn from "@/components/LoginBtn.vue";
import RegionBtn from "@/components/RegionBtn.vue";
</script>

<template>
  <div id="app">
    <MapMain />
    <LoginBtn />
    <SideMenu />
    <RegionBtn />
    <RouterView />
  </div>
</template>

<style>
/* 스타일 추가 */
@font-face {
  font-family: 'Pretendard-Regular';
  src: url('https://fastly.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
  font-weight: 400;
  font-style: normal;
}

#app {
  font-family: 'Pretendard-Regular', Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
}
</style>
