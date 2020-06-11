<template>
  <div class="Renting">   
      <h1>{{roomid}}</h1>
      <h1>{{money}}</h1>

      <van-picker
      title="请选择租期"
      :default-index="0"
      show-toolbar
      :columns="columns"
      @confirm="onConfirm"
      @cancel="onCancel"
      @change="onChange"/> 

      <h1>已选时长：{{time}}</h1>
      <van-cell is-link @click="showPopup">查看合同内容</van-cell>
      <van-popup
      v-model="show"
      closeable
      style="white-space: pre-wrap;"
      position="bottom"
      :style="{ height: '50%' }"><span style="font-size:25px;">{{hetong}}</span></van-popup>

      <p>{{msg}}</p>
      <van-button type="primary" @click="pay" :disabled="disabled">提交订单</van-button>
      <p><van-button type="primary" to="/Search">返回</van-button></p>

  </div>
</template>

<script>
import { Toast } from 'vant';
import axios from 'axios';

export default {
  name: 'Renting',
  data () {
    return {
        roomid:'',
        time:'3天',
        money:'',
        timeindex:0,
        disabled:false,
        msg:'如已经确认没有问题，请确认提交，等待客服回复。',
        show: false,
        hetong:'合同\n甲方义务和责任:\n乙方义务和责任:\n租客租房时长:\n所需租金:\n所需定金:\n甲方签名:\n乙方签名:',
        columns: ['3天','一周','两周','三周','1个月', '2个月', '3个月', '4个月', '12个月'],
		myid:localStorage.getItem('email'),
		success:null,
    }
  },
  methods:{
      onConfirm(value, index) {
          this.time=this.columns[index];
      },
      onChange(picker, value, index) {
          this.time=this.columns[index];
          this.timeindex=index;
      },
      onCancel() {
      },
      showPopup() {
          this.show = true;
      },
      pay(){
          this.msg='提交成功！请等待客服回复，或直接加客服微信进行协商';
          this.disabled=true;
		  var _this = this;
		  axios.get('/pay',{
			params:{
				user:_this.myid,
				roomid:_this.roomid,
				type:_this.timeindex,
			}
		  })
		  .then(function(response){
			_this.success = response.data;
			if(response.data == false){
				Toast('提交失败');
			}
			else{
				Toast('提交成功');
			}
		  })
		  .catch(function(error){
			console.log(error);
		  });
      }
  },
  created(){
      this.roomid = this.$route.query.roomid;
      this.money=this.$route.query.money;
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
