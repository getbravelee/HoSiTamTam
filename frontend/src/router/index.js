import { createRouter, createWebHistory } from "vue-router";
import MainView from "@/views/MainView.vue";
import MapView from "@/views/MapView.vue";
import NoticeView from "@/views/NoticeView.vue";
import SearchView from "@/views/SearchView.vue";
import SavedView from "@/views/SavedView.vue";
import RegionListView from "@/views/RegionListView.vue";
import AptListView from "@/views/AptListView.vue";
import AptDetailView from "@/views/AptDetailView.vue";

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
            path: "/saved",
            name: "saved",
            component: SavedView,
        },
        {
            path: "/search",
            name: "search",
            component: SearchView,
            children: [
                {
                    path: "",
                    name: "regionList",
                    component: RegionListView,
                    props: true,
                },
                {
                    path: "apartments/:region",
                    name: "aptList",
                    component: AptListView,
                    props: true,
                },
                {
                    path: "aptDetail/:aptId",
                    name: "aptDetail",
                    component: AptDetailView,
                    props: true,
                },
            ]
        },
    ],
});

export default router;
