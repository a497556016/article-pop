import Vue from 'vue'
import App from './App.vue'

import router from "./router";
import store from "./store";

const app = {
    install(){
        if("undefined" === typeof cordova){
            this.renderApp();
        }else{
            this.renderInCordova();
        }
    },
    renderApp(){
        new Vue({
            render: h => h(App),
            router,
            store
        }).$mount('#app')
    },
    renderInCordova(){
        document.addEventListener('deviceready', () => {
            this.renderApp();
        }, false);
    }
}

export default app;
