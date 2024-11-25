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

    return { placesData, addPlaceData, resetPlaceData };
})