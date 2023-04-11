import Vue from "vue";
import Vuex from "vuex"
import router from "@/router";

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    },
    getters: {
        getUserInfo: (state) => {
            return state.user;
        }
    },
    mutations: {
        logout() {
            localStorage.removeItem("user");
            localStorage.removeItem("menus");
            router.push("/login");
            // 重置路由
            resetRoutes();
        }
    },
    actions: {},
    modules: {}
})

export default store