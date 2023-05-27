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
    <el-table :data="tableData" stripe border style="width: 95%">

      <el-table-column prop="id" label="ID" width="100" sortable />
      <el-table-column prop="testName" label="考试名称" width="150" />
      <el-table-column prop="time" label="请假时间" width="200" />
      <el-table-column prop="reasons" label="请假原因" width="150" />
      <el-table-column prop="teacher" label="请假教师" width="150" />
      <el-table-column prop="state" label="状态" width="150" />
      <el-table-column prop="createTime" label="创建时间" />

      <el-table-column fixed="right" align="center" label="操 作" width="100">
        <template #default="scope">
          <el-button  type="success" size="default" @click="handleEdit(scope.row)">审核</el-button>
        </template>
      </el-table-column>
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


      <!--     弹窗-->
      <el-dialog v-model="dialogFormVisible" title="审核">
        <el-form :model="form">
          <el-form-item label="状态" :label-width="formLabelWidth">
            <el-radio-group v-model="form.state">
              <el-radio label="通过"></el-radio>
              <el-radio label="不通过"></el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save">
          确认
        </el-button>
      </span>
        </template>
      </el-dialog>

    </div>

  </div>
</template>

<script>
// @ is an alias to /src

import request from "@/utils/request";

export default {
  name: 'Leave',
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
      dialogTableVisible:false,
      dialogFormVisible:false,
      formLabelWidth :'140px',
      form:{
        userRole:"学生"
      },
      tableData: []
    }
  },
  created() {
    this.load();
  },
  methods:{
    load(){
      request.get("/leaves/queryLeaves",{
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
    handleEdit(row){
      this.form=JSON.parse(JSON.stringify(row));
      this.dialogFormVisible = true;
    },
    //分页
    handleSizeChange(){
      this.load();
    },
    handleCurrentChange(){
      this.load();
    },
    // 保存数据到后端
    save(){
        //编辑
        request.put("/leaves",this.form).then(
            res=>{
              if (res.code==0){
                //小提示
                this. $message({
                  type : "success" ,
                  message:res.msg
                })
                this.load();
                this.dialogFormVisible = false;
              }else {
                //小提示
                this. $message({
                  type : "error" ,
                  message:res.msg
                })
                this.dialogFormVisible = false;
              }
            }
        )
    }
  },
}

</script>

<style scoped>

</style>
