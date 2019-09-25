import http from "../../net/http"

export default {
    selectList(page, tag){
        return http.withLoading().get("/article/list", {params: {page: page||0, size: 10, tag}})
    },
    getById(id) {
        return http.get("/article/"+id);
    }
}