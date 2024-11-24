<template>
  <SearchBar />
  <AptList />
  <AptDetail />
  <FilteringWord />
  <ChatBot/>
</template>

<script>
import SearchBar from "@/components/SearchBar.vue";
import FilteringWord from "@/components/FilteringWord.vue";
import ChatBot from "@/components/ChatBot.vue";
import AptDetail from "@/components/AptDetail.vue";
import AptList from "@/components/AptList.vue";

export default {
  name: 'App',
  components: {
    SearchBar,
    AptDetail,
    AptList,
    FilteringWord,
    ChatBot
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
