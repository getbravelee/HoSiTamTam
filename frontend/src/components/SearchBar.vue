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
        />
        <font-awesome-icon :icon="['fas', 'magnifying-glass']" class="search-icon"/>
      </div>
    </div>

    <!--    <table v-if="suggestions.length" border="1">-->
    <!--      <thead>-->
    <!--      <tr>-->
    <!--        <th>ID</th>-->
    <!--        <th>제안 이름</th>-->
    <!--      </tr>-->
    <!--      </thead>-->
    <!--      <tbody>-->
    <!--      <tr v-for="suggestion in suggestions" :key="suggestion.id" @click="selectSuggestion(suggestion)">-->
    <!--        <td>{{ suggestion.id }}</td>-->
    <!--        <td>{{ suggestion.locationName }}</td>-->
    <!--      </tr>-->
    <!--      </tbody>-->
    <!--    </table>-->
    <div v-if="showResults && results.length" class="search-results" @mousedown="onResultClick">
      <ul>
        <li v-for="(result) in results" :key="result.id" @click="selectSuggestion(result)">{{ result.locationName }}</li>
      </ul>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  // data() {
  //   return {
  //     query: '',
  //     suggestions: [],
  //   };
  // },
  // methods: {
  //   fetchSuggestions() {
  //     if (this.query.length > 1) {
  //       axios.get(`/api/search/suggestions?keyword=${this.query}`)
  //           .then(response => {
  //             this.suggestions = response.data; // 서버에서 받은 제안 목록
  //           })
  //           .catch(error => {
  //             console.error("Error fetching suggestions:", error);
  //           });
  //     } else {
  //       this.suggestions = [];
  //     }
  //   },
  //   selectSuggestion(suggestion) {
  //     this.query = suggestion.locationName; // 선택한 제안으로 검색어 업데이트
  //     this.suggestions = []; // 제안 목록 비우기
  //   }
  // }
  props: {
    backgroundColor: {
      type: String,
    }
  },
  data() {
    return {
      query: "",
      results: [],
      showResults: false,
      isInput: false,
      debounceTimer: null,
      isClickingSearchResult: false,
    };
  },
  methods: {
    onSearch() {
      // const allLocations = ["서울", "부산", "대구", "인천", "광주", "대전", "울산"];
      // this.results = allLocations.filter((location) =>
      //     location.includes(this.query)
      // );
      // this.showResults = this.results.length > 0;
      if (this.query.length > 1) {
        axios.get(`/api/search/suggestions?keyword=${this.query}`)
            .then(response => {
              this.results = response.data; // 서버에서 받은 제안 목록
            })
            .catch(error => {
              console.error("Error fetching suggestions:", error);
            });
      } else {
        this.results = [];
      }
    },

    onInput() {
      if (this.debounceTimer) {
        clearTimeout(this.debounceTimer);
      }

      this.debounceTimer = setTimeout(() => {
        if (!this.isComposing) {
          this.onSearch();
        }
      }, 100);
    },

    hideResults() {
      this.showResults = false;
    },
    inputEnded() {
      this.isInput = false;
    },
    onResultClick() {
      this.isClickingSearchResult = true;
    },
    onBlur() {
      if (this.isClickingSearchResult) {
        console.log("Click on search result, skipping blur");
        this.isClickingSearchResult = false;
        return;
      }

      console.log("Handling blur...");
      this.hideResults();
      this.inputEnded();
    },
    onCompositionStart() {
      this.isInput = true;
    },
    onFocus() {
      if (this.query.length > 0) {
        this.isInput = true;
        this.showResults = true;
      }
    },
    selectSuggestion(result) {
      // this.query = result;
      this.query = result.locationName;
      this.results = [];
      this.showResults = false;
      this.isInput = false;
    },
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
  z-index: 9;
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
