<template>
  <div>
    <h2>아파트 필터링</h2>
    <div>
      <label for="area">평수:</label>
      <select v-model="selectedArea" @change="applyFilter">
        <option value="">선택하세요</option>
        <option v-for="area in areaOptions" :key="area" :value="area">{{ area }} 평</option>
      </select>
    </div>

    <div>
      <label for="price">가격:</label>
      <select v-model="selectedPrice" @change="applyFilter">
        <option value="">선택하세요</option>
        <option v-for="price in priceOptions" :key="price.value" :value="price.value">{{ price.label }}</option>
      </select>
    </div>

    <div>
      <label for="type">매물 종류:</label>
      <select v-model="selectedType" @change="applyFilter">
        <option value="">선택하세요</option>
        <option v-for="type in typeOptions" :key="type" :value="type">{{ type }}</option>
      </select>
    </div>

    <button @click="fetchApartments">필터링 적용</button>

    <h3>결과</h3>
    <ul>
      <li v-for="apartment in apartments" :key="apartment.id">{{ apartment.locationName }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      selectedArea: '',
      selectedPrice: '',
      selectedType: '',
      apartments: [],
      areaOptions: [10, 20, 30, 40, 50, 60],
      priceOptions: [
        { value: '5000', label: '5천만원 이하' },
        { value: '10000', label: '1억원 이하' },
        { value: '20000', label: '2억원 이하' },
        { value: '30000', label: '3억원 이하' },
        { value: '50000', label: '5억원 이하' },
        { value: '100000', label: '10억원 이하' },
        { value: '100001', label: '10억원 이상' },
      ],
      typeOptions: ['월세', '전세', '매매'],
    };
  },
  methods: {
    applyFilter() {
      // 필터링 조건이 변경되면 자동으로 fetchApartments를 호출합니다.
      this.fetchApartments();
    },
    fetchApartments() {
      const filters = {
        area: this.selectedArea || null,
        price: this.selectedPrice || null,
        type: this.selectedType || null,
      };

      // 쿼리 파라미터 생성
      const queryParams = Object.entries(filters)
          .filter(([key, value]) => key !== null && value !== null) // null이 아닌 필터만 포함
          .map(([key, value]) => `${key}=${value}`)
          .join('&');

      fetch(`/api/apartments?${queryParams}`)
          .then(response => {
            if (!response.ok) {
              throw new Error('Network response was not ok');
            }
            return response.json();
          })
          .then(data => {
            this.apartments = data; // API 응답으로 아파트 목록 업데이트
          })
          .catch(error => {
            console.error('Error fetching apartments:', error);
          });
    },
  },
};
</script>

<style scoped>
/* 스타일 추가 */
</style>
