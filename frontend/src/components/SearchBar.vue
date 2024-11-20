<template>
  <div>
    <label for="search-input">Search:</label>
    <input type="text" id="search-input" v-model="query" @keyup="onKeyUp" placeholder="검색어 입력" />
    <table v-if="suggestions.length" border="1">
      <thead>
      <tr>
        <th>ID</th>
        <th>제안 이름</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="suggestion in suggestions" :key="suggestion.id" @click="selectSuggestion(suggestion)">
        <td>{{ suggestion.id }}</td>
        <td>{{ suggestion.locationName }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios';
import debounce from 'lodash/debounce'; // lodash의 debounce 함수 임포트

export default {
  data() {
    return {
      query: '',
      suggestions: [],
    };
  },
  methods: {
    // 디바운스된 함수로 API 호출
    fetchSuggestions: debounce(function() {
      if (this.query.length >= 1) {
        axios.get(`/api/search/suggestions?keyword=${this.query}`)
            .then(response => {
              this.suggestions = response.data; // 서버에서 받은 제안 목록
            })
            .catch(error => {
              console.error("Error fetching suggestions:", error);
            });
      } else {
        this.suggestions = [];
      }
    }, 300), // 300ms 후에 호출

    // 키 입력 시 호출되는 함수
    onKeyUp(event) {
      if (event.key === 'Enter') {
        this.fetchSuggestions(); // 엔터키 입력 시 즉시 호출
      } else {
        this.fetchSuggestions(); // 타이핑이 멈추면 자동 호출
      }
    },

    // 제안 선택 시 처리
    selectSuggestion(suggestion) {
      this.query = suggestion.locationName; // 선택한 제안으로 검색어 업데이트
      this.suggestions = []; // 제안 목록 비우기
      this.$router.push(`/region/${suggestion.id}`); // 선택한 동에 해당하는 페이지로 이동
    }
  }
};
</script>

<style scoped>
/* 스타일 추가 */
table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 10px;
  text-align: left;
}

tr:hover {
  background-color: #f2f2f2;
}
</style>