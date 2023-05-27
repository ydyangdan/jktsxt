<template>
  <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex">
    <span style="width: 60px"></span>
    <div style="flex: 1"><h1 align="center" class="pm pm1" style="size: 30px;color: #1cb669">教师监考智能提示系统-教师端</h1></div>
    <div class="items-center" style="width: 100px; padding-left: 30px;padding-top: 10px">
      <el-dropdown>
          <span class="el-dropdown-link">
             {{showUserName}}
             <el-icon class="el-icon--right">
               <arrow-down />
             </el-icon>
          </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item><el-link type="primary" href="/#/personalInfo" :underline="false">个人信息</el-link></el-dropdown-item>
            <el-dropdown-item @click="logout">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>


<script>
import Cookies from "js-cookie";

export default {
  name: "TeacherHeader",
  computed:{
    showUserName(){
      return Cookies.get("userName");
    }
  },
  methods:{
    logout(){
      Cookies.remove("userName");
      this.$router.push("/"); //跳转到登录页面
    }
  }
}
</script>

<style scoped>

</style>