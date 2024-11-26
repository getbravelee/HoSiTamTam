<template>
  <div>
    <!-- 지도 -->
    <div id="map"></div>

    <!-- 카테고리 선택 -->
    <ul id="category">
      <li v-for="(category) in categories" :key="category.id"
          :class="{'on': currCategory === category.id}"
          @click="onCategoryClick(category)">
        <span :class="['category_bg', category.icon]"></span>
        <span>{{ category.name }}</span>
      </li>
    </ul>

    <!-- 지도 확대, 축소 컨트롤 div -->
    <div class="zoom-control">
        <span @click="zoomIn">
          <font-awesome-icon :icon="['fas', 'plus']" size="xl"/>
        </span>
      <span @click="zoomOut">
          <font-awesome-icon :icon="['fas', 'minus']" size="xl"/>
        </span>
    </div>

  </div>
</template>

<script setup>
import {ref, onMounted, watch} from 'vue';
import {useRegionStore} from "@/stores/Region";
import {eventBus} from "@/eventBus/eventBus";

// 카카오 지도 관련 변수
const map = ref(null);
const placeOverlay = ref(null);
const contentNode = ref(null);
const markers = ref([]);

// 카테고리 정보 (아이콘, 이름 등)
const categories = ref([
  {id: 'MT1', name: '마트', icon: 'mart'},
  {id: 'PM9', name: '약국', icon: 'pharmacy'},
  {id: 'SW8', name: '지하철', icon: 'subway'},
  {id: 'HP8', name: '병원', icon: 'hospital'},
  {id: 'PS3', name: '유치원', icon: 'store'},
  {id: 'SC4', name: '학교', icon: 'school'},
]);

const currCategory = ref(''); // 현재 선택된 카테고리
let polygons = [];
let detailMode = 0; // 0: 시도, 1: 시군구, 2: 읍면동
let level = '';
const regionStore = useRegionStore();

// 카카오 지도 API와 관련된 함수들
const loadMap = () => {
  const container = document.getElementById('map');
  const options = {
    center: new window.kakao.maps.LatLng(37.566826, 126.9786567),
    level: 12
  };

  map.value = new window.kakao.maps.Map(container, options);
  placeOverlay.value = new window.kakao.maps.CustomOverlay({zIndex: 2});
  contentNode.value = document.createElement('div');
  contentNode.value.className = 'placeinfo_wrap';

  // 지도에 폴리곤 추가
  init('/json/sido.json');
  window.kakao.maps.event.addListener(map.value, 'zoom_changed', onZoomChanged);

  // 중심의 지역명
  window.kakao.maps.event.addListener(map.value, 'center_changed', onMapMoveEnd);
  updateRegionNameFromCenter();
};

// 지도 확대 축소
const zoomIn = () => {
  map.value.setLevel(map.value.getLevel() - 1);
};

const zoomOut = () => {
  map.value.setLevel(map.value.getLevel() + 1);
};

// 현재 지도 중심 좌표에 해당하는 행정 구역 이름을 찾는 함수
const updateRegionNameFromCenter = () => {
  const center = map.value.getCenter();
  const lat = center.getLat();
  const lng = center.getLng();

  const geocoder = new window.kakao.maps.services.Geocoder();
  geocoder.coord2RegionCode(lng, lat, (result, status) => {
    if (status === window.kakao.maps.services.Status.OK) {
      const regionName = result[0].address_name;
      regionStore.setRegionName(regionName);
      console.log("현재 지역: ", regionName);
    }
  });
};

// 지도 중심이 변경될 때마다 실행되는 함수
const onMapMoveEnd = () => {
  updateRegionNameFromCenter();
};


// JSON 데이터 불러오기 및 폴리곤 생성
const init = (path) => {
  const url = new URL(path, window.location.origin);
  // path 경로의 json 파일 파싱
  fetch(url)
      .then(response => response.json())
      .then(geojson => {
        const units = geojson.features; // JSON "features" key의 값
        const areas = [];

        units.forEach((unit) => {
          const coordinates = unit.geometry.coordinates[0];
          const name = unit.properties.SIG_KOR_NM;
          const location = unit.properties.SIG_CD;

          const area = {
            name: name,
            path: coordinates.map(coordinate => new window.kakao.maps.LatLng(coordinate[1], coordinate[0])),
            location: location,
          };
          areas.push(area);
        });

        // 지도에 영역 데이터로 폴리곤 표시
        areas.forEach(area => {
          displayArea(area)
        });
      }).catch(error => {
    console.error("Error fetching areas:", error);
  });
};

const init2 = (path) => {
  const url = new URL(path, window.location.origin);
  fetch(url)
      .then(response => response.json())
      .then(geojson => {
        const units = geojson.features; // JSON "features" key의 값
        const areas = [];

        units.forEach((unit) => {
          const coordinates = unit.geometry.coordinates[0];
          const name = unit.properties.EMD_ENG_NM;
          const location = unit.properties.EMD_CD;

          const area = {
            name: name,
            path: coordinates.map(coordinate => new window.kakao.maps.LatLng(coordinate[1], coordinate[0])),
            location: location,
          };
          areas.push(area);
        });

        areas.forEach(area => {
          displayArea(area)
        });
      }).catch(error => {
    console.error("Error fetching areas:", error);
  });
};

