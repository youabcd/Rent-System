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
        label="再次输入密码"
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
        v-model="yzm"
        center
        clearable
        label="邮箱验证码"
        placeholder="请输入邮箱验证码">
        <template #button>
          <van-button size="small" type="primary" @click="idcode">发送验证码至邮箱</van-button>
        </template>
        </van-field>
      </van-cell-group>
    
	<h2 v-if="this.success==false" style="color:red;">{{message}}</h2>
	<h2 v-if="this.success==true" style="color:green;">修改成功</h2>
	
    <div style="margin-top: 16px;">
    <van-button round block type="warning" native-type="submit" >确认修改</van-button>
    </div>
    </van-form>
  </div>


    <div>
        
        <p><van-button round plain block type="warning" to="/MyInfo">返回</van-button></p>
        <p></p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import {Toast} from 'vant';

export default {
  name: 'Register',
  data () {
    return {
      msg: '顷刻',
	  email: localStorage.getItem('email'),
      useremail: localStorage.getItem('email'),
      password1: '',
      password2: '',
      userphone: '',
      username: '',
      yzm: '',
	  subidcode: 'rent',
      imgSrc:require('../assets/citynight1.jpg'),
	  message: '',
	  success: null,
    };
  },
  methods: {
    onSubmit(values) {
      console.log('submit', values);
	  var _this = this;
	  axios.get('/changeinfo',{
        params: {
		  type:'renter',
		  email: _this.email,
		  useremail: _this.useremail,
		  subidcode: _this.subidcode,
		  password1: _this.password1,
		  password2: _this.password2,
		  idcode: _this.yzm,
		  username: _this.username,
		  userphone: _this.userphone,
        }
      })
      .then(function (response){
	    _this.success = response.data.success;
	    if(response.data.success==true){
	        localStorage.setItem('email',this.useremail);
	        Toast('修改成功！')
	        this.$router.push({
	            path:'/MyInfo',
	        })
		}
		else{
		  _this.message = response.data.message;
		}
      })
      .catch(function (error) { // 请求失败处理
        console.log(error);
      });
    },
	idcode(){
	  var _this = this;
	  axios.get('/idcode',{
        params: {
          useremail: _this.useremail,
        }
      })
      .then(function (response){
	    _this.subidcode = response.data;
      })
      .catch(function (error) { // 请求失败处理
        console.log(error);
      });
	},
  },
  mounted(){
    var _this = this;
    axios.get('/showinfo',{
        params: {
          useremail: _this.useremail,
		  type: 'renter',
        }
      })
      .then(function (response){
	    _this.userphone = response.data.phone;
		_this.username = response.data.name;
      })
      .catch(function (error) { // 请求失败处理
        console.log(error);
      });
  }
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
