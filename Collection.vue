<template>
  <div class="Collection">
    <van-sticky >

    <van-nav-bar
    title="我的收藏"
    left-text="返回"
    :right-text="myid"
    left-arrow
    @click-left="onClickLeft"
    @click-right="onClickRight"/>

    </van-sticky>

    <h1>{{ msg }}</h1>
    <h2>你可以在这里查看收藏的房子</h2>

    <div>
        <van-list
        v-model="loading"
        :finished="finished"
        finished-text="没有更多了"
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
                  <div v-if="state[index]=='available'">
                    <div style="font-size: 20px; color: #FF0000">{{rent[index]}}</div><div style="color: #FF0000">元/月</div>
                  </div>
                  <div v-if="state[index]!='available'">
                    <div style="font-size: 20px; color: #FF0000">已被租出或下架</div>
                  </div>
                </template>
                <template #label>
                    <div v-bind:style="{width:'80%'}"><div class="van-ellipsis">{{address[index]}}</div></div>
                </template>
            </van-cell>
        </van-list>

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
    </div>
        
  </div>
</template>

<script>
import Foot_bar from './Foot_bar';
import axios from 'axios';

export default {
  name: 'Collection',
  data () {
    return {
      msg: '我的收藏',
      myid:localStorage.getItem('email'),
      pictures: ['qk1000101.jpg','qk1000201.jpg','qk1000301.jpg'],
      type: ['1','2','3'],
      rent: ['1','2','3'],
      address: ['一二三四五六七八九十一二三四五六七八九十','2','3'],
      state:['available','available','rented'],

      show: false,
      show1:false,
      actions: [{ name: '查看详情',color:'#07c160' }, { name: '取消收藏',color:'red'}],
      actions1: [{ name: '取消收藏',color:'red'}],
      whichroom:'',
	  message:'',
	  success:null,
	  
    }
  },
  methods:{
      onCancel(){},
      onCancel1(){},
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
      onload(){
          if(this.type.length==this.rent.length){
              this.finished=true;
          }
      },
      pop(room,num){
          if(this.state[num]=='stop'||this.state[num]=='rented'){
              this.show=false;
              this.show1=true;
          }
          else{
              this.show=true;
              this.show1=false;
          }
          this.whichroom=room;
      },
      onselect(item){
          if(item.name=='查看详情'){
              this.$router.push({
                  path:'/Room',
                  query:{roomname:this.whichroom,from:'collection'},
              });
          }
          else if(item.name=='取消收藏'){
		    var _this = this;
            axios.get('/deletecollection',{
	          params: {
                  user: _this.myid,
                  roomid: _this.whichroom,
	  	      }
            })
            .then(function (response){
		        console.log(response.data);
		        _this.success = response.data;
            })
            .catch(function (error) { // 请求失败处理
                console.log(error);
            });
            location.reload();
            this.$router.go(0);
		    }
      },
      onselect1(item){
          if(item.name=='取消收藏'){
              var _this = this;
            axios.get('/deletecollection',{
	          params: {
                  user: _this.myid,
                  roomid: _this.whichroom,
	  	      }
            })
            .then(function (response){
		        console.log(response.data);
		        _this.success = response.data;
            })
            .catch(function (error) { // 请求失败处理
                console.log(error);
            });
            location.reload();
            this.$router.go(0);
          }
      },
  },
  components:{
  },
  created(){
	  var _this = this;
	  axios.get('/showcollection',{
	        params: {
                user: _this.myid,
		    }
          })
          .then(function (response){
		    console.log(response.data.pictures);
		    _this.count = response.data.num;
            _this.type = response.data.type;
            _this.rent = response.data.rent;
            _this.address = response.data.address;
            _this.pictures = response.data.pictures;
			_this.state = response.data.state; // rented已租出 available未租出 stop停止出租
          })
          .catch(function (error) { // 请求失败处理
            console.log(error);
          });
  },
  mounted:function(){
  }
}
</script>
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
.goods-card {
    margin: 0;
    background-color: @white;
}
.delete-button {
    height: 100%;
}
</style>
