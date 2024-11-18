import { createRouter, createWebHistory } from "vue-router";
import MainView from "@/views/MainView.vue";
import MapView from "@/views/MapView.vue";

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
    ],
});

export default router;
