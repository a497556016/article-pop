import axios from 'axios'
import Vue from 'vue'
import {BASE_URL} from "../../config"

import interceptor from '../interceptor'

interceptor(axios);

// axios默认配置
axios.defaults.timeout = 10000;   // 超时时间
axios.defaults.baseURL = BASE_URL;  // 默认地址

axios.withLoading = function (msg) {
    const toast = Vue.prototype.$createToast({
        msg: msg||'加载中...',
        type: 'loading',
        mask: true,
        time: 0
    });
    toast.show();
    this.$loading = toast;
    return this;
}

axios.closeLoading = function () {
    if(this.$loading) {
        this.$loading.hide();
    }
}

export default axios;