<script setup>
import { ref } from 'vue';
import {useRouter} from "vue-router";

const notices = ref([
  { id: 1, title: '개인정보처리방침 개정 안내', content: '안녕하세요. 호시탐탐입니다.\n' +
        '\n' +
        '개인정보처리방침이 다음과 같이 개정될 예정임을 안내드립니다.\n' +
        '\n' +
        '개정항목\n' +
        '제2조(개인정보의 처리 및 보유기간)\n' +
        '중개서비스 관련 신규 항목 추가\n' +
        '제4조(개인정보처리의 위탁)\n' +
        '위탁받는 자 및 위탁하는 업무 내용 추가\n' +
        '\n' +
        '개정일정\n' +
        '공고일자 : 2024년 10월 28일 (월)\n' +
        '시행일자 : 2024년 11월 1일 (금)\n' +
        '\n' +
        '자세한 사항은 개인정보처리방침을 통해 확인할 수 있습니다.\n' +
        '링크\n' +
        '\n' +
        '호시탐탐 드림', date: '2024.11.01', isExpanded: false },
  { id: 2, title: '개인정보처리방침 개정 안내', content: '안녕하세요. 호시탐탐입니다.\n' +
        '\n' +
        '개인정보처리방침이 다음과 같이 개정될 예정임을 안내드립니다.\n' +
        '\n' +
        '개정항목\n' +
        '제2조(개인정보의 처리 및 보유기간)\n' +
        '중개서비스 관련 신규 항목 추가\n' +
        '제4조(개인정보처리의 위탁)\n' +
        '위탁받는 자 및 위탁하는 업무 내용 추가\n' +
        '\n' +
        '개정일정\n' +
        '공고일자 : 2024년 10월 28일 (월)\n' +
        '시행일자 : 2024년 11월 1일 (금)\n' +
        '\n' +
        '자세한 사항은 개인정보처리방침을 통해 확인할 수 있습니다.\n' +
        '링크\n' +
        '\n' +
        '호시탐탐 드림', date: '2024.11.01', isExpanded: false },
  // 초기 데이터
]);

// const loadMoreNotices = () => {
//   // 예시로 공지사항 추가 (서버 호출로 대체 가능)
//   for (let i = 0; i < 5; i++) {
//     const nextId = notices.value.length + 1;
//     notices.value.push({ id: nextId, title: `공지사항 ${nextId}`, content: `전체 내용 ${nextId}`, isExpanded: false });
//   }
// };

const toggleExpand = (id) => {
  const notice = notices.value.find((n) => n.id === id);
  if (notice) notice.isExpanded = !notice.isExpanded;
};

// const handleScroll = (e) => {
//   const bottomReached =
//       e.target.scrollHeight - e.target.scrollTop <= e.target.clientHeight;
//   if (bottomReached) {
//     loadMoreNotices();
//   }
// };

// onMounted(() => {
//   const bodyContainer = document.querySelector('.body-container');
//   bodyContainer.addEventListener('scroll', handleScroll);
// });

// 이전 페이지로 이동하기
const router = useRouter();
const goBack = () => {
  router.back();
}
</script>

<template>
  <div>
<!--    페이지 상단 bar-->
    <div class="top-bar">
      <font-awesome-icon :icon="['fas', 'arrow-left']" size="xl" style="color: #FFFFFF" @click="goBack()"/>
      <div class="top-bar-title">호시탐탐 소식</div>
    </div>
<!--    페이지 body-->
    <div class="body-container">
<!--      <div v-for="notice in notices" :key="notice.id" class="notice-item">-->
<!--        <h3>{{ notice.title }}</h3>-->
<!--        <p v-if="notice.isExpanded">{{ notice.content }}</p>-->
<!--        <p v-else>{{ notice.content.substring(0, 20) }}...</p>-->
<!--        <button @click="toggleExpand(notice.id)">-->
<!--          {{ notice.isExpanded ? '접기' : '더보기' }}-->
<!--        </button>-->
<!--      </div>-->
      <div v-for="notice in notices" :key="notice.id" class="notice-item">
        <div class="notice-head">
          <div class="notice-title">{{ notice.title }}</div>
          <div class="notice-date">{{ notice.date }}</div>
        </div>
        <p v-if="notice.isExpanded" class="notice-content">{{ notice.content }}</p>
        <p v-else class="notice-content">{{ notice.content.substring(0, 70) }}...</p>
        <button class="more-btn" @click="toggleExpand(notice.id)">
          {{ notice.isExpanded ? '접기' : '..더보기' }}
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.top-bar {
  background-color: #293A67;
  position: fixed;
  top: 0;
  left: 80px;
  padding: 14px 19px;
  width: 375px;
  z-index: 10;
  display: flex;
  align-items: center;
}

.top-bar-title {
  color: #FFFFFF;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
}

.body-container {
  position: fixed;
  top: 52px;
  left: 80px;
  width: 375px;
  z-index: 10;
  background-color: #EBF2FC;
  height: calc(100vh - 65px);
  overflow-y: auto;
}

.notice-item {
  margin-bottom: 15px;
  background: white;
  padding: 15px;
}

.notice-head {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.notice-title {
  font-size: 16px;
  font-weight: bold;
  color: #293A67;
}

.notice-date {
  font-size: 11px;
  color: #898989;
}

.notice-content {
  font-size: 11px;
  text-align: left;
  white-space: pre-line;
}

.more-btn {
  all: unset;
  cursor: pointer;
  font-size: 14px;
  font-weight: 600;
  color: #275DE8;
}

.more-btn:hover {
  color: #001c3e;
}
</style>