const currentAreaName = ref('');
// 폴리곤을 지도에 표시하는 함수
const displayArea = (area) => {
  const polygon = new window.kakao.maps.Polygon({
    map: map.value,
    path: area.path,
    strokeWeight: 2,
    strokeColor: '#004c80',
    strokeOpacity: 0.8,
    fillColor: '#fff',  // 중심 지역일 경우만 강조
    fillOpacity: 0.7,
  });
  polygons.push(polygon);

  window.kakao.maps.event.addListener(polygon, 'mouseover', (mouseEvent) => {
    if (currentAreaName.value !== area.name) {
      polygon.setOptions({fillColor: '#09f'});
      placeOverlay.value.setContent(`<div class="area">${area.name}</div>`);
      placeOverlay.value.setPosition(mouseEvent.latLng);
      placeOverlay.value.setMap(map.value);
      currentAreaName.value = area.name;
    } else {
      polygon.setOptions({fillColor: '#09f'});
    }
  });

  window.kakao.maps.event.addListener(polygon, 'mouseout', () => {
    polygon.setOptions({fillColor: '#fff'});
    placeOverlay.value.setMap(null);
  });

  window.kakao.maps.event.addListener(polygon, 'click', (mouseEvent) => {
    if (detailMode === 0) {
      map.value.setLevel(10);
      map.value.panTo(mouseEvent.latLng);
    } else if (detailMode === 1) {
      map.value.setLevel(5);
      map.value.panTo(mouseEvent.latLng);
    } else {
      console.log(regionStore.regionName);
    }
  });
};

const removePolygon = () => {
  polygons.forEach(polygon => polygon.setMap(null));
  polygons = [];
};

// 줌 레벨 변경에 따라 json 파일을 변경하는 함수
const onZoomChanged = () => {
  level = map.value.getLevel();
  if (detailMode !== 1 && level <= 10) {
    detailMode = 1;
    removePolygon();
    init('/json/sig.json');
  } else if (detailMode !== 2 && level <= 6) {
    detailMode = 2;
    removePolygon();
    init2('/json/emd.json');
    console.log("읍면동 출력 예정");
  } else {
    if (detailMode !== 0) {
      detailMode = 0;
      removePolygon();
      init('/json/sido.json');
    }
  }
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
  ps.categorySearch(category, placesSearchCB, {useMapBounds: true});
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
  // const order = categories.value.findIndex(category => category.id === currCategory.value);
  places.forEach(place => {
    const marker = addMarker(new window.kakao.maps.LatLng(place.y, place.x));
    window.kakao.maps.event.addListener(marker, 'click', () => {
      displayPlaceInfo(place);
    });
  });
};

const categoryIcons = {
  MT1: require('@/assets/mart-32.png'),
  PM9: require('@/assets/pharm-32.png'),
  SW8: require('@/assets/sub-32.png'),
  HP8: require('@/assets/hospital-32.png'),
  PS3: require('@/assets/kinder-32.png'),
  SC4: require('@/assets/school-32.png'),
};

const addMarker = (position) => {
  const imageSrc = categoryIcons[currCategory.value];
  const imageSize = new window.kakao.maps.Size(32, 32);
  const imgOptions = {offset: new window.kakao.maps.Point(11, 28)};

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

const addMarkersToMap = (aptList) => {
  if (!aptList || aptList.length === 0) return;

  removeMarker();

  aptList.forEach(apt => {
    const latLng = new window.kakao.maps.LatLng(apt.lat, apt.lng);
    const imageSrc = require('@/assets/home--32.png');
    const imageSize = new window.kakao.maps.Size(32, 32);
    const imgOptions = {offset: new window.kakao.maps.Point(11, 28)};

    const markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions);
    const marker = new window.kakao.maps.Marker({
      position: latLng,
      image: markerImage
    });

    // window.kakao.maps.event.addListener(marker, 'click', () => {
    //   showApartmentInfo(apt);
    // });

    marker.setMap(map.value);
  });
};

// 아파트 목록 수신 (EventBus)
watch(() => eventBus.aptList, (newAptList) => {
  if (newAptList) {
    addMarkersToMap(newAptList); // 데이터가 갱신되면 마커 추가
  }
});

const displayPlaceInfo = (place) => {
  const style = document.createElement('style');
  style.innerHTML = `
    .placeinfo:hover {
      color: #FFFFFF !important;
      background-color: #30467B !important;
    }
  `;
  document.head.appendChild(style);

  const content = `
  <div class="placeinfo" style="position: relative; bottom: 50px; padding: 5px; border-radius: 6px; border: 1px solid #ccc; border-bottom: 2px solid #ddd; float: left; box-shadow: 0px 1px 2px #888; background-color: #FFFFFF">
    <a class="title" href="${place.place_url}" target="_blank" style="display: block; text-decoration: none; color: #000; text-align: center; border-radius: 6px; font-size: 14px; font-weight: bold; overflow: hidden;">
      ${place.place_name}
    </a>
  </div>
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
#map {
  width: 100%;
  height: 100vh;
  position: relative;
  overflow: hidden;
}

#category {
  position: absolute;
  top: 14px;
  left: 50%;
  transform: translateX(-50%);
  padding: 0;
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

.zoom-control {
  position: absolute;
  bottom: 50px;
  right: 20px;
  width: 36px;
  height: 80px;
  overflow: hidden;
  z-index: 1;
  background-color: #fffdfd;
  border: 1px #bfbfbf solid;
  border-radius: 5px;
}

.zoom-control span {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 36px;
  height: 40px;
  cursor: pointer;
}

.zoom-control span:first-child {
  border-bottom: 1px solid #bfbfbf;
}
</style>