<template>
    <div>
        <!--新建按钮-->
        <div v-bind:style="{top: hoverHeight+'px', left: hoverWidth+'px'}" style="position: fixed; z-index: 1" >
            <van-icon name="add-o" size="30px" @click="newPerson"></van-icon>
        </div>
        <van-search v-model="searchKey" clearable @search="onSearch" @clear="onClear" placeholder="请输入搜索关键词" />
        <!--下拉菜单-->
        <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
            <van-list
                    v-model="loading"
                    :finished="finished"
                    finished-text="没有更多了"
                    @load="onLoad"
            >
                <van-cell v-for="item in list" :key="item" :title="item.title" title-style="text-align: left; margin-left: 20px" :icon="item.avatar" @click="onClickUser(item.id)">
                    <template #icon>
                        <van-image
                                round
                                width="45"
                                height="45"
                                src="http://imagemxc.test.upcdn.net//工人.png"
                        >
                        </van-image>
                    </template>
                    <template #label>
                        <div v-bind:style="{width: commentWidth+'px'}"><div class="van-ellipsis">{{item.label}}</div></div>
                    </template>
                </van-cell>
            </van-list>
        </van-pull-refresh>

        <!--新建菜单-->
        <van-popup round v-model="showNewPersonPanel" closeable position="bottom" :style="{ height: '50%' }">
            <h3>新建修理工</h3>
            <van-form @submit="onSubmit">
                <van-field
                        v-model="username"
                        name="用户名（邮箱）"
                        label="用户名（邮箱）"
                        placeholder="用户名（邮箱）"
                        :rules="[{ required: true, message: '请填写用户名' }]"
                />
                <van-field
                        v-model="password"
                        type="password"
                        name="密码"
                        label="密码"
                        placeholder="密码"
                        :rules="[{ required: true, message: '请填写密码' }]"
                />
                <van-field
                        v-model="cpassword"
                        type="password"
                        name="确认密码"
                        label="确认密码"
                        placeholder="确认密码"
                        :rules="[{ required: true, message: '请填写密码' }]"
                />
				<van-field
                        v-model="realname"
                        name="姓名"
                        label="姓名"
                        placeholder="姓名"
                        :rules="[{ required: true, message: '请填写用户真实姓名' }]"
                />
                <van-field
                        v-model="phoneNumber"
                        name="电话号码"
                        label="电话号码"
                        placeholder="电话号码"
                        :rules="[{ required: true, message: '请填写用户电话号码' }]"
                />
                <div style="margin: 16px;">
                    <van-button round block type="info" native-type="submit">
                        提交
                    </van-button>
                </div>
            </van-form>
        </van-popup>

        <!--用户操作菜单-->
        <van-action-sheet v-model="showUserPanel" :actions="actions" @select="onSelectUser" />

        <!--确认删除框-->
        <van-popup v-model="showDeletePanel" round style="height: 200px; width: 300px">
            <div style="margin-top: 40px; margin-bottom: 20px">您确认要删除该修理工吗？</div>
            <div style="margin-bottom: 10px"><van-button round type="danger" style="width: 180px" @click="deleteSuccess">删除</van-button></div>
            <div><van-button round type="default" style="width: 180px" @click="deleteCancel">取消</van-button></div>
        </van-popup>
    </div>
</template>

<script>
    import {Toast} from "vant";
import axios from 'axios';
    export default {
        name: "RepairerList",
        data() {
            return{
                commentWidth: document.documentElement.clientWidth-100,
                hoverHeight: document.documentElement.clientHeight-150,
                hoverWidth: document.documentElement.clientWidth-50,
                showActions: false,
                loading: false,
                finished: false,
                refreshing: false,
                showNewPersonPanel: false, // 注册页面
                showUserPanel: false, // 查看、删除页面
                showDeletePanel: false, // 确认删除页面
                width: 0,
				realname: '',
				phoneNumber: '',
                userList: [
                    {id: "", title: "", label: ""}
                ],
                list: [

                ],
                username: '',
                password: '',
                cpassword: '',
                actions: [
                    { name: '查看详情' },
                    { name: '删除修理工' ,color: "#ff0000"},
                ],
                thisUser: '',
                searchKey: '',
            }
        },
        methods: {
            onLoad() {
                var _this = this;
				  axios.get('/repairmanlist',{
				})
				.then(function (response){
                    setTimeout(() => {
                    for (let i = 0; i < response.data.obj.length; i++) {
						_this.userList[0]= response.data.obj[i];
                        _this.list.push(_this.userList[0]);
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
            onSelectUser(item) {
                // 默认情况下点击选项时不会自动收起
                // 可以通过 close-on-click-action 属性开启自动收起
                //Toast(item.name);
                if(item.name=="查看详情"){
                    //Toast.success("查看详情");
                    this.toUserHome(this.thisUser);
                }
                else
                    this.showDeletePanel = true;
            },
            onclick(){
                this.showActions = true;
            },
            onSearch(){
                var _this = this;
				  axios.get('/searchrepairman',{
				params: {
				  Key: _this.searchKey,
				}
				})
				.then(function (response){
				   
                    setTimeout(() => {
                    _this.list=[];
					for (let i = 0; i < response.data.obj.length; i++) {
						_this.userList[0]= response.data.obj[i];
                        _this.list.push(_this.userList[0]);
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
            newPerson() {
                this.showNewPersonPanel = true;
            },
            onSubmit() {
				var _this = this;
				axios.get('/addrepairman',{
				params: {
				  UserName:_this. username,
                  Ps1:_this.password,
                  Ps2:_this.cpassword,
				  Phone:_this.phoneNumber,
				  Name:_this.realname,
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					Toast.success('提交成功');
                    _this.showNewPersonPanel = false;
                    // 记得更新列表
                    _this.refreshing = true;
                    _this.onRefresh();
				  }
				   else
                    Toast.fail('提交失败');
			  })
			  .catch(function (error) { // 请求失败处理
				console.log(error);
			  });      
            },
            onClickUser(UserId) {
                // 选中用户的动作
                this.thisUser = UserId;
                this.showUserPanel = true;
            },
            deleteSuccess(){
				var _this = this;
				axios.get('/deleterepairman',{
				params: {
				  UserId:_this.thisUser,
				}
			  })
			  .then(function (response){
				  console.log(response);
				  if(response.data.success == true){
					Toast.success("删除成功");
					_this.showUserPanel = false;
					_this.showDeletePanel = false;
					// 记得更新列表
					_this.refreshing = true;
					_this.onRefresh();
				  }
			  })
			  .catch(function (error) { // 请求失败处理
				console.log(error);
			  });   
            },
            deleteCancel(){
                this.showDeletePanel = false;
            },
            toUserHome(userId){
                this.$router.push({ path: "/CS/repairerHomeSP/" + userId });
            }
        }
    }
</script>

<style scoped>
</style>