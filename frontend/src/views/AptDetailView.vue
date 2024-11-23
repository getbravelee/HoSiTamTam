<script setup>
import SearchBar from "@/components/SearchBar.vue";
import {onMounted, onUnmounted, ref, watch} from "vue";
import {useRoute} from "vue-router";

const route = useRoute();
const aptName = ref(history.state.aptName || '');
const query = ref(route.params.query || '');
const results = ref(history.state.results || '[]');

const tab = ref('deal-info');

watch(() => route.params.query, (newQuery) => {
  query.value = newQuery || '';
});

const updateResults = (newResults) => {
  results.value = newResults;
};

const focusOption = ref(null);
const handleMouseMove = (e) => {
  const container = focusOption.value;
  if (!container) return;
  const containerWidth = container.offsetWidth;
  const mouseX = e.clientX - container.getBoundingClientRect().left;

  // 10% 범위로 왼쪽 끝과 오른쪽 끝에서 스크롤이 자동으로 시작되도록 설정
  const scrollThreshold = 0.15 * containerWidth;

  if (mouseX < scrollThreshold) {
    container.scrollLeft -= 10; // 왼쪽 끝에서 자동으로 스크롤
  } else if (mouseX > containerWidth - scrollThreshold) {
    container.scrollLeft += 10; // 오른쪽 끝에서 자동으로 스크롤
  }
};

onMounted(() => {
  const container = focusOption.value;
  if (container) {
    container.addEventListener('mousemove', handleMouseMove);
  }
});

onUnmounted(() => {
  const container = focusOption.value;
  if (container) {
    container.removeEventListener('mousemove', handleMouseMove);
  }
});

const dealInfoSection = ref(null);
const aptInfoSection = ref(null);
const shopInfoSection = ref(null);
const schoolInfoSection = ref(null);
const aptReviewSection = ref(null);

const moveDealInfo = () => {
  dealInfoSection.value.scrollIntoView({behavior: "smooth"});
}

const moveAptInfo = () => {
  aptInfoSection.value.scrollIntoView({behavior: "smooth"});
}
const moveShopInfo = () => {
  shopInfoSection.value.scrollIntoView({behavior: "smooth"});
}
const moveSchoolInfo = () => {
  schoolInfoSection.value.scrollIntoView({behavior: "smooth"});
}

const moveAptReview = () => {
  aptReviewSection.value.scrollIntoView({behavior: "smooth"});
}


const tabs = ['초등학교', '중학교', '고등학교'];
const activeTab = ref('초등학교');

const elementarySchools = ref([
  { name: '부민초등학교', distance: '486m', time: '7분' },
]);

const middleSchools = ref([
  { name: '명호중학교', rank: '상위 16%', specialAdmissions: '특목고 17명/자사고 5명' },
  { name: '경일중학교', rank: '상위 28%', specialAdmissions: '특목고 14명' },
  { name: '명지중학교', rank: '상위 48%', specialAdmissions: '특목고 4명/자사고 2명' },
]);

const highSchools = ref([
  { name: '부경고등학교', distance: '822m', studentsPerClass: '19.6명' },
  { name: '혜광고등학교', distance: '834m', studentsPerClass: '16.6명' },
  { name: '부산서여자고등학교', distance: '863m', studentsPerClass: '20.2명' },
]);

const changeTab = (tab) => {
  activeTab.value = tab;
};
</script>

