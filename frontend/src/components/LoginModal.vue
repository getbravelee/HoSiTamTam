<script setup>
import {defineEmits, onMounted, ref, watch} from 'vue';
import axios from "axios";
import {useCookies} from "vue3-cookies";

const { cookies } = useCookies();

const emit = defineEmits(['closeModal']);

const tab = ref('signIn'); // Default tab is Sign In
const username = ref('');
const id = ref('');
const password = ref('');
const repeatPassword = ref('');
const email = ref('');
const rememberMe = ref(false);
const errors = ref({
  id: '',
  password: '',
  repeatPassword: '',
  email: '',
  username: ''
});

// 유효성 검사 함수
const validateId = () => {
  if (id.value.length < 4 && id.value !== '') {
    errors.value.id = '아이디는 4자 이상이어야 합니다';
  } else {
    errors.value.id = '';
  }
};

const validatePassword = () => {
  if (password.value.length < 8 && password.value != '') {
    errors.value.password = '비밀번호는 7자 이상이어야 합니다.';
    return false;
  } else {
    errors.value.password = '';
    return true;
  }
};

const validateRepeatPassword = () => {
  if (password.value !== repeatPassword.value) {
    errors.value.repeatPassword = '비밀번호가 일치하지 않습니다';
  } else {
    errors.value.repeatPassword = '';
  }
};

const validateEmail = () => {
  const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
  if (email.value && !emailPattern.test(email.value)) {
    errors.value.email = '유효한 이메일 주소를 입력해주세요';
  } else {
    errors.value.email = '';
  }
};

const validateUsername = () => {
  if (username.value.trim() === '') {
    errors.value.username = '사용자 이름을 입력해주세요';
  } else {
    errors.value.username = '';
  }
};

// 각 필드 감시
watch([id, password, repeatPassword, email, username], () => {
  if (tab.value === 'signUp') {
    validateId();
    validatePassword();
    validateRepeatPassword();
    validateEmail();
    validateUsername();
  }
});

// 페이지 로드 시 저장된 쿠키 있는지 확인
onMounted(() => {
  const savedId = cookies.get('userId');
  if (savedId) {
    id.value = savedId;
    rememberMe.value = true;
  }
})

// 로그인 버튼 클릭 시
function handleSignIn() {
  if (!id.value.trim()) {
    errors.value.id = '아이디를 입력해주세요';
  } else {
    errors.value.id = '';
  }

  if (!password.value.trim()) {
    errors.value.password = '비밀번호를 입력해주세요';
  } else {
    errors.value.password = '';
  }

  if (errors.value.id || errors.value.password) {
    return;
  }

  axios.post('/auth/login', {
    userLoginId: id.value,
    userPassword: password.value,
  }).then((response) => {
    if (response.data.code === 1) {
      console.log(response.data.msg);
      // 아이디 저장
      if (rememberMe.value) {
        cookies.set('userId', id.value, '7d'); // 7일 쿠키 저장
      } else {
        cookies.remove('userId');
      }
      localStorage.setItem('authToken', response.data.data); // 임시로 로컬에 토큰 저장
      emit('closeModal');
    } else {
      console.log(response.data.msg);
    }
  }) .catch((error) => {
    console.log(error.message);
  })
}

// 회원가입 버튼 클릭 시
function handleSignUp() {
  const isIdValid = validateId.value;
  const isPasswordValid = validatePassword.value;
  const isRepeatPasswordValid = validateRepeatPassword.value;
  const isEmailValid = validateEmail.value;
  const isUsernameValid = validateUsername.value;

  if (!isIdValid || !isPasswordValid || !isEmailValid || !isUsernameValid || !isRepeatPasswordValid) {
    alert("모든 필드를 정확히 입력해주세요.");
    return;
  }

  axios.post('/auth/register', {
    userLoginId: id.value,
    userPassword: password.value,
    userNickname: username.value,
    userEmail: email.value,
  }).then((response) => {
    if (response.data.code === 1) {
      console.log(response.data.msg);
      alert("회원가입이 완료되었습니다.");
      emit('closeModal'); // 다시 로그인 탭으로?
    } else {
      alert(response.data.msg);
    }
  }).catch((error) => {
    console.error(error.message);
  });
}
</script>

