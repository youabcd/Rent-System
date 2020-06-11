<template>
    <div>
        <!--页首-->
        <navig-bar></navig-bar>

        <div>
            <!-- 个人页面展示 -->
            <!-- 头像 -->
            <div style="margin-top:30%; margin-bottom: 10%">
                <van-image
                        round
                        width="120"
                        height="120"
                        v-bind:src=userAvatar
                ></van-image>
            </div>
            <!--ID-->
            <div id="showID">{{userName}}</div>
            <van-cell-group>
                <van-cell title="租客账号:" :value="userId" icon="records"/>
                <van-cell title="真实姓名:" :value="userName" icon="user-o" @click="setName"/>
                <van-cell title="真实性别:" :value="userSex" icon="friends-o" @click="setSex"/>
                <van-cell title="手机号码:" :value="userPhoneNumber" icon="phone-o" @click="setPhoneNumber"/>
            </van-cell-group>
        </div>
        <!--修改姓名托盘-->
        <van-popup v-model="showNameEdit" position="bottom" @close="cancelName" :style="{ height: '30%' }">
            <h5>修改姓名</h5>
            <van-form @submit="submitName">
                <van-field
                        v-model="newName"
                        name="真实姓名"
                        label="真实姓名"
                        placeholder="请填写该用户的真实姓名"
                        :rules="[{ required: true, message: '请填写该用户的真实姓名' }]"
                />
                <div style="margin: 16px;">
                    <van-button round block type="info" native-type="submit">
                        提交
                    </van-button>
                </div>

            </van-form>
        </van-popup>
        <!--修改性别托盘-->
        <van-popup v-model="showSexEdit" position="bottom" @close="cancelSex" :style="{ height: '30%' }">
            <h5>修改性别</h5>
            <van-form @submit="submitSex">
                <van-radio-group v-model="newSex" direction="horizontal">
                    <van-radio name="男">男</van-radio>
                    <van-radio name="女">女</van-radio>
                </van-radio-group>
                <div style="margin: 16px;">
                    <van-button round block type="info" native-type="submit">
                        提交
                    </van-button>
                </div>
            </van-form>
        </van-popup>
        <!--修改手机号码托盘-->
        <van-popup v-model="showPhoneEdit" position="bottom" @close="cancelPhoneNumber" :style="{ height: '30%' }">
            <h5>修改手机号码</h5>
            <van-form @submit="submitPhoneNumber">
                <van-field
                        v-model="newPhoneNumber"
                        name="手机号码"
                        label="手机号码"
                        placeholder="请填写该用户的手机号码"
                        :rules="[{ required: true, message: '请填写该用户的手机号码' }]"
                />
                <div style="margin: 16px;">
                    <van-button round block type="info" native-type="submit">
                        提交
                    </van-button>
                </div>

            </van-form>
        </van-popup>
        <tab-bar></tab-bar>
    </div>
</template>

<script>
    import Vue from 'vue';
    import navigBar from '../navigBar';
    import tabBar from "../tabBar";
	import axios from 'axios';
	import {Toast} from "vant";
    export default {
        name: 'userHomeSP',
        components: {
            navigBar,
            tabBar,
        },
        data() {
            return{
                userId: '',
                userName: '张飞',
                userSex: '男',
                userPhoneNumber: '12345678910',
                userAvatar: 'http://imagemxc.test.upcdn.net//用户.png',
                showNickEdit: false,
                showNameEdit: false,
                showSexEdit: false,
                showPhoneEdit: false,
                showAvatarEdit: false,
                newName: '',
                newSex: '男',
                newPhoneNumber: '',
                columns: [
                    '男', '女'
                ],
                activeNames: ['1'],
            }
        },
        created() {
            this.userId = this.$route.params.id;
			var _this = this;
				axios.get('/rentinitial',{
				params: {
				  UserId:_this.userId,
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					_this.userName = response.data.obj[0];
					_this.userPhoneNumber = response.data.obj[1];
					_this.userSex=response.data.obj[2];
				  }
			  })
			  .catch(function (error) { // 请求失败处理
				console.log(error);
			  }); 
        },
        methods: {
            setName(){
                this.showNameEdit = true;
            },
            submitName(){
				var _this = this;
				axios.get('/changerentername',{
				params: {
				  UserId:_this.userId,
				  UserName:_this.newName,
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					_this.showNameEdit = false;
					Toast.success("修改成功");
					_this.userName = _this.newName;
				  }
			  })
			  .catch(function (error) { // 请求失败处理
				console.log(error);
			  }); 
            },
            cancelName(){
                this.showNameEdit = false;
            },
            setSex(){
                this.showSexEdit = true;
            },
            submitSex(value){
				var _this = this;
				axios.get('/changerentersex',{
				params: {
				  UserId:_this.userId,
				  UserSex:_this.newSex,
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					_this.showSexEdit = false;
					Toast.success("修改成功");
					_this.userSex = _this.newSex;
				  }
			  })
			  .catch(function (error) { // 请求失败处理
				console.log(error);
			  }); 
            },
            cancelSex(){
                this.showSexEdit = false;
            },
            setPhoneNumber(){
                this.showPhoneEdit = true;
            },
            submitPhoneNumber(){
				var _this = this;
				axios.get('/changerenterphone',{
				params: {
				  UserId:_this.userId,
				  UserPhone:_this.newPhoneNumber,
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					_this.showSexEdit = false;
					Toast.success("修改成功");
					_this.userPhoneNumber = _this.newPhoneNumber;
				  }
			  })
			  .catch(function (error) { // 请求失败处理
				console.log(error);
			  }); 
            },
            cancelPhoneNumber(){
                this.showPhoneEdit = false;
            },
            afterRead(){
                Toast.success("上传成功");
            },
        }
    }
    //import {Toast} from 'vant';
    Vue.use(Toast);
</script>

<style scoped>

</style>