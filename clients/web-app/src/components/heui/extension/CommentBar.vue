<template>
    <div class="comment-bar">
        <he-tool-bar :actions="actions" :position="{bottom: '8px'}" :shadow="true">
            <template slot="action" slot-scope="{action, index}">
                <div class="left" v-if="index == 0">
                    <div class="item" v-for="(item, index1) in action" @click="onLeftItemClick(item, index1)">
                        <i :class="['icon',item.icon]"></i>
                        <span class="text">{{item.text}}</span>
                    </div>
                </div>
                <div class="right" v-if="index == 1" @click="onCommentClick">
                    <i :class="['icon',action.icon]"></i>
                    <span class="text">{{action.text}} {{action.count}}</span>
                </div>
            </template>
        </he-tool-bar>
    </div>
</template>

<script>
    import ToolBar from "../base/ToolBar";
    export default {
        name: "CommentBar",
        components: {
            "he-tool-bar": ToolBar
        },
        props: {
            commentsCount: Number,
            liked: {
                type: Boolean,
                default: false
            },
            collected: {
                type: Boolean,
                default: false
            }
        },
        data(){
            return {
                isLiked: this.liked,
                isCollected: this.collected,

            }
        },
        computed: {
            actions(){
                return [
                    [
                        {text: '点赞', icon: this.isLiked?'fa fa-heart':'fa fa-heart-o'},
                        {text: '收藏', icon: this.isCollected?'fa fa-star':'fa fa-star-o'}
                    ],
                    {text: '评论数', count: this.commentsCount||0, icon: 'fa fa-commenting-o'}
                ]
            }
        },
        methods: {
            onLeftItemClick(item, index){
                if(index == 0) {
                    this.$emit('like', !this.isLiked, () => {
                        this.isLiked = !this.isLiked;
                    }, () => {

                    });
                }else if(index == 1){
                    this.$emit('collect', !this.isCollected, () => {
                        this.isCollected = !this.isCollected;
                    }, () => {

                    })
                }
            },
            onCommentClick(){
                this.$emit('clickComment');
            }
        }
    }
</script>

<style scoped lang="less">
    .comment-bar {
        font-weight: bold;
        .left{
            display: flex;
            color: #6299df;
            .item {
                .text{
                    font-size: 0.8em;
                    margin-left: 2px;
                }
                .icon{
                    font-size: 1.1em;
                }
                margin-right: 1.5em;
            }
        }
        .right {
            color: #ef7859;
            .text{
                font-size: 0.8em;
                margin-left: 2px;
            }
            .icon{
                font-size: 1.2em;
            }
        }
    }
</style>