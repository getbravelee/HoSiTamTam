<script setup>
import {defineEmits, ref} from 'vue';

const emit = defineEmits(['closeModal']);

const tab = ref('signIn'); // Default tab is Sign In
const username = ref('');
const password = ref('');
const repeatPassword = ref('');
const email = ref('');
const rememberMe = ref(false);

function handleSignIn() {
  // Handle Sign In logic
  console.log('Sign In', username.value, password.value, rememberMe.value);
}

function handleSignUp() {
  // Handle Sign Up logic
  console.log('Sign Up', username.value, password.value, repeatPassword.value, email.value);
}
</script>

<template>
  <div class="modal-overlay" @click.self="emit('closeModal')">
    <div class="login-wrap">
      <div class="login-html">
        <input id="tab-1" type="radio" v-model="tab" value="signIn" class="sign-in" />
        <label for="tab-1" class="tab">Sign In</label>
        <input id="tab-2" type="radio" v-model="tab" value="signUp" class="sign-up" />
        <label for="tab-2" class="tab">Sign Up</label>

        <div class="login-form">
          <!-- Sign In Form -->
          <div :class="{'sign-in-htm': true, 'show': tab === 'signIn'}">
            <div class="group">
              <label for="user" class="label">Username</label>
              <input v-model="username" id="user" type="text" class="input" />
            </div>
            <div class="group">
              <label for="pass" class="label">Password</label>
              <input v-model="password" id="pass" type="password" class="input" />
            </div>
            <div class="group">
              <input v-model="rememberMe" id="check" type="checkbox" class="check" />
              <label for="check"><span class="icon"></span> Keep me Signed in</label>
            </div>
            <div class="group">
              <input type="submit" class="button" value="Sign In" @click="handleSignIn" />
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
              <input v-model="username" id="user" type="text" class="input" />
            </div>
            <div class="group">
              <label for="pass" class="label">Password</label>
              <input v-model="password" id="pass" type="password" class="input" />
            </div>
            <div class="group">
              <label for="pass" class="label">Repeat Password</label>
              <input v-model="repeatPassword" id="pass" type="password" class="input" />
            </div>
            <div class="group">
              <label for="email" class="label">Email Address</label>
              <input v-model="email" id="email" type="text" class="input" />
            </div>
            <div class="group">
              <input type="submit" class="button" value="Sign Up" @click="handleSignUp" />
            </div>
            <div class="hr"></div>
            <div class="foot-lnk">
              <label for="tab-1">Already Member?</label>
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
}

.login-wrap {
  width: 100%;
  margin: auto;
  max-width: 525px;
  min-height: 670px;
  background: #2C3650;
  border-radius: 30px;
  box-shadow: 0 12px 15px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19);
  position:relative;
}

.login-html {
  width:100%;
  height:100%;
  position:absolute;
  padding: 90px 70px 50px 70px;
  text-align: left;
}

.login-html .sign-in-htm,
.login-html .sign-up-htm{
  top:0;
  left:0;
  right:0;
  bottom:0;
  position:absolute;
  transform:rotateY(180deg);
  backface-visibility:hidden;
  transition:all .4s linear;
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



.login-form{
  min-height:345px;
  position:relative;
  //perspective:1000px;
  transform-style:preserve-3d;
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

.login-form .group .label{
  color:#aaa;
  font-size:12px;
}

.input,
.button {
  border: none;
  padding: 15px 20px;
  border-radius: 25px;
  background: rgba(255, 255, 255, 0.1);
}

.button {
  background: #1161ee;
  cursor: pointer;
}

.input[data-type='password'] {
  text-security: circle;
  -webkit-text-security: circle;
}

.login-form .group label .icon{
  width:15px;
  height:15px;
  border-radius:2px;
  position:relative;
  display:inline-block;
  background:rgba(255,255,255,.1);
}
.login-form .group label .icon:before,
.login-form .group label .icon:after{
  content:'';
  width:10px;
  height:2px;
  background:#fff;
  position:absolute;
  transition:all .2s ease-in-out 0s;
}
.login-form .group label .icon:before{
  left:3px;
  width:5px;
  bottom:6px;
  transform:scale(0) rotate(0);
}
.login-form .group label .icon:after{
  top:6px;
  right:0;
  transform:scale(0) rotate(0);
}
.login-form .group .check:checked + label{
  color:#fff;
}
.login-form .group .check:checked + label .icon{
  background:#1161ee;
}
.login-form .group .check:checked + label .icon:before{
  transform:scale(1) rotate(45deg);
}
.login-form .group .check:checked + label .icon:after{
  transform:scale(1) rotate(-45deg);
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
</style>