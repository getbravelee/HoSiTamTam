import { createRouter, createWebHistory } from "vue-router";
import MainView from "@/views/MainView.vue";
import MapView from "@/views/MapView.vue";
import NoticeView from "@/views/NoticeView.vue";
import SearchView from "@/views/SearchView.vue";
import SavedView from "@/views/SavedView.vue";

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
        },
        {
            path: "/saved",
            name: "saved",
            component: SavedView,
        }
    ],
});

export default router;
