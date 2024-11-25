<template>
  <div class="sidebar">
    <ul>
      <RouterLink :to="{ name: 'main' }" class="no-decoration">
        <li>
          <font-awesome-icon :icon="['fas', 'house']" size="2xl"/>
          <span>Home</span>
        </li>
      </RouterLink>
      <RouterLink :to="{ name: 'map' }" class="no-decoration">
        <li>
          <font-awesome-icon :icon="['fas', 'map']" size="2xl"/>
          <span>Map</span>
        </li>
      </RouterLink>
      <li @click="goToSaved">
        <font-awesome-icon :icon="['fas', 'bookmark']" size="2xl"/>
        <span>Saved</span>
      </li>
      <li @click="openMyPage">
        <font-awesome-icon :icon="['fas', 'user']" size="2xl"/>
        <span>MyPage</span>
      </li>
    </ul>

    <MyPageModal v-if="isModalOpen" :isModalOpen="isModalOpen" @closeModal="isModalOpen = false"/>
  </div>
</template>

<script>
import {defineComponent, ref} from "vue";
import MyPageModal from "@/components/MyPageModal.vue";
import {useRouter} from "vue-router";
import {useUserStore} from "@/stores/user";

export default defineComponent({
  components: {MyPageModal},
  setup() {
    const isModalOpen = ref(false);
    const userStore = useUserStore();
    const router = useRouter();

    const goToSaved = () => {
      if (userStore.isLogin) {
        router.push('/saved');
      } else {
        alert('로그인이 필요합니다.');
      }
    };

    const openMyPage = () => {
      if (userStore.isLogin) {
        isModalOpen.value = !isModalOpen.value;
      } else {
        alert('로그인이 필요합니다.');
      }
    };

    return {
      isModalOpen,
      goToSaved,
      openMyPage,
    };
  }
})
</script>

<style scoped>
.sidebar {
  position: fixed;
  top: 0;
  left: 0;
  width: 80px;
  height: 100vh;
  background-color: #f4f4f4;
  box-sizing: border-box;
  overflow-y: auto;
  z-index: 100;
}

ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

li {
  margin: 20px 0;
  display: flex;
  align-items: center;
  flex-direction: column;
}

span {
  font-size: 14px;
  font-weight: bold;
  color: #333;
}

.no-decoration {
  text-decoration: none;
  color: black;
}
</style>
