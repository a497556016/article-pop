<template>
    <div class="home">
        <he-title-bar title="I推广" :left="{back: false}" :right="{icon: 'fa fa-user'}"></he-title-bar>
        <he-scroll-nav-bar style="position: fixed;top: 4em;left: 0.5em;right: 0.5em;z-index: 999;" :data="tagsData" :active-index="1" @change="onTagsChange"></he-scroll-nav-bar>
        <div ref="body" class="body">
            <he-panel style="margin-top: 10px">
                <he-article-list :data="recommendList" :options="{imagePosition: 'left', loadMore: haveMore, loadingBarPosition: 5}" @loadMore="loadMore" @itemClick="onItemClick">
                    <div slot="footer-left" slot-scope="{metadata}">
                        <span v-if="metadata.source">来源：{{metadata.source}} </span>
                        <span v-if="metadata.chineseTag">标签：{{metadata.chineseTag}}</span>
                    </div>
                    <div slot="footer-right" slot-scope="{metadata}">
                        {{metadata.date}}
                    </div>
                </he-article-list>
<!--                <div class="load-more" v-if="haveMore" @click="loadMore" align="center">加载更多</div>-->
            </he-panel>
        </div>
    </div>
</template>

<script>
    import {mapGetters,mapActions} from "vuex"
    import moduleTypes from "../store/types"

    export default {
        name: "Home",
        data(){
            return {
                tagsData: [
                    '推荐',
                    '热点',
                    '科技',
                    '体育',
                    '社会',
                    '两性',
                    '娱乐',
                    '国际观察'
                ],
                scrollTop: 0
            }
        },
        computed: {
            ...mapGetters({
                haveMore: moduleTypes.article.HAVE_MORE_PAGES,
                recommendList: moduleTypes.article.GET_RECOMMEND_LIST
            })
        },
        activated(){
            // console.log(this.scrollTop)
            this.$refs.body.scrollTo(0, this.scrollTop)
        },
        mounted(){
            this.selectRecommendList();
            this.$refs.body.onscroll = (e) => {
                // console.log(e.target.scrollTop)
                this.scrollTop = e.target.scrollTop;
            }
        },
        methods: {
            ...mapActions({
                selectRecommendList: moduleTypes.article.SELECT_RECOMMEND_PAGE_DATA,
                loadNextPage: moduleTypes.article.SELECT_NEXT_RECOMMEND_PAGE_DATA
            }),
            onTagsChange(item, index){
                console.log(item, index)
            },
            onItemClick(article) {
                console.log(article)
                this.$router.push({
                    path: '/article/'+article.metadata.id
                })
            },
            async loadMore(){
                await this.loadNextPage();
                this.$refs.body.scrollTo(0, 0);
            }
        }
    }
</script>

<style scoped lang="less">
    .home{
        /*height: 100%;*/
        /*width: 100%;*/
        .body{
            position: absolute;
            top: 7em;
            left: 0.5em;
            right: 0.5em;
            bottom: 0.5em;
            overflow: hidden auto;

            .load-more {
                text-align: center;
                padding: 1em 0;
                font-size: 0.8em;
                color: #8888ff;
            }
        }
    }

</style>