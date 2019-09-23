import http from "../../net/http"

export default {
    selectRecommendList(page){
        return http.get("/article/list", {params: {page: page||0, size: 10}})
    },
    getById(id) {
        return http.get("/article/"+id);
    }
}