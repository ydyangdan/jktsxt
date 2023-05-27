<template>
 <div>
   <!--    头部-->
   <TeacherHeader />
   <!--    主体-->
   <div style="display: flex">
     <!--      菜单栏-->
     <TeacherAside />
     <!--      内容区域-->
     <router-view style="flex: 1"/>

   </div>
 </div>
</template>

<script>
import TeacherAside from "@/components/TeacherAside";
import TeacherHeader from "@/components/TeacherHeader";

export default {
  name: "TeacherLayout",
  components:{
    TeacherHeader,
    TeacherAside
  }
}
</script>

<style scoped>

</style>