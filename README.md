# 权限管理系统

## 项目简介

该项目是一款基于SpringBoot + Vue2 +Jwt+Elementui的管理系统。

主要以自定义拦截器和jwt结合进行权限验证

##  技术选型

**1、系统环境**

- Java EE 8
- Servlet 3.0
- Apache Maven 3
- Mysql 5.5

**2、主框架**

- Spring Boot 2.4.2
- Jwt
- hutool
- redis
- swagger
- mybatis-plus-generator

**3、持久层**

- Apache MyBatis 3.5.x
- Apache MyBatis -Plus 3.5.x
- Alibaba Druid 1.2.x

**4、视图层**

- Aplayer
- 一言
- mavon-editor
- echarts
- Element ui
- Vue2

## 内置功能

- 欢迎页：介绍系统技术选型和统计

- 系统管理：整个系统的管理包括用户菜单
  - 用户管理：用户是系统操作者，该功能主要完成系统用户配置。
  - 菜单管理：配置系统菜单，操作权限，按钮权限标识等。
  - 角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
  - 字典管理：对系统中经常使用的一些较为固定的数据进行维护。
  - 文件管理：对系统上传文件进行管理。

- 文章管理：发布和修改文章，以及文章分类。

- 系统接口：根据业务代码自动生成相关的api接口文档。

- 连接池监视：监视当期系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。

## 动态路由配置

```js
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
                console.log(itemCMenu)
                homeRoutes.children.push(itemPMenu);
            }
        })

        console.log(homeRoutes)
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
```



## 运行

1，导入创建数据库并导入sql文件

2，导入idea中，修改配置文件

3，运行服务，http://localhost:8082/

4,，运行前端：

```
npm install
或
yarn
```

```
npm run serve
或
yarn serve
```

## 部分效果图展示

![](.\img\注册页面.png)

![登录页面](.\img\登录页面.png)

![首页](.\img\首页.png)

![菜单管理](.\img\菜单管理.png)

![分类管理](.\img\分类管理.png)

![个人主页](.\img\个人主页.png)

![修改密码](.\img\修改密码.png)

![角色管理](.\img\角色管理.png)

![权限菜单](.\img\权限菜单.png)

![添加角色](.\img\添加角色.png)

![添加用户](.\img\添加用户.png)

![添加字典](.\img\添加字典.png)

![文件管理](.\img\文件管理.png)

![文章管理](.\img\文章管理.png)

![](.\img\编辑文章.png)

![写文章](.\img\写文章.png)

![修改用户](.\img\修改用户.png)

![用户管理](.\img\用户管理.png)

![下线用户](.\img\下线用户.png)

![字典管理](.\img\字典管理.png)

![接口文档](.\img\接口文档.png)

## 关注作者

https://ltbk.net

