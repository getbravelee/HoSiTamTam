// src/utils/eventBus.js
import {reactive, watch} from 'vue';

export const eventBus = reactive({
    // 데이터 전송용 빈 객체
    emit(event, data) {
        this[event] = data;
    },
    on(event, callback) {
        // event를 구독하고, 데이터가 변경될 때마다 callback 호출
        watch(() => this[event], callback);
    }
});
