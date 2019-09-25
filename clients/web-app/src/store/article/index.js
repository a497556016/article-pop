import * as types from "./types";
import moment from "moment";

import articleApi from "../../api/article"

const state = {
    resultPage: {},
    loadMore: {
        haveMore: false,
        lastTime: null,
        position: 0
    },
    viewArticleData: {}
}

const getters = {
    [types.HAVE_MORE_PAGES] (state){
        return state.loadMore;
    },
    [types.GET_RECOMMEND_LIST](state){
        if(!state.resultPage.content){
            return [];
        }
        return state.resultPage.content.map(item => {
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
    async [types.SELECT_ARTICLE_PAGE_DATA]({state}, tag){
        const page = await articleApi.selectList(0, tag);
        state.resultPage = page;
        state.loadMore = {
            haveMore: !page.last,
            lastTime: moment().format('HH:mm:ss'),
            position: page.content.length-1
        }
    },
    async [types.SELECT_NEXT_ARTICLE_PAGE_DATA]({state}, tag){
        const page = await articleApi.selectList(state.resultPage.pageable.pageNumber+1, tag);
        state.loadMore = {
            haveMore: !page.last,
            lastTime: moment().format('HH:mm:ss'),
            position: page.content.length-1
        }
        const content = [].concat(...page.content).concat(...state.resultPage.content);
        page.content = content;
        state.resultPage = page;

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