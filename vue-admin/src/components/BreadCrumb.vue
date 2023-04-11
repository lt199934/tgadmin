<template>
  <el-breadcrumb separator="/">
    <el-breadcrumb-item v-for="item in breadcrumbList" :to="{path:item.path}" :key="item.path">
<!--        <span v-if="item.pid">{{ item.meta.title }}</span>-->
<!--        <a v-else @click.prevent="handleLink(item)">{{ item.meta.title }}</a>-->
        {{item.meta.title}}
    </el-breadcrumb-item>
  </el-breadcrumb>
</template>

<script>
export default {
  name: "BreadCrumb",
  computed: {
    //控制台打印看结构
    breadcrumbList() {
        // console.log(this.$route.matched)
      return this.$route.matched;
    }
  },
    methods:{
        handleLink(val) {

        }
    }
}
</script>

<style scoped>

</style>