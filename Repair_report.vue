<template>
    <div>
    <!--  页首  -->
        <navig-bar></navig-bar>
    <!--  页面  -->
        <div>
            <van-tabs>
                <van-tab title="对工单进行回复">
                    <!--搜索栏-->

                    <!--工单列表-->
                    <van-pull-refresh v-model="refreshingReports" @refresh="onRefreshReports">
                        <van-list
                                v-model="loadingReports"
                                :finished="finishedReports"
                                finished-text="没有更多了"
                                @load="onLoadReports"
                        >
                            <van-cell v-for="item in listReports" :key="item" :title="item.ReportID" :value="item.ReportUser" @click="showReply(item)">
                                <template #icon>
                                    <van-image
                                            round
                                            width="45"
                                            height="45"
                                            src="http://imagemxc.test.upcdn.net//订单.png"
                                    >
                                    </van-image>
                                </template>
                                <template #label>
                                    <div v-if="item.replied=='false'" style="color: #ff0000">待回复</div>
                                    <div v-else style="color: #00aa00">已回复</div>
                                </template>
                            </van-cell>
                        </van-list>
                    </van-pull-refresh>
                </van-tab>
            </van-tabs>
            <!--  工单托盘  -->
            <van-popup v-model="showReplyPanel" position="bottom" @close="cancelReply" :style="{ height: '60%' }">
                <van-form @submit="submitReply">
                    <h4>回复工单</h4>
                    <div style="color: #A9A9A9; float:left; margin-left: 50px; font-size: 20px" >工单id:{{thisItem.ReportID}}</div>
                    <div style="color: #A9A9A9; float:left; margin-left: 50px; font-size: 20px" >租客邮箱:{{thisItem.ReportUser}}</div>
                    <div style="color: #A9A9A9; float:left; margin-left: 50px; font-size: 20px" >任务信息:{{thisItem.ReportItem}}</div>
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
    import tabBar from "../tabBar1";
    import navigBar from "../navigBar1";
    import Vue from 'vue';
    import {Toast} from 'vant';
	import axios from 'axios';
    Vue.use(Toast);
    export default {
        name: "Repair_report",
        components: {
            tabBar,
            navigBar
        },
        data(){
            return{
                reports: [
                    { ReportID: 'R11111', ReportUser: 'U11111111', ReportItem: '新北区15-102  水龙头堵塞', replied: false},
                    { ReportID: 'R22222', ReportUser: 'U22222222', ReportItem: '新北区15-205  空调失效', replied: false},
                ],
                listReports: [

                ],
                searchKey: '',
                loading: false,
                finished: false,
                refreshing: false,
                loadingReports: false,
                finishedReports: false,
                refreshingReports: false,
                showReplyPanel: false,
                thisItem: '',
                editReply: '',
            }
        },
        methods: {
       

            onClearReports(){
                this.searchKey='';
                this.refreshingReports=true;
                this.onRefreshReports();
            },
            onLoadReports() {
                var _this = this;
				  axios.get('/rmreplylist',{
				  params: {
				  Rid:localStorage.getItem('repairemail')
				}
				})
				.then(function (response){
					console.log(response);
                    setTimeout(() => {
                    for (let i = 0; i < response.data.obj.length; i++) {
						_this.reports[0]= response.data.obj[i];
                        _this.listReports.push(_this.reports[0]);
                    }
                    // 加载状态结束
                    _this.loadingReports = false;
                    _this.finishedReports = true;

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
            onRefreshReports() {
                if (this.refreshingReports) {
                    this.listReports = [];
                    this.refreshingReports = false;
                }

                // 清空列表数据
                this.finishedReports = false;

                // 重新加载数据
                // 将 loading 设置为 true，表示处于加载状态
                this.loadingReports = true;
                this.onLoadReports();
            },
            showReply(item){
                this.thisItem=item;
				this.nowId=item.ReportID;
                if(item.replied=='false'){
                    this.showReplyPanel = true;
                }
            },
            cancelReply(){
                this.showReplyPanel = false;
            },
            submitReply(){
				var _this = this;
				axios.get('/rmreplywo',{
				params: {
				  CId:_this.nowId,
				  Reply:_this.editReply,
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					_this.showReplyPanel = false;
                _this.refreshingReports=true;
                _this.onRefreshReports();
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
