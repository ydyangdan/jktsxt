<template xmlns="http://www.w3.org/1999/html">
  <div>
    <div  id="cc">
      <div id="aa">
        <h1 align="center">全局设置</h1>
        <br/>
        <el-form :model="form" status-icon  size="large" ref="form" label-width="120px" >
          <el-form-item label="提醒方式" prop="remindType">
            <el-select v-model="selectedOptions" :multiple="true"  @change="handleChange" placeholder="请选择提醒方式">
              <el-option label="微信提醒" value="微信提醒" />
              <el-option label="短信提醒" value="短信提醒" />
              <el-option label="邮件提醒" value="邮件提醒" />
            </el-select>
          </el-form-item>
          <el-form-item label="提前时间(分)" prop="duration">
            <el-input type="number"  v-model="form.duration" style="width: 250px"></el-input>
          </el-form-item>
          <el-form-item label="微信号" prop="wechat">
            <el-input  v-model="form.wechat" style="width: 250px"></el-input>
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input  v-model="form.phone" style="width: 250px"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="mail">
            <el-input  v-model="form.mail" style="width: 250px"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="submitForm()" style="width: 150px">提 交</el-button>
            <el-button type="success" @click="resetForm('form')" style="width: 100px" >重 置</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div id="ss">
        <span>扫码关注公众号</span>
        <div style="width: 300px;margin: auto">
          <el-image :src="src" ></el-image>
        </div>
      </div>
    </div>
  </div>

</template>

<script>

import request from "@/utils/request";

export default {
  name:"TeacherRemind",
  data() {
    return {
      src:"/gzh.jpg",
      form: {
        remindType: '',
        duration: '60',
        wechat:'',
        phone:'',
        mail:'',
      },
      selectedOptions:[]
    };
  },
  methods: {
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    handleChange() {
      // 将所选选项合并为字符串
      this.form.remindType = this.selectedOptions.join(',')
    },
    submitForm() {
          request.post('/user/add', this.form).then(res => {
            if (res.code==0){
              //小提示
              this. $message({
                type : "success" ,
                message:res.msg
              })
            }else {
              //小提示
              this. $message({
                type : "error" ,
                message:res.msg
              })
            }
            // 处理响应
          }).catch(error => {
            // 处理错误
          })
    }
  }
};
</script>
<style scoped>
#aa{
  width: 600px;
  flex: 1;
}
#cc{
  width: 930px;
  height: 600px;
  margin-left: 30px;
  margin-top: 30px;
  padding: 40px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  display: flex;
}
#ss{
  margin-left: 20px;
  width: 300px;
  text-align: center;
  font-size: 30px;
  flex: 1;
}
</style>