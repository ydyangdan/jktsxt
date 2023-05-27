<template>
  <div id="app">
    <div id="qq">
      <h2 align="center">公告信息</h2>
      <div v-for="(notice, index) in dataList" :key="index" class="aa">
        <span class="cho">{{index+1}}. {{ notice.noticeName}}</span>
        <span style="margin-left: 100px">{{ notice.time }}</span><br/>
        <span>{{ notice.content}}</span><br/>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "TeacherNotice",
  data() {
    return {
      dataList:[
        // {title:"ss",content:"ddddddd",time:"2023-4-21"},
        // {title:"qqq",content:"ddddddd",time:"2023-4-2"}
      ],
    }
  },
  created() {
    this.getNotice();
  },
  methods: {
    getNotice() {
      request.get("/notice/getNotice").then(res => {
        console.log(res.data);
        this.dataList = res.data;
      })
    }
  }
}
</script>

<style scoped>
.aa{
  margin-top: 15px;
  /*padding-top: 10px;*/
  font-size: 16px;
  border-bottom: #1d6fbd 2px solid;
  border-top: #1d6fbd 2px solid;
}
#qq{
  height: 650px;
  width: 60%;
  margin: auto;
  margin-top: 20px;
  padding: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}
</style>