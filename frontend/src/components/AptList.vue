<template>
  <div>
    <input
        type="text"
        v-model="query"
        @input="fetchApts"
        placeholder="검색어 입력"
    />
    <table v-if="apts.length" border="1">
      <thead>
      <tr>
        <th>id</th>
        <th>아파트 이름</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="apt in apts" :key="apt.id" @click="selectApt(apt)">
        <td>{{ apt.id }}</td>
        <td>{{ apt.aptName }}</td>
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
      apts: []
    };
  },
  methods: {
    fetchApts() {
      if (this.query.length > 1) {
        axios
            .get(`/region/${this.query}`)
            .then(response => {
              this.apts = response.data; // 서버에서 받은 결과를 상태에 저장
            })
            .catch(error => {
              console.error("Error fetching apts:", error);
            });
      } else {
        this.apts = [];
      }
    },
    selectApt(apt) {
      this.query = apt.aptName; // 선택한 제안으로 검색어 업데이트
      this.apts = []; // 제안 목록 비우기
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
