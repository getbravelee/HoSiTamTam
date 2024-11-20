import { createRouter, createWebHistory } from "vue-router";
import MainView from "@/views/MainView.vue";
import MapView from "@/views/MapView.vue";
import NoticeView from "@/views/NoticeView.vue";
import SearchView from "@/views/SearchView.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: "/",
            name: "main",
            component: MainView,
        },
        {
            path: "/map",
            name: "map",
            component: MapView,
        },
        {
            path: "/notice",
            name: "notice",
            component: NoticeView,
        },
        {
            path: "/search",
            name: "search",
            component: SearchView,
        }
    ],
});

export default router;
