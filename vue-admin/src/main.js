import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
import './assets/gloable.css'
import request from "@/utils/request";
import store from "./store"
import axios from 'axios'
//引入路由配置
import router from './router'
import mavonEditor from "mavon-editor";
import "mavon-editor/dist/css/index.css";

Vue.use(mavonEditor);


axios.defaults.withCredentials = true
Vue.prototype.request = request
Vue.use(ElementUI,{size: 'mini'});
Vue.config.http = request
new Vue({
    router,
    store,
    el: '#app',
    render: h => h(App),
}).$mount('#app')
