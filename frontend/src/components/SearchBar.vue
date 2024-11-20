<template>
  <div>
    <div class="search-container" :style="{ backgroundColor: backgroundColor }">
      <div :class="{ 'is-input': isInput, 'search-bar': !isInput }">
        <input
            type="text"
            v-model="query"
            placeholder="지역명 검색"
            @focus="onFocus()"
            @blur="onBlur()"
            @input="onInput()"
            @compositionstart="onCompositionStart"
            @keyup.enter="handleEnter"
        />
        <font-awesome-icon :icon="['fas', 'magnifying-glass']" class="search-icon"/>
      </div>
    </div>
    <!-- 결과 목록 -->
    <div v-if="showResults && results.length" class="search-results" @mousedown="onResultClick">
      <ul>
        <li v-for="(result, index) in results" :key="index" @click="selectSuggestion(result)">{{ result }}</li>
      </ul>
<!--      <ul>-->
<!--        <li v-for="(result) in results" :key="result.id" @click="selectSuggestion(result)">{{ result.loationName }}</li>-->
<!--      </ul>-->
    </div>
  </div>
</template>

<script>
import { useRouter } from 'vue-router';
import {ref} from "vue";

export default {
  props: {
    backgroundColor: {
      type: String,
    }
  },
  setup() {
    const router = useRouter();
    const query = ref("");
    const results = ref([]);
    const showResults = ref(false);
    const isInput = ref(false);
    const debounceTimer = ref(null);
    const isClickingSearchResult = ref(false);

    const onSearch = () => {
      const allLocations = ["서울", "부산", "대구", "인천", "광주", "대전", "울산"];
      results.value = allLocations.filter((location) =>
          location.includes(query.value)
      );
      showResults.value = results.value.length > 0;

      // if (this.query.length > 1) {
      //   axios.get(`/api/search/suggestions?keyword=${this.query}`)
      //       .then(response => {
      //         this.results = response.data; // 서버에서 받은 제안 목록
      //       })
      //       .catch(error => {
      //         console.error("Error fetching suggestions:", error);
      //       });
      // } else {
      //   this.results = [];
      // }
    };

    const onInput = () => {
      if (debounceTimer.value) {
        clearTimeout(debounceTimer.value);
      }

      debounceTimer.value = setTimeout(() => {
        onSearch();
      }, 100);
    };

    const hideResults = () => {
      showResults.value = false;
    };

    const inputEnded = () => {
      isInput.value = false;
    };

    const onResultClick = () => {
      isClickingSearchResult.value = true;
    };

    const onBlur = () => {
      if (isClickingSearchResult.value) {
        console.log("Click on search result, skipping blur");
        isClickingSearchResult.value = false;
        return;
      }

      console.log("Handling blur...");
      hideResults();
      inputEnded();
    };

    const onCompositionStart = () => {
      isInput.value = true;
    };

    const onFocus = () => {
      if (query.value.length > 0) {
        isInput.value = true;
        showResults.value = true;
      }
    };

    const selectSuggestion = (result) => {
      query.value = result;
      // query.value = result.locationName;
      results.value = [];
      showResults.value = false;
      isInput.value = false;
    };

    const handleEnter = () => {
      const data = results.value.map(item => item);
      router.push({
        name: 'search',
        query: { query: query.value, results: results.value },  // query는 URL 쿼리로 전달
        state: { results: data },  // results는 state로 전달
      });

      showResults.value = false;
      results.value = [];
    };

    return {
      query,
      results,
      showResults,
      isInput,
      debounceTimer,
      isClickingSearchResult,
      onSearch,
      onInput,
      hideResults,
      inputEnded,
      onResultClick,
      onBlur,
      onCompositionStart,
      onFocus,
      selectSuggestion,
      handleEnter
    };
  },
};
</script>

<style scoped>
.search-container {
  position: fixed;
  top: 0;
  left: 80px;
  padding: 14px 19px;
  width: 375px;
  z-index: 10;
}

.search-bar {
  display: flex;
  align-items: center;
  background-color: white;
  border-radius: 25px;
  padding: 10px 15px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.is-input {
  display: flex;
  align-items: center;
  background-color: white;
  padding: 10px 15px;
  //box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  border-radius: 16px 16px 0 0;
  border-bottom: 1px solid #dadce0;
}

input {
  flex: 1;
  border: none;
  outline: none;
  font-size: 14px;
  color: #333;
}

.search-icon {
  color: #888;
}

.search-results {
  position: absolute;
  top: 62px;
  left: 99px;
  width: 330px;
  background: white;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  border-radius: 0 0 10px 10px;
  z-index: 15;
}

.search-results ul {
  list-style: none;
  padding: 10px 0;
  margin: 0;
}

.search-results li {
  padding: 10px;
  cursor: pointer;
}

.search-results li:hover {
  background-color: #f1f1f1;
}

</style>
