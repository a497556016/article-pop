import * as types from "./types";

import articleApi from "../../api/article"

const state = {
    recommendPage: {},
    viewArticleData: {}
}

const getters = {
    [types.HAVE_MORE_PAGES] (state){
        return !state.recommendPage.last;
    },
    [types.GET_RECOMMEND_LIST](state){
        if(!state.recommendPage.content){
            return [];
        }
        return state.recommendPage.content.map(item => {
            return {
                title: item.title,
                content: item.abstractText,
                image: item.cover,
                metadata: item
            }
        });
    },
    [types.GET_VIEW_ARTICLE_DATA] (state){
        return state.viewArticleData;
    }
}

const mutations = {}

const actions = {
    async [types.SELECT_RECOMMEND_PAGE_DATA]({state}){
        const page = await articleApi.selectRecommendList(0);
        state.recommendPage = page;
    },
    async [types.SELECT_NEXT_RECOMMEND_PAGE_DATA]({state}){
        const page = await articleApi.selectRecommendList(state.recommendPage.pageable.pageNumber+1);
        const content = [].concat(...state.recommendPage.content).concat(...page.content);
        page.content = content;
        state.recommendPage = page;
    },
    async [types.FIND_ARTICLE_BY_ID]({state}, id){
        const article = await articleApi.getById(id);
        state.viewArticleData = article;
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}