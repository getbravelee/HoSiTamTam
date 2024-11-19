<template>
  <div class="chatbot">
    <h2>챗봇</h2>
    <textarea v-model="userMessage" placeholder="챗봇과 대화하기..." rows="3"></textarea>
    <button @click="sendMessage">전송</button>
    <div v-if="chatResponse">
      <h3>챗봇 응답:</h3>
      <p>{{ chatResponse }}</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      userMessage: '',
      chatResponse: null
    };
  },
  methods: {
    sendMessage() {
      if (this.userMessage.trim() === '') return;
      // OpenAI API 호출을 위한 AJAX 요청
      axios.post('/api/chat', this.userMessage) // userMessage를 직접 보내기
          .then(response => {
            this.chatResponse = response.data; // 챗봇의 응답 저장
            this.userMessage = ''; // 입력 필드 초기화
          })
          .catch(error => {
            console.error("Error sending message:", error);
          });
    }

  }
};
</script>

<style scoped>
/* 스타일 추가 */
.chatbot {
  margin-top: 20px;
}
textarea {
  width: 100%;
  resize: none;
}
button {
  margin-top: 10px;
}
</style>
