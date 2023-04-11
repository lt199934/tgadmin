//该文件专门用来创建和管理整个应用的路由器
import Vue from "vue";
import VueRouter from "vue-router"

Vue.use(VueRouter)
//地址和组件的对应关系
const routes = [
    {
        path: '/login',
        name: 'login',
        meta: {
            title: '登录'
        },
        component: () => import('@/views/Login')
    },
    {
        path: '/register',
        name: 'register',
        meta: {
            title: '注册'
        },
        component: () => import('@/views/Register')
    },
    {
        path: '/404',
        name: 'NotFound',
        component: () => import('@/views/404')
    },
    {
        path: '/401',
        name: 'NotFound',
        component: () => import('@/views/401')
    }
]
const router = new VueRouter({
    routes,
    base: process.env.BASE_URL,
    mode: "history"
})

export const resetRoutes = () => {
    router.matcher = new VueRouter({
        routes,
        base: process.env.BASE_URL,
        mode: "history"
    })
}


export const setRoutes = () => {
    const storeMenus = localStorage.getItem("menus");
    if (storeMenus) {
        //一级路由
        const homeRoutes = {
            path: '/', name: 'Home', component: () => import('../views/Home.vue'), redirect: '/index',
            meta: {
                isAuto: false,//是否需要路由组件拦截
                title: '首页'
            },
            children: [{
                path: 'person',
                name: 'Person',
                component: () => import('@/views/Person'),
                meta: {title: '个人主页'},
            }, {
                path: 'upwd',
                name: 'Upwd',
                component: () => import('../views/Upwd.vue'),
                meta: {title: '修改密码'}
            }]
        }
        const menus = JSON.parse(storeMenus);
        let itemPMenu;
        let itemCMenu;
        //二级路由
        menus.forEach(item => {
            if (item.path) {
                let itemMenu = {
                    path: item.path.replace("/", ""),
                    name: item.name,
                    component: () => import('../views/' + item.component + '.vue'),
                    meta: {
                        title: item.name,
                        pid: item.pid
                    },
                    children: []
                }
                homeRoutes.children.push(itemMenu);
            } else if (item.children.length) {
                itemPMenu = {
                    path: item.path.replace("/", ""),
                    name: item.name,
                    component: {render(c) {return c('router-view')}},
                    meta: {
                        title: item.name,
                    },
                    children: []
                }
                //三级路由
                item.children.forEach(child => {
                    if (child.pid === item.id) {
                        itemCMenu = {
                            path: child.path,
                            name: child.name,
                            component: () => import('../views/' + child.component + '.vue'),
                            meta: {
                                title: child.name,
                                pid: child.pid
                            }
                        }
                        itemPMenu.children.push(itemCMenu);
                    }
                })
                homeRoutes.children.push(itemPMenu);
            }
        })
        // console.log(homeRoutes)
        const currentRoutes = router.getRoutes().map(v => v.name);
        if (!currentRoutes.includes("Home")) {
            router.addRoute(homeRoutes)
        }
    }
}
setRoutes();

// 前置路由守卫 在每个路由之前
// to 到哪去
// from 由哪来
// next 是否放行
router.beforeEach((to, from, next) => {
    if (to.matched.length === 0) {
        const storeMenus = localStorage.getItem("menus");
        if (storeMenus) {
            // next('/404');
        } else {
            next('/login');
        }
    } else {
        next();
    }

});
// 后置路由守卫
router.afterEach((to, from) => {
    document.title = to.meta.title;

})
export default router