import { defineStore } from 'pinia';

export const useAptListStore = defineStore('aptList', {
    state: () => ({
        aptList: [],
    }),
    actions: {
        setAptList(aptList) {
            this.aptList = aptList;
        },
        clearAptList() {
            this.aptList = [];
        },
    },
});
