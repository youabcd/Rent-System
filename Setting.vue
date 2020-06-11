<template>
  <div class="MyInfo">
    <div class="background">
      <img :src="imgSrc" width="100%" height="100%" alt="" />
    </div>

    <van-tabs @change="onchange">
        <van-tab title="评价修理员" ></van-tab>
        <van-tab title="评价客服" ></van-tab>
    </van-tabs>

    <van-row type="flex" justify="end">
      <h2><a :href="islogin" @click="judge" style="color:blue;">{{myid}}</a></h2>
    </van-row>
    <h1>{{ top }}</h1>
    <!--{{v1}}
    {{v2}}-->

  <div v-if="v1">
    <h2>你可以在这里投诉或者评价修理员的完成情况</h2>

    <div>
        <van-cell v-for="(item,index) in workorderid" :key="item" clickable @click=comment(item,index) :title="workorderid[index]" title-style="text-align: left; margin-left: 20px">
            <template #icon>
                <van-image
                    width="95"
                    height="95"
                    v-bind:src="workorderpic[index]">
                 </van-image>
            </template>
            <template #default>
                <div style="color: #FF0000" >当前状态:</div><div style="font-size: 20px; color: #FF0000" v-if="workorderstate[index]=='finished'">已完成</div>
                <div style="font-size: 20px; color: #FF0000" v-if="workorderstate[index]=='waiting'">未完成</div>
                <div style="font-size: 20px; color: #FF0000" v-if="workorderstate[index]=='returned'">已评价</div>
            </template>
            <template #label>
                <div v-bind:style="{width: commentWidth+'px'}"><div class="van-ellipsis">修理员电话:{{repairmanphone[index]}}</div>
                <div class="van-ellipsis">描述内容:{{renteritem[index]}}</div>
                <div class="van-ellipsis">报修日期:{{workorderdate[index]}}</div>
                <div class="van-ellipsis">客服电话:{{servicephone[index]}}</div>
                </div>
            </template>
        </van-cell>
    </div>

    <van-popup v-model="show" closeable round :style="{height:'45%',width:'100%'}">
      <div style="margin-top:32px;">
        <h1>请提交您对这次维修任务的评价</h1>
        <van-rate v-model="value" touchable="true" />
        <van-field v-model="message" rows="3" autosize label="评价" type="textarea" maxlength="100" label-width="35px" placeholder="请输入评价" show-word-limit/>
        <form style="margin-top:16px;">
        <van-button type="info" @click="submit">提交评价</van-button>
        </form>
      </div>
    </van-popup>
  </div>

  <div v-if="v2">
      <h2>你可以在这里查看投诉客服后的回复</h2>
      <div>
        <van-cell v-for="(item,index) in complaintid" :key="item" clickable @click=comment1(item,index) :title="complaintid[index]" title-style="text-align: left; margin-left: 20px">
            <template #icon>
                <van-image
                    width="95"
                    height="95"
                    v-bind:src="pic[index]">
                 </van-image>
            </template>
            <!--<template #default>
                <div style="color: #FF0000" >当前状态:</div><div style="font-size: 20px; color: #FF0000" v-if="workorderstate[index]=='finished'">已完成</div>
                <div style="font-size: 20px; color: #FF0000" v-if="workorderstate[index]=='waiting'">未完成</div>
                <div style="font-size: 20px; color: #FF0000" v-if="workorderstate[index]=='returned'">已评价</div>
            </template>-->
            <template #label>
                <div v-bind:style="{width: commentWidth+'px'}"><div class="van-ellipsis">被投诉客服电话:{{servicephone1[index]}}</div>
                <div class="van-ellipsis">客服姓名:{{servicename1[index]}}</div>
                <div class="van-ellipsis">投诉内容:{{renteritem1[index]}}</div>
                <div class="van-ellipsis">客服回复内容:{{serviceitem[index]}}</div>
                </div>
            </template>
        </van-cell>
    </div>
  </div>

    <div>
    <p>可以通过以下方式直接联系客服</p>
    客服电话:{{phone}} 客服邮箱:{{email}} 客服微信:{{weixin}}
    </div>
    

    <div style="margin-top:16px;">
        <Foot_bar></Foot_bar>
    </div>
  </div>
</template>

