<template>
  <div class="MyInfo">
  <div class="background">
      <img :src="imgSrc" width="100%" height="100%" alt="" />
  </div>
  <van-row type="flex" justify="end">
      <h2><a :href="islogin" @click="judge" style="color:blue;">{{myid}}</a></h2>
  </van-row>

    <h1>{{ msg }}</h1>
    <van-icon name="user-circle-o" size="100px"/>


    <div style="margin-top: 200px;">
        <p><van-cell  size="large" title="修改个人信息" is-link to="/Change" /></p>
        <p><van-cell  size="large" title="查看自己的小屋" is-link to="/CheckRoom" /></p>
        <p><van-cell  size="large" title="我的收藏" is-link to="/Collection" /></p>
        <p><van-cell  size="large" title="申请续租" is-link to="/Check_out" /></p>
        <p><van-button type="danger" to="/" @click="quit">退出登入</van-button></p>
        <p></p>
        <p></p>
        <p></p>
    </div>

        

    <div>
        <p></p>
        <p></p>
        <p></p>
        <p></p>
        <p></p>
    </div>
    

    <div style="margin-top:16px;">
        <Foot_bar></Foot_bar>
    </div>
  </div>
</template>

<script>
import Foot_bar from './Foot_bar'
export default {
  name: 'MyInfo',
  data () {
    return {
      msg: '个人中心',
      imgSrc:require('../assets/MyInfo_back.gif'),
      islogin:'#/MyInfo',
      myid:localStorage.getItem('email'),

    }
  },
  methods:{
      quit(){
          localStorage.setItem('email','请先登入');
          localStorage.setItem('searchkey','');
          localStorage.setItem('v1',0);
          localStorage.setItem('v2','a');
          localStorage.setItem('v3',3);
          localStorage.setItem('v4','A');
      },
      judge(){
          if(this.myid=="请先登入"){
              this.islogin="/";
          }
      },
  },
  components:{
      Foot_bar
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
  color:white
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
.background{
    width:100%;  
    height:50%;  /**宽高100%是为了图片铺满屏幕 */
    z-index:-1;
    position: absolute;

}
</style>