<template>
  <div>
    <SearchBar :value="query" @results="updateResults" :backgroundColor="'#293A67'"/>
    <div class="result-bar">
      <font-awesome-icon :icon="['fas', 'arrow-left']" size="lg"/>
      {{ aptName }}
      <font-awesome-icon :icon="['fas', 'xmark']" size="xl"/>
    </div>

    <div class="body-container">
      <!-- 해당 위치로 바로가기 버튼 -->
      <div class="focus-option" ref="focusOption">
        <input id="tab-1" type="radio" v-model="tab" value="deal-info" class="option" @click="moveDealInfo()"/>
        <label for="tab-1" class="tab">실거래가 정보</label>
        <input id="tab-2" type="radio" v-model="tab" value="apt-info" class="option" @click="moveAptInfo()"/>
        <label for="tab-2" class="tab">아파트 정보</label>
        <input id="tab-3" type="radio" v-model="tab" value="shop-info" class="option" @click="moveShopInfo()"/>
        <label for="tab-3" class="tab">주변 상권 정보</label>
        <input id="tab-4" type="radio" v-model="tab" value="school-info" class="option" @click="moveSchoolInfo()"/>
        <label for="tab-4" class="tab">학군 정보</label>
        <input id="tab-5" type="radio" v-model="tab" value="apt-review" class="option" @click="moveAptReview()"/>
        <label for="tab-5" class="tab">아파트 이야기</label>
      </div>

      <!-- content -->
      <div class="content">
        <!-- 실거래가 정보 -->
        <div class="section" ref="dealInfoSection">
          <div class="info-title">📈실거래가 정보</div>
          <table class="deal-info-table">
            <thead>
            <tr>
              <th>계약일</th>
              <th>가격</th>
              <th>평형</th>
              <th>총</th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td>22.04.22</td>
              <td>22.4억</td>
              <td>57.7평</td>
              <td>11</td>
            </tr>
            <tr>
              <td>22.03.21</td>
              <td>33.3억</td>
              <td>51.4평</td>
              <td>14</td>
            </tr>
            <tr>
              <td>22.01.18</td>
              <td>32억</td>
              <td>50.4평</td>
              <td>7</td>
            </tr>
            <tr>
              <td>21.05.17</td>
              <td>31.6억</td>
              <td>50.4평</td>
              <td>5</td>
            </tr>
            <tr>
              <td>21.05.12</td>
              <td>33억</td>
              <td>51.4평</td>
              <td>11</td>
            </tr>
            </tbody>
          </table>
          <button class="more-btn">
            더보기
            <font-awesome-icon :icon="['fas', 'angle-down']" />
          </button>
        </div>
        <!-- 아파트 정보 -->
        <div class="section" ref="aptInfoSection">
          <div class="info-title">📝아파트 정보</div>
          <div>
            <img
                src="https://images.unsplash.com/photo-1489936724440-afaf5a115ede?q=80&w=2051&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                alt="AptImg"
                style="width: 100%; height: 100%; border-radius: 5px"/>
            <div class="apt-info">
              <ul>
                <li>아파트</li>
                <li>2012년 6월 (13년차)</li>
                <li>최고 10층</li>
                <li>건폐율 22%</li>
                <li>지역난방</li>
              </ul>
              <ul>
                <li>414세대</li>
                <li>17개동</li>
                <li>용적률 126%</li>
                <li>계단식</li>
                <li>열병합</li>
              </ul>
            </div>
          </div>
        </div>
        <!-- 주변 상권 정보 -->
        <div class="section" ref="shopInfoSection">
          <div class="info-title">🛒주변 상권 정보</div>
          <div class="shop-info">
            <div class="shop-item">
              <div class="shop-icon">🛍️</div>
              <div class="shop-details">
                <div class="shop-name">스타필드 (스타필드시티명지)</div>
                <div class="shop-distance">1.3km, 5분 거리</div>
              </div>
            </div>
            <div class="shop-item">
              <div class="shop-icon">🌳</div>
              <div class="shop-details">
                <div class="shop-name">남명공원 (테니스장 5.7배)</div>
                <div class="shop-distance">14m, 1분 거리</div>
              </div>
            </div>
            <div class="shop-item">
              <div class="shop-icon">⚽</div>
              <div class="shop-details">
                <div class="shop-name">명지공원 (축구장 23.9배)</div>
                <div class="shop-distance">563m, 2분 거리</div>
              </div>
            </div>
          </div>
          <button class="more-btn">
            더보기
            <font-awesome-icon :icon="['fas', 'angle-down']" />
          </button>
        </div>
        <!-- 학군 정보 -->
        <div class="section" ref="schoolInfoSection">
          <div class="info-title">🏫학군 정보</div>
          <div class="tab-buttons">
            <button
                v-for="(tab, index) in tabs"
                :key="index"
                :class="{ active: activeTab === tab }"
                @click="changeTab(tab)"
            >
              {{ tab }}
            </button>
          </div>
          <div class="school-data">
            <template v-if="activeTab === '초등학교'">
              <div v-for="school in elementarySchools" :key="school.name" class="school-card">
                <p>{{ school.name }}</p>
                <p>거리: {{ school.distance }} / {{ school.time }}</p>
              </div>
            </template>
            <template v-else-if="activeTab === '중학교'">
              <div v-for="school in middleSchools" :key="school.name" class="school-card">
                <p>{{ school.name }}</p>
                <p>상위: {{ school.rank }}</p>
                <p>특목/자사고 진학: {{ school.specialAdmissions }}</p>
              </div>
            </template>
            <template v-else-if="activeTab === '고등학교'">
              <div v-for="school in highSchools" :key="school.name" class="school-card">
                <p>{{ school.name }}</p>
                <p>거리: {{ school.distance }}</p>
                <p>학급당 학생 수: {{ school.studentsPerClass }}</p>
              </div>
            </template>
          </div>
        </div>
        <!-- 아파트 이야기 -->
        <div class="section" ref="aptReviewSection" style="height: 500px;">
          <div class="info-title">💬아파트 이야기</div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.result-bar {
  position: fixed;
  top: 71px;
  left: 80px;
  width: 375px;
  padding: 10px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #293A67;
  color: #FFFFFF;
  font-size: 18px;
  z-index: 10;
}

