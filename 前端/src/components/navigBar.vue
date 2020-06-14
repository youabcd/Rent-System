<template>
  <div style="margin:32px">
    <van-nav-bar title="顷刻" fixed='fixed' border='border' @click-left="onClickLeft" @click-right="onClickRight" left-arrow>
        <template #right>
            <div id="typetag">客服</div>
            <van-image
                round
                width="30"
                height="30"
                v-bind:src=csAvatar
            ></van-image>
        </template>
    </van-nav-bar>
  </div>
</template>

<script>
    export default {
        name: "header",
        data(){
            return{
                csAvatar: "http://imagemxc.test.upcdn.net//服务评价-客服.png"
            }
        },
        methods: {
            onClickLeft(){
                window.history.back(-1);
            },
            onClickRight(){
                this.$router.push({path: '/Service/'});
            }
        }
    }
</script>

<style scoped>
#typetag{
    margin-left: 10px;
    margin-right: 10px;
    background: #FF66CC;
    width:40px;
    height:20px;
    border-radius:4px;
    text-align: center;
    color: white;
}
</style>