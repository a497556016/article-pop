import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

import article from "./article"

export default new Vuex.Store({
    modules: {
        article
    }
})