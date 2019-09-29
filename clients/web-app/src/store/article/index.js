import * as types from "./types";
import moment from "moment";

import articleApi from "../../api/article"
import moduleTypes from "../types";

const HISTORY_VIEW_RECORDS = "HISTORY_VIEW_RECORDS";

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
    },

    historyViewRecords: []
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
                image: item.cover.startsWith('//')?("https:"+item.cover):item.cover,
                metadata: item
            }
        });
    },
    [types.GET_VIEW_ARTICLE_DATA] (state){
        return state.viewArticleDetail;
    },
    [types.GET_HISTORY_VIEW_RECORDS](state){
        if(!state.historyViewRecords || state.historyViewRecords.length === 0){
            const json = localStorage.getItem(HISTORY_VIEW_RECORDS);
            if(json) {
                state.historyViewRecords = JSON.parse(json);
            }
        }
        return state.historyViewRecords.reverse();
    }
}

const mutations = {
    [types.ADD_HISTORY_VIEW_RECORDS] (state, article){
        state.historyViewRecords.push({
            id: article.id,
            title: article.title,
            content: article.abstractText,
            image: article.cover.startsWith('//')?("https:"+article.cover):article.cover,
            time: new Date().getTime(),
        });
        if(state.historyViewRecords.length > 20){
            state.historyViewRecords.splice(0, 20-state.historyViewRecords.length)
        }
        const json = JSON.stringify(state.historyViewRecords);
        localStorage.setItem(HISTORY_VIEW_RECORDS, json);
    }
}

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