<template>
    <div>
        <navig-bar></navig-bar>
        <div>
            <van-swipe style="width: 100%; height:200px" :autoplay="3000">
                <van-swipe-item v-for="(image, index) in images" :key="index">
                    <img width="100%" v-lazy="image" />
                </van-swipe-item>
            </van-swipe>
            <van-cell-group>
                <van-cell title="房间编号" center :value="RoomId" icon="records"></van-cell>
                <van-cell title="房间类型" center :value="RoomType" icon="wap-home-o"></van-cell>
                <van-cell title="房间面积" center :value="RoomArea" icon="shrink-o"></van-cell>
                <van-cell title="租金" center :value="RoomRent" icon="gold-coin-o"></van-cell>
                <van-cell title="客服id" center :value="OwnerName" icon="wap-home-o"></van-cell>
                <van-cell title="房间地址" center :value="RoomAddress" icon="location-o"></van-cell>
                <van-cell title="状态" center icon="bill-o">
                    <template #default>
                        <div v-if="RoomAvailable" style="color: #40FF40">
                            房间可用
                        </div>
                        <div v-else style="color: #FF0000">
                            房间不可用
                        </div>
                    </template>
                </van-cell>
                <van-cell title="房间备注" center value="" icon="notes-o"></van-cell>
                <div v-html="RoomItem" style="color: #A9A9A9; font-size: 12px; margin-left: 20px; margin-right: 20px; margin-top: 10px; margin-bottom: 20px; text-align: left; line-height: 150%" ></div>
            </van-cell-group>
            <!--提交按钮-->
            <van-button round style="width: 80%; margin-bottom: 10px" type="primary" @click="showBook" :disabled="!RoomAvailable">租赁房间</van-button>
            <van-button round style="width: 80%; margin-bottom: 80px" @click="showEdit">修改信息</van-button>
            <!--修改按钮-->
            <!--修改属性弹出层-->
            <div>
                <!--租赁房屋-->
                <van-popup round position="bottom" :style="{ height: '30%' }" @close="cancelBook" v-model="showBookPanel">
                    <van-form @submit="submitBook">
                        <van-field
                                v-model="bookData.userId"
                                name="租客账号"
                                label="租客账号"
                                placeholder="租客账号"
                                :rules="[{ required: true, message: '请填写租客账号' }]"
                        />
                        <van-field
                                v-model="bookData.bookType"
                                name="租约类型"
                                label="租约类型"
                                placeholder="租约类型"
                                :rules="[{ required: true, message: '请填写租约类型' }]"
                        />
                        <van-field
                                v-model="bookData.bookComment"
                                name="订单备注"
                                label="订单备注"
                                placeholder="订单备注"
                                :rules="[{ required: true, message: '请填写订单备注' }]"
                        />
                        <van-button round block type="info" native-type="submit">
                            提交
                        </van-button>
                    </van-form>
                </van-popup>
                <!--修改信息-->
                <van-popup round position="bottom" :style="{ height: '90%' }" @close="cancelEdit" v-model="showEditPanel">
                    <van-form @submit="submitEdit">
                        <h5>修改信息</h5>
                        <van-field
                                v-model="editData.RoomType"
                                name="房间类型"
                                label="房间类型"
                                placeholder="房间类型"
                                :rules="[{ required: true, message: '请填写房间类型' }]"
                        />
                        <van-field
                                v-model="editData.RoomArea"
                                type="number"
                                name="房间面积"
                                label="房间面积"
                                placeholder="房间面积(数字)"
                                :rules="[{ required: true, message: '请填写房间面积(数字)' }]"
                        />
                        <van-field
                                v-model="editData.RoomAvailable"
                                name="房间是否可用"
                                label="房间是否可用"
                                placeholder="房间是否可用(true/false)"
                                :rules="[{ required: true, message: '请填写房间是否可用' }]"
                        />
                        <van-field
                                v-model="editData.RoomRent"
                                type="number"
                                name="租金"
                                label="租金"
                                placeholder="租金(数字)"
                                :rules="[{ required: true, message: '请填写租金(数字)' }]"
                        />
                        <van-field
                                v-model="editData.RoomAddress"
                                name="房间地址"
                                label="房间地址"
                                placeholder="房间地址"
                                :rules="[{ required: true, message: '请填写房间地址' }]"
                        />
                        <van-field
                                v-model="editData.RoomItem"
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
        </div>
        <tab-bar></tab-bar>
    </div>
