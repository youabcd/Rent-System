<template>
    <div>
    <!--  页首  -->
        <navig-bar></navig-bar>
    <!--  页面  -->
        <div>
            <van-tabs>
                <van-tab title="报修">
                    <!--搜索栏-->
                    <van-search v-model="searchKey" clearable @search="onSearch" @clear="onClear" placeholder="请输入搜索关键词" />
                    <!--工单列表-->
                    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
                        <van-list
                                v-model="loading"
                                :finished="finished"
                                finished-text="没有更多了"
                                @load="onLoad"
                        >
                            <van-cell v-for="item in list" :key="item" :title="item.WorkOrderId" :value="item.RoomId"  @click="onclickWO(item.WorkOrderId)">
                                <template #icon>
                                    <van-image
                                            round
                                            width="45"
                                            height="45"
                                            src="http://imagemxc.test.upcdn.net//工单.png"
                                    >
                                    </van-image>
                                </template>
                                <template #label>
                                    <div  v-if="item.RepairmanId" style="color:#40FF40">已建立工单</div>
                                    <div v-else style="color: #EE0000">待建立工单</div>
                                </template>
                            </van-cell>
                        </van-list>
                    </van-pull-refresh>
                </van-tab>
                <van-tab title="投诉">
                    <!--搜索栏-->
                    <van-search v-model="searchKey" clearable @search="onSearchComplaints" @clear="onClearComplaints" placeholder="请输入搜索关键词" />
                    <!--投诉列表-->
                    <van-pull-refresh v-model="refreshingComplaints" @refresh="onRefreshComplaints">
                        <van-list
                                v-model="loadingComplaints"
                                :finished="finishedComplaints"
                                finished-text="没有更多了"
                                @load="onLoadComplaints"
                        >
                            <van-cell v-for="item in listComplaints" :key="item" :title="item.ComplaintID" :value="item.ComplaintUser" @click="showReply(item)">
                                <template #icon>
                                    <van-image
                                            round
                                            width="45"
                                            height="45"
                                            src="http://imagemxc.test.upcdn.net//投诉.png"
                                    >
                                    </van-image>
                                </template>
                                <template #label>
                                    <div v-if="item.replied=='false'" style="color: #EE0000">待回复</div>
                                    <div v-else style="color: #40FF40">已回复</div>
                                </template>
                            </van-cell>
                        </van-list>
                    </van-pull-refresh>
                </van-tab>
            </van-tabs>
            <!--  投诉托盘  -->
            <van-popup v-model="showReplyPanel" position="bottom" @close="cancelReply" :style="{ height: '60%' }">
                <van-form @submit="submitReply">
                    <h4>回复投诉</h4>
                    <van-cell title="投诉内容:" value=""></van-cell>
					<div style="color: #A9A9A9; float:left; margin-left: 32px; font-size: 14px" v-html="thisItem"></div>
                    <van-cell title="图片描述:" value=""></van-cell>
					<van-list>
 					 <van-image v-for="item in complaintImg" :key="item" style="width: '90%" fit="contain" :src="item.src" />
					</van-list>
                    <van-field
                            v-model="editReply"
                            name="回复内容"
                            label="回复内容"
                            placeholder="请填写回复内容"
                            :rules="[{ required: true, message: '请填写回复内容' }]"
                    />
                    <van-button round block type="info" native-type="submit">
                        提交
                    </van-button>
                </van-form>
            </van-popup>
        </div>
    <!--  页尾  -->
        <tab-bar></tab-bar>
    </div>
</template>

