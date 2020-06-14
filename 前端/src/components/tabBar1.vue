<template>
    <div style="margin-top:64px;">
        <router-view />
        <van-tabbar route v-model="active" fixed='fixed' border='true' active-color="#07c160" inactive-color="#000">
            <van-tabbar-item to="/Repair/Repair_report" icon="balance-list-o" >接收并回复任务</van-tabbar-item>
            <van-tabbar-item to="/Repair/Repair_job" icon="newspaper-o">查看任务及评价</van-tabbar-item>
        </van-tabbar>
    </div>

</template>

<script>
    export default {
        name: "tabBar1",
        data(){
            return{
                active: 0,
            }
        }
    }
</script>

<style scoped>

</style>
