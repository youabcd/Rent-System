<template>
    <div>
        <router-view />
        <van-tabbar route v-model="active" fixed='fixed' border='true' active-color="#07c160" inactive-color="#000">
            <van-tabbar-item to="/CS/CSOrders" icon="balance-list-o" >订单管理</van-tabbar-item>
            <van-tabbar-item to="/CS/CSUsers" icon="friends-o">用户管理</van-tabbar-item>
            <van-tabbar-item to="/CS/CSRooms" icon="wap-home-o">房间管理</van-tabbar-item>
            <van-tabbar-item to="/CS/CSWorkOrders" icon="newspaper-o">工单管理</van-tabbar-item>
            <van-tabbar-item to="/Service" icon="user-circle-o">我的账户</van-tabbar-item>
        </van-tabbar>
    </div>

</template>

<script>
    export default {
        name: "tabBar",
        data(){
            return{
                active: 0,
            }
        }
    }
</script>

<style scoped>

</style>