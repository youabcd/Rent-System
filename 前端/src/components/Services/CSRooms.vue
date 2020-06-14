<template>
    <div>
        <!--页首-->
        <navig-bar></navig-bar>
        <!--主体部分-->
        <div>
            <!--切换房源-->
            <van-sticky offset-top="200px">
                <van-tabs>
                    <van-tab title="可用房源"><available-house-list></available-house-list></van-tab>
                    <van-tab title="所有房源"><all-house-list></all-house-list></van-tab>
                </van-tabs>
            </van-sticky>
        </div>
        <!--页尾-->
        <tab-bar></tab-bar>
    </div>
</template>

<script>
    import navigBar from "../navigBar";
    import tabBar from "../tabBar";
    import AvailableHouseList from "./AvailableHouseList";
    import AllHouseList from "./AllHouseList";

    export default {
        name: 'CSRooms',
        components: {
            navigBar,
            tabBar,
            AvailableHouseList,
            AllHouseList
        },
        data(){
            return{
            }
        },
        methods: {

        }

    }
</script>

<style scoped>

</style>