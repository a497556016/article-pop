<template>
  <div id="app">
      <transition :name="transitionName" :mode="viewMode">
          <keep-alive :include="['Home']">
              <router-view class="child-view"></router-view>
          </keep-alive>
      </transition>
  </div>
</template>

<script>

export default {
  name: 'app',
  components: {

  },
  data(){
    return {
      viewMode: ''
    }
  },
    computed: {
        transitionName(){
            return this.$route.meta.transitionName;
        }
    }
}
</script>

<style lang="less">
  html, body  {
    height: 100%;
    width: 100%;
    margin: 0;
  }
  #app{
    height: 100%;
    width: 100%;
    background: #efefef;
  }

  .child-view {
    position: absolute;
    width:100%;
    height: 100%;
    /*transition: all .5s cubic-bezier(.55,0,.1,1);*/
  }
  .slide-left-enter, .slide-right-leave-active {
      /*opacity: 0;*/
    -webkit-transform: translate(100%, 0);
    transform: translate(100%, 0);
  }
  .slide-left-leave-active, .slide-right-enter {
    /*opacity: 0;*/
    -webkit-transform: translate(-100%, 0);
    transform: translate(-100%, 0);
  }

  //转场动画
  .slide-right-enter,
  .slide-left-enter,
  .slide-right-enter-active,
  .slide-right-leave-active,
  .slide-left-enter-active,
  .slide-left-leave-active {
    // 启用硬件加速
    will-change: transform;
    transition: all 500ms cubic-bezier(0.25,0.1,0.25,1);//cubic-bezier(.55,0,.1,1);
    /*position: fixed;*/
  }

</style>
