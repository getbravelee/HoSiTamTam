<script setup>
import { ref } from 'vue';
import CommentItem from '@/components/CommentItem.vue';
import { useRouter } from "vue-router";

const comments = ref([
  {
    username: '랑이',
    date: '2024.11.02',
    content: "강남 한복판에 위치해 있어서 교통이 매우 편리합니다. 주변에 마트와 카페도 많고, 아파트 내부는 매우 깨끗하고 관리가 잘 되어 있습니다. 특히 헬스장과 수영장이 마음에 듭니다.",
    isHelpChecked: false,
  },
  {
    username: '랑이',
    date: '2024.11.02',
    content: "가격에 비해서 괜찮은 아파트입니다. 내부 시설은 잘 갖춰져 있으나, 가끔 엘리베이터가 고장 나는 점이 불편하네요. 위치는 정말 좋습니다. 강남에서 살아본 적이 없어 이 곳이 정말 마음에 듭니다.",
    isHelpChecked: false,
  },
  {
    username: '랑이',
    date: '2024.11.02',
    content: "이 아파트에서 살게 되어 정말 행복합니다. 보안이 철저하고, 대로변과 가까워서 상업시설이 풍부해서 정말 편리해요. 바닥과 벽이 두꺼워 소음 차단도 잘 되어 있어서 이웃과의 관계도 좋습니다.",
    isHelpChecked: false,
  },
]);

// 댓글 추가
const newComment = ref('');
const uploadedImages = ref([]);
const maxImages = 3;

const addComment = () => {
  if (newComment.value.trim()) {
    comments.value.push({
      username: '랑이',
      date: new Date().toISOString().split('T')[0],
      content: newComment.value,
      images: uploadedImages.value.slice(),
    });
    newComment.value = '';
    uploadedImages.value = [];
  }
};

const handleFileUpload = (event) => {
  const files = Array.from(event.target.files);
  if (uploadedImages.value.length + files.length <= maxImages) {
    files.forEach((file) => {
      const reader = new FileReader();
      reader.onload = () => {
        uploadedImages.value.push(reader.result);
      };
      reader.readAsDataURL(file);
    });
  }
};

const removeImage = (index) => {
  uploadedImages.value.splice(index, 1);
};

// 페이지 안 특정 위치로 이동 (댓글 작성란)
const inputSection = ref(null);
const moveInputSection = () => {
  inputSection.value.scrollIntoView({behavior: "smooth"});
}

// 이전 페이지로 이동하기
const router = useRouter();
const goBack = () => {
  router.back();
}

// 더보기 버튼 클릭 시 실행
const commentsPerPage = 3;
const visibleComments = ref(comments.value.slice(0, commentsPerPage));
const showMore = ref(true);

const loadMoreComments = () => {
  const currentLength = visibleComments.value.length;
  if (currentLength < comments.value.length) {
    const nextComments = comments.value.slice(currentLength, currentLength + commentsPerPage);
    visibleComments.value.push(...nextComments);
  }

  if (visibleComments.value.length >= comments.value.length) {
    showMore.value = false;
  }
};
</script>

<template>
  <div>
    <div class="result-bar">
      <font-awesome-icon :icon="['far', 'pen-to-square']" size="lg" @click="moveInputSection()"/>
      아파트 이야기
      <font-awesome-icon :icon="['fas', 'xmark']" size="xl" @click="goBack()"/>
    </div>

    <div class="body-container">
      <div class="input-container" ref="inputSection">
        <div class="input-box">
          <div class="author">작성자: 랑이</div>
          <textarea
              class="text-area"
              v-model="newComment"
              placeholder="이야기가 풀어질 공간"
          ></textarea>
          <div class="input-btns">
            <label class="camera-icon">
              <input type="file" multiple accept="image/*" @change="handleFileUpload" hidden />
              <font-awesome-icon :icon="['fas', 'camera']" size="xl" />
            </label>
            <div class="image-previews">
              <p v-if="uploadedImages.length === 0" class="image-limit-message">
                최대 3장까지 추가할 수 있습니다.
              </p>
              <div v-for="(image, index) in uploadedImages" :key="index" class="thumbnail">
                <img :src="image" alt="Preview" />
                <button @click="removeImage(index)">
                  <font-awesome-icon :icon="['far', 'circle-xmark']" size="sm" />
                </button>
              </div>
            </div>
            <button class="submit-btn" @click="addComment">등록</button>
          </div>
          <div v-if="uploadedImages.length >= maxImages" class="no-more-images">
            더 이상 추가할 수 없습니다.
          </div>
        </div>
      </div>
      <div class="comment-list">
        <CommentItem
            v-for="(comment, index) in visibleComments"
            :key="index"
            :comment="comment"
        />

        <button
            v-if="showMore"
            class="more-btn"
            @click="loadMoreComments"
        >
          더보기
          <font-awesome-icon :icon="['fas', 'angle-down']" />
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.result-bar {
  position: fixed;
  top: 0;
  left: 455px;
  width: 375px;
  padding: 16px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #ffffff;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
  color: #000000;
  font-size: 18px;
  z-index: 11;
}

.body-container {
  position: fixed;
  top: 59px;
  left: 455px;
  width: 375px;
  //padding: 20px;
  background-color: #ffffff;
  z-index: 10;
  height: calc(100vh - 59px);
  overflow-y: auto;
  -ms-overflow-style: none;
}

.body-container::-webkit-scrollbar {
  display: none;
}

.input-container {
  padding: 13px 17px;
  background-color: #EBF2FC;
}

.input-box {
  padding: 12px 13px;
  border: 1px solid #ddd;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  text-align: left;
  background-color: #FFFFFF;
}

.author {
  font-size: 14px;
  margin-bottom: 5px;
}

.text-area {
  width: 100%;
  height: 140px;
  border: 1px solid #ddd;
  padding: 8px;
  font-size: 14px;
  margin-bottom: 8px;
  resize: none;
}

.input-btns {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}

.camera-icon {
  color: #9098a7;
  margin: 0 10px 0 0;
  cursor: pointer;
}

.image-limit-message {
  font-size: 12px;
  color: #aaa;
  margin: 0;
}

.no-more-images {
  color: red;
  font-size: 12px;
}

.image-previews {
  display: flex;
  gap: 10px;
  flex: 1;
}

.thumbnail {
  position: relative;
  width: 35px;
  height: 35px;
}

.thumbnail img {
  width: 100%;
  height: 100%;
  border-radius: 4px;
  object-fit: cover;
}

.thumbnail button {
  all: unset;
  position: absolute;
  top: -3px;
  right: 0;
  color: red;
  cursor: pointer;
}

.submit-btn {
  all: unset;
  background-color: #293A67;
  color: white;
  padding: 5px 16px;
  border-radius: 8px;
  font-size: 15px;
  cursor: pointer;
}

.comment-list {
  background-color: #FFFFFF;
  padding: 20px;
}

.more-btn {
  all: unset;
  cursor: pointer;
  font-size: 14px;
  font-weight: 600;
  color: #293a67;
  padding: 5px 10px;
  display: inline-block;
  margin-top: 8px;
  text-align: center;
  border-radius: 25px;
  border: 1px solid #293A67;
}

.more-btn:hover {
  color: #3e62c0;
}
</style>