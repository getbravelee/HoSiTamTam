<script setup>
// import SearchBar from './components/SearchBar.vue';
// import ChatBot from './components/ChatBot.vue'; // ChatBot 컴포넌트 import
// import FilteringWord from "./components/FilteringWord.vue";
import SideMenu from "@/components/SideMenu.vue";
import { RouterView } from "vue-router";
import LoginBtn from "@/components/LoginBtn.vue";
</script>

<template>
  <div id="app">
    <LoginBtn />
    <SideMenu />
    <RouterView />
  </div>
</template>

<style>
/* 스타일 추가 */
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
}
</style>
