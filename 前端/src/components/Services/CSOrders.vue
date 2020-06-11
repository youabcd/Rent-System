<template>
    <div id='csorders'>
        <!-- 页首 -->
        <navig-bar></navig-bar>
        <!-- 页面主体 -->
        <div>
            <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
                <van-list
                        v-model="loading"
                        :finished="finished"
                        finished-text="没有更多了"
                        @load="onLoad"
                >
                    <van-cell v-for="item in list" :key="item" :title="item.title" :value="item.value" :label="item.label" @click="onclick(item.title)">
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
                            <div v-if="item.viewed=='waiting'" style="color: #A9A9A9">未审核</div>
                            <div v-if="item.viewed=='accepted'" style="color: #40FF40">已通过</div>
							<div v-if="item.viewed=='rejected'" style="color: #FF0000">未通过</div>
							<div v-if="item.viewed=='renew'" style="color: #FF0000">续租审核</div>
                        </template>
                    </van-cell>
                </van-list>
            </van-pull-refresh>
        </div>

        <!-- 选项菜单 -->
        <van-action-sheet v-model="show" :actions="actions" :color="actions.color"  @select="onSelect" />
        <!--删除-->
        <van-popup v-model="showDeletePanel" style="width: 200px; height: 150px" @close="cancelDelete" round>
            <div style="font-size: 20px; color: #FF0000 ; margin-top: 30px">您确定要删除该订单吗？</div>
            <van-button type="danger" @click="submitDelete">确认</van-button>
            <van-button type="default" @click="cancelDelete">取消</van-button>
        </van-popup>
        <!-- 底边栏 -->
        <div>
            <tabBar></tabBar>
        </div>
    </div>
</template>

<script>
    import Vue from 'vue';
    import navigBar from '../navigBar';
    import tabBar from "../tabBar";
	import axios from 'axios';
    // import { NavBar } from 'vant';
    // Vue.use(NavBar);
    new Vue({
    });
    export default {
        name: 'CSOrders',
        el: '#csorder',
        components: {
            tabBar,
            navigBar
        },
        data() {
            return {
                show: false,
                actions: [
                    { name: '查看详情' },
                    { name: '删除合同' ,color: "#ff0000"},
                ],
                testlist: [
                    {title: "O12560",value: "新北区15-433", viewed: "0"}
                ],
                list: [

                ],
                loading: false,
                finished: false,
                refreshing: false,
                thisOrder: '',
                showDeletePanel: false,
            }
        },
        methods: {
            onLoad() {
                 var _this = this;
				  axios.get('/orderslist',{
				})
				.then(function (response){
                    setTimeout(() => {
                    for (let i = 0; i < response.data.obj.length; i++) {
						_this.testlist[0]= response.data.obj[i];
                        _this.list.push(_this.testlist[0]);
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
            onSelect(item) {
                // 默认情况下点击选项时不会自动收起
                // 可以通过 close-on-click-action 属性开启自动收起
                this.show = false;
                // Toast(item.name);
                if(item.name=='查看详情')
                    this.$router.push("/CS/CSOrders/"+this.thisOrder);
                else{
                    this.show=false;
                    this.showDeletePanel = true;
                }

            },
            onclick(orderId){
                this.show = true;
                this.thisOrder = orderId;
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
            cancelDelete(){
                this.showDeletePanel =false;
            },
            submitDelete(){
				 var _this = this;
				axios.get('/deleteorder',{
				params: {
				  OrderId:_this.thisOrder,
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					 _this.showDeletePanel = false;
					 Toast.success("删除成功");
					 _this.refreshing=true;
				     _this.onRefresh();
				  }
			  })
			  .catch(function (error) { // 请求失败处理
				console.log(error);
			  });   
			  this.list = [];
			   this.finished = false;
			   this.loading = true;
			  this.onLoad();
            }
			
        }
    }
    import { Toast } from 'vant';
    Vue.use(Toast);
</script>

<style scoped>

</style>