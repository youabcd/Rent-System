<template>
    <div>
        <h3>订单详情</h3>
        <!--  页首  -->
        <navig-bar></navig-bar>
        <!--  主要内容  -->
        <van-cell-group>
            <van-cell title="订单编号:" :value="OrderId" icon="records"/>
            <van-cell title="房间编号:" :value="RoomId" icon="wap-home-o"/>
            <van-cell title="租客账号:" :value="RentalEmail" icon="manager-o"/>
            <van-cell title="租约类型:" :value="OrderType" icon="service-o"/>
            <van-cell title="审核状态:" :value="AuditStatus" icon="service-o">
                <template #default>
                    <div v-if="AuditStatus=='已通过'" style="color: #40FF40">已通过</div>
                    <div v-else-if="AuditStatus=='未审核'" style="color: #A9A9A9">未审核</div>
                    <div v-else style="color: #FF0000">未通过</div>
                </template>
            </van-cell>
            <van-cell title="订单备注:" :value="OrderItem" icon="notes-o"/>
        </van-cell-group>
        <div v-if="AuditStatus=='未审核'">
            <van-button style="margin-top: 50px; " round block type="primary" @click="auditAccept">审核通过</van-button>
            <van-button style="margin-top: 20px; " round block type="danger" @click="auditDeny">审核不通过</van-button>
        </div>

        <div v-if="AuditStatus=='已通过'">
            <van-button v-if="OrderType=='长租'" style="margin-top: 20px; " round block type="primary" @click="exportContract">导出合同</van-button>
        </div>
		
        <!--  页尾  -->
        <tab-bar></tab-bar>

    </div>
</template>

<script>
    import navigBar from "../navigBar";
    import tabBar from "../tabBar";
    import Vue from 'vue';
	import axios from 'axios';
    import {Toast}from 'vant';
    Vue.use(Toast);
    export default {
        name: 'OrderSP',
        components: {
            navigBar,
            tabBar
        },
        created() {
            this.OrderId=this.$route.params.id;
			var _this = this;
				axios.get('/orderinitial',{
				params: {
				  OrderId:_this.OrderId,
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					_this.RoomId = response.data.obj[0];
					_this.RentalEmail = response.data.obj[1];
					_this.OrderType=response.data.obj[2];
					_this.AuditStatus=response.data.obj[3];
					_this.OrderItem=response.data.obj[4];
				  }
			  })
			  .catch(function (error) { // 请求失败处理
				console.log(error);
			  }); 
        },
        data(){
            return{
                OrderId: '',
                RentalEmail: 'shang@163.com',
                RoomId: 'R1321',
                OrderType: '',
                OrderItem: '自带生活用品',
                AuditStatus: '未审核',
				ServiceId:localStorage.getItem('semail'),
            }
        },
        methods:{
            auditAccept(){
				var _this = this;
				axios.get('/acceptorder',{
				params: {
				  OrderId:_this.OrderId,
				  ServiceId:_this.ServiceId,
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					Toast.success("审核成功");
                    _this.AuditStatus='已通过';
				  }
			  })
			  .catch(function (error) { // 请求失败处理
				console.log(error);
			  }); 
            },
            auditDeny(){
			var _this = this;
				axios.get('/rejectorder',{
				params: {
				  OrderId:_this.OrderId,
				  ServiceId:_this.ServiceId,
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					 Toast.success("审核成功");
					 _this.AuditStatus='未通过';
				  }
			  })
			  .catch(function (error) { // 请求失败处理
				console.log(error);
			  }); 
            },
            exportContract(){
                this.$router.push("/CS/ExportContract/"+this.OrderId);
            }
        }
    }
</script>

<style scoped>

</style>