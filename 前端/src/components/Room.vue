<template>
  <div class="Room">
  <van-sticky >

    <van-nav-bar
    :title="roomid"
    left-text="返回"
    :right-text="myid"
    left-arrow
    @click-left="onClickLeft"
    @click-right="onClickRight"/>

  </van-sticky>

    <van-swipe :autoplay="2000" indicator-color="white">
        <van-swipe-item v-for="(image, index) in images" :key="index">
            <img :src="require('../assets/'+image)" width=100% height="300px" fit="contain">
        </van-swipe-item>
    </van-swipe>

    <h1>{{ roomid }}</h1>


    <van-row type="flex" justify="space-between" style="margin:0px" gutter="50">
        <van-color span="1">
        </van-color>

        <van-color span="6">
            <h3>房租:</h3>
        </van-color>

        <van-color span="12">
            <h3>户型:</h3>
        </van-color>

        <van-color span="6">
            <h3>面积:</h3>
        </van-color>

        <van-color span="1">
        </van-color>
    </van-row>

    <div style="margin:0px">
    <van-row type="flex" justify="space-between" gutter="20">
        <van-color span="1">
        </van-color>

        <van-color span="6">
            <h2 style="color:red;">{{money}}元/月</h2>
        </van-color>

        <van-color span="6">
            <h2 style="color:red;">{{type}}</h2>
        </van-color>

        <van-color span="6">
            <h2 style="color:red;">{{area}}㎡</h2>
        </van-color>

        <van-color span="1">
        </van-color>
    </van-row>
    </div>

    <div style="margin-top:30px">
      <van-row>
          <van-col span="24"><h3>地址: {{address}}</h3></van-col>
      </van-row>
      <van-row gutter="20">
          <van-col span="12"><h3>联系人姓名: {{ownername}}</h3></van-col>
          <van-col span="12"><h3>联系电话: {{ownerphone}}</h3></van-col>
      </van-row>
      <van-row>
          <van-col span="24"><h3 style="white-space: pre-wrap;">其他信息: {{item}}</h3></van-col>
      </van-row>
    </div>
    
	<h2 v-if="this.success==false" style="color:red;">{{message}}</h2>
	<h2 v-if="this.success==true" style="color:blue;">{{message}}</h2>
	
    <div style="margin-top:150px;">
        <van-goods-action>
            <van-goods-action-icon icon="chat-o" text="客服" to="/ConnectService" />
            <van-goods-action-icon icon="star" text="我的收藏" to="/Collection" />
            <van-goods-action-button type="warning" :disabled="disabled" :text="iscollect" @click="onClickButton1" />
            <van-goods-action-button type="danger" text="立即租房" @click="onClickButton2" />
        </van-goods-action>
    </div>
    
  </div>
</template>

<script>
import Foot_bar from './Foot_bar';
import { Toast } from 'vant';
import axios from 'axios';

export default {
  name: 'Room',
  data () {
    return {
      money:'',
      iscollect:'加入收藏',
      disabled:false,
      type:'',
      area:'',
      from:'',
	  address:'',
	  ownername: '',
	  ownerphone: '',
      roomname: '',
      islogin:'#/MyInfo',
      myid:localStorage.getItem('email'),
      images:[],
      roomid:'',
	  item:'',
	  success:null,
	  message:'',
    }
  },
  components:{
      Foot_bar
  },
  methods:{
      judge(){
          if(this.myid=="请先登入"){
              this.islogin="/";
          }
      },
      onClickLeft() {
          if(this.from=='renter'){
              this.$router.push({
              path:'/Renter',
              });
          }
          else if(this.from=='collection'){
              this.$router.push({
              path:'/Collection',
              });
          }
          else{
              this.$router.push({
              path:'/Search',
              });
          }
      },
      onClickRight() {
          this.$router.push({
            path:'/MyInfo',
          });
      },
      onClickButton1(){
          // 收藏房间
		  var _this = this;
		  axios.get('/collectroom',{
	      params: {
		      user: _this.myid,
	     	  pic: _this.roomid,
	      }
        })
        .then(function (response){
          console.log(response);
		  _this.success = response.data.success;
		  _this.message = response.data.message;
      if(_this.success==true){
          _this.disabled=true;
          _this.iscollect='已收藏';
      }
        })
        .catch(function (error) { // 请求失败处理
          console.log(error);
        });
      },
      onClickButton2(){
          this.$router.push({
              path:'/Renting',
              query:{roomid:this.roomid.substr(0,7),money:this.money},
          });
      },
  },
  created(){
      this.roomid = this.$route.query.roomname.substr(0,7);
      this.from=this.$route.query.from;
	  
	  var _this = this;
	  axios.get('/roompage',{
	    params: {
		  user: _this.myid,
		  pic: _this.roomid,
		}
      })
      .then(function (response){
        console.log(response.data.pic);
        _this.type = response.data.obj[0].type;
		_this.money = response.data.obj[0].rent;
		_this.area = response.data.obj[0].area;
		_this.item = response.data.obj[0].item;
		_this.ownername = response.data.obj[0].ownerName;
		_this.ownerphone = response.data.obj[0].ownerPhone;
		_this.address = response.data.obj[0].address;
		_this.images = response.data.pic;
		if(response.data.collected == true){
		    _this.iscollect = '已收藏';
			_this.disabled=true;
		}
      })
      .catch(function (error) { // 请求失败处理
        console.log(error);
      });
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1 {
  font-weight: normal;
}
h3,h4{
  font-weight: normal;
  color:grey;
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
