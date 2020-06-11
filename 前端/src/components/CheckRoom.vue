<template>
  <div class="CheckRoom">

    <van-sticky >

    <van-nav-bar
    title="顷刻"
    left-text="返回"
    :right-text="myid"
    left-arrow
    @click-left="onClickLeft"
    @click-right="onClickRight"/>

  </van-sticky>

    <h1>我的小屋</h1>

    <van-pull-refresh v-model="isLoading" @refresh="onRefresh">
    </van-pull-refresh>

    <van-list
        v-model="loading"
        :finished="finished"
        finished-text=""
        @load="onload">

            <van-cell v-for="(item,index) in pictures" :key="item" :value="rent[index]" :title="type[index]" title-style="text-align: left; margin-left: 20px"  clickable @click="pop(item,index)" :icon="pictures[index]">
                <template #icon>
                    <van-image
                         width="80"
                          height="80"
                          v-bind:src="require('../assets/'+pictures[index])">
                    </van-image>
                </template>
                <template #default>
                  <div v-if="ostate[index]=='accepted'">
                    <div style="font-size: 20px; color: #FF0000">{{rent[index]}}</div><div style="color: #FF0000">租约到期</div>
                  </div>
                  <div v-if="ostate[index]=='waiting'">
                    <div style="font-size: 20px;color: #FF0000">待审核</div>
                  </div>
                  <div v-if="ostate[index]=='rejected'">
                    <div style="font-size: 20px;color: #FF0000">审核不通过，已被拒绝</div>
                  </div>
                  <div v-if="ostate[index]=='finished'">
                    <div style="font-size: 20px;color: #FF0000">租房已于{{rent[index]}}到期</div>
                  </div>
                  <div v-if="ostate[index]=='renew'">
                    <div style="font-size: 20px;color: #FF0000">续租申请中</div>
                  </div>
                </template>
            </van-cell>
    </van-list>

    <p><van-cell  size="large" title="查询本月需交费用" is-link arrow-direction="down" @click="check"/></p>

    <div v-if="v1">
        <van-cell v-for="(item,index) in ordersid" :key="item" :value="money[index]" :title="delay[index]" title-style="text-align: left; margin-left: 20px"  clickable @click="clicktopay(item,index)" :icon="pictures[index]" v-if="pay[index]==false">
            <template #icon>
                <van-image
                    width="80"
                    height="80"
                    v-bind:src="require('../assets/'+pictures[index])">
                </van-image>
            </template>
            <template #default>
                <div>
                    <div style="font-size: 20px; color: #FF0000">{{money[index]}}</div><div style="color: #FF0000">元人民币</div>
                </div>
            </template>
        </van-cell>
    </div>

    <div v-if="v2">
        没有费用需要交。
    </div>

    <van-action-sheet
        v-model="show"
        :actions="actions"
        cancel-text="取消"
        @select="onselect"
        close-on-click-action
        @cancel="onCancel"/>

    <van-action-sheet
        v-model="show1"
        :actions="actions1"
        cancel-text="取消"
        @select="onselect1"
        close-on-click-action
        @cancel="onCancel1"/>

    <a @click="downloadFile("
    
  </div>
</template>

<script>
import Foot_bar from './Foot_bar';
import { Toast } from 'vant';
import axios from 'axios';
import { Dialog } from 'vant';

export default {
  name: 'CheckRoom',
  data () {
    return {
      msg1: '距离租约到期还有：',
      islogin:'#/MyInfo',
      myid:localStorage.getItem('email'),
      days:'100',
      msg2:'本月应交',
      url:'static/test1.txt',
      contracturl:['static/contract/order1.txt','static/contract/test1.txt'],

      show: false,
      show1:false,
      actions: [{ name: '查询应交费用',color:'red'},{name:'查看地址',color:'blue'},{name:'下载合同',color:'#07c160'}],
      actions1: [{ name: '删除',color:'red'}],

      isLoading: false,
      paylist:[
          '1',
          '2',
      ],
      v1:false,
      v2:false,
      pictures: ['qk1000101.jpg','qk1000201.jpg'],
      type: ['1','2'],
      rent: ['2020年12月1日','2021年12月1日'],
      address: ['1','2'],
	  state:[],
	  ostate:['accepted','renew'],
	  pay:[],
	  delay:[],
	  ordersid:[],
	  orderstype:['long','long'],
    money:[],
    whichnum:0,
    }
  },
  components:{
      Foot_bar
  },
  methods:{
      onRefresh() {
          setTimeout(() => {
              Toast('刷新成功');
              this.isLoading = false;
           }, 1000);
      },
      pop(room,num){
          if(this.ostate[num]=='rejected'){
              this.show=false;
              this.show1=true;
          }
          else if(this.ostate[num]=='waiting'){
              this.show=false;
              this.show1=false;
          }
          else if(this.ostate[num]=='finished'){
              this.show=false;
              this.show1=true;
          }
          else{
              this.show=true;
              this.show1=false;
          }
          this.whichnum=num;
      },
      onselect(item){
          if(item.name=='查询应交费用'){
              if(this.paylist.length==0){
                  this.v1=false;
                  this.v2=true;
              }
              else{
                  this.v2=false;
                  this.v1=true;
              }
          }
          else if(item.name=='查看地址'){
              Dialog({message:this.address[this.whichnum]});
          }
          else if(item.name=='下载合同'){
              if(this.orderstype[this.whichnum]=='short'){
                  Dialog({message:"短租无合同"});
              }
              else{
                  this.url=this.contracturl[this.whichnum];
                  window.open(this.url);
                  //Dialog({message:"按ctrl+s下载"});
              }
          }
      },
      onselect1(item){
          if(item.name=='删除'){
			var _this = this;
			axios.get('/deleterenterorder',{
				params:{
					ordersid:_this.ordersid[_this.whichnum],
				}
			})
			.then(function(response){
				console.log(response.data);
				if(response.data == true){
					Toast('删除成功');
				}
				else{
					Toast('删除失败');
				}
			})
			.catch(function(error){
				console.log(error);
			});
            location.reload();
            this.$router.go(0);
          }
      },
      judge(){
          if(this.myid=="请先登入"){
              this.islogin="/";
          }
      },
      onload(){
          if(this.type.length==this.rent.length){
              this.finished=true;
          }
      },
      check(){
          if(this.paylist.length==0){
              this.v1=false;
              this.v2=true;
          }
          else{
              this.v1=true;
              this.v2=false;
          }
      },
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
      clicktopay(orderid,num){
        this.$router.push({
            path:'/Paying',
            query:{ordersid:orderid},
        });
      }
  },
  mounted(){
	var _this = this;
	axios.get('/showrenterroom',{
		params:{
			user:_this.myid,
		}
	})
	.then(function(response){
		console.log(response.data.pictures);
		console.log(response.data.date);
		
		_this.pictures = response.data.pictures;
		_this.type = response.data.type;
		_this.rent = response.data.date;
		_this.state = response.data.state;
		_this.address = response.data.address;
		
		_this.ostate = response.data.ordersState; //订单状态waiting accepted rejected finished
		_this.pay = response.data.pay;
		_this.delay = response.data.delaydate;
		_this.money = response.data.money;
		_this.ordersid = response.data.ordersId;
		
		_this.orderstype = response.data.ordersType;
		
		_this.contracturl = response.data.contracturl;
	})
	.catch(function(error){
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
