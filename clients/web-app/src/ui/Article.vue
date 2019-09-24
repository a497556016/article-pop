<template>
    <div class="article">
        <he-title-bar :title="articleData.title" :left="{back: true}" :right="{icon: 'fa fa-share'}"></he-title-bar>
        <div class="container" :style="{display: show?'block':'none'}">
            <div class="title">{{articleData.title}}</div>

            <div class="user">
                <div class="ad-words">想变强吗？来找我吧！！</div>
                <div class="info">
                    <div class="username">何少伟</div>
                </div>

                <a @click="goAnchor('#user_card')">联系他/她</a>
            </div>

            <div class="content" v-html="articleData.content"></div>

            <div id="user_card" class="user-card">
                <div class="username">何少伟</div>
                <div class="jobs">工地经理</div>
<!--                <div class="declaration">战斗吧，少年！！！</div>-->
                <div class="contact">
                    <div class="qrCode" @click="showQrCode"><i class="fa fa-commenting-o"></i>&nbsp;添加微信</div>
                    <a class="phone" href="tel:13249833007"><i class="fa fa-mobile-phone"></i>&nbsp;手机通话</a>
                </div>
            </div>
        </div>
        <he-comment-bar @clickComment="onCommentClick" :comments-count="articleData.commentsCount" @like="onLike" @collect="onCollect"></he-comment-bar>

        <he-dialog ref="commentsDialog" v-model="commentsVisible" :header="{title: '评论详情'}">
            <div class="comments-body" slot="body">
                <div v-if="articleComments.length == 0" align="center" style="color: #aaaaaa">还没有人评论</div>
                <div v-else class="comment" v-for="comment in articleComments">
                    <div class="info">
                        <div class="user">来自 {{comment.username}} 的评论 </div>
                        <div class="date">{{comment.date}}</div>
                    </div>
                    <div class="content">{{comment.content}}</div>
                </div>
                <div class="footer">
                    <textarea class="input" v-model="commentContent" placeholder="添加评论"></textarea>
                    <a @click="addComment">发布</a>
                </div>
            </div>
        </he-dialog>
    </div>
</template>

<script>
    import {mapGetters,mapActions} from "vuex"
    import moduleTypes from "../store/types"
    export default {
        name: "Article",
        props: {
            id: String
        },
        data(){
            return {
                show: false,

                commentsVisible: false,
                commentContent: ''
            }
        },
        computed: {
            ...mapGetters({
                articleData: moduleTypes.article.GET_VIEW_ARTICLE_DATA,
                articleComments: moduleTypes.comment.GET_ARTICLE_COMMENTS
            })
        },
        activated(){
            this.loadArticle();
        },
        mounted(){

        },
        methods: {
            ...mapActions({
                findById: moduleTypes.article.FIND_ARTICLE_BY_ID,
                loadArticleComments: moduleTypes.comment.LOAD_ARTICLE_COMMENTS,
                addArticleComment: moduleTypes.comment.ADD_ARTICLE_COMMENT
            }),
            loadArticle(){
                this.findById(this.id).then(() => {
                    setTimeout(() => {
                        const imgs = this.$el.getElementsByTagName('IMG');
                        // alert(imgs.length)
                        console.log(imgs)
                        for (let i = 0; i < imgs.length; i++) {
                            imgs.item(i).style.width = '100%';
                        }

                        this.show = true;
                    }, 500);
                });
            },
            goAnchor(selector) {
                const anchor = this.$el.querySelector(selector) // 参数为要跳转到的元素id
                document.body.scrollTop = anchor.offsetTop; // chrome
                document.documentElement.scrollTop = anchor.offsetTop; // firefox
            },
            showQrCode(){

            },
            onCommentClick(){
                // alert('查看评论')
                this.commentsVisible = true;
                this.loadArticleComments();
            },
            async addComment(){
                await this.addArticleComment(this.commentContent);
                this.commentContent = '';
                this.articleData.commentsCount += 1;
            },
            onLike(liked, success, fail){
                setTimeout(() => {
                    success();
                }, 200)
            },
            onCollect(collected, success) {
                success()
            }
        }
    }
</script>

<style scoped lang="less">

    .article {
        background: #ffffff;
        padding: 0.5em;
        /*height: 100%;*/
        .container {
            position: relative;
            top: 4em;
            .title {
                font-weight: bold;
                font-size: 1.5em;
                margin-bottom: 2em;
            }

            .user {
                padding: 15px;
                text-align: center;
                background: #3ab566;
                position: relative;
                height: 5em;
                /*line-height: 3em;*/

                .ad-words{
                    text-align: left;
                    color: #ef2b30;
                }

                .info{
                    margin-top: 0.5em;
                    .username {
                        color: #ffffff;
                        font-size: 1.4em;
                        font-weight: bold;
                    }
                }

                a{
                    position: absolute;
                    right: 5px;
                    bottom: 5px;
                    background: #51d3c2;
                    color: #4966c3;
                    border-radius: 2px;
                    font-size: 0.8em;
                    height: 1.5em;
                    line-height: 1.5em;
                    padding: 2px 10px;
                }
            }

            .content{
                margin-top: 3em;
            }

            .user-card {
                margin: 5em 1em;
                padding: 18px 25px;
                border-radius: 5px;
                border: 0.05em solid #aaaaaa;
                background: #52b5ad;
                color: white;
                .username{
                    text-align: center;
                    font-size: 1.5em;
                    font-weight: bold;
                    color: #2c343c;
                }

                .jobs{
                    margin: 10px;
                    text-align: center;
                }

                .declaration{
                    font-size: 0.8em;
                    color: #383738;
                    text-align: center;
                }

                .contact {
                    margin-top: 1em;
                    display: flex;
                    justify-content: space-between;

                    font-size: 0.8em;
                    .qrCode{
                        background: #3ab566;
                        padding: 5px;
                        border-radius: 5px;
                    }
                    .phone{
                        background: #5c7fff;
                        padding: 5px;
                        border-radius: 5px;
                        text-decoration: none;
                    }
                }
            }
        }

        .comments-body {
            padding: 3em 0;
            .comment {
                border-bottom: 0.05em solid #efefef;
                padding: 15px;
                .info{
                    font-size: 0.8em;
                    color: #a0a0a0;
                    display: flex;
                    justify-content: space-between;
                    .user{
                        font-weight: bold;
                    }
                    .date{

                    }
                }

                .content {
                    padding: 10px 0;
                    white-space: pre-line;
                }
            }

            .footer {
                position: fixed;
                bottom: 0;
                left: 0;
                right: 0;
                height: 3.5em;
                line-height: 3.5em;
                border-top: 0.05em solid #efefef;
                padding: 0 0.5em;
                background: white;
                display: flex;
                .input{
                    height: 3em;
                    margin-top: 0.5em;
                    border: 0;
                    flex: 1;
                }
                a{
                    width: 3em;
                    color: #5c7fff;
                    margin-left: 10px;
                }
            }
        }

    }
</style>