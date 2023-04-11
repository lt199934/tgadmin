<template>
    <el-container id="app">
        <el-aside :width="asideWidth+'px'">
            <Menu :isCollapse="isCollapse" :menus="menus" :opens="opens" :logoTextShow="logoTextShow"
                  :systemName="systemName"/>
        </el-aside>
        <el-container>
            <el-header style="">
                <Header :collapseBtnClass="collapseBtnClass" :user="user" :avatarUrl="avatarUrl" :collapse="collapse"/>
            </el-header>
            <el-main>
                <router-view @refreshUser="getUser"/>
            </el-main>
            <!--底部-->
            <el-footer
                    style="border-top: 1px solid #ccc;border-bottom: 1px solid #ccc;text-align: center;line-height: 60px">
                涛哥权限管理后台
            </el-footer>
        </el-container>
    </el-container>
</template>

<script>

import Menu from "@/components/Menu.vue";
import Header from "@/components/Header.vue";

export default {
    // eslint-disable-next-line vue/multi-word-component-names
    name: 'Manage',
    components: {
        Header,
        Menu,
    },
    data() {
        return {
            systemName: '后台管理系统',
            avatarUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
            title: '',
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            asideWidth: 200,
            logoTextShow: true,
            user: {},
            menus: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")) : [],
            opens: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")).map(v => v.id + '') : []
        }
    },
    created() {
        this.getUser();
    },
    methods: {
        // 菜单收缩
        collapse() {
            this.isCollapse = !this.isCollapse
            if (this.isCollapse) {
                this.asideWidth = 64
                this.collapseBtnClass = 'el-icon-s-unfold'
                this.logoTextShow = false
            } else {
                this.asideWidth = 200
                this.collapseBtnClass = 'el-icon-s-fold'
                this.logoTextShow = true
            }
        },
        getUser() {
            // 后台拿取最新用户信息
            this.request.get("/user/person").then(res => {
                this.user = res.data;
            });
        }
    }
};


</script>

<style lang="scss">
#app {
  width: 100%;
  height: 100%;
  padding: 0;
  margin: 0;
  border-top: 2px solid #eee
}

.el-header {
  border-bottom: 1px solid #ccc;
}

.el-aside {
  height: 100%;
  overflow-x: hidden;
  background-color: #409EFF;
}


.el-mian {
  background-color: #eaedf1;
}

</style>