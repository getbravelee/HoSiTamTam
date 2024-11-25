<template>
  <div>
    <h1>아파트 상세 정보</h1>
    <div>
      <label for="aptId">아파트 ID:</label>
      <input
          id="aptId"
          v-model="aptId"
          type="text"
          placeholder="아파트 ID를 입력하세요"
      />
      <button @click="fetchAptDetail">조회</button>
    </div>
    <div v-if="aptDetail">
      <h2>상세 정보</h2>
      <p><strong>ID:</strong> {{ aptDetail.aptId }}</p>
      <p><strong>이름:</strong> {{ aptDetail.aptName }}</p>
      <p><strong>총 세대수:</strong> {{ aptDetail.totalSede }}</p>
    </div>
    <div v-if="error" class="error">
      <p>{{ error }}</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      aptId: "",
      aptDetail: null,
      error: null,
    };
  },
  methods: {
    async fetchAptDetail() {
      try {
        this.error = null; // 기존 에러 초기화
        this.aptDetail = null; // 기존 데이터 초기화

        if (!this.aptId) {
          this.error = "아파트 ID를 입력해주세요.";
          return;
        }

        const response = await axios.get(`/apt/${this.aptId}`);
        this.aptDetail = response.data;
      } catch (err) {
        this.error = "아파트 정보를 불러오는 데 실패했습니다.";
        console.error(err);
      }
    },
  },
};
</script>

<style>
.error {
  color: red;
  font-weight: bold;
}
</style>