</template>

<script>
    import navigBar from "../navigBar";
    import tabBar from "../tabBar";
    import Vue from 'vue';
    import { Swipe, SwipeItem } from 'vant';
	import axios from 'axios';
    Vue.use(Swipe);
    Vue.use(SwipeItem);
    import {Lazyload} from 'vant';
    Vue.use(Lazyload);
    import {Toast} from 'vant';
    Vue.use(Toast);

    export default {
        name: "roomSP",
        components: {
            navigBar,
            tabBar,
        },
        created() {
            this.RoomId = this.$route.params.id;
			var _this = this;
				axios.get('/roominitial',{
				params: {
				  RoomId:_this.RoomId,
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					_this.RoomType = response.data.obj[0];
					_this.RoomArea = response.data.obj[1];
					_this.RoomRent = response.data.obj[2];
					_this.OwnerName = response.data.obj[3];
					_this.RoomAddress = response.data.obj[4];
				    _this.RoomAvailable = response.data.obj[5];
					_this.RoomItem = response.data.obj[6];
				  }
				  for (let i = 0; i < response.data.pic.length; i++) {
						_this.images[i]= require('../../assets/'+response.data.pic[i]);
                    }
			  })
			  .catch(function (error) { // 请求失败处理
				console.log(error);
			  }); 
        },
        data(){
            return {
                RoomId: '',
                RoomType: '',
                RoomArea: 105.6,
                RoomRent: 1000.0,
                OwnerName: '王石',
                RoomAddress: "北京市海淀区学院路37号内15号学生公寓",
                RoomItem: "",
                RoomAvailable: true,
                images: [ ],
                showEditPanel: false,
                showBookPanel: false,
                bookData: {
                    userId: '',
                    bookType: '',
                    bookComment: '',
                },
                editData: {
                    RoomType: '',
                    RoomArea: 0.0,
                    RoomAvailable: true,
                    RoomRent: '999999',
                    OwnerName: '',
                    RoomItem: '',
                    RoomAddress: ''
                }
            }
        },
        methods: {
            showEdit(){
                this.editData.OwnerName=this.OwnerName;
                this.editData.RoomAvailable=this.RoomAvailable;
                this.editData.RoomItem=this.RoomItem;
                this.editData.RoomRent=this.RoomRent;
                this.editData.RoomType=this.RoomType;
                this.editData.RoomArea=this.RoomArea;
                this.editData.RoomAddress=this.RoomAddress;
                this.showEditPanel = true;
            },
            cancelEdit(){
                this.showEditPanel = false;
            },
            submitEdit(){
			  var _this = this;
			  axios.get('/modifyroom',{
				params: {
				  RoomId:_this.RoomId,
				  RoomType: _this.editData.RoomType,
				  RoomAvailable:_this.editData.RoomAvailable,
				  RoomRent:_this.editData.RoomRent,
				  RoomAddress:_this.editData.RoomAddress,
				  RoomArea:_this.editData.RoomArea,
				  RoomItem:_this.editData.RoomItem,
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					  _this.showEditPanel = false;
					  Toast.success("修改成功");
				  }
			  })
			  .catch(function (error) { // 请求失败处理
				console.log(error);
			  });
            },
            showBook(){
                this.showBookPanel = true;
            },
            cancelBook(){
                this.showBookPanel = false;
            },
            submitBook(){
				var _this = this;
				axios.get('/srentroom',{
				params: {
				  RoomId:_this.RoomId,
				  UserId:_this.bookData.userId,
				  BookType: _this.bookData.bookType,
				  BookComment:_this.bookData.bookComment,
				  
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					 Toast.success("租赁成功");
					 _this.RoomAvailable = false;
					 _this.showBookPanel = false;
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