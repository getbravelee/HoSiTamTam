<script setup>
import {defineProps, ref} from 'vue';

const props = defineProps({
  comment: {
    type: Object,
    required: true
  }
});

const localComment = ref({ ...props.comment });
</script>

<template>
  <div class="comment-box">
    <div class="comment-header">
      <font-awesome-icon :icon="['fas', 'circle-user']" class="icon" size="lg" style="color: #a6a6a6;" />
      <div class="writer-info">
        <div class="username">{{ localComment.username }}</div>
        <div class="date">{{ localComment.date }}</div>
      </div>
      <input type="checkbox" id="help-checkbox" class="help-checkbox">
      <input type="checkbox" v-model="localComment.isHelpChecked" :id="'help-checkbox-' + index" class="help-checkbox">
      <label :for="'help-checkbox-' + index" class="help-label">
        <font-awesome-icon :icon="['far', 'thumbs-up']" class="icon"/>
        <span>도움돼요</span>
      </label>
    </div>
    <div class="comment-content">
      {{ localComment.content }}
    </div>
    <div class="comment-footer">
      <div class="help-count">0명에게 도움이 됐어요</div>
    </div>
  </div>
</template>

<style scoped>
.comment-box {
  border-radius: 15px;
  padding: 12px 10px;
  margin-bottom: 10px;
  background-color: #F3F8FF;
}

.comment-header {
  display: flex;
  align-items: center;
}

.icon {
  text-align: center;
  margin-right: 5px;
}

.writer-info {
  flex: 1;
  text-align: left;
  line-height: 12px;
}

.username {
  font-size: 12px;
  font-weight: bold;
}

.date {
  font-size: 10px;
  color: #a1a1a1;
}

.help-checkbox {
  display: none;
}

.help-label {
  cursor: pointer;
  font-size: 14px;
  color: #757575;
  display: flex;
  align-items: center;
  transition: color 0.2s ease;
}

.help-label:hover {
  color: #608fd3;
}

.help-checkbox:checked + .help-label {
  color: #007bff;
}

.comment-content {
  text-align: left;
  font-size: 14px;
  line-height: 1.6;
  white-space: pre-wrap;
  margin: 8px 0;
}

.comment-footer {
  font-size: 12px;
  color: #AFAFAF;
  text-align: right;
}
</style>