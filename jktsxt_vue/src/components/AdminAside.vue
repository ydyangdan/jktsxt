<template>
  <div>
    <el-menu
        style="width: 200px; min-height: calc(100vh - 50px)"
        active-text-color="#1cb669"
        background-color="#545c64"
        class="el-menu-vertical-demo"
        default-active="2"
        router
        text-color="#fff"
        @open="handleOpen"
        @close="handleClose"
    >
<!--      <el-sub-menu index="1">-->
<!--        <template #title>-->
<!--          <el-icon><Grid /></el-icon>-->
<!--          <span>试题管理</span>-->
<!--        </template>-->
<!--        <el-menu-item index="queryQuestion">查询试题</el-menu-item>-->
<!--        <el-menu-item index="addQuestion">上传试题</el-menu-item>-->
<!--        <el-menu-item index="questionBank">题库管理</el-menu-item>-->
<!--      </el-sub-menu>-->

      <el-menu-item index="user">
        <el-icon><User /></el-icon>
        <span>用户管理</span>
      </el-menu-item>

      <el-menu-item index="exam">
        <el-icon><Tickets /></el-icon>
        <span>考试管理</span>
      </el-menu-item>

      <el-menu-item index="leave">
        <el-icon><User /></el-icon>
        <span>请假管理</span>
      </el-menu-item>

      <el-menu-item index="notice">
        <el-icon><User /></el-icon>
        <span>公告管理</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "AdminAside"
}
</script>

<style scoped>

</style>