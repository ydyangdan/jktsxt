<template>
  <div class="home" style="padding: 20px">
     <div style="margin: 10px ">
        <el-button type="success" @click="add" size="large">新增</el-button>
        <el-input v-model="search"  placeholder="根据用户名查询" style="width: 20%;margin-left: 300px;height: 40px" clearable >
            <template #suffix>
              <el-icon class="el-input__icon"><Search/></el-icon>
            </template>
        </el-input>
        <el-button type="success" style="margin-left:5px" @click="load" size="large">查询</el-button>
    </div>
    <el-table :data="tableData" stripe border style="width: 95%">

      <el-table-column prop="id" label="ID" width="100" sortable />
      <el-table-column prop="userName" label="用户名" width="100" />
      <el-table-column prop="password" label="密码" width="100" />
      <el-table-column prop="userRole" label="用户身份" width="100" />
      <el-table-column prop="remindType" label="提醒方式"  />
      <el-table-column prop="duration" label="提前时间" width="100" />
      <el-table-column prop="wechat" label="微信号" width="100" />
      <el-table-column prop="phone" label="手机号" width="100" />
      <el-table-column prop="mail" label="邮箱" width="100" />
      <el-table-column prop="userSex" label="用户性别" width="100" />

      <el-table-column fixed="right" align="center" label="操 作" width="150">
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
          <el-form-item label="用户名" :label-width="formLabelWidth">
            <el-input v-model="form.userName" autocomplete="off" style="width: 80%" />
          </el-form-item>
          <el-form-item label="密码" :label-width="formLabelWidth">
            <el-input type="password" v-model="form.password" autocomplete="off" style="width: 80%" clearable />
          </el-form-item>
          <el-form-item label="用户身份" :label-width="formLabelWidth">
            <el-radio-group v-model="form.userRole">
              <el-radio label="教师"></el-radio>
              <el-radio label="管理员"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="用户性别" :label-width="formLabelWidth">
            <el-radio-group v-model="form.userSex">
              <el-radio label="男"></el-radio>
              <el-radio label="女"></el-radio>
              <el-radio label="未知"></el-radio>
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
  name: 'User',
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
        userRole:"教师"
      },
      tableData: []
    }
  },
  created() {
    this.load();
  },
  methods:{
    load(){
      request.get("/user/queryUser",{
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
    //删除
    handleDelete(val){
      //删除
      request.delete("/user/"+val).then(
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
      if (this.form.id){
        //编辑
        request.put("/user",this.form).then(
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
        request.post("/user/save",this.form).then(
            res=>{
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