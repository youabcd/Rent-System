<template>
    <div>
        <!--搜索栏-->
        <van-search v-model="searchKey" clearable @search="onSearch" @clear="onClear" placeholder="请输入搜索关键词" />
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

    </div>
</template>

<script>
	import axios from 'axios';
    export default {
        name: 'AvailableHouseList',
        data(){
            return{
                houseList: [
                    { id: '1', title: '新北区15公寓',value: '1000', label: "北京市海淀区学院路38号内15号学生公寓", avatar: "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=402336215,3875604598&fm=26&gp=0.jpg"},
                    { id: '2', title: '普东新苑鑶楼精装两室', value: '2400', label: "天津市北辰区普东街宜白路1728号", avatar: "https://pic1.58cdn.com.cn/anjuke_58/b4a301ce08762cc39cf2363242fc42af?w=696&h=522&crop=1&t=1"}
                ],
                list: [

                ],
                commentWidth: document.documentElement.clientWidth-150,
                loading: false,
                refreshing: false,
                finished: false,
                searchKey: '',

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
				  axios.get('/availableroom',{
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
				  axios.get('/searchavailableroom',{
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
            }
        }
    }
</script>

<style scoped>

</style>