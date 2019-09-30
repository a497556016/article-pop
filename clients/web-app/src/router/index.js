import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

import routes from './routes'
import store from "../store";

const router = new VueRouter({
    routes,
    // mode: 'history',
    /*scrollBehavior (to, from, savedPosition) {
        console.log(from, document.scrollingElement.scrollTop)
        if (savedPosition) {
            return savedPosition
        } else {
            from.meta.savedPosition = document.scrollingElement.scrollTop;
            return { x: 0, y: to.meta.savedPosition ||0}
        }
    }*/
})

/**
 * 监听返回事件
 */
window.addEventListener("popstate", function(e) {
    router.isBack = true;
}, false);
/**
 * 重写返回方法
 */
VueRouter.prototype.go = function(location){
    this.isBack = true;
    window.history.go(-1);
}

router.beforeResolve((to, from, next) => {
    console.log('resolve router');
    if(router.isBack){
        to.meta.transitionName = 'slide-right';
    }else {
        to.meta.transitionName = 'slide-left';
    }
    router.isBack = false;
    next()
})

router.beforeEach((to, from, next) => {
    console.log('each router')
    // ...
    console.log(location)
    console.log(`切换路由从`,from,`到`,to);
    /*if(to.path.startsWith('/main/user')){
        //check login
        const userInfo = store.getters['account/GET_LOGIN_USER'];
        console.log('检查用户登录信息：', userInfo)
        if(!userInfo || (!userInfo.username && !userInfo.phone)){
            next({path: '/login'});
        }
    }*/
    /*if(to.path.startsWith("/article/")&&!to.query.flag){
        location.assign('/#'+to.fullPath+'?flag=1');
    }else {
        next();
    }*/

    next();
})


export default router;