.body-container {
  position: fixed;
  top: 118px;
  left: 80px;
  width: 375px;
  z-index: 10;
  background-color: #EBF2FC;
  height: calc(100vh - 65px);
}

input[type=radio] {
  display: none;
}

.focus-option {
  display: flex;
  flex-direction: row;
  padding: 10px 0;
  overflow-x: auto;
  white-space: nowrap;
  -ms-overflow-style: none;
}

.focus-option::-webkit-scrollbar{
  display:none;
}

.tab {
  font-size: 14px;
  padding: 2px 5px;
  margin-right: 8px;
  color: #4E5E77;
  border-radius: 10px;
  border: 2px #4E5E77 solid;
  background-color: #FFFFFF;
  cursor: pointer;
  white-space: nowrap;
}

.tab:hover {
  color: #fff;
  background-color: #4E5E77;
}

.option:checked + .tab {
  color: #fff;
  background-color: #4E5E77;
  box-shadow: inset 0 4px 6px rgba(0, 0, 0, 0.3);
}


.content {
  height: calc(100vh - 65px);
  overflow-y: auto;
  -ms-overflow-style: none;
}

.content::-webkit-scrollbar{
  display:none;
}

.section {
  padding: 13px 10px;
  margin-bottom: 10px;
  background-color: #FFFFFF;
}

.info-title {
  font-weight: 800;
  text-align: left;
  margin-bottom: 10px;
}


.deal-info-table {
  width: 100%;
  text-align: center;
  //border-collapse: collapse;
  //border-radius: 15px;
  //box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
  overflow: hidden;
}

.deal-info-table th {
  background-color: #f8f8f8;
  font-size: 13px;
  font-weight: bold;
  padding: 8px;
}

.deal-info-table td {
  font-size: 14px;
  border-bottom: 1px solid #e9effb;
  padding: 8px 0;
}

.deal-info-table tr:last-child {
  border-bottom: none;
}

.more-btn {
  all: unset;
  cursor: pointer;
  font-size: 14px;
  font-weight: 600;
  color: #293A67;
  padding: 5px;
  margin-top: 10px;
}

.more-btn:hover {
  color: #3E62C0;
}


.apt-info {
  display: flex;
  margin-top: 15px;
  font-size: 14px;
  color: #1e1d1d;
  text-align: left;
}

.apt-info ul {
  list-style-type: disc;
  padding-left: 20px;
  margin: 0;
  flex-basis: 50%;
}

.apt-info li {
  margin-bottom: 5px;
}

.shop-info {
  display: flex;
  flex-direction: column;
  gap: 15px;
  margin-top: 10px;
}

.shop-item {
  display: flex;
  align-items: center;
  padding: 10px;
  background-color: #f8f9fa;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.shop-icon {
  font-size: 24px;
  margin-right: 15px;
}

.shop-details {
  display: flex;
  flex-direction: column;
  text-align: left;
}

.shop-name {
  font-weight: bold;
  font-size: 14px;
  color: #293a67;
}

.shop-distance {
  font-size: 12px;
  color: #4e5e77;
}

.more-btn {
  all: unset;
  cursor: pointer;
  font-size: 14px;
  font-weight: 600;
  color: #293a67;
  padding: 5px;
  display: inline-block;
  margin-top: 15px;
  text-align: center;
}

.more-btn:hover {
  color: #3e62c0;
}


.school-info {
  border: 1px solid #ccc;
  padding: 16px;
  background-color: #f9f9f9;
  border-radius: 8px;
}

.info-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 16px;
}

.tab-buttons {
  display: flex;
  margin-bottom: 16px;
}

.tab-buttons button {
  flex: 1;
  padding: 8px 12px;
  border: 1px solid #ccc;
  background-color: #fff;
  cursor: pointer;
  transition: all 0.3s ease;
}

.tab-buttons button.active {
  background-color: #007bff;
  color: #fff;
}

.tab-buttons button:not(:last-child) {
  margin-right: 8px;
}

.school-data {
  display: flex;
  flex-direction: column;
}

.school-card {
  border: 1px solid #ddd;
  padding: 12px;
  margin-bottom: 8px;
  border-radius: 6px;
  background-color: #fff;
}
</style>