<script setup>
import { ref, computed } from 'vue';
import LoginModal from "@/components/LoginModal.vue";

const isLoggedIn = ref(false);
const showModal = ref(false);

const buttonText = computed(() => (isLoggedIn.value ? '로그아웃' : '로그인'));

const buttonClass = computed(() => ({
  'login-button': true,
  // 'logged-in': isLoggedIn.value, // 로그인 성공 시 css 변경?
}));

const toggleModal = () => {
  showModal.value = !showModal.value;
};

</script>

<template>
  <div>
    <button @click="toggleModal" :class="buttonClass">
      {{ buttonText }}
    </button>

    <LoginModal v-if="showModal" :showModal="showModal" @closeModal="showModal = false" />
  </div>
</template>

<style scoped>
.login-button {
  position: fixed;
  top: 14px;
  right: 20px;
  background-color: #3b5998; /* Dark blue color */
  color: white;
  padding: 8px 15px;
  border: none;
  border-radius: 8px;
  font-size: 18px;
  cursor: pointer;
  z-index: 1000;
}
</style>