<template>
  <div class="modal-overlay" @click.self="emit('closeModal')">
    <div class="login-wrap">
      <div class="login-html">
        <input id="tab-1" type="radio" v-model="tab" value="signIn" class="sign-in"/>
        <label for="tab-1" class="tab">Sign In</label>
        <input id="tab-2" type="radio" v-model="tab" value="signUp" class="sign-up"/>
        <label for="tab-2" class="tab">Sign Up</label>

        <div class="login-form">
          <!-- Sign In Form -->
          <div :class="{'sign-in-htm': true, 'show': tab === 'signIn'}">
            <div class="group">
              <label for="id" class="label">Id</label>
              <input v-model="id" id="id" type="text" class="input"/>
              <p v-if="errors.id" class="error-message">{{ errors.id }}</p>
            </div>
            <div class="group">
              <label for="pass" class="label">Password</label>
              <input v-model="password" id="pass" type="password" class="input"/>
              <p v-if="errors.password" class="error-message">{{ errors.password }}</p>
            </div>
            <div class="group">
              <input v-model="rememberMe" id="check" type="checkbox" class="check"/>
              <label for="check"><span class="icon"></span> Keep me Signed in</label>
            </div>
            <div class="group">
              <input type="submit" class="button" value="Sign In" @click="handleSignIn"/>
            </div>
            <div class="hr"></div>
            <div class="foot-lnk">
              <a href="#forgot">Forgot Password?</a>
            </div>
          </div>

          <!-- Sign Up Form -->
          <div :class="{'sign-up-htm': true, 'show': tab === 'signUp'}">
            <div class="group">
              <label for="user" class="label">Username</label>
              <input v-model="username" id="user" type="text" class="input"/>
              <p v-if="errors.username" class="error-message">{{ errors.username }}</p>
            </div>
            <div class="group">
              <label for="id" class="label">Id</label>
              <input v-model="id" id="id" type="text" class="input"/>
              <p v-if="errors.id" class="error-message">{{ errors.id }}</p>
            </div>
            <div class="group">
              <label for="pass" class="label">Password</label>
              <input v-model="password" id="pass" type="password" class="input"/>
              <p v-if="errors.password" class="error-message">{{ errors.password }}</p>
            </div>
            <div class="group">
              <label for="pass" class="label">Repeat Password</label>
              <input v-model="repeatPassword" id="pass" type="password" class="input"/>
              <p v-if="errors.repeatPassword" class="error-message">{{ errors.repeatPassword }}</p>
            </div>
            <div class="group">
              <label for="email" class="label">Email Address</label>
              <input v-model="email" id="email" type="text" class="input"/>
              <p v-if="errors.email" class="error-message">{{ errors.email }}</p>
            </div>
            <div class="group">
              <input type="submit" class="button" value="Sign Up" @click="handleSignUp"/>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
body {
  margin: 0;
  //color: #6a6f8c;
  //background: #c8c8c8;
  font: 600 16px/18px 'Open Sans', sans-serif;
}

*,
:after,
:before {
  box-sizing: border-box;
}

.clearfix:after,
.clearfix:before {
  content: '';
  display: table;
}

.clearfix:after {
  clear: both;
  display: block;
}

a {
  color: rgba(255, 255, 255, 0.6);
  text-decoration: none;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.login-wrap {
  width: 100%;
  margin: auto;
  max-width: 525px;
  min-height: 670px;
  background: #2C3650;
  border-radius: 30px;
  box-shadow: 0 12px 15px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19);
  position: relative;
}

.login-html {
  width: 100%;
  height: 100%;
  position: absolute;
  padding: 50px 70px 50px 70px;
  text-align: left;
}

.login-html .sign-in-htm,
.login-html .sign-up-htm {
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  position: absolute;
  transform: rotateY(180deg);
  backface-visibility: hidden;
  transition: all .4s linear;
  opacity: 0;
}

.login-html .sign-in-htm.show,
.login-html .sign-up-htm.show {
  transform: rotateY(0);
  opacity: 1;
}

input[type=radio],
input[type=checkbox] {
  display: none;
}

.tab {
  display: inline-block;
  font-size: 22px;
  font-weight: 700;
  padding-bottom: 5px;
  margin: 0 15px 10px 0;
  color: rgba(255, 255, 255, 0.6);
  border-bottom: 2px solid transparent;
  text-transform: uppercase;
  cursor: pointer;
}

.tab:hover {
  color: #fff;
}

.sign-in:checked + .tab,
.sign-up:checked + .tab {
  color: #fff;
  border-color: #1161ee;
}


.login-form {
  min-height: 345px;
  position: relative;
  margin-top: 20px;
  //perspective:1000px;
  transform-style: preserve-3d;
}

.group {
  margin-bottom: 15px;
}

.label,
.input,
.button {
  width: 100%;
  color: #fff;
}

.login-form .group .label {
  color: #aaa;
  font-size: 12px;
}

.input,
.button {
  border: none;
  padding: 10px 20px;
  border-radius: 25px;
  background: rgba(255, 255, 255, 0.1);
}

.button {
  background: #1161ee;
  cursor: pointer;
  margin-top: 20px;
}

.input[data-type='password'] {
  text-security: circle;
  -webkit-text-security: circle;
}

.login-form .group label .icon {
  width: 15px;
  height: 15px;
  border-radius: 2px;
  position: relative;
  display: inline-block;
  background: rgba(255, 255, 255, .1);
}

.login-form .group label .icon:before,
.login-form .group label .icon:after {
  content: '';
  width: 10px;
  height: 2px;
  background: #fff;
  position: absolute;
  transition: all .2s ease-in-out 0s;
}

.login-form .group label .icon:before {
  left: 3px;
  width: 5px;
  bottom: 6px;
  transform: scale(0) rotate(0);
}

.login-form .group label .icon:after {
  top: 6px;
  right: 0;
  transform: scale(0) rotate(0);
}

.login-form .group .check:checked + label {
  color: #fff;
}

.login-form .group .check:checked + label .icon {
  background: #1161ee;
}

.login-form .group .check:checked + label .icon:before {
  transform: scale(1) rotate(45deg);
}

.login-form .group .check:checked + label .icon:after {
  transform: scale(1) rotate(-45deg);
}

.hr {
  height: 2px;
  background: rgba(255, 255, 255, 0.2);
  margin: 60px 0 50px;
}

.foot-lnk {
  color: rgba(255, 255, 255, 0.6);
  text-align: center;
}

.error-message {
  color: red;
  font-size: 12px;
  margin-top: 5px;
}
</style>