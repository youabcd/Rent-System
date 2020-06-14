<template>
    <van-nav-bar title="顷刻修理工页面" style="color: coral;" fixed='fixed' border='border' @click-left="onClickLeft" @click-right="onClickRight" left-arrow>
        <template #right>
            <div id="typetag">主页</div>
            <van-image
                round
                width="30"
                height="30"
                src="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1442450257,3940326983&fm=26&gp=0.jpg"
                >
            ></van-image>
        </template>
    </van-nav-bar>
</template>

<script>
    export default {
        name: "header1",
        data(){
            return{

            }
        },
        methods: {
            onClickLeft(){
                window.history.back(-1);
            },
            onClickRight(){
                this.$router.push({path: '/Repairer'});
            }
        }
    }
</script>

<style scoped>
#typetag{
    margin-left: 10px;
    margin-right: 10px;
    background: coral;
    width:40px;
    height:20px;
    border-radius:4px;
    text-align: center;
    color: white;
}
</style>