<script>
import Foot_bar from './Foot_bar';
import axios from 'axios';
import { Toast } from 'vant';

export default {
  name: 'Setting',
  data () {
    return {
      top: '评价',
      v1:true,
      v2:false,
      message:'',
      islogin:'#/MyInfo',
      myid:localStorage.getItem('email'),
      value: 5,
      show:false,
      workorder:'',
      whichnum:0,
      phone:'17815566221',
      email:'1322496098@qq.com',
      weixin:'17815566221',
      imgSrc:require('../assets/pugongying.jpg'),
	  
	  workorderid:['1','2','3'],
	  workorderstate:['finished','waiting','returned'],
	  serviceid:[],
	  servicename:[],
	  servicephone:[],
	  repairmanname:[],
	  repairmanid:[],
	  repairmanphone:['qk10001','qk10002','qk10003'],
	  roomaddress:[],
	  workorderdate:['qk10001','qk10002','qk10003'],// 投诉日期
	  workorderpic:['static/pictures/test.jpg','static/pictures/t2.jpg'],// 租客报修图片的第一张
	  renteritem:['h','a','c'],// 租客描述内容
	  repairmanitem:[],// 师傅回复内容
	  returnitem:[],// 租客评价内容
	  
	  complaintid:['3','4','5'],
	  servicename1:['1','2','3'],
	  serviceid1:[],
	  servicephone1:['1','2','3'],
	  complaintstate:[], // waiting finished returned
	  renteritem1:['h','a','c'],// 租客描述内容
	  serviceitem:['1','2','3'],// 客服回复内容
	  returnitem1:[],// 租客评价内容
	  pic:[],
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
      onchange(name,title){
          if(title=='评价修理员'){
              this.v1=true;
              this.v2=false;
          }
          else if(title=='评价客服'){
              this.v2=true;
              this.v1=false;
			  var _this = this;
			  axios.get('/getmycomplaint',{
				params:{
					user: _this.myid,
				}
		      })
			  .then(function(response){
				_this.complaintid = response.data.complaintId;
				_this.servicename1 = response.data.serviceName;
				_this.serviceid1 = response.data.serviceId;
				_this.servicephone1 = response.data.servicePhone;
				_this.complaintstate = response.data.complaintState;
				_this.renteritem1 = response.data.renterItem;
				_this.serviceitem = response.data.serviceItem;
				_this.returnitem1 = response.data.returnItem
				_this.pic = response.data.pic;
			  })
			  .catch(function(error){
				console.log(error);
			  });
		  }
      },
      comment(name,num){
          if(this.workorderstate[num]=='finished'){
              this.show=true;
          }
          this.workorder=name;
          this.whichnum=num;
      },
      submit(){
		  var _this = this;
		  axios.get('/returnworkorder',{
			params:{
			    workorderId: _this.workorder,
				content:_this.message,
				level: _this.value,
			}
		  })
		  .then(function(response){
			if(response.data == true){
				location.reload();
                this.$router.go(0);
                Toast('评价成功！');
			}
			else{
				Toast('评价失败！');
			}
		  })
		  .catch(function(error){
			console.log(error);
		  })
          
      },
  },
  mounted(){
		var _this = this;
		  axios.get('/getmyworkorder',{
			params:{
				user:_this.myid,
			}
		  })
		  .then(function(response){
			_this.workorderid = response.data.workorderid;
			_this.workorderstate = response.data.workorderstate;	
			_this.serviceid = response.data.serviceid;
			_this.servicename = response.data.servicename;
			_this.servicephone = response.data.servicephone;
			_this.repairmanname = response.data.repairmanname;
			_this.repairmanid = response.data.repairmanid;
			_this.repairmanphone = response.data.repairmanphone;
			_this.roomaddress = response.data.roomaddress;
			_this.workorderdate = response.data.workorderdate; 
			_this.workorderpic = response.data.workorderpic;	
			_this.renteritem = response.data.renteritem; 
			_this.repairmanitem = response.data.repairmanitem;	
			_this.returnitem = response.data.returnitem;	
		  })
		  .catch(function(error){
			console.log(error);
		  });
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
.background{
    width:100%;  
    height:100%;  /**宽高100%是为了图片铺满屏幕 */
    z-index:-1;
    position: fixed;
}
</style>
