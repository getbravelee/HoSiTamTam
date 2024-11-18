<template>
  <div>
    <h2>카테고리별 장소 검색하기</h2>
    <div id="map" style="width:100%;height:350px;position:relative;overflow:hidden;"></div>

    <ul id="category">
      <li v-for="(category) in categories" :key="category.id"
          :class="{'on': currCategory === category.id}"
          @click="onCategoryClick(category)">
        <span :class="['category_bg', category.icon]"></span>
        {{ category.name }}
      </li>
    </ul>

    <!-- 지도 확대, 축소 컨트롤 div -->
    <div class="custom_zoomcontrol radius_border">
        <span @click="zoomIn">
          <img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/ico_plus.png" alt="확대">
        </span>
      <span @click="zoomOut">
          <img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/ico_minus.png" alt="축소">
        </span>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

// 카카오 지도 관련 변수
const map = ref(null);
const placeOverlay = ref(null);
const contentNode = ref(null);
const markers = ref([]);

// 카테고리 정보 (아이콘, 이름 등)
const categories = ref([
  { id: 'BK9', name: '은행', icon: 'bank' },
  { id: 'MT1', name: '마트', icon: 'mart' },
  { id: 'PM9', name: '약국', icon: 'pharmacy' },
  { id: 'OL7', name: '주유소', icon: 'oil' },
  { id: 'CE7', name: '카페', icon: 'cafe' },
  { id: 'CS2', name: '편의점', icon: 'store' }
]);

const currCategory = ref(''); // 현재 선택된 카테고리

// 카카오 지도 API와 관련된 함수들
const loadMap = () => {
  const container = document.getElementById('map');
  const options = {
    center: new window.kakao.maps.LatLng(37.566826, 126.9786567),
    level: 5
  };

  map.value = new window.kakao.maps.Map(container, options);
  placeOverlay.value = new window.kakao.maps.CustomOverlay({ zIndex: 2 });
  contentNode.value = document.createElement('div');
  contentNode.value.className = 'placeinfo_wrap';
};

// 지도 확대
const zoomIn = () => {
  map.value.setLevel(map.value.getLevel() - 1);
};

// 지도 축소
const zoomOut = () => {
  map.value.setLevel(map.value.getLevel() + 1);
};

const onCategoryClick = (category) => {
  currCategory.value = currCategory.value === category.id ? '' : category.id;
  placeOverlay.value.setMap(null); // 선택 시 오버레이 숨기기
  removeMarker(); // 마커 제거
  if (currCategory.value) {
    searchPlaces(currCategory.value); // 선택된 카테고리로 장소 검색
  }
};

const searchPlaces = (category) => {
  if (!category) return;
  const ps = new window.kakao.maps.services.Places(map.value);
  ps.categorySearch(category, placesSearchCB, { useMapBounds: true });
};

const placesSearchCB = (data, status) => {
  if (status === window.kakao.maps.services.Status.OK) {
    displayPlaces(data);
  } else if (status === window.kakao.maps.services.Status.ZERO_RESULT) {
    // 검색 결과가 없는 경우 처리
  } else if (status === window.kakao.maps.services.Status.ERROR) {
    // 에러 처리
  }
};

const displayPlaces = (places) => {
  const order = categories.value.findIndex(category => category.id === currCategory.value);
  places.forEach(place => {
    const marker = addMarker(new window.kakao.maps.LatLng(place.y, place.x), order);
    window.kakao.maps.event.addListener(marker, 'click', () => {
      displayPlaceInfo(place);
    });
  });
};

const addMarker = (position, order) => {
  const imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png';
  const imageSize = new window.kakao.maps.Size(27, 28);
  const imgOptions = {
    spriteSize: new window.kakao.maps.Size(72, 208),
    spriteOrigin: new window.kakao.maps.Point(46, order * 36),
    offset: new window.kakao.maps.Point(11, 28)
  };

  const markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions);
  const marker = new window.kakao.maps.Marker({
    position: position,
    image: markerImage
  });

  marker.setMap(map.value);
  markers.value.push(marker);
  return marker;
};

const removeMarker = () => {
  markers.value.forEach(marker => marker.setMap(null));
  markers.value = [];
};

const displayPlaceInfo = (place) => {
  let content = `
    <div class="placeinfo">
      <a class="title" href="${place.place_url}" target="_blank">${place.place_name}</a>
  `;

  if (place.road_address_name) {
    content += `
      <span>${place.road_address_name}</span>
      <span class="jibun"> (지번 : ${place.address_name})</span>
    `;
  } else {
    content += `<span>${place.address_name}</span>`;
  }

  content += `
      <span class="tel">${place.phone}</span>
    </div>
    <div class="after"></div>
  `;

  contentNode.value.innerHTML = content;
  placeOverlay.value.setContent(contentNode.value);
  placeOverlay.value.setPosition(new window.kakao.maps.LatLng(place.y, place.x));
  placeOverlay.value.setMap(map.value);
};

// 카카오 지도 API 스크립트 로드 함수
const loadScript = () => {
  const script = document.createElement('script');
  script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${process.env.VUE_APP_MAP_KEY}&autoload=false&libraries=services`;
  script.onload = () => window.kakao.maps.load(loadMap);
  document.head.appendChild(script);
};

// 컴포넌트가 마운트될 때 API 스크립트가 로드되었는지 확인하고 로드
onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    loadMap();
  } else {
    loadScript();
  }
});
</script>

<style scoped>
#category {
  position: absolute;
  top: 10px;
  left: 10px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 2;
}
#category li {
  float: left;
  list-style: none;
  width: 50px;
  border-right: 1px solid #acacac;
  padding: 6px 0;
  text-align: center;
  cursor: pointer;
}
#category li.on {
  background: #eee;
}
#category li:hover {
  background: #ffe6e6;
  border-left: 1px solid #acacac;
  margin-left: -1px;
}
#category li:last-child {
  margin-right: 0;
  border-right: 0;
}
#category li span {
  display: block;
  margin: 0 auto 3px;
  width: 27px;
  height: 28px;
}
.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}
.placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
.placeinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #d95050;
  background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center;
}
.placeinfo .tel {
  color: #0f7833;
}
.placeinfo .jibun {
  color: #999;
  font-size: 11px;
  margin-top: 0;
}

.custom_zoomcontrol {
  position: absolute;
  top: 50px;
  right: 10px;
  width: 36px;
  height: 80px;
  overflow: hidden;
  z-index: 1;
  background-color: #f5f5f5;
}

.custom_zoomcontrol span {
  display: block;
  width: 36px;
  height: 40px;
  text-align: center;
  cursor: pointer;
}

.custom_zoomcontrol span img {
  width: 15px;
  height: 15px;
  padding: 12px 0;
  border: none;
}

.custom_zoomcontrol span:first-child {
  border-bottom: 1px solid #bfbfbf;
}
</style>