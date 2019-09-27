import * as types from "./types";
import moment from "moment";

import articleApi from "../../api/article"
import moduleTypes from "../types";

const state = {
    resultPage: {},
    loadMore: {
        haveMore: false,
        lastTime: null,
        position: 0
    },
    viewArticleDetail: {
        article: {},
        liked: false,
        collected: false
    }
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
        return state.viewArticleDetail;
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
        const result = await articleApi.selectDetailById(id);
        state.viewArticleDetail = result;
    },
    async [types.UPDATE_ARTICLE_VIEWS_COUNT]({state, rootGetters}, {articleId, userId}){
        const loginUser = rootGetters[moduleTypes.user.GET_LOGIN_USER_DATA];
        if(loginUser.id && loginUser.id === userId){
            //自己看自己的推广图文不算
            userId = null;
        }

        await articleApi.updateViewsCount(articleId, userId);

        if(state.resultPage.content) {
            const articles = state.resultPage.content.filter(article => article.id == articleId);
            // console.log(article)
            if (articles.length) {
                articles[0].viewsCount = (articles[0].viewsCount || 0) + 1;
            }
        }
    },
    async [types.CHECK_USER_CARD_COUNT]({state, rootGetters}, {articleId, userId}){
        const loginUser = rootGetters[moduleTypes.user.GET_LOGIN_USER_DATA];
        if(loginUser.id && loginUser.id === userId){
            //自己看自己的推广图文不算
            userId = null;
        }
        await articleApi.checkUserCardCount(articleId, userId);
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}