import * as types from "./types"
import moduleTypes from "../types"

import commentApi from "../../api/comment"

const state = {
    articleComments: []
}

const getters = {
    [types.GET_ARTICLE_COMMENTS] (state) {
        return state.articleComments;
    }
}

const mutations = {}

const actions = {
    async [types.LOAD_ARTICLE_COMMENTS] ({state, rootGetters}){
        console.log(rootGetters);
        const article = rootGetters[moduleTypes.article.GET_VIEW_ARTICLE_DATA];
        const page = await commentApi.queryArticleComments(article.id);
        state.articleComments = page.content;
    },
    async [types.ADD_ARTICLE_COMMENT] ({state, rootGetters}, commentContent) {
        const article = rootGetters[moduleTypes.article.GET_VIEW_ARTICLE_DATA];
        const comment = await commentApi.addArticleComment(article.id, commentContent);
        state.articleComments = [comment].concat(state.articleComments);
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}