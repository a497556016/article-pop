<template>
    <he-page :header="true">
        <template slot="header">
            <he-title-bar title="浏览历史记录"></he-title-bar>
        </template>
        <template slot="body">
            <he-panel>
                <he-article-list v-if="records.length" :data="records" :options="{imagePosition: 'left'}" @itemClick="onArticleClick">
                    <div slot="footer-left" slot-scope="{item}">
                        <span v-if="item.time">浏览时间：{{formatDate(item.time)}}</span>
                    </div>
                </he-article-list>
                <he-alert v-else>没有浏览记录</he-alert>
            </he-panel>
        </template>
    </he-page>
</template>

<script>
    import {mapGetters} from "vuex"
    import moduleTypes from "../store/types";
    import moment from "moment"
    export default {
        name: "UserViewsRecord",
        props: {
            id: String
        },
        computed: {
            ...mapGetters({
                records: moduleTypes.article.GET_HISTORY_VIEW_RECORDS
            })
        },
        methods: {
            formatDate(time){
                return moment(time).format("YYYY-MM-DD HH:mm:ss")
            },
            onArticleClick(article){
                const path = '/article/'+article.id+'/'+this.id;

                this.$router.push({
                    path
                })
            }
        }
    }
</script>

<style scoped>

</style>