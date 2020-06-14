<template>
  <div class="Paying">
    <h1>{{ msg }}</h1>
    <h2>你需要缴纳{{money}}元人民币</h2>
    <h1>{{ordersid}}</h1>
    <div style="align-text:center;">
        <van-radio-group v-model="radio" direction="horizontal">
            <van-radio name="1">
                <van-image width="100" height="100" :src="require('../assets/weixin1.jpg')" />
            </van-radio>
            <van-radio name="2">
                <van-image width="100" height="100" :src="require('../assets/zhifubao1.jpg')" />
            </van-radio>
            <van-radio name="3">
                <van-image width="100" height="100" :src="require('../assets/yinlian1.jpg')" />
            </van-radio>
        </van-radio-group>
    </div>
    {{tip}}
    <div>
        <p><van-button type="primary" @click="paying" :disabled="disabled">确认缴费</van-button></p>
        <p><van-button type="primary" to="/CheckRoom">返回</van-button></p>
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
import Foot_bar from './Foot_bar';
import {Toast} from 'vant';
import axios from 'axios';

export default {
  name: 'Paying',
  data () {
    return {
      msg: '正在缴费',
      money:'',
      ordersid:'',
      tip:'还未缴费',
      disabled:false,
      radio: '1',
    }
  },
  methods:{
      paying(){
          this.money='0';
          this.tip='缴费成功！';
          this.disabled=true;
          Toast('缴费成功！');
		  var _this = this;
		  axios.get('/payed',{
			params:{
				ordersid:_this.ordersid,
			}
		  })
		  .then(function(response){
		  })
		  .catch(function(error){
			console.log(error);
		  });
		  
          this.$router.push({
              path:'/CheckRoom',
          });
      }
  },
  components:{
      Foot_bar
  },
  created(){
      this.ordersid=this.$route.query.ordersid;
	  var _this = this;
	  axios.get('/getpay',{
		params:{
			ordersid:_this.ordersid,
		}
	  })
	  .then(function(response){
		console.log(response.data);
		_this.money = response.data;
	  })
	  .catch(function(error){
		console.log(error);
	  });
  }
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
