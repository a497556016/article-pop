import * as types from "./types"
import moduleTypes from "../types"

const state = {
    userData: {
        id: null,
        username: 'test',
        company: '',
        post: '',
        declaration: ''
    }
}

const getters = {
   [types.GET_LOGIN_USER_DATA](state){
       if(!state.userData.id){
           return {
               avatar: require("../../assets/logo.png"),
               username: "临时游客",
               company: "未填写公司信息",
               post: "未填写职位",
               declaration: "我们的征途是星辰大海。"
           }
       }
       return state.userData;
   }
}

const mutations = {}

const actions = {

}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}