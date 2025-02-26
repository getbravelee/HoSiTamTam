import {defineStore} from "pinia";
import {ref} from "vue";

export const usePlaceStore = defineStore('place', () => {
    const placesData = ref([]);

    const addPlaceData = function (newData) {
        placesData.value.push(...newData);
    }

    const resetPlaceData = function () {
        placesData.value = [];
    }

    // 카테고리별로 데이터를 그룹화하는 함수
    const getCategoryItems = () => {
        const categoryItems = {
            '대형마트': [],
            '약국': [],
            '지하철': [],
            '병원': [],
            '어린이집,유치원': [],
            '학교': [],
        };

        // placesData를 카테고리별로 그룹화
        placesData.value.forEach(place => {
            if (categoryItems[place.category_group_name]) {
                categoryItems[place.category_group_name].push(place);
            }
        });

        return categoryItems;
    };

    return { placesData, addPlaceData, resetPlaceData, getCategoryItems };
})