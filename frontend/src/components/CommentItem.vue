<script setup>
import {computed, defineProps, ref} from 'vue';

const props = defineProps({
  comment: {
    type: Object,
    required: true
  }
});

const localComment = ref({...props.comment});
const currentImageIndex = ref(0);

// Compute the total number of images
const totalImages = computed(() => localComment.value.images?.length || 0);

// Move to the previous image
const showPreviousImage = () => {
  currentImageIndex.value = (currentImageIndex.value - 1 + totalImages.value) % totalImages.value;
};

// Move to the next image
const showNextImage = () => {
  currentImageIndex.value = (currentImageIndex.value + 1) % totalImages.value;
};
</script>

<template>
  <div class="comment-box">
    <div class="comment-header">
      <font-awesome-icon :icon="['fas', 'circle-user']" class="icon" size="lg" style="color: #a6a6a6;"/>
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
      <div v-if="totalImages > 0" class="image-carousel">
        <img :src="localComment.images[currentImageIndex]" alt="Comment Image" class="carousel-image"/>
        <button v-if="totalImages > 1" class="carousel-btn left" @click="showPreviousImage">
          <font-awesome-icon :icon="['fas', 'circle-chevron-right']" size="xl" style="color: #7a7f85;" />
        </button>
        <button v-if="totalImages > 1" class="carousel-btn right" @click="showNextImage">
          <font-awesome-icon :icon="['fas', 'circle-chevron-right']" size="xl" style="color: #7a7f85;" />
        </button>
      </div>
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

.image-carousel {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 8px;
}

.carousel-image {
  width: 100%;
  border-radius: 8px;
}

.carousel-btn {
  all: unset;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
}

.carousel-btn.left {
  left: 8px;
}

.carousel-btn.right {
  right: 8px;
}

.comment-footer {
  font-size: 12px;
  color: #AFAFAF;
  text-align: right;
}
</style>