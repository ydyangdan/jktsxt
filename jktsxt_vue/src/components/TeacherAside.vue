<template>
<div>
      <el-menu
          style="width: 200px; min-height: calc(100vh - 50px)"
          active-text-color="#1cb669"
          background-color="#55585a"
          class="el-menu-vertical-demo"
          default-active="2"
          text-color="#fff"
          router
          @open="handleOpen"
          @close="handleClose"
      >

        <el-menu-item index="teacherExam">
          <el-icon><setting /></el-icon>
          <span>我的监考</span>
        </el-menu-item>

        <el-menu-item index="teacherLeave">
          <el-icon><User /></el-icon>
          <span>请假管理</span>
        </el-menu-item>

        <el-menu-item index="teacherNotice">
          <el-icon><User /></el-icon>
          <span>公告中心</span>
        </el-menu-item>

        <el-menu-item index="teacherRemind">
          <el-icon><User /></el-icon>
          <span>监考提醒</span>
        </el-menu-item>

<!--        <el-sub-menu index="1">-->
<!--            <template #title>-->
<!--              <el-icon><location /></el-icon>-->
<!--              <span>监考提醒</span>-->
<!--            </template>-->
<!--            <el-menu-item index="teacherQueryQuestion">微信提醒</el-menu-item>-->
<!--            <el-menu-item index="teacherAddQuestion">短信提醒</el-menu-item>-->
<!--        </el-sub-menu>-->

      </el-menu>
</div>
</template>

<script>
export default {
  name: "TeacherAside"
}
</script>

<style scoped>

</style>