<template>
  <div class="zuke">
  <div class="background">
      <img :src="imgSrc" width="100%" height="100%" alt="" />
  </div>

  <van-row>
      <van-col span="8"></van-col>
  </van-row>

  <h1>{{ msg }}</h1>

    <div>
      <van-image
      fit="contain"
      :src="require('../assets/backhome.jpg')"/>
    </div>

    <div style="margin-top:289px;">
        <van-button  disabled type="warning">租客登入</van-button>
        <van-button  type="warning" to="/ServiceLogin">客服登入</van-button>
        <van-button  type="warning" to="/RepairerLogin">修理员登入</van-button>
    </div>

      <div style="margin-top:16px">
          <van-button round type="info" size="small" @click="changeway()">{{ways}}</van-button>
      </div>

    <div style="margin-top:5px;">
    <van-form @submit="onSubmit">

      <van-cell-group>
        <van-field
        v-model="username"
        type="email"
        left-icon="contact"
        label-width=60
        name="邮箱"
        label="邮箱"
        placeholder="请输入邮箱"
       :rules="[{ required: true, message: '请填写邮箱' }]"/>
        
        <van-field v-if="firstway"
        v-model="password"
        type="password"
        left-icon="closed-eye"
        label-width=60
        name="密码"
        label="密码"
        placeholder="请输入密码"
        :rules="[{ required: true, message: '请填写密码' }]"/>

        <van-field v-if="secondway"
        :rules="[{ required: true, message: '请填写验证码' }]"
        v-model="password"
        center
        clearable
        label="邮箱验证码"
        placeholder="请输入邮箱验证码">
        <template #button v-if="secondway">
          <van-button size="small" type="primary" @click="submitIdCode()" :disabled="disabled">{{timeContent}}</van-button>
        </template>
        </van-field>

      </van-cell-group>
      <h3 style="color:red;">{{ message }}</h3>
    
      <div style="margin-top: 16px;">
          <van-button round block type="warning" native-type="submit" >登入</van-button>
      </div>
    </van-form>
  </div>


    <div style="margin-top: 16px;">

        <van-button round plain block type="warning"  to="/Register">注册</van-button>

        <p></p>
    </div>

  </div>
</template>

<script>
import Foot_bar from './Foot_bar';
import axios from 'axios';

export default {
  name: 'RenterLogin',
  data () {
    return {
      disabled:false,
      timeContent: '发送验证码',
      msg: '顷刻',
      ways:'邮箱验证码登入',
      username: '',
      password: '',
      secondway:false,
      firstway:true,
      imgSrc:require('../assets/citynight.jpg'),
      radio:'1',
      checked:false,
      subidcode: 'rent',
      message: '',
      type: 'password',
    };
  },
  methods: {
    submitIdCode() {
      var _this = this;
      if(this.timeContent=='发送验证码'){
          let time=59;
          let timer=setInterval(()=>{
              if(time>0){
                  this.timeContent=time+'s';
                  this.disabled=true;
                  time--;
              }
              else{
                  window.clearInterval(timer);
                  this.disabled=false;
                  this.timeContent='发送验证码';
              }
          },1000);
      }
      axios.get('/idcode',{
        params: {
          useremail: _this.username,
        }
      })
      .then(function (response){
          console.log(response);
          _this.subidcode = response.data;
      })
      .catch(function (error) { // 请求失败处理
        console.log(error);
      });
    },

    onSubmit(values) {
      console.log('submit', values);
      var _this = this;
	  
      localStorage.setItem('email',this.username);
      localStorage.setItem('v1','0');
      localStorage.setItem('v2','a');
      localStorage.setItem('v3','4');
      localStorage.setItem('v4','A');
      localStorage.setItem('searchkey','');
      axios.get('/login',{
        params: {
          logintype: 'renter',
          type: _this.type,
          useremail: _this.username,
          password: _this.password,
          subidcode: _this.subidcode,
        }
      })
      .then(function (response){
		  if(response.data.success == true){
		    _this.$router.push({
            path:'/Renter',
            query:{name:_this.username},
          });
		  }
		  else{
		    _this.message= response.data.message;
		  }
      })
      .catch(function (error) { // 请求失败处理
        console.log(error);
      });
    },

    changeway(){
        if(this.firstway==true){
            this.password='';
            this.firstway=false;
            this.secondway=true;
            this.ways='邮箱密码登入';
			      this.type='idcode';
        }
        else{
            this.password='';
            this.firstway=true;
            this.secondway=false;
            this.ways='邮箱验证码登入';
			this.type='password';
        }
    },
    
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
  color:white;
}
h6{
	color:white;
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
    height:60%;  /**宽高100%是为了图片铺满屏幕 */
    z-index:-1;
    position: absolute;
}
</style>
