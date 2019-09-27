import http from "../../net/http"

export default {
    selectList(page, tag){
        return http.withLoading().get("/article/list", {params: {page: page||0, size: 10, tag}})
    },
    getById(id) {
        return http.get("/article/"+id);
    },
    selectDetailById(id){
        return http.get("/article/detail/"+id);
    },
    updateViewsCount(articleId, userId, wxUsername) {
        if(!articleId) {
            console.error('articleId不能为空！');
            return;
        }
        return http.put('/article/view', null, {
            params: {
                articleId,
                userId,
                wxUsername
            }
        });
    },
    checkUserCardCount(articleId, userId){
        if(!articleId || !userId) {
            console.error(`articleId和userId不能为空！`)
            return;
        }

        return http.put('/article/checkUserCard', null, {
            params: {
                articleId,
                userId,
            }
        })
    },
    like(articleId, userId, liked){
        if(!articleId || !userId) {
            console.error(`articleId和userId不能为空！`)
            return;
        }
        return http.post('/article/like', null, {
            params: {articleId, userId, liked}
        })
    },
    collect(articleId, userId, collected){
        if(!articleId || !userId) {
            console.error(`articleId和userId不能为空！`)
            return;
        }
        return http.post('/article/collect', null, {
            params: {articleId, userId, collected}
        })
    }
}