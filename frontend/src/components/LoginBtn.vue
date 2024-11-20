<script setup>
import { ref, computed } from 'vue';
import LoginModal from "@/components/LoginModal.vue";
import {useUserStore} from "@/stores/user";

const userStore = useUserStore();
const showModal = ref(false);

const buttonClass = computed(() => ({
  'login-button': true,
}));

const toggleModal = () => {
  showModal.value = !showModal.value;
};

const handleLogout = () => {
  userStore.removeAuthToken();
};
</script>

<template>
  <div>
    <button v-if="userStore.isLogin" @click="handleLogout" :class="buttonClass" >
      로그아웃
    </button>
    <button v-else @click="toggleModal" :class="buttonClass">
      로그인
    </button>

    <LoginModal v-if="showModal" :showModal="showModal" @closeModal="showModal = false" />
  </div>
</template>

<style scoped>
.login-button {
  position: fixed;
  top: 14px;
  right: 20px;
  background-color: #3b5998;
  color: white;
  padding: 8px 15px;
  border: none;
  border-radius: 8px;
  font-size: 18px;
  cursor: pointer;
  z-index: 1000;
}
</style>