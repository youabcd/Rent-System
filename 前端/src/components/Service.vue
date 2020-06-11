
<template>
    <div id='cussrv'>
        <!-- 页首 -->
        <navig-bar></navig-bar>
        <!-- 页面主体 -->
        <div>
            <!-- 个人页面展示 -->
            <!-- 头像 -->
            <div style="margin-top:30%; margin-bottom: 10%">
                <van-image
                    round
                    width="120"
                    height="120"
                    v-bind:src=csAvatar
                ></van-image>
            </div>
            <!--ID-->
                <div id="showID">{{ServiceId}}</div>
            <van-cell-group>
                <van-cell title="客服姓名:" :value="ServiceName"/>
                <van-cell title="客服性别:" :value="ServiceSex"/>
                <van-cell title="电话号码:" :value="ServicePhone"/>
            </van-cell-group>
        </div>

        <div>
            <van-button round type="warning" to="/" block>退出</van-button>
        </div>

        <!-- 底边栏 -->
        <div style="margin-top:60px;">
            <tabBar></tabBar>
        </div>
    </div>
</template>

<script>
    import Vue from 'vue';
    import navigBar from './navigBar';
    import tabBar from "./tabBar";
	import axios from 'axios';
    // import { NavBar } from 'vant';
    // Vue.use(NavBar);
    new Vue({});
    export default {
    name:'Service',
    el: '#cussrv',
    components: {
        tabBar,
        navigBar
    },
	created() {
            this.ServiceId=localStorage.getItem('semail');
			var _this = this;
				axios.get('/servicehome',{
				params: {
				  ServiceId:_this.ServiceId,
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					_this.ServiceName = response.data.obj[0];
					_this.ServiceSex = response.data.obj[1];
					_this.ServicePhone=response.data.obj[2];
				  }
			  })
			  .catch(function (error) { // 请求失败处理
				console.log(error);
			  }); 
        },
    data() {
        return {
            csAvatar: "http://imagemxc.test.upcdn.net//服务评价-客服.png",
            ServiceId: "答案唯一的四重选择题",
            ServiceName: "张蓓浩",
            ServiceSex: "男",
            ServicePhone: "12345678910"
        }
    },
    methods: {
    }
    }
    import { Toast } from 'vant';
    Vue.use(Toast);
</script>

<style>
#showID{
    margin-bottom: 40px;
    font-size: 20px;
    font-weight: bolder;
    horiz-align: center;
    vertical-align: center;
}
</style>