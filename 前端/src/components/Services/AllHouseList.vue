<template>
    <div>
        <!--搜索栏-->
        <van-search v-model="searchKey" clearable @search="onSearch" @clear="onClear" placeholder="请输入搜索关键词" />
        <!--新建房屋-->
        <div v-bind:style="{top: hoverHeight+'px', left: hoverWidth+'px'}" style="position: fixed; z-index: 1" >
            <van-icon name="add-o" size="30px" @click="showNewHouse"></van-icon>
        </div>
        <!--下拉列表-->
        <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
            <van-list
                    v-model="loading"
                    :finished="finished"
                    finished-text="没有更多了"
                    @load="onLoad"
            >
                <van-cell v-for="item in list" :key="item" :value="item.value" :title="item.title" title-style="text-align: left; margin-left: 20px" :icon="item.avatar" @click="onClickHouse(item.id)" >
                    <template #icon>
                        <van-image
                                width="45"
                                height="45"
                                v-bind:src="item.avatar"
                        >
                        </van-image>
                    </template>
                    <template #default>
                        <div style="font-size: 20px; color: #FF0000">{{item.value}}</div><div style="color: #FF0000">元/月</div>
                    </template>
                    <template #label>
                        <div v-bind:style="{width: commentWidth+'px'}"><div class="van-ellipsis">{{item.label}}</div></div>
                    </template>
                </van-cell>
            </van-list>
        </van-pull-refresh>
        <!--  新建房屋  -->
        <van-popup round position="bottom" :style="{ height: '90%' }" @close="cancelNewHouse" v-model="showNewHousePanel">
            <van-form @submit="submitNewHouse">
                <h5>新建房屋</h5>
                <van-field
                        v-model="newHouse.RoomType"
                        name="房间类型"
                        label="房间类型"
                        placeholder="房间类型"
                        :rules="[{ required: true, message: '请填写房间类型' }]"
                />
                <van-field
                        v-model="newHouse.RoomArea"
                        type="number"
                        name="房间面积"
                        label="房间面积"
                        placeholder="房间面积(数字)"
                        :rules="[{ required: true, message: '请填写房间面积(数字)' }]"
                />
                <van-field
                        v-model="newHouse.RoomAvailable"
                        name="房间是否可用"
                        label="房间是否可用"
                        placeholder="房间是否可用(true/false)"
                        :rules="[{ required: true, message: '请填写房间是否可用' }]"
                />
                <van-field
                        v-model="newHouse.RoomRent"
                        type="number"
                        name="租金"
                        label="租金"
                        placeholder="租金(数字)"
                        :rules="[{ required: true, message: '请填写租金(数字)' }]"
                />
                <!--<van-field
                        v-model="newHouse.OwnerName"
                        name="房东姓名"
                        label="房东姓名"
                        placeholder="房东姓名"
                        :rules="[{ required: true, message: '请填写房东姓名' }]"
                />
                <van-field
                        v-model="newHouse.OwnerPhone"
                        name="房东联系电话"
                        type="tel"
                        label="房东联系电话"
                        placeholder="房东联系电话"
                        :rules="[{ required: true, message: '请填写房东联系电话' }]"
                />-->
                <van-field
                        v-model="newHouse.RoomAddress"
                        name="房间地址"
                        label="房间地址"
                        placeholder="房间地址"
                        :rules="[{ required: true, message: '请填写房间地址' }]"
                />
                <van-field
                        v-model="newHouse.RoomItem"
                        type="textarea"
                        :autosize="{maxHeight: 150, minHeight: 150}"
                        name="房间备注"
                        label="房间备注"
                        placeholder="房间备注"
                        :rules="[{ required: true, message: '请填写房间备注' }]"
                />
                <van-button style="margin-bottom: 10px" round block type="info" native-type="submit">
                    提交
                </van-button>
            </van-form>
        </van-popup>
    </div>
</template>

<script>
    import Vue from 'vue';
    import {Toast} from 'vant';
	import axios from 'axios';
    Vue.use(Toast);
    export default {
        name: "AllHouseList",
        data(){
            return{
                houseList: [{id: '', title: '',value: 0.0, label: '', avatar: ''}],
                list: [

                ],
                commentWidth: document.documentElement.clientWidth-150,
                loading: false,
                refreshing: false,
                finished: false,
                searchKey: '',
                hoverHeight: document.documentElement.clientHeight-150,
                hoverWidth: document.documentElement.clientWidth-50,
                showNewHousePanel: false,
                newHouse: {
                    RoomType: 'single',
                    RoomArea: 0.0,
                    RoomAvailable: 'available',
                    RoomRent: '999999',
                    OwnerName: '',
                    OwnerPhone: '',
                    RoomItem: '',
                    RoomAddress: ''
                },
				picture:'',

            }
        },
        methods: {
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
            onLoad() {
				var _this = this;
				  axios.get('/serviceallroom',{
				})
				.then(function (response){
                    setTimeout(() => {
                    for (let i = 0; i < response.data.obj.length; i++) {
					_this.picture=require('../../assets/'+response.data.pic[i]);
						response.data.obj[i].avatar=_this.picture;
						_this.houseList[0]= response.data.obj[i];
                        _this.list.push(_this.houseList[0]);
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
            onSearch(){
				var _this = this;
				  axios.get('/searchallroom',{
				params: {
				  Key: _this.searchKey,
				}
			  })
				.then(function (response){
                    setTimeout(() => {
					_this.list=[];
                    for (let i = 0; i < response.data.obj.length; i++) {
					_this.picture=require('../../assets/'+response.data.pic[i]);
						response.data.obj[i].avatar=_this.picture;
						_this.houseList[0]= response.data.obj[i];
                        _this.list.push(_this.houseList[0]);
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
            onClickHouse(houseId){
                this.$router.push({path: '/CS/CSRooms/'+houseId});
            },
            showNewHouse(){
                this.showNewHousePanel = true;
            },
            cancelNewHouse(){
                this.showNewHousePanel = false;
            },
            submitNewHouse(){
                 var _this = this;
			  axios.get('/serviceaddroom',{
				params: {
				  RoomType: _this.newHouse.RoomType,
				  RoomAvailable:_this.newHouse.RoomAvailable,
				  RoomRent:_this.newHouse.RoomRent,
				  RoomAddress:_this.newHouse.RoomAddress,
				  RoomArea:_this.newHouse.RoomArea,
				  RoomItem:_this.newHouse.RoomItem,
				  ServiceId: localStorage.getItem('semail'),

				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					  Toast.success("创建成功");
					  _this.showNewHousePanel = false;
					  _this.refreshing=true;
					  _this.onRefresh();  
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