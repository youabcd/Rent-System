<template>
  <div class="picture">
  <div class="background">
      <img :src="imgSrc" width="100%" height="100%" alt="" />
  </div>
  

  <van-row type="flex" justify="end">
      <h2><a :href="islogin" @click="judge" style="color:blue;">{{myid}}</a></h2>
  </van-row>

  <van-sticky>
    <van-field
    v-model="searchkey"
    center
    clearable
    left-icon="search"
    label="搜索房源"
    placeholder="请输入关键字">
        <template #button>
            <van-button size="small" type="info" @click="Search">搜索</van-button>
        </template>
    </van-field>

    <van-dropdown-menu >
        <van-dropdown-item v-model="value1" :options="option1" @change="onchange1"/>
        <van-dropdown-item v-model="value2" :options="option2" @change="onchange2"/>
        <van-dropdown-item v-model="value3" :options="option3" @change="onchange3"/>
        <van-dropdown-item v-model="value4" :options="option4" @change="onchange4"/>
    </van-dropdown-menu>

  </van-sticky> 

    <!--<h1 style="color:red;">{{msg}}</h1>-->

    <div>
        <van-list
        v-model="loading"
        :finished="finished"
        finished-text="没有更多了"
        @load="onload">

            <van-cell v-for="(item,index) in pictures" :key="item" :value="rent[index]" :title="type[index]" title-style="text-align: left; margin-left: 20px"  clickable @click="toroom(item)" :icon="pictures[index]">
                <template #icon>
                    <van-image
                         width="80"
                          height="80"
                          v-bind:src="require('../assets/'+pictures[index])">
                    </van-image>
                </template>
                <template #default>
                    <div style="font-size: 20px; color: #FF0000">{{rent[index]}}</div><div style="color: #FF0000">元/月</div>
                </template>
                <template #label>
                    <div v-bind:style="{width:'80%'}"><div class="van-ellipsis">{{address[index]}}</div></div>
                </template>
            </van-cell>
        </van-list>
    </div>


    
    <div>
    </div>

    <div style="margin-top:16px;">
        <Foot_bar></Foot_bar>
    </div>

  </div>
</template>

<script>
import Foot_bar from './Foot_bar'
import axios from 'axios';

export default {
  name: 'Renter',
  data () {
    return {
      msg: '顷刻',
      imgSrc:require('../assets/search.jpg'),
      islogin:'#/MyInfo',
      myid:localStorage.getItem('email'),
      radio1:'0',
      radio2:'0',
      searchkey:localStorage.getItem('searchkey'),
      count:'6',

      pictures: [],
	    type: [],
	    rent: [],
	    address: [],

      msg:'hello',
	  
      loading: false,
      finished: false,

      value1:localStorage.getItem('v1'),
      value2:localStorage.getItem('v2'),
      value3:localStorage.getItem('v3'),
      value4:localStorage.getItem('v4'),
      option1: [
        { text: '单人间', value: '0' },
        { text: '双人间', value: '1' },
        { text: '四人间', value: '2' },
		    { text: '所有的', value: '3' },
      ],
      option2: [
        { text: '默认价格排序', value: 'a' },
        { text: '价格降序', value: 'b' },
        { text: '价格升序', value: 'c' },
      ],
      option3: [
        { text: '默认面积排序', value: '4' },
        { text: '面积降序', value: '5' },
        { text: '面积升序', value: '6' },
      ],
      option4: [
        { text: '价格范围（全部）', value: 'A' },
        { text: '0-1000元/月', value: 'B' },
        { text: '1000-3000元/月', value: 'C' },
        { text: '3000-6000元/月', value: 'D' },
        { text: '6000元/月以上', value: 'E' },
      ],
    };
  },
  components:{
      Foot_bar
  },
  methods:{
      onchange1(value){
          var _this = this;
      axios.get('/rentersearchroom',{
          params: {
                value1:_this.value1,//房子类型
                value2:_this.value2,//价格排序方式
                value3:_this.value3,//面积排序方式
                value4:_this.value4,//价格范围
            key: _this.searchkey,
        }
          })
          .then(function (response){
        console.log(response.data.pictures);
        _this.count = response.data.num;
      
      _this.type = response.data.type;
      _this.rent = response.data.rent;
      _this.address = response.data.address;
      _this.pictures = response.data.pictures;
          })
          .catch(function (error) { // 请求失败处理
            console.log(error);
          });
          /*if(value=='0'){//单人间
          }
          else if(value=='1'){//双人间
          }
          else if(value=='2'){//三人间
          }
          else if(value=='3'){//四人间
          }*/
      },
      onchange2(value){
          var _this = this;
      axios.get('/rentersearchroom',{
          params: {
                value1:_this.value1,//房子类型
                value2:_this.value2,//价格排序方式
                value3:_this.value3,//面积排序方式
                value4:_this.value4,//价格范围
            key: _this.searchkey,
        }
          })
          .then(function (response){
        console.log(response.data.pictures);
        _this.count = response.data.num;
      
      _this.type = response.data.type;
      _this.rent = response.data.rent;
      _this.address = response.data.address;
      _this.pictures = response.data.pictures;
          })
          .catch(function (error) { // 请求失败处理
            console.log(error);
          });
          /*if(value=='a'){
          }
          else if(value=='b'){//价格降序
          }
          else if(value=='c'){//价格升序
          }*/
      },
      onchange3(value){
          var _this = this;
      axios.get('/rentersearchroom',{
          params: {
                value1:_this.value1,//房子类型
                value2:_this.value2,//价格排序方式
                value3:_this.value3,//面积排序方式
                value4:_this.value4,//价格范围
            key: _this.searchkey,
        }
          })
          .then(function (response){
        console.log(response.data.pictures);
        _this.count = response.data.num;
      
      _this.type = response.data.type;
      _this.rent = response.data.rent;
      _this.address = response.data.address;
      _this.pictures = response.data.pictures;
          })
          .catch(function (error) { // 请求失败处理
            console.log(error);
          });
          /*if(value=='4'){
          }
          else if(value=='5'){//面积降序
          }
          else if(value=='6'){//面积升序
          }*/
      },
      onchange4(value){
          var _this = this;
      axios.get('/rentersearchroom',{
          params: {
                value1:_this.value1,//房子类型
                value2:_this.value2,//价格排序方式
                value3:_this.value3,//面积排序方式
                value4:_this.value4,//价格范围
            key: _this.searchkey,
        }
          })
          .then(function (response){
        console.log(response.data.pictures);
        _this.count = response.data.num;
      
      _this.type = response.data.type;
      _this.rent = response.data.rent;
      _this.address = response.data.address;
      _this.pictures = response.data.pictures;
          })
          .catch(function (error) { // 请求失败处理
            console.log(error);
          });
          /*if(value=='A'){//全部价格
          }
          else if(value=='B'){//0-1000
          }
          else if(value=='C'){//1000-3000
          }
          else if(value=='D'){//3000-6000
          }
          else if(value=='E'){//6000以上
          }*/
      },
      toroom(name){
          this.$router.push({
              path:'/Room',
              query:{roomname:name},
          });
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
      Search(){
      localStorage.setItem('searchkey',this.searchkey);
      localStorage.setItem('v1',this.value1);
      localStorage.setItem('v2',this.value2);
      localStorage.setItem('v3',this.value3);
      localStorage.setItem('v4',this.value4);
		  var _this = this;
		  axios.get('/rentersearchroom',{
	        params: {
                value1:_this.value1,//房子类型
                value2:_this.value2,//价格排序方式
                value3:_this.value3,//面积排序方式
                value4:_this.value4,//价格范围
	     	    key: _this.searchkey,
		    }
          })
          .then(function (response){
		    console.log(response.data.pictures);
		    _this.count = response.data.num;
			
			_this.type = response.data.type;
			_this.rent = response.data.rent;
			_this.address = response.data.address;
			_this.pictures = response.data.pictures;
          })
          .catch(function (error) { // 请求失败处理
            console.log(error);
          });
      },
  },
  created(){
      this.id=this.$route.query.name;
  },
  mounted:function(){
      this.Search();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1{
  font-weight: normal;
}
.background{
    width:100%;  
    height:100%;  /**宽高100%是为了图片铺满屏幕 */
    z-index:-1;
    position: fixed;
}
 
.front{
    z-index:1;
    position: absolute;
}
</style>
