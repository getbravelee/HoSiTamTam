import { defineStore } from 'pinia';
import {ref} from "vue";

export const useSearchStore = defineStore('search', () => {
    const query = ref("");
    const results = ref([]);

    const setQuery = function (newQuery) {
        query.value = newQuery;
    }

    const setResults = function (newResults) {
        results.value = newResults;
    }

    return { query, results, setQuery, setResults};
})