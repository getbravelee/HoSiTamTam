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
      userMessage: '',  // 사용자가 입력한 메시지
      chatResponse: null,  // 챗봇의 응답을 저장
    };
  },
  methods: {
    sendMessage() {
      if (this.userMessage.trim() === '') return;  // 공백 메시지 전송 방지

      // Spring Boot 서버의 /api/chat 엔드포인트로 POST 요청
      axios.post('/api/chat', this.userMessage)
          .then(response => {
            this.chatResponse = response.data;  // 챗봇 응답 저장
            this.userMessage = '';  // 입력 필드 초기화
          })
          .catch(error => {
            console.error("Error sending message:", error);
            this.chatResponse = "챗봇과의 연결에 문제가 발생했습니다. 다시 시도해주세요.";  // 오류 처리
          });
    }
  }
};
</script>

<style scoped>
/* 스타일 추가 */
.chatbot {
  margin-top: 20px;
  width: 100%;
  max-width: 600px;
  margin-left: auto;
  margin-right: auto;
}

textarea {
  width: 100%;
  resize: none;
  padding: 10px;
  font-size: 14px;
  margin-top: 10px;
}

button {
  margin-top: 10px;
  padding: 10px 15px;
  background-color: #4CAF50;
  color: white;
  border: none;
  cursor: pointer;
  font-size: 14px;
  border-radius: 5px;
}

button:hover {
  background-color: #45a049;
}

h3 {
  margin-top: 20px;
}

p {
  background-color: #f1f1f1;
  padding: 15px;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
</style>
