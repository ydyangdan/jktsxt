<template>
  <div class="block">
    <span class="demonstration">Default</span>
    <el-date-picker
        v-model="value1"
        type="datetime"
        placeholder="Select date and time"
    />
  </div>
</template>

<script>
import { ref } from 'vue'
export default {
  name: "test",
  data(){
    return{
      value1:'',

    }
  },
  watch:{
    value1:{
      handler(n){
        console.log(n)
      }
    }

}
}

</script>

<style scoped>

</style>