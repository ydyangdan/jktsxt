import { createRouter, createWebHashHistory } from 'vue-router'
import AdminLayout from "@/layout/AdminLayout";
import TeacherLayout from "@/layout/TeacherLayout";
import Login from "@/views/Login";

const routes = [
  //默认
  {
    path: '/',
    name: 'index',
    component: Login,
  },
    // 教师
  {
    path: '/teacher',
    name: 'teacherLayout',
    component: TeacherLayout,
    children:[

      {
        path: '/teacherExam',
        name: 'teacherExam',
        component:  () => import('@/views/teacher/TeacherExam.vue')
      },
      {
        path: '/teacherNotice',
        name: 'teacherNotice',
        component:  () => import('@/views/teacher/TeacherNotice.vue')
      },
      {
        path: '/teacherLeave',
        name: 'teacherLeave',
        component:  () => import('@/views/teacher/TeacherLeave.vue')
      },
      {
        path: '/teacherRemind',
        name: 'teacherRemind',
        component:  () => import('@/views/teacher/TeacherRemind.vue')
      },
      {
        path: '/personalInfo',
        name: 'personalInfo',
        component:  () => import('@/views/teacher/PersonalInfo.vue')
      },

   ]
  },

    //管理员
  {
    path: '/admin',
    name: 'adminLayout',
    component: AdminLayout,
    // redirect: "/home",
    children:[
      {
        path: '/user',
        name: 'user',
        component:  () => import('@/views/admin/User.vue')
      },
      {
        path: '/exam',
        name: 'exam',
        component:  () => import('@/views/admin/Exam.vue')
      },
      {
        path: '/notice',
        name: 'notice',
        component:  () => import('@/views/admin/Notice.vue')
      },
      {
        path: '/leave',
        name: 'leave',
        component:  () => import('@/views/admin/Leave.vue')
      },
      {
        path: '/adminPersonalInfo',
        name: 'adminPersonalInfo',
        component:  () => import('@/views/admin/AdminPersonalInfo.vue')
      },

    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/views/Register.vue')
  },
  {
    path: '/test',
    name: 'test',
    component: () => import('@/views/test.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
