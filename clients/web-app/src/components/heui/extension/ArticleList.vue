<template>
    <div class="he-article-list">
        <he-list :data="data">
            <template slot="item" slot-scope="{item, index}">
                <div @click="onItemClick(item)">
                    <div class="he-article-title">
                        <div class="title">{{item.title}}</div>
                        <div class="actions" v-if="config.actions && config.actions.length" @click="actionsHandler($event, item)">
                            <i class="fa fa-ellipsis-h"></i>
                        </div>
                    </div>
                    <div v-if="item.image||item.content" class="he-article-content" :style="{display: 'flex', flexDirection: (config.imagePosition == 'left' || config.imagePosition == 'right')?'row':'column'}">
                        <div v-if="item.image" class="img" :style="{order: (config.imagePosition == 'left' || config.imagePosition == 'top')?1:2}">
                            <img :src="item.image"/>
                        </div>
                        <div class="text" :style="{flex: 1, order: (config.imagePosition == 'left' || config.imagePosition == 'top')?2:1}" v-html="item.content">

                        </div>
                    </div>
                    <div v-if="!config.footerHidden" class="he-article-footer">
                        <div class="l">
                            <slot name="footer-left" :item="item" :metadata="item.metadata"></slot>
                        </div>
                        <div class="r">
                            <slot name="footer-right" :item="item" :metadata="item.metadata"></slot>
                        </div>
                    </div>
                </div>
                <div class="load-more" @click="loadMore" v-if="config.loadMore.haveMore && (index == config.loadMore.position)">
                    <span v-if="config.loadMore.lastTime">{{config.loadMore.lastTime}} 看到这里，</span><a>点击刷新</a>
                </div>
            </template>
        </he-list>
        <div class="load-more" @click="loadMore" v-if="config.haveMore">加载更多...</div>

        <div ref="actions" v-if="actionsVisible">
            <he-modal :opacity="0.05" @click="actionsVisible = false"></he-modal>
            <he-list :data="config.actions" :style="actionsStyle">
                <template slot="item" slot-scope="{item}">
                    <div style="padding: 2px 10px" @click="actionsItemHandler(item)">{{item}}</div>
                </template>
            </he-list>
        </div>
    </div>
</template>

<script>
    import List from "../base/List";
    import Modal from "../dialog/Modal";
    export default {
        name: "ArticleList",
        components: {'he-list': List, 'he-modal': Modal},
        props: {
            data: {
                type: Array
            },
            options: {
                type: Object,
                default(){
                    return {

                    }
                }
            }
        },
        data(){
            return {
                actionsStyle: {
                    position: 'absolute',
                    right: '20px',
                    zIndex: 999,
                    backgroundColor: '#ffffff',
                    boxShadow: '0 0 1px #ededed',
                    // border: '1px solid #ededed',
                    borderRadius: '2px'
                },
                actionsVisible: false,
                actionData: null
            }
        },
        computed: {
            config(){
                return Object.assign({
                    imagePosition: 'top', //left top right bottom
                    loadMore: {
                        haveMore: false,
                        lastTime: null,
                        position: 0
                    },
                    haveMore: false,
                    footerHidden: false,
                    actions: []
                }, this.options);
            }
        },
        methods: {
            onItemClick(item){
                this.$emit('itemClick', item)
            },
            loadMore(){
                this.$emit('loadMore');
            },
            actionsHandler(e, data){
                e.cancelBubble = true;
                // e.preventDefault();
                console.log(e)

                this.actionData = data;

                this.actionsVisible = false;

                Object.assign(this.actionsStyle, {
                    top: e.target.offsetTop+'px',
                    left: (e.target.offsetLeft-72)+'px'
                })

                this.actionsVisible = true;
            },
            actionsItemHandler(label){
                this.$emit('clickActionsItem', label, this.actionData);
                this.actionsVisible = false;
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