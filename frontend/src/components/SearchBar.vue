<template>
  <div>
    <input
        type="text"
        v-model="query"
        @input="fetchSuggestions"
        placeholder="검색어 입력"
    />
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

export default {
  data() {
    return {
      query: '',
      suggestions: []
    };
  },
  methods: {
    fetchSuggestions() {
      if (this.query.length > 1) {
        axios.get(`/search/suggestions?keyword=${this.query}`)
            .then(response => {
              this.suggestions = response.data; // 서버에서 받은 제안 목록
            })
            .catch(error => {
              console.error("Error fetching suggestions:", error);
            });
      } else {
        this.suggestions = [];
      }
    },
    selectSuggestion(suggestion) {
      this.query = suggestion.locationName; // 선택한 제안으로 검색어 업데이트
      this.suggestions = []; // 제안 목록 비우기
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
