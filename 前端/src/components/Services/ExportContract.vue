<template>
    <div>
        <navig-bar></navig-bar>
        <div class="CTTitle" style="margin-top:100px;">合同编号</div>
        <div class="CTText" v-html="ContractId"></div>
        <div class="CTTitle">租客账号</div>
        <div class="CTText" v-html="RenterEmail"></div>
        <div class="CTTitle">房间编号</div>
        <div class="CTText" v-html="RoomId"></div>
        <div class="CTTitle">客服编号</div>
        <div class="CTText" v-html="ServiceId"></div>
        <div class="CTTitle">租约类型</div>
        <div class="CTText" v-html="ContractType"></div>
        <div class="CTTitle">签订时间</div>
        <div class="CTText" v-html="ContractDate"></div>
        <div class="CTTitle">合同备注</div>
        <div class="CTText" v-html="ContractItem"></div>
    </div>
</template>

<script>
    import navigBar from "../navigBar";
	import axios from 'axios';
    export default {
        name: 'ExportContract',
        components: {
            navigBar,
        },
        data(){
            return{
                ContractId: 'CT1113',
                RenterEmail: 'ming@163.com',
                RoomId: 'R14124',
                ServiceId: 'S13124',
                ContractType: '长租',
                ContractDate: '2020-9-12',
                ContractItem: '自带生活用品'
            }
        },
		created() {
            this.ContractId = this.$route.params.id;
			var _this = this;
				axios.get('/contract',{
				params: {
				  ContractId:_this.ContractId,
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					_this.RenterEmail = response.data.obj[0];
					_this.RoomId=response.data.obj[1];
					_this.ServiceId=localStorage.getItem('semail');
					_this.ContractType=response.data.obj[2];
					_this.ContractDate=response.data.obj[3];
					_this.ContractItem=response.data.obj[4];
				  }
			  })
			  .catch(function (error) { // 请求失败处理
				console.log(error);
			  }); 
        },
    }
</script>

<style scoped>
.CTTitle{
    margin-top: 30px;
    font-size: 20px;
    font-weight: bold;
}

.CTText{
}
</style>