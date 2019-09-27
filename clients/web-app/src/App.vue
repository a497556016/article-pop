<template>
  <div id="app">
    <keep-alive :include="['Home']">
      <router-view></router-view>
    </keep-alive>
  </div>
</template>

<script>

export default {
  name: 'app',
  components: {

  }
}
</script>

<style>
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
</style>
