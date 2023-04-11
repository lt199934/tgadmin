<template>
    <div id="app">
        <div class="collapse">
            <span :class="collapseBtnClass" @click="collapse"></span>
            <div>
                <BreadCrumb/>
            </div>
        </div>
        <el-dropdown trigger="click">
      <span class="el-dropdown-link">
        <div class="headImg">
          <el-avatar size="large" :src="user.avatarUrl"
                     class="mr-5">user
          </el-avatar>
        </div>
        {{ user.nickname }}<i class="el-icon-arrow-down ml-5"></i>
      </span>

            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>
                    <router-link to="/person">个人信息</router-link>
                </el-dropdown-item>
                <el-dropdown-item>
                    <router-link to="/upwd">修改密码</router-link>
                </el-dropdown-item>
                <el-dropdown-item>
                    <span @click="logout">退出</span>
                </el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>

<script>
import BreadCrumb from "@/components/BreadCrumb.vue";

export default {
    name: "Header",
    components: {BreadCrumb},
    props: {
        collapseBtnClass: String,
        avatarUrl: String,
        collapse: {
            type: Function,
            default: () => {
                return Function
            }
        },
        user: Object
    },
    data() {
        return {}
    },
    methods: {
        logout() {
            this.request.get("/user/logout").then(res => {
                if (res.code === '200') {
                    this.$store.commit("logout");
                    this.$message.success("退出成功");
                } else {
                    this.$message.success("退出失败");
                }
            })

        }
    }


}
</script>

<style lang="scss">
#app {
  font-size: 12px;
  line-height: 60px;
  display: flex
}

.collapse {
  flex: 1;
  font-size: 18px
}

.collapse span {
  cursor: pointer
}

.collapse div {
  display: inline-block;
  margin-left: 10px;
}

.el-dropdown {
  width: 150px;
  cursor: pointer;
  text-align: right
}

.headImg {
  display: inline-block;
}

.headImg .el-avatar {
  position: relative;
  top: 11px
}

.el-dropdown-menu {
  width: 100px;
  text-align: center
}

.el-dropdown-item {
  font-size: 14px;
  padding: 5px 0
}

a {
  text-decoration: none;
  color: #606266
}
</style>