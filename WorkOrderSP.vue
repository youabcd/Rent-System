<template>
    <div>
        <h3>工单详情</h3>
        <!--  页首  -->
        <navig-bar></navig-bar>
        <!--  主要内容  -->
        <van-cell-group>
            <van-cell title="工单编号:" :value="WorkOrderId" icon="records"/>
            <van-cell title="房间编号:" :value="RoomId" icon="wap-home-o"/>
            <van-cell v-if="RepairmanId" title="师傅编号:" :value="RepairmanId" icon="manager-o"/>
            <van-cell v-if="ServiceId" title="客服编号:" :value="ServiceId" icon="service-o"/>
            <van-cell title="报修备注:" :value="WorkOrderItem" icon="notes-o"/>
        </van-cell-group>

        <van-image width="50" height="50" :src="picture" />

        <van-button v-if="RepairmanId==''" style="margin-top: 50px; " round block type="primary" @click="showArrange">安排师傅</van-button>
        <!--设置师傅-->
        <van-popup v-model="showArrageRPPanel" position="bottom" @close="cancelArrange" :style="{ height: '30%' }">
            <h5>修改昵称</h5>
            <van-form @submit="submitArrange">
                <van-field
                        v-model="arrange.RepairmanId"
                        name="师傅ID"
                        label="师傅ID"
                        placeholder="请填写要安排的修理工ID"
                        :rules="[{ required: true, message: '请填写要安排的修理工ID' }]"
                />
                <div style="margin: 16px;">
                    <van-button round block type="info" native-type="submit">
                        提交
                    </van-button>
                </div>
            </van-form>
        </van-popup>
        <!--  页尾  -->
        <tab-bar></tab-bar>

    </div>
</template>

<script>
    import navigBar from "../navigBar";
    import tabBar from "../tabBar";
    import Vue from 'vue';
    import {Toast}from 'vant';
	import axios from 'axios';
    Vue.use(Toast);
    export default {
        name: 'WorkOrderSP',
        components: {
            navigBar,
            tabBar
        },
        created() {
            this.WorkOrderId=this.$route.params.id;
			var _this = this;
				axios.get('/workorderinitial',{
				params: {
				  WorkOrderId:_this.WorkOrderId,
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					_this.RoomId = response.data.obj[0];
					_this.WorkOrderItem = response.data.obj[1];
					_this.ServiceId=response.data.obj[2];
					_this.RepairmanId=response.data.obj[3];
					_this.picture = response.data.obj[4];
				  }
			  })
			  .catch(function (error) { // 请求失败处理
				console.log(error);
			  }); 
        },
        data(){
            return{
                WorkOrderId: '',
                RoomId: 'R1321',
                RepairmanId: '',
                ServiceId: '',
                WorkOrderItem: '换水龙头',
				SID:localStorage.getItem('semail'),
                showArrageRPPanel: false,
                arrange: {
                    RepairmanId:'',
                    ServiceId: '',
                },
                picture:'',
            }
        },
        methods:{
            showArrange(){
                this.showArrageRPPanel = true;
            },
            cancelArrange(){
                this.showArrageRPPanel = false;
            },
            submitArrange(){
				 var _this = this;
			  axios.get('/addworkorder',{
				params: {
				  WorkOrderId:_this.WorkOrderId,
				 ServiceId:_this.SID,
				  RepairmanId:_this.arrange.RepairmanId,
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
				  _this.ServiceId=localStorage.getItem('semail');
				  _this.RepairmanId=_this.arrange.RepairmanId;
				  _this.showArrageRPPanel = false;
					Toast.success("安排成功");  
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