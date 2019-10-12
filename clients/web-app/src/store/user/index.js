import * as types from "./types"
import moduleTypes from "../types"

import userApi from "../../api/user"

const STORAGE_USER_KEY = "storage_user_key";

const defaultUser = {
    avatar: require("../../assets/user.png"),
    username: "临时游客",
    company: "未填写公司信息",
    post: "未填写职位",
    declaration: "我们的征途是星辰大海。"
}

const state = {
    userData: {
        id: null,
        username: '',
        company: '',
        post: '',
        declaration: ''
    }
}

const getters = {
   [types.GET_LOGIN_USER_DATA](state){
       if(state.userData.id){
           return state.userData;
       }
       //取缓存
       const json = localStorage.getItem(STORAGE_USER_KEY);
       if(json){
           state.userData = JSON.parse(json);
       }
       if(!state.userData.id){
           return Object.assign({}, defaultUser);;
       }
       return state.userData;
   }
}

const mutations = {
    [types.SET_LOGIN_USER_DATA] (state, user){
        state.userData = user;
        localStorage.setItem(STORAGE_USER_KEY, JSON.stringify(user));
    },
    [types.LOGOUT](state){
        state.userData = Object.assign({}, defaultUser);
        localStorage.removeItem(STORAGE_USER_KEY);
    }
}

const actions = {
    async [types.SAVE_USER]({state, commit}, user){
        if (!user.collectedArticles){
            delete user.collectedArticles;
        }
        if (!user.likedArticles){
            delete user.likedArticles;
        }
        const re = await userApi.save(user);
        commit(types.SET_LOGIN_USER_DATA, re);
    },
    async [types.LOGIN_WITH_PWD]({state, commit}, account) {
        console.log(account)
        const user = await userApi.loginWithPwd(account.username, account.password);
        commit(types.SET_LOGIN_USER_DATA, user);
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}