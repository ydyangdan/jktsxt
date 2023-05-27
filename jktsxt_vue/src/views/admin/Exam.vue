<template>
  <div class="home" style="padding: 20px">
    <div style="margin: 10px ">
      <el-button type="success" @click="add" size="large">新增</el-button>
      <el-input v-model="search"  placeholder="根据考试名称查询" style="width: 20%;margin-left: 300px;height: 40px" clearable >
        <template #suffix>
          <el-icon class="el-input__icon"><Search/></el-icon>
        </template>
      </el-input>
      <el-button type="success" style="margin-left:5px" @click="load" size="large">查询</el-button>
    </div>

    <el-table :data="tableData" stripe border style="width: 98%">

      <el-table-column prop="id" label="ID" width="80" sortable />
      <el-table-column prop="examName" label="考试名称" width="100" />
      <el-table-column prop="startTime" label="开始时间" width="200"/>
      <el-table-column prop="totalTime" label="总时长" width="100" />
      <el-table-column prop="location" label="考试地点" width="100" />
      <el-table-column prop="number" label="考生人数" width="100" />
      <el-table-column prop="teacher" label="监考教师" width="150"/>
      <el-table-column prop="content" label="考试描述" />

      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button  type="success" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="你确定删除吗?" @confirm="handleDelete(scope.row.id)">
            <template #reference="scope">
              <el-button  type="danger" size="small" >删除</el-button>
            </template>
          </el-popconfirm>

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
      <el-dialog v-model="dialogFormVisible" >
        <el-form :model="form">
          <el-form-item label="id" :label-width="formLabelWidth">
            <el-input v-model="form.id" autocomplete="off" style="width: 80%" />
          </el-form-item>
          <el-form-item label="考试名称" :label-width="formLabelWidth">
            <el-input v-model="form.examName" autocomplete="off" style="width: 80%" />
          </el-form-item>
          <el-form-item label="开始时间" :label-width="formLabelWidth">
            <el-date-picker
                v-model="form.startTime"
                type="datetime"
                placeholder="请选择考试开始时间"
            />
          </el-form-item>
          <el-form-item label="总时长" :label-width="formLabelWidth">
            <el-input type="number" v-model="form.totalTime" autocomplete="off" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="考试地点" :label-width="formLabelWidth">
            <el-input v-model="form.location" autocomplete="off" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="考生人数" :label-width="formLabelWidth">
            <el-input v-model="form.number" autocomplete="off" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="监考教师" :label-width="formLabelWidth">
            <el-input v-model="form.teacher" autocomplete="off" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="考试描述" :label-width="formLabelWidth">
            <el-input v-model="form.content" autocomplete="off" style="width: 80%"/>
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
  name: 'Exam',
  components: {

  },
  data(){
    return {
      flag:false,
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

      },
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
    //编辑
    handleEdit(row){
      this.flag=true;
      this.form=JSON.parse(JSON.stringify(row));
      this.dialogFormVisible = true;
    },
    //删除
    handleDelete(val){
      //删除
      request.delete("/exam/"+val).then(
          res=>{
            if (res.code==0){
              //小提示
              this. $message({
                type : "success" ,
                message:res.msg
              })
              this.load();
            }else {
              //小提示
              this. $message({
                type : "error" ,
                message:res.msg
              })
            }
          }
      )
    },
    //分页
    handleSizeChange(){
      this.load();
    },
    handleCurrentChange(){
      this.load();
    },
    add(){
      this.dialogFormVisible = true;
      this.form={};
    },
    // 保存数据到后端
    save(){
      if (this.flag){
        this.flag=false;
        //编辑
        request.put("/exam",this.form).then(
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

      }else {  //新增
        request.post("/exam/save",this.form).then(
            res=>{
              if (res.code==0){
                //小提示
                this. $message({
                  type : "success" ,
                  message:res.msg
                })
                this.load();
              }else {
                //小提示
                this. $message({
                  type : "error" ,
                  message:res.msg
                })
              }
              this.load();
              this.dialogFormVisible = false;
            }
        )
      }
    }
  },
}

</script>

<style scoped>

</style>
