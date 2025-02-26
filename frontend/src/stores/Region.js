import {defineStore} from "pinia";
import {ref} from "vue";

export const useRegionStore = defineStore('region', () => {
    const regionName = ref('');

    const setRegionName = function (Name) {
        regionName.value = Name;
    }

    const removeRegionName = function () {
        regionName.value = '';
    }

    return { regionName, setRegionName, removeRegionName };
})