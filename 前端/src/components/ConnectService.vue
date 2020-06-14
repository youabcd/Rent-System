<template>
  <div class="picture">
  <div class="background">
      <img :src="imgSrc" width="100%" height="100%" alt="" />
  </div>
    <van-row type="flex" justify="end">
      <h2><a :href="islogin" @click="judge" style="color:blue;">{{myid}}</a></h2>
  </van-row>

    <p><h1>{{ msg }}</h1></p>

    <!--<van-image src='static/test.jpg'>
    </van-image>-->

    <van-cell is-link @click="showPopup">请选择需要报修的房间或需要投诉的客服</van-cell>
    
    <van-popup v-model="show" position="left" :style="{ height: '60%',width:'100%' }" closeable>
        <van-cell v-for="(item,index) in myroom" :key="item" :icon="myroom[index]" clickable @click="pop(item,index)">
            <template #icon>
                <van-image
                    width="80"
                    height="80"
                    v-bind:src="require('../assets/'+myroom[index]+'01.jpg')">
                </van-image>
            </template>
            <template #default>
                <div>
                    <div style="color: #FF0000"> 房间编号:</div>
                    <div style="font-size: 20px; color: #FF0000"> {{myroom[index]}}</div>
                </div>
            </template>
        </van-cell>
    </van-popup>

    <van-action-sheet
        v-model="show1"
        :actions="actions"
        cancel-text="取消"
        @select="onselect"
        close-on-click-action
        @cancel="onCancel"/>

    <div v-if="type=='room'">
        <h1>正在对房间:<span style="color:red;">{{roomid}}</span>进行报修</h1>
        <van-image width=25% height=25% fit="contain" :src="image"/>
    </div>

    <div v-if="type=='service'">
        <h1>正在对客服{{servicename}}:</h1><h1><span style="color:red;">{{serviceid}}</span></h1><h1>进行投诉</h1>
        <van-image width=25% height=25% fit="contain" :src="image"/>
    </div>

    <div>
    <van-form @submit="onSubmit" enctype="multipart/form-data">

      <van-cell-group>
        <van-field
        v-model="message"
        rows="5"
        autosize
        label="留言"
        type="textarea"
        maxlength="150"
        placeholder="请输入您想要反应的问题并上传照片，我们的客服会在第一时间给出答复"
        show-word-limit/>
      </van-cell-group> 

      <div style="margin:16px">
        <van-uploader :after-read="afterRead" v-model="fileList" multiple :max-count="3" upload-text="点击选择图片上传" preview-size="100px"/>
      </div>
        <div style="margin-top: 16px;" v-if="roomid!='qk'">
        <van-button round  type="warning" block native-type="submit" >提交</van-button>
        </div>
        <div v-if="roomid=='qk'"><h2>还未选择投诉或报修对象，无法提交！</h2></div>
    </van-form>
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
import {Toast} from 'vant';
import axios from 'axios';

export default {
  name: 'Renter',
  data () {
    return {
      islogin:'#/MyInfo',
      ni:'test.jpg',
      myid:localStorage.getItem('email'),
      msg: '有问题，找客服',
      imgSrc:require('../assets/service1.jpg'),
      fileList: [],
      value:'',
      message:'',
      image:'',
      type:'',
      phone:'17815566221',
      email:'1322496098@qq.com',
      judge:false,
      weixin:'17815566221',
      roomid:'qk',
      serviceid:'',
      servicename:'',
      show: false,
      show1:false,
      myroom:['qk10001','qk10002','qk10003'],
      myservice:['123','256','65'],
	    myservicename:['a','b','c'],
	    myservicephone:['1','2','3'],
      actions: [{ name: '报修',color:'red' }, { name: '投诉该房客服',color:'red'}],
      pictures:[],
	};
  },
  components:{
      Foot_bar
  },
  methods:{
      onCancel(){},
      judge(){
          if(this.myid=="请先登入"){
              this.islogin="/";
          }
      },
      showPopup() {
          this.show = true;
      },
      pop(name,num){
          Toast(this.myroom[num]);
          this.roomid=this.myroom[num];
          this.serviceid=this.myservice[num];
          this.servicename=this.myservicename[num];
          this.phone=this.myservicephone[num];
          this.email=this.myservice[num];
          this.weixin=this.myservicephone[num];
          this.show1=true;
      },
      onselect(item){
          if(item.name=='报修'){
              this.type='room';
              this.image=require('../assets/'+this.roomid+'01.jpg');
              this.show=false;
          }
          else if(item.name=='投诉该房客服'){
              this.show=false;
              this.type='service';
              this.image=require('../assets/'+this.roomid+'01.jpg');
              this.show=false;
          }
      },
      afterRead(file) {
		  this.pictures.push(file.content);
		  console.log(file);
      },
      onSubmit(values) {
          //console.log('submit', values);
		  var _this = this;
		  let data = new FormData();
		  data.append('user',this.myid);
		  data.append('roomid',this.roomid);
		  data.append('content',this.message);
		  data.append('type',this.type);
		  data.append('pictures',this.pictures);
		  axios.post('/rentercomplaint',data,)
		  .then(function(response){
			if(response.data == true){
				Toast('发送成功');
        location.reload();
        this.$router.go(0);
			}
			else{
				Toast('发送失败');
			}
		  })
		  .catch(function(error){
			console.log(error);
		  });
		  
          this.$router.push({
              path:'/ConnectService',
          });
      },
  },
  created(){
      this.id=this.$route.query.name;
	  var _this = this;
	  axios.get('/renterrentedroom',{
		params:{
			user:_this.myid,
		}
	  })
	  .then(function(response){
		_this.myroom = response.data.roomId;
		_this.myservice = response.data.serviceId;
		_this.myservicename = response.data.serviceName;
		_this.myservicephone = response.data.servicePhone;
	  })
	  .catch(function(error){
		console.log(error);
	  })
  },
  mounted:function(){
      this.jud();
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
