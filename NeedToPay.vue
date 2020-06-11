<template>
  <div class="NeedToPay">
    <h1>{{ msg }}</h1>
    <h2>你可以在这里进行缴费</h2>

    <div>
        <p></p>
        <p><van-button type="primary" to="/MyInfo">返回</van-button></p>
        <p></p>
        <p></p>
        <p></p>
    </div>

    <div>
        <p></p>
        <p></p>
        <p></p>
        <p></p>
        <p></p>
    </div>
    

        
  </div>
</template>

<script>
import Foot_bar from './Foot_bar'
export default {
  name: 'NeedToPay',
  data () {
    return {
      msg: '缴费项目'
    }
  },
  components:{
      Foot_bar
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
