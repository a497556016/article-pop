<template>
    <div class="he-article-list">
        <he-list :data="data">
            <template slot="item" slot-scope="{item, index}">
                <div @click="onItemClick(item)">
                    <div class="he-article-title">{{item.title}}</div>
                    <div class="he-article-content" :style="{display: 'flex', flexDirection: (options.imagePosition == 'left' || options.imagePosition == 'right')?'row':'column'}">
                        <div v-if="item.image" class="img" :style="{order: (options.imagePosition == 'left' || options.imagePosition == 'top')?1:2}">
                            <img :src="item.image"/>
                        </div>
                        <div class="text" :style="{flex: 1, order: (options.imagePosition == 'left' || options.imagePosition == 'top')?2:1}" v-html="item.content">

                        </div>
                    </div>
                    <div class="he-article-footer">
                        <div class="l">
                            <slot name="footer-left" :metadata="item.metadata"></slot>
                        </div>
                        <div class="r">
                            <slot name="footer-right" :metadata="item.metadata"></slot>
                        </div>
                    </div>
                </div>
                <div class="load-more" @click="loadMore" v-if="options.loadMore.haveMore && (index == options.loadMore.position)">
                    <span v-if="options.loadMore.lastTime">{{options.loadMore.lastTime}} 看到这里，</span><a>点击刷新</a>
                </div>
            </template>
        </he-list>
        <div class="load-more" @click="loadMore" v-if="options.haveMore">加载更多...</div>
    </div>
</template>

<script>
    export default {
        name: "ArticleList",
        props: {
            data: {
                type: Array
            },
            options: {
                type: Object,
                default(){
                    return {
                        imagePosition: 'top', //left top right bottom
                        loadMore: {
                            haveMore: false,
                            lastTime: null,
                            position: 0
                        },
                        haveMore: false
                    }
                }
            }
        },
        methods: {
            onItemClick(item){
                this.$emit('itemClick', item)
            },
            loadMore(){
                this.$emit('loadMore');
            }
        }
    }
</script>

<style scoped>
    .load-more {
        text-align: center;
        padding: 1em 0;
        font-size: 0.8em;
        color: #8888ff;
        border-top: 0.05em solid #efefef;
        margin-top: 1em;
        background: #efefef;
        border-radius: 5px;
    }
</style>