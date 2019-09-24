import http from "../../net/http"

export default {
    addArticleComment(articleId, comment, username){
      return http.post('/comment', {
          articleId,
          content: comment,
          username: username || '游客'
      });
    },
    queryArticleComments(articleId, page, size){
        return http.get('/comment/'+articleId, {
            params: {
                page: page || 0,
                size: size || 10
            }
        });
    }
}