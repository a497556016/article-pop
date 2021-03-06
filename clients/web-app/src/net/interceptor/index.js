import store from '../../store'
import moduleTypes from '../../store/types'

import Vue from 'vue'

const excludes = [
    '/login'
]

export default (http) => {
    // 添加一个请求拦截器
    http.interceptors.request.use(function (config) {
        // alert(config.baseURL+config.url)

        //过滤不需要拦截的请求
        if(excludes.includes(config.url) || config.url.endsWith('/config.json')){
            return config;
        }

        const loginUser = store.getters[moduleTypes.user.GET_LOGIN_USER_DATA];
        if(loginUser && loginUser.id){
            config.headers['token'] = loginUser.token;
            config.headers['userId'] = loginUser.id;
            config.headers['username'] = encodeURIComponent(loginUser.username);
        }


        return config;
    }, function (error) {
        http.closeLoading();

        // Do something with request error
        return Promise.reject(error);
    });

    // 添加一个响应拦截器
    http.interceptors.response.use(function (response) {
        http.closeLoading();

        // Do something with response data
        console.log('http after', response);
        if(response.data.code == 0 && response.data.msg){
            alert(response.data.msg);
            throw new Error(response);
        }

        return response.data;
    }, function (error) {
        http.closeLoading();

        let msg = '';


        if(error.response){

            if(error.response.status == 500){
                msg = '系统异常！';
            }else if(error.response.status == 401) {
                msg = '没有访问权限！';
            }else if(error.response.status == 408) {
                msg = '登录已超时，请重新登录！';
            }else if(error.response.status == 203) {
                msg = '非法Token值！';
            }else if(error.response.status == 404) {
                msg = `访问路径${error.response.config.url}不存在！`;
            }

            if(error.response.data) {
                msg += error.response.data;
            }
            // msg += error.response.statusText;
        }else if(error.message) {
            msg = error.message;
        }

        alert(msg)

        return Promise.reject(msg);
    });
}