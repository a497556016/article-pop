import http from "../../net/http"

export default {
    save(user){
        return http.withLoading().post('/user', user);
    },
    getById(id){
        return http.withLoading().get('/user/'+id);
    },
    loginWithPwd(username, password){
        return http.get('/user/loginWithPwd', {
            params: {username, password}
        })
    }
}