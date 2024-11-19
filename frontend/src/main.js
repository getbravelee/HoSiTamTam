import {createApp} from 'vue'
import App from './App.vue'
import router from "@/router";

// fontawesome
import {library} from '@fortawesome/fontawesome-svg-core'
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'
import {fas} from '@fortawesome/free-solid-svg-icons'

// 부트스트랩
import BootstrapVue3 from 'bootstrap-vue-3'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'

library.add(fas);

const app = createApp(App);

app.component('font-awesome-icon', FontAwesomeIcon);
app.use(BootstrapVue3);
app.use(router);

app.mount('#app');