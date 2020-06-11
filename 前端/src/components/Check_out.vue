<template>
  <div class="Check_out">
  <van-sticky >

    <van-nav-bar
    title="申请续租"
    left-text="返回"
    :right-text="myid"
    left-arrow
    @click-left="onClickLeft"
    @click-right="onClickRight"/>

  </van-sticky>

    <div>
        <van-cell title="请选择想要续租的房子" is-link arrow-direction="down" value="请选择" @click="choose"/>
        <div v-if="c1">
        <van-cell v-for="(item,index) in roomid" :key="item" :value="endday[index]" :title="roomid[index]" title-style="text-align: left; margin-left: 20px"  clickable @click="pop(item,index)">
            <template #icon>
                <van-image
                    width="80"
                    height="80"
                    v-bind:src="require('../assets/'+roomid[index]+'01.jpg')">
                </van-image>
            </template>
            <template #default>
                <div>
                    <div style="font-size: 18px;color: #FF0000"><div>该房将于</div><div>{{endday[index]}}</div><div>到期</div></div>
                </div>
            </template>
            <template #label>
                <van-row>
                    <van-col>
                         地址:{{address[index]}}
                    </van-col>
                </van-row>
            </template>
        </van-cell>
        </div>
        <div v-if="c2">
            <h2>正在对房间编号为:<span style="color:red;">{{nowroom}}</span>的租房进行续租</h2>
            <van-picker title="请选择续租时长" show-toolbar :columns="columns" @confirm="onConfirm" @cancel="onCancel" @change="onChange"/>

            <h2>续租时长:<span style="color:red;">{{nowtime}}</span></h2>

            <van-button type="primary" @click="onsubmit">申请续租</van-button>
        </div>
    </div>
    <div>
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
  name: 'Check_out',
  data () {
    return {
        myid:localStorage.getItem('email'),
        roomid:['qk10001','qk10002','qk10003'],
        address:['a','b','c'],
        endday:['2020/12/1','2020/12/1','2020/12/1'],
        c1:true,
        c2:false,
        nowroom:'',
        columns: ['1个月', '2个月', '3个月', '4个月','5个月','6个月'],
        nowtime:'1个月',
        nowindex:0,
		ordersid:[],
    }
  },
  components:{
      Foot_bar
  },
  methods:{
      onClickLeft() {
          this.$router.push({
              path:'/MyInfo',
          });
      },
      onClickRight() {
          this.$router.push({
            path:'/MyInfo',
          });
      },
      pop(name,num){
          this.c1=false;
          this.c2=true;
          this.nowroom=name;
		  this.nowindex = num;
      },
      choose(){
          this.c2=false;
          this.c1=true;
      },
      onConfirm(value, index) {
          Toast(`当前时长：${value}`);
          this.nowtime=value;
      },
      onChange(picker, value, index) {
          Toast(`当前时长：${value}`);
          this.nowtime=value;
      },
      onCancel() {},
      onsubmit(){
		  var _this = this;
		  axios.get('/renterrenew',{
			params:{
				ordersid: _this.ordersid[_this.nowindex],
				time: _this.nowtime,
			}
		  })
		  .then(function(response){
			if(response.data == true){
				    Toast('提交成功!');
            _this.c1=true;
            _this.c2=false;
            location.reload();
            this.$router.go(0);
			}
			else{
				Toast('提交失败!');
			}
		  })
		  .catch(function(error){
			console.log(error);
		  });
          
      }
  },
  mounted(){
	var _this = this;
	axios.get('/showrenewableroom',{
		params:{
			user:_this.myid,
		}
	})
	.then(function(response){
		_this.roomid = response.data.roomId;
		_this.address = response.data.address;
		_this.endday = response.data.duetime;
		_this.ordersid = response.data.ordersId;
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
