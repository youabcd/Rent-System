<template>
  <div style="margin-top:110px;">
    <router-view/>
    <van-tabbar route v-model="active">
        <van-tabbar-item replace icon="home-o" to="/Renter">首页</van-tabbar-item>
        <van-tabbar-item  replace to="/MyInfo" icon="https://b.yzcdn.cn/vant/icon-demo-1126.png">
            <span>个人中心</span>
        </van-tabbar-item>
        <van-tabbar-item replace icon="search" to="/Search">搜索</van-tabbar-item>
        <van-tabbar-item replace icon="http://imagemxc.test.upcdn.net//服务评价-客服.png" to="/ConnectService">投诉/报修</van-tabbar-item>
        <van-tabbar-item replace icon="chat-o" to="/Setting">评价</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
export default {
  name: 'Foot_bar',
  data () {
    return {
        active: 0,
        icon: {
            active: 'https://img.yzcdn.cn/vant/user-active.png',
            inactive: 'https://img.yzcdn.cn/vant/user-inactive.png',
        },
    }
  }
}
</script>

<style scoped>
</style>
