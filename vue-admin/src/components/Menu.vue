<template>
    <el-menu :default-openeds="opens" class="el-menu-vertical"
             background-color="rgb(48,65,86)"
             text-color="#fff"
             active-text-color="#ffd04b"
             :collapse-transition="false"
             :collapse="isCollapse"
             router>
        <div class="logo">
            <img src="../assets/logo.png" alt="加载失败">
            <b v-show="logoTextShow">{{ systemName }}</b>;
        </div>
        <template v-for="item in menus">
            <!-- 一级菜单 -->
            <el-menu-item v-if="item.children.length===0" :index="item.path" :key="item.path">
                <i :class="item.icon"></i>
                <span slot="title">{{ item.name }}</span>
            </el-menu-item>
            <!--二级菜单-->
            <el-submenu v-else :index="item.id + ''" :key="item.id">
                <template slot="title">
                    <i :class="item.icon"></i>
                    <span slot="title">{{ item.name }}</span>
                </template>
                <!--三级菜单-->
                <template v-for="sub in item.children">
                    <el-menu-item :index="sub.path" :key="sub.id">
                        <i :class="sub.icon"></i>
                        <span slot="title">{{ sub.name }}</span>
                    </el-menu-item>
                </template>
            </el-submenu>
        </template>
    </el-menu>
</template>

<script>
export default {
    // eslint-disable-next-line vue/multi-word-component-names
    name: 'Menu',
    props: {
        isCollapse: Boolean,
        logoTextShow: Boolean,
        systemName: String,
        menus: {
            type: Array,
            default: () => {
                return []
            }
        },
        opens: {
            type: Array,
            default: () => {
                return []
            }
        },
    },
    data() {
        return {}
    },
    methods: {
        load() {

        }
    }
}
</script>

<style scoped>
/*菜单*/
.el-menu {
    min-height: 100%;
    overflow-x: hidden
}

/*logo*/
.logo {
    height: 60px;
    line-height: 60px;
    text-align: center
}

/*logo图标*/
.logo img {
    width: 20px;
    position: relative;
    top: 5px;
    /*margin-right: 5px;*/
}

/*logo系统名*/
.logo b {
    font-size: 15px;
    color: #fff;
    text-align: center;
    margin-left: 5px;
}

</style>