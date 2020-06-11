<template>
    <div id="csusers">
        <!--页首-->
        <navigBar></navigBar>
        <!--页面主体-->
        <div >
            <van-sticky offset-top="200px">
                <van-tabs>
                    <van-tab title="租客管理"><UserList></UserList></van-tab>
                    <van-tab title="修理工管理"><RepairerList></RepairerList></van-tab>
                </van-tabs>
            </van-sticky>

        </div>
        <!--页尾-->
        <tabBar></tabBar>
    </div>
</template>

<script>
    import Vue from 'vue';
    import navigBar from '../navigBar';
    import tabBar from "../tabBar";
    import UserList from "./UserList";
    import RepairerList from "./RepairerList";
    import { Toast } from 'vant';
    Vue.use(Toast);
    new Vue({})
    export default {
        name: 'CSUsers',
        components: {
            navigBar,
            tabBar,
            UserList,
            RepairerList,
        },
        data() {
            return{

            }
        },
        methods: {
        }
    }
</script>

<style scoped>

</style>