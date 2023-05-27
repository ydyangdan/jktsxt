<template>
  <div class="home" style="padding: 20px">
    <div style="margin: 10px ">
      <el-input v-model="search"  placeholder="根据考试名称查询" style="width: 20%;height: 40px" clearable >
        <template #suffix>
          <el-icon class="el-input__icon"><Search/></el-icon>
        </template>
      </el-input>
      <el-button type="success" style="margin-left:5px" @click="load" size="large">查询</el-button>
    </div>

    <el-table :data="tableData" stripe border style="width: 98%">

      <el-table-column prop="id" label="ID" width="100" sortable />
      <el-table-column prop="examName" label="考试名称" width="200" />
      <el-table-column prop="startTime" label="开始时间" width="200"/>
      <el-table-column prop="totalTime" label="总时长" width="100" />
      <el-table-column prop="location" label="考试地点" width="150" />
      <el-table-column prop="number" label="考生人数" width="100" />
      <el-table-column prop="teacher" label="监考教师" width="100"/>
      <el-table-column prop="content" label="考试描述" />

    </el-table>

    <!--    分页-->
    <div style="margin: 10px 0">
      <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 20, 30]"
          :small="small"
          :disabled="disabled"
          :background="background"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />

    </div>

  </div>
</template>

<script>
// @ is an alias to /src

import request from "@/utils/request";

export default {
  name: 'TeacherExam',
  components: {

  },
  data(){
    return {
      search:"",
      currentPage:1,
      total:20,
      pageSize:10,
      small:false,
      background:false,
      disabled:false,
      tableData: []
    }
  },
  created() {
    this.load();
  },
  methods:{
    load(){
      request.get("/exam/queryExam",{
        params:{
          pageNum : this.currentPage ,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res=>{
        this.total=res.data.total;
        this.tableData=res.data.records;
      })
    },
    //分页
    handleSizeChange(){
      this.load();
    },
    handleCurrentChange(){
      this.load();
    },

  },
}

</script>

<style scoped>

</style>
