<template>
    <div class="home">
        <he-title-bar title="I推广" :left="{back: false}" :right="{icon: 'fa fa-user'}" @rightClick="gotoUserCenter"></he-title-bar>
        <he-scroll-nav-bar style="position: fixed;top: 4em;left: 0.5em;right: 0.5em;z-index: 999;" :data="tagsData" :active-index="activeTagIndex" @change="onTagsChange">
            <div slot-scope="{item}">{{item.label}}</div>
        </he-scroll-nav-bar>
        <div ref="body" class="body">
            <he-panel style="margin-top: 10px">
                <he-article-list :data="recommendList" :options="{imagePosition: 'left', loadMore: haveMore}" @loadMore="loadMore" @itemClick="onItemClick">
                    <div slot="footer-left" slot-scope="{metadata}">
                        <span v-if="metadata.source">来源：{{metadata.source}} </span>
                        <span v-if="metadata.chineseTag">标签：{{metadata.chineseTag}}</span>
                    </div>
                    <div slot="footer-right" slot-scope="{metadata}">
                        {{metadata.date}}
                    </div>
                </he-article-list>
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
                activeTagIndex: 1,
                tagsData: [
                    {label: '推荐', tag: 'news_recommend'},
                    {label: '热点', tag: 'news_hot'},
                    {label: '时政', tag: 'news_politics'},
                    {label: '社会', tag: 'news_society'},
                    {label: '国际', tag: 'news_world'},
                    {label: '娱乐', tag: 'news_entertainment'},
                    {label: '科技', tag: 'news_tech'},
                    {label: '体育', tag: 'news_sports'},
                    {label: '军事', tag: 'news_military'}
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
            this.selectArticleList(this.tagsData[this.activeTagIndex].tag);
            this.$refs.body.onscroll = (e) => {
                // console.log(e.target.scrollTop)
                this.scrollTop = e.target.scrollTop;
            }
        },
        methods: {
            ...mapActions({
                selectArticleList: moduleTypes.article.SELECT_ARTICLE_PAGE_DATA,
                loadNextPage: moduleTypes.article.SELECT_NEXT_ARTICLE_PAGE_DATA
            }),
            onTagsChange(item, index){
                console.log(item, index)
                this.activeTagIndex = index;
                this.selectArticleList(item.tag);
            },
            onItemClick(article) {
                console.log(article)
                this.$router.push({
                    path: '/article/'+article.metadata.id
                })
            },
            async loadMore(){
                await this.loadNextPage(this.tagsData[this.activeTagIndex].tag);
                this.$refs.body.scrollTo(0, 0);
            },
            gotoUserCenter(){
                this.$router.push("/user")
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

        }
    }

</style>