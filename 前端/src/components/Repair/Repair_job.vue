<template>
    <div>
    <!--  页首  -->
        <navig-bar></navig-bar>
    <!--  页面  -->
        <div>
            <van-tabs>
                <van-tab title="我的任务">
                    <!--搜索栏-->

                    <!--工单列表-->
                    <van-pull-refresh v-model="refreshingRepair_jobs" @refresh="onRefreshRepair_jobs">
                        <van-list
                                v-model="loadingRepair_jobs"
                                :finished="finishedRepair_jobs"
                                finished-text="没有更多了"
                                @load="onLoadRepair_jobs"
                        >
                            <van-cell v-for="item in listRepair_jobs" :key="item" :title="item.Repair_jobID" :value="item.Repair_jobUser" @click="showReply(item)">
                                <template #icon>
                                    <van-image
                                            round
                                            width="45"
                                            height="45"
                                            src="http://imagemxc.test.upcdn.net//订单 (1).png"
                                    >
                                    </van-image>
                                </template>
                                <template #label>
                                    <div v-if="item.replied=='false'" style="color: #ff0000">待完成</div>
                                    <div v-else style="color: #00aa00">已完成</div>
                                </template>
                            </van-cell>
                        </van-list>
                    </van-pull-refresh>
                </van-tab>
            </van-tabs>
            <!--  工单说明部分  -->
            <van-popup v-model="showReplyPanel" position="bottom" @close="cancelReply" :style="{ height: '60%' }">
                <van-form @submit="submitReply">
                    <h4>工单详细内容</h4>
                    <div style="color: #A9A9A9; float:left; margin-left: 50px; font-size: 20px" >工单id:{{thisItem.Repair_jobID}}</div>
                    <div style="color: #A9A9A9; float:left; margin-left: 50px; font-size: 20px" >租客邮箱:{{thisItem.Repair_jobUser}}</div>
                    <div style="color: #A9A9A9; float:left; margin-left: 50px; font-size: 20px" >任务信息:{{thisItem.Repair_jobItem}}</div>
                    <van-button round block :disabled="thisItem.replied=='true'" type="info" native-type="submit">
                        我已完成该任务
                    </van-button>
                </van-form>
            </van-popup>
        </div>
    <!--  页尾  -->
        <tab-bar></tab-bar>
    </div>
</template>

<script>
    import tabBar from "../tabBar1";
    import navigBar from "../navigBar1";
    import Vue from 'vue';
    import {Toast} from 'vant';
	import axios from 'axios';
    Vue.use(Toast);
    export default {
        name: "Repair_job",
        components: {
            tabBar,
            navigBar
        },
        data(){
            return{
                repair_jobs: [
                    { Repair_jobID: '', Repair_jobUser: '', Repair_jobItem: '', replied: ''},
                   ],
                listRepair_jobs: [

                ],
                searchKey: '',
                loading: false,
                finished: false,
                refreshing: false,
                loadingRepair_jobs: false,
                finishedRepair_jobs: false,
                refreshingRepair_jobs: false,
                showReplyPanel: false,
                thisItem: '',
                editReply: '',
                disabled:false,
				nowId:'',
            }
        },
        methods: {
		
            onClearRepair_jobs(){
                this.searchKey='';
                this.refreshingRepair_jobs=true;
                this.onRefreshRepair_jobs();
            },
            onLoadRepair_jobs() {
                var _this = this;
				  axios.get('/rmlist',{
				  params: {
				  Rid:localStorage.getItem('repairemail')
				}
				})
				.then(function (response){
					console.log(response);
                    setTimeout(() => {
                    for (let i = 0; i < response.data.obj.length; i++) {
						_this.repair_jobs[0]= response.data.obj[i];
                        _this.listRepair_jobs.push(_this.repair_jobs[0]);
						console.log(_this.repair_jobs[0]);
                    }
                    // 加载状态结束
                    _this.loadingRepair_jobs = false;
                    _this.finishedRepair_jobs = true;
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
            onRefreshRepair_jobs() {
                if (this.refreshingRepair_jobs) {
                    this.listRepair_jobs = [];
                    this.refreshingRepair_jobs = false;
                }

                // 清空列表数据
                this.finishedRepair_jobs = false;

                // 重新加载数据
                // 将 loading 设置为 true，表示处于加载状态
                this.loadingRepair_jobs = true;
                this.onLoadRepair_jobs();
            },
            showReply(item){
                this.thisItem=item;
				this.nowId=item.Repair_jobID;
                if(item.replied==true){
                    this.disabled=true;
                }
                this.showReplyPanel = true;
            },
            cancelReply(){
                this.showReplyPanel = false;
            },
            submitReply(){
				var _this = this;
				axios.get('/rmdowo',{
				params: {
				  CId:_this.nowId,
				  Reply:_this.editReply,
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					_this.showReplyPanel = false;
                    _this.refreshingRepair_jobs=true;
                    _this.onRefreshRepair_jobs();
                Toast.success("回复成功");
				  }
			  })
			  .catch(function (error) { // 请求失败处理
				console.log(error);
			  }); 
                
            }
        },
    }
</script>

<style scoped>

</style>
