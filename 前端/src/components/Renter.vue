<template>
  <div class="picture">
  <div >
  <div class="background">
      <img :src="imgSrc" width="100%" height="100%" alt="" />
  </div>
  
  <van-row>
      <van-col span="8"></van-col>
  </van-row>

  <van-row type="flex" justify="end">
      <h2><a :href="islogin" @click="judge" style="color:blue;">{{myid}}</a></h2>
  </van-row>

  <h1>品质租房</h1>
  <h3>高品质租房体验，从顷刻开始</h3>

  <div style="margin-top:100px">
    <van-sticky>
        <van-button type="info" block to="/Search">搜索更多优质租房 》 》 》</van-button>
    </van-sticky>
  </div>

    <div style="margin-top: 30px;">
        <van-grid :gutter="10" :border="false" clickable :column-num="2" icon-size="150px" square>
            <van-grid-item :icon="rooma('rooma.jpg')" :text="roomA" style="white-space: pre-wrap;" @click="clicka"/>
            </van-grid-item>
            <van-grid-item :icon="roomb('rooma.jpg')" :text="roomB" style="white-space: pre-wrap;" @click="clickb"/>
            </van-grid-item>
            <van-grid-item :icon="roomc('rooma.jpg')" :text="roomC" style="white-space: pre-wrap;margin-top:100px;" @click="clickc"/>
            </van-grid-item>
            <van-grid-item :icon="roomd('rooma.jpg')" :text="roomD" style="white-space: pre-wrap;margin-top:100px;" @click="clickd"/>
            </van-grid-item>
            <van-grid-item :icon="roome('rooma.jpg')" :text="roomE" style="white-space: pre-wrap;margin-top:100px;" @click="clicke"/>
            </van-grid-item>
            <van-grid-item :icon="roomf('rooma.jpg')" :text="roomF" style="white-space: pre-wrap;margin-top:100px;" @click="clickf"/>
            </van-grid-item>
            <van-grid-item :icon="roomg('rooma.jpg')" :text="roomG" style="white-space: pre-wrap;margin-top:100px;" @click="clickg"/>
            </van-grid-item>
            <van-grid-item :icon="roomh('rooma.jpg')" :text="roomH" style="white-space: pre-wrap;margin-top:100px;" @click="clickh"/>
            </van-grid-item>
        </van-grid>
    </div>

    <div>
    </div>

    <div style="margin-top:30px;">
        <Foot_bar></Foot_bar>
    </div>
  </div>

  

  </div>
</template>

<script>
import {ImagePreview} from 'vant';
import Foot_bar from './Foot_bar';
import axios from 'axios';

export default {
  name: 'Renter',
  data () {
    return {
      islogin:'#/MyInfo',
      myid:localStorage.getItem('email'),
      imgSrc:require('../assets/Sofa.jpg'),
      roomA:'地点\n类型\n价格',
      roomB:'地点\n类型\n价格',
      roomC:'地点\n类型\n价格',
      roomD:'地点\n类型\n价格',
      roomE:'地点\n类型\n价格',
      roomF:'地点\n类型\n价格',
      roomG:'地点\n类型\n价格',
      roomH:'地点\n类型\n价格',
      aid:'qk10001',
      bid:'qk10002',
      cid:'qk10003',
      did:'qk10004',
      eid:'qk10005',
      fid:'qk10006',
      gid:'qk10007',
      hid:'qk10008',
	  
	  pictures: [], //图片
	  address: [],  //地址
	  type: [],     //类型
	  rent: [],     //价格
    };
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
      rooma(){
	      this.roomA = '地点：'+this.address[0]+'\n类型：'+this.type[0]+'\n价格：'+this.rent[0]+' 元/月';
	      var src = this.pictures[0];
          return require("../assets/"+src);
      },
      roomb(){
	      this.roomB = '地点：'+this.address[1]+'\n类型：'+this.type[1]+'\n价格：'+this.rent[1]+' 元/月';
	      var src = this.pictures[1];
          return require('../assets/'+src);
      },
      roomc(){
	      this.roomC = '地点：'+this.address[2]+'\n类型：'+this.type[2]+'\n价格：'+this.rent[2]+' 元/月';
     	  var src = this.pictures[2];
          return require('../assets/'+src);
      },
      roomd(){
	      this.roomD = '地点：'+this.address[3]+'\n类型：'+this.type[3]+'\n价格：'+this.rent[3]+' 元/月';
          var src = this.pictures[3];
		  return require('../assets/'+src);
      },
      roome(){
	      this.roomE = '地点：'+this.address[4]+'\n类型：'+this.type[4]+'\n价格：'+this.rent[4]+' 元/月';
          var src = this.pictures[4];
		  return require('../assets/'+src);
      },
      roomf(){
	      this.roomF = '地点：'+this.address[5]+'\n类型：'+this.type[5]+'\n价格：'+this.rent[5]+' 元/月';
          var src = this.pictures[5];
		  return require('../assets/'+src);
      },
      roomg(){
	      this.roomG = '地点：'+this.address[6]+'\n类型：'+this.type[6]+'\n价格：'+this.rent[6]+' 元/月';
          var src = this.pictures[6];
		  return require('../assets/'+src);
      },
      roomh(){
	      this.roomH = '地点：'+this.address[7]+'\n类型：'+this.type[7]+'\n价格：'+this.rent[7]+' 元/月';
          var src = this.pictures[7];
		  return require('../assets/'+src);
      },
      clicka(){
          this.$router.push({
            path:'/Room',
            query:{roomname:this.pictures[0],from:'renter'},
          });
      },
      clickb(){
          this.$router.push({
            path:'/Room',
            query:{roomname:this.pictures[1],from:'renter'},
          });
      },
      clickc(){
          this.$router.push({
            path:'/Room',
            query:{roomname:this.pictures[2],from:'renter'},
          });
      },
      clickd(){
          this.$router.push({
            path:'/Room',
            query:{roomname:this.pictures[3],from:'renter'},
          });
      },
      clicke(){
          this.$router.push({
            path:'/Room',
            query:{roomname:this.pictures[4],from:'renter'},
          });
      },
      clickf(){
          this.$router.push({
            path:'/Room',
            query:{roomname:this.pictures[5],from:'renter'},
          });
      },
      clickg(){
          this.$router.push({
            path:'/Room',
            query:{roomname:this.pictures[6],from:'renter'},
          });
      },
      clickh(){
          this.$router.push({
            path:'/Room',
            query:{roomname:this.pictures[7],from:'renter'},
          });
      },
  },
  mounted(){
    var _this = this;
	  axios.get('/renterhomepage',{
    })
    .then(function (response){
      console.log(response.data.obj[1]);
      _this.pictures = response.data.obj[0];
	  _this.address = response.data.obj[1];
	  _this.type = response.data.obj[2];
	  _this.rent = response.data.obj[3];
	})
    .catch(function (error) { // 请求失败处理
      console.log(error);
    });
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1,h3,h2{
  font-weight: normal;
  color:white;
}
.background{
    width:100%;  
    height:40%;  /**宽高100%是为了图片铺满屏幕 */
    z-index:-1;
    position: absolute;
}
 
.front{
    z-index:1;
    position: absolute;
}


</style>
