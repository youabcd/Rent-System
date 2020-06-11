<template>
  <div class="hello">
  <div class="background">
      <img :src="imgSrc" width="100%" height="100%" alt="" />
  </div>

  <van-row>
      <van-col span="8"></van-col>
  </van-row>
    <h1>{{ msg }}</h1>

      
    <div style="margin-top:270px">
    <van-form @submit="onSubmit">
      <van-cell-group>
        <van-field
        v-model="useremail"
        label-width=60
        type="email"
        left-icon="contact"
        name="邮箱"
        label="邮箱"
        placeholder="请输入邮箱"
        :rules="[{ required: true, message: '请填写邮箱' }]"/>
        
        <van-field
        v-model="password1"
        label-width=60
        type="password"
        left-icon="closed-eye"
        name="密码"
        label="密码"
        placeholder="请输入密码"
        :rules="[{ required: true, message: '请填写密码' }]"/>
        
        <van-field
        v-model="password2"
        label-width=60
        type="password"
        left-icon="closed-eye"
        name="再次密码"
        label="再次密码"
        placeholder="请输入与上一次一样的密码"
        :rules="[{ required: true, message: '请填写密码' }]"/>
        
        <van-field
        v-model="userphone"
        label-width=60
        type="digit"
        left-icon="phone-o"
        name="手机号"
        label="手机号"
        placeholder="请输入手机号"
        :rules="[{ required: true, message: '请填写手机号' }]"/>
        
        <van-field
        v-model="username"
        type="text"
        left-icon="contact"
        label-width=60
        name="姓名"
        label="姓名"
        placeholder="请输入您的姓名"
        :rules="[{ required: true, message: '请填写姓名' }]"/>
        
        <van-field
        :rules="[{ required: true, message: '请填写验证码' }]"
        v-model="idcode"
        center
        label="邮箱验证码"
        placeholder="请输入邮箱验证码">
        <template #button>
          <van-button size="small" type="primary" @click="submitIdCode()" :disabled="disabled">{{timeContent}}</van-button>
        </template>
        </van-field>

      </van-cell-group>
    <h3 style="color:red;">{{ message }}</h3>
    <div style="margin-top: 16px;">
    <van-button round block type="warning" native-type="submit" >注册</van-button>
    </div>
    </van-form>
  </div>


    <div>
        
        <p><van-button round plain block type="warning" to="/">返回</van-button></p>
        <p></p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'Register',
  data () {
    return {
      disabled:false,
      timeContent: '发送验证码',
      msg: '顷刻',
      useremail: '',
      password1: '',
      password2: '',
      userphone: '',
      username: '',
      idcode: '',
	  subidcode: 'rent',
	  message: '',
      imgSrc:require('../assets/citynight1.jpg'),
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
          useremail: _this.useremail,
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
	  var _this = this;
      console.log('submit', values);
      axios.get('/register',{
        params: {
          useremail: _this.useremail,
          password1: _this.password1,
          password2: _this.password2,
          userphone: _this.userphone,
          username: _this.username,
		  idcode: _this.idcode,
		  type:'renter',
		  subidcode: _this.subidcode,
        }
      })
      .then(function (response){
	    console.log(response);
		_this.message = response.data.message;
		if(response.data.success == true){
          _this.$router.push({
            path:'/',
          });
        }
      })
      .catch(function (error) { // 请求失败处理
        console.log(error);
      });
    },
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
  color:white
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
    height:50%;  /**宽高100%是为了图片铺满屏幕 */
    z-index:-1;
    position: absolute;
}

</style>
