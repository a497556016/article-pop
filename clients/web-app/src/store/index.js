import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

import article from "./article"
import comment from "./comment"
import user from "./user"

export default new Vuex.Store({
    modules: {
        article,
        comment,
        user
    }
})