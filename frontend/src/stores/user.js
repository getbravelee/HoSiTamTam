import { defineStore } from 'pinia';
import { useCookies } from 'vue3-cookies';
import { onMounted, ref } from "vue";

const { cookies } = useCookies();

export const useUserStore = defineStore('user', () => {
    const authToken = ref("");
    const isLogin = ref(false);

    onMounted(() => {
        const savedToken = cookies.get('authToken');
        if (savedToken) {
            authToken.value = savedToken;
            isLogin.value = true;
        }
    });

    const setAuthToken = function (token) {
        authToken.value = token;
        isLogin.value = true;
        cookies.set('authToken', token);
    }

    const removeAuthToken = function () {
        authToken.value = "";
        isLogin.value = false;
        cookies.remove('authToken');
    }

    return { authToken, isLogin, setAuthToken, removeAuthToken };
});
