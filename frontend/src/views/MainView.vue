<script setup>
import SearchBar from "@/components/SearchBar.vue";
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
            <li>동해물과 백두산이 마르고 닳도록</li>
            <li>아리랑아라리요 다 이뤄지라</li>
            <li>괭갈리는 바람</li>
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
            <div class="news-box">
              <div class="news-title title">마포 새 아파트 잡겠다고 여섯 식구가 집 없이 15년 버텼다</div>
              <div class="news-info info">한국경제 | 한 달 전</div>
            </div>
            <div class="news-box">
              <div class="news-title title">자그마치 5000가구 새 아파트 강북 최대 재건축 속도 붙었다 [부동산360]</div>
              <div class="news-info info">헤럴드경제 | 19일 전</div>
            </div>
            <div class="news-box">
              <div class="news-title title">“서울 집값 오른다는데” 지금 사려면 '이것' 알아야 합니다 [더 머니이스트]</div>
              <div class="news-info info">한국경제 | 21일 전</div>
            </div>
          </div>
          <button class="more-btn">
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
            <div class="section-body carousel-item active">
              <div class="notice-box">
                <div class="notice-title title" title>개인정보처리방침 개정 안내</div>
                <div class="notice-info info">2024.11.01</div>
              </div>
            </div>
            <div class="section-body carousel-item">
              <div class="notice-box">
                <div class="notice-title title" title>서비스 출시</div>
                <div class="notice-info info">2024.12.25</div>
              </div>
            </div>
            <div class="section-body carousel-item">
              <div class="notice-box">
                <div class="notice-title title" title>서비스 업데이트 안내</div>
                <div class="notice-info info">2024.11.27</div>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.body-container {
  position: fixed;
  top: 65px;
  left: 80px;
  padding: 20px 19px;
  width: 375px;
  z-index: 10;
  background-color: #EBF2FC;
  height: calc(100vh - 65px);
  overflow-y: auto;
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
</style>