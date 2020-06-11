<template>
    <div id='cussrv'>
        <!-- 页首 -->
        <navig-bar></navig-bar>
        <!-- 页面主体 -->
        <div>
            <!-- 个人页面展示 -->
            <!-- 头像 -->
            <div style="margin-top:10%; margin-bottom: 10%">
                <van-image
                    round
                    width="120"
                    height="120"
                    src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591425744399&di=2b326790db442e271f6d6fc98dc63eac&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F17%2F02%2F08%2Faf6cdbfbdb33ff3d721f66ba4ea8763f.jpg%2521%2Ffwfh%2F804x804%2Fquality%2F90%2Funsharp%2Ftrue%2Fcompress%2Ftrue"
                ></van-image>
            </div>
            <!--ID-->
                <div id="showID">你好，{{RepairId}}</div>
            <van-cell-group>
                <van-cell title="姓名:" :value="RepairName"/>
                <van-cell title="性别:" :value="RepairSex"/>
                <van-cell title="电话号码:" :value="RepairPhone"/>
            </van-cell-group>
        </div>

        <van-button type="danger" to='/RepairerLogin'>退出登入</van-button>

        <!-- 底边栏 -->
        <div>
            <tabBar></tabBar>
        </div>
    </div>
</template>

<script>
    import Vue from 'vue';
    import navigBar from './navigBar1';
    import tabBar from "./tabBar1";
	import axios from 'axios';
    // import { NavBar } from 'vant';
    // Vue.use(NavBar);
    new Vue({
    });
    export default {
    el: '#cussrv',
    name:'Repair',
    components: {
        tabBar,
        navigBar
    },
    data() {
        return {
            csAvatar: "http://imagemxc.test.upcdn.net//服务评价-客服.png",
            RepairId: localStorage.getItem('repairemail'),
            RepairName: "李四",
            RepairSex: "男",
            RepairPhone: "12345678910"
        }
    },
    methods: {
    },
	mounted(){
		var _this = this;
		axios.get('/showrepairmaninfo',{
			params:{
				rapairId : _this.RepairId,
			}
		})
		.then(function(response){
			_this.RepairName = response.data.name;
			_this.RepairSex = response.data.sex;
			_this.RepairPhone = response.data.phone;
		})
		.catch(function(error){
			console.log(error);
		});
	},
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