<script>
    import tabBar from "../tabBar";
    import navigBar from "../navigBar";
    import Vue from 'vue';
    import {Toast} from 'vant';
	import axios from 'axios';
    Vue.use(Toast);
    export default {
        name: 'CSWorkOrder',
        components: {
            tabBar,
            navigBar
        },
        data(){
            return{
                workOrders: [
                    { WorkOrderId: '', RoomId: '', RepairmanId: "", ServiceId: "", WorkOrderItem: "", replied: ""},
                    ],
                list: [
                    
                ],
                complaints: [
                    { ComplaintID: '', ComplaintUser: '', ComplaintItem: '', replied: ''},
                   ],
                listComplaints: [

                ],
                searchKey: '',
                loading: false,
                finished: false,
                refreshing: false,
                loadingComplaints: false,
                finishedComplaints: false,
                refreshingComplaints: false,
                showReplyPanel: false,
                thisItem: '',
                editReply: '',
				complaintImg:[{src:''},{src:''},{src:''},{src:''},{src:''},{src:''},{src:''},{src:''}],
				nowId:'',
            }
        },
        methods: {
            onSearch(){
                var _this = this;
				  axios.get('/searchworkorder',{
				params: {
				  Key: _this.searchKey,
				}
				})
				.then(function (response){
					console.log(response);
                    setTimeout(() => {
					_this.list=[];
                    for (let i = 0; i < response.data.obj.length; i++) {
						_this.workOrders[0]= response.data.obj[i];
                        _this.list.push(_this.workOrders[0]);
                    }
                    // 加载状态结束
                    _this.loading = false;
                    _this.finished = true;

                    // 数据全部加载完成
                    // if (this.list.length >= this.actions.length) {
                    // this.finished = true;
                    // }
                }, 1000);
				})
				.catch(function (error) { // 请求失败处理
				  console.log(error);
				});
            },
            onClear(){
                this.searchKey='';
                this.refreshing=true;
                this.onRefresh();
            },
            onLoad() {
                var _this = this;
				  axios.get('/workorderlist',{
				})
				.then(function (response){
					console.log(response);
                    setTimeout(() => {
                    for (let i = 0; i < response.data.obj.length; i++) {
						_this.workOrders[0]= response.data.obj[i];
                        _this.list.push(_this.workOrders[0]);
                    }
                    // 加载状态结束
                    _this.loading = false;
                    _this.finished = true;

                    // 数据全部加载完成
                    // if (this.list.length >= this.actions.length) {
                    // this.finished = true;
                    // }
                }, 1000);
				})
				.catch(function (error) { // 请求失败处理
				  console.log(error);
				});
            },
            onRefresh() {
                if (this.refreshing) {
                    this.list = [];
                    this.refreshing = false;
                }

                // 清空列表数据
                this.finished = false;

                // 重新加载数据
                // 将 loading 设置为 true，表示处于加载状态
                this.loading = true;
                this.onLoad();
            },
            onclickWO(woID){
                this.$router.push({path: '/CS/CSWorkOrders/'+woID});
            },
            onSearchComplaints(){
			var _this = this;
				  axios.get('/csearch',{
				params: {
				  Key: _this.searchKey,
				}
				})
				.then(function (response){
					console.log(response);
                    setTimeout(() => {
					this.listComplaints=[];
                    for (let i = 0; i < response.data.obj.length; i++) {
						_this.complaints[0]= response.data.obj[i];
                        _this.listComplaints.push(_this.complaints[0]);
                    }
                    // 加载状态结束
                    _this.loadingComplaints = false;
                    _this.finishedComplaints = true;

                    // 数据全部加载完成
                    // if (this.list.length >= this.actions.length) {
                    // this.finished = true;
                    // }
                }, 1000);
				})
				.catch(function (error) { // 请求失败处理
				  console.log(error);
				});
            },
            onClearComplaints(){
                this.searchKey='';
                this.refreshingComplaints=true;
                this.onRefreshComplaints();
            },
            onLoadComplaints() {
				var _this = this;
				  axios.get('/clist',{
				})
				.then(function (response){
					console.log(response);
                    setTimeout(() => {
                    for (let i = 0; i < response.data.obj.length; i++) {
						_this.complaints[0]= response.data.obj[i];
                        _this.listComplaints.push(_this.complaints[0]);
                    }
                    // 加载状态结束
                    _this.loadingComplaints = false;
                    _this.finishedComplaints = true;

                    // 数据全部加载完成
                    // if (this.list.length >= this.actions.length) {
                    // this.finished = true;
                    // }
                }, 1000);
				})
				.catch(function (error) { // 请求失败处理
				  console.log(error);
				});
            },
            onRefreshComplaints() {
                if (this.refreshingComplaints) {
                    this.listComplaints = [];
                    this.refreshingComplaints = false;
                }

                // 清空列表数据
                this.finishedComplaints = false;

                // 重新加载数据
                // 将 loading 设置为 true，表示处于加载状态
                this.loadingComplaints = true;
                this.onLoadComplaints();
            },
            showReply(item){
				var _this = this;
				console.log(item);
				axios.get('/cinitial',{
				params: {
				  CId:item.ComplaintID,
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					for (let i = 0; i < response.data.pic.length; i++) {
						_this.complaintImg[i].src= response.data.pic[i];
                    }
					_this.nowId=item.ComplaintID;
					_this.showReplyPanel = true;
					_this.thisItem=response.data.obj[0];
				  }
			  })
			  .catch(function (error) { // 请求失败处理
				console.log(error);
			  }); 
            },
            cancelReply(){
                this.showReplyPanel = false;
            },
            submitReply(){
				var _this = this;
				axios.get('/creply',{
				params: {
				  CId:_this.nowId,
				  Reply:_this.editReply,
				  Sid:localStorage.getItem('semail'),
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					_this.showReplyPanel = false;
					_this.refreshingComplaints=true;
					_this.onRefreshComplaints();
					Toast.success("回复成功");
				  }
			  })
			  .catch(function (error) { // 请求失败处理
				console.log(error);
			  }); 
                
            }
        }
    }
</script>

<style scoped>

</style>