<script setup>
import SearchBar from "@/components/SearchBar.vue";
import {onMounted, ref} from "vue";
import axios from "axios";


// 뉴스 데이터 불러오기
const newsList = ref([]);
const fetchNews = async () => {
  try {
    const response = await axios.get('/news');
    newsList.value = response.data;
    console.log(response.data);
  } catch (error) {
    console.error('뉴스 데이터를 가져오는 데 실패했습니다:', error);
  }
};

const itemsPerPage = 3;
const displayedNews = ref([]);
const currentPage = ref(0);

const loadMoreItems = () => {
  const startIndex = currentPage.value * itemsPerPage;
  const nextItems = newsList.value.slice(startIndex, startIndex + itemsPerPage);
  displayedNews.value.push(...nextItems);
  currentPage.value++;
};

// 날짜 포맷팅 함수
const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date.toLocaleDateString('ko-KR');
};

onMounted(() => {
  fetchNews();
  const checkItemsReady = () => {
    if (newsList.value.length > 0) {
      loadMoreItems();
    } else {
      setTimeout(checkItemsReady, 100);  // 0.1초마다 확인
    }
  };
  checkItemsReady();
});
</script>

<template>
  <div>
    <SearchBar :backgroundColor="'#293A67'"/>
    <div class="body-container">
<!--      서비스 소개-->
      <div class="section">
        <h3 class="section-title">📌 서비스 소개</h3>
        <div class="section-body">
          <ul class="service-list">
            <li>[부동산 최종 관통 프로젝트]</li>
            <li>아파트의 상세 정보를 확인할 수 있는 프로젝트입니다.</li>
            <li></li>
            <li>이수철은 수채모로라 천제</li>
            <li>매운 바람 옷을 없다</li>
            <li>명지 영도</li>
          </ul>
        </div>
      </div>
<!--      뉴스-->
      <div class="section">
        <h3 class="section-title">📰 뉴스</h3>
        <div class="section-body">
          <div class="news-list">
            <!-- 뉴스 리스트 렌더링 -->
            <div v-for="news in displayedNews" :key="news.id" class="news-box">
              <a :href="news.url" target="_blank" class="news-title title">
                {{ news.title }}
              </a>
              <div class="news-info info">
                {{ formatDate(news.newsDate) }} | 추천수 {{ news.recommendation }}
              </div>
            </div>
          </div>
          <button v-if="displayedNews.length < newsList.length" class="more-btn" @click="loadMoreItems">
            더보기
            <font-awesome-icon :icon="['fas', 'angle-down']" />
          </button>
        </div>
<!--       공지사항-->
      </div>
      <div class="section">
        <h3 class="section-title">📢 공지사항</h3>
        <div id="noticeCarousel" class="carousel slide" data-bs-ride="carousel">
          <div class="carousel-indicators">
            <button type="button" data-bs-target="#noticeCarousel" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
            <button type="button" data-bs-target="#noticeCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
            <button type="button" data-bs-target="#noticeCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
          </div>
          <div class="carousel-inner">
            <RouterLink :to="{ name: 'notice' }" class="no-decoration">
              <div class="section-body carousel-item active">
                <div class="notice-box">
                  <div class="notice-title title" title>개인정보처리방침 개정 안내</div>
                  <div class="notice-info info">2024.11.01</div>
                </div>
              </div>
            </RouterLink>
            <RouterLink :to="{ name: 'notice' }" class="no-decoration">
              <div class="section-body carousel-item active">
                <div class="notice-box">
                  <div class="notice-title title" title>개인정보처리방침 개정 안내</div>
                  <div class="notice-info info">2024.11.01</div>
                </div>
              </div>
            </RouterLink>
            <RouterLink :to="{ name: 'notice' }" class="no-decoration">
              <div class="section-body carousel-item active">
                <div class="notice-box">
                  <div class="notice-title title" title>개인정보처리방침 개정 안내</div>
                  <div class="notice-info info">2024.11.01</div>
                </div>
              </div>
            </RouterLink>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.body-container {
  position: fixed;
  top: 71px;
  left: 80px;
  padding: 20px 19px;
  width: 375px;
  z-index: 10;
  background-color: #EBF2FC;
  height: calc(100vh - 65px);
  overflow-y: auto;
  -ms-overflow-style: none;
}

.body-container::-webkit-scrollbar {
  display: none;
}

.section {
  margin-bottom: 20px;
}

.section-title {
  font-size: 16px;
  font-weight: bold;
  text-align: left;
  margin-top: 0;
  margin-bottom: 10px;
}

.section-body {
  border: 1px #EEEEEE solid;
  border-radius: 8px;
  padding: 18px 20px;
  background-color: #FFFFFF;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  z-index: 1;
}



.service-list,
.news-list {
  list-style: none;
  padding: 0;
  margin: 0;
  text-align: left;
}

.service-list li {
  font-size: 16px;
  color: #293A67;
  margin-bottom: 6px;
}



.news-box {
  height: 74px;
  border-bottom: 1px #d7d7d7 solid;
  margin-bottom: 13px;
}

.news-title {
  all: unset;
}

.news-title:hover {
  text-decoration: underline;
}

.title {
  font-size: 16px;
  font-weight: bold;
  color: #293A67;
}

.info {
  font-size: 11px;
  color: #898989;
}

.more-btn {
  all: unset;
  cursor: pointer;
  font-size: 14px;
  font-weight: 600;
  color: #293A67;
  padding: 5px;
}

.more-btn:hover {
  color: #3E62C0;
}

.notice-box {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}


#noticeCarousel {
  position: relative;
}

.carousel-indicators {
  position: absolute;
  bottom: -40px;
  left: 35%;
  transform: translateX(-50%);
  display: flex;
  justify-content: center;
}

.carousel-indicators button {
  background-color: #ddd;
  border: none;
  width: 12px;
  height: 12px;
  border-radius: 50%;
}

.carousel-indicators .active {
  background-color: #293A67;
}

.no-decoration {
  text-decoration: none;
  //color: black;
}
</style>