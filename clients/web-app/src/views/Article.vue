<template>
    <he-page ref="page" class="article" :header="true" :footer="true">
        <template slot="header">
            <he-title-bar :title="articleData.article.title" :left="{icon: 'fa fa-home'}" @leftClick="backToHome" @rightClick="sharePage" :right="{icon: 'fa fa-share'}"></he-title-bar>
        </template>
        <template slot="body">
            <div class="container" :style="{display: show?'block':'none'}">
                <div class="title">{{articleData.article.title}}</div>
                <div class="desc">
                    <span>收藏：{{articleData.article.collectsCount||0}}</span>
                    <span>点赞：{{articleData.article.likesCount||0}}</span>
                    <span>阅读：{{articleData.article.viewsCount||0}}</span>
                </div>

                <div class="user">
                    <div class="ad-words">{{userData.declaration}}</div>
                    <!--<div class="info">
                        <div class="username">何少伟</div>
                    </div>-->

                    <a @click="goAnchor('#user_card')">联系他/她</a>
                </div>

                <div class="content" v-html="articleData.article.content"></div>

                <div id="user_card" class="user-card">
                    <div class="username">{{userData.username}}</div>
                    <div class="avatar" v-if="userData.avatar">
                        <img :src="userData.avatar"/>
                    </div>
                    <div class="jobs">{{userData.post}}</div>
    <!--                <div class="declaration">战斗吧，少年！！！</div>-->
                    <div class="contact">
                        <div class="qrCode" @click="showQrCode"><i class="fa fa-commenting-o"></i>&nbsp;添加微信</div>
                        <a class="phone" :href="'tel:'+userData.phone"><i class="fa fa-mobile-phone"></i>&nbsp;手机通话</a>
                    </div>
                </div>

                <div class="foot">
                    <span>哇！好厉害，我也要！！</span>
                    <a @click="goUserEdit">点这里立刻注册</a>
                </div>
            </div>

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

            <he-dialog v-model="wxQRCodeVisible" :type="1" :size="['200px', '200px']">
                <div style="height: 100%;width: 100%" slot="body">
                    <img style="height: 100%;width: 100%" v-if="userData.wxQRCode" :src="userData.wxQRCode"/>
                    <div v-else>没有上传微信二维码</div>
                </div>
            </he-dialog>
        </template>
        <template slot="footer">
            <he-comment-bar :liked="articleData.liked" :collected="articleData.collected" @clickComment="onCommentClick" :comments-count="articleData.article.commentsCount||0" @like="onLike" @collect="onCollect"></he-comment-bar>
        </template>
    </he-page>
</template>

<script>
    import {mapGetters,mapActions} from "vuex"
    import moduleTypes from "../store/types"
    import userApi from "../api/user"
    import articleApi from "../api/article"
    import {BASE_URL} from "../config"
    export default {
        name: "Article",
        props: {
            id: String,
            userId: String
        },
        data(){
            return {
                show: false,

                commentsVisible: false,
                commentContent: '',

                wxQRCodeVisible: false,

                userData: {
                    declaration: '想要变得更强吗？来吧，过来吧，少年！！',
                    username: '临时游客',
                    post: '登录后生成推广链接'
                }
            }
        },
        computed: {
            ...mapGetters({
                loginUser: moduleTypes.user.GET_LOGIN_USER_DATA,
                articleData: moduleTypes.article.GET_VIEW_ARTICLE_DATA,
                articleComments: moduleTypes.comment.GET_ARTICLE_COMMENTS
            })
        },
        mounted(){
            this.updateViewsCount({articleId: this.id, userId: this.userId});
            this.loadArticle();
            this.loadUserData();
        },
        destroyed(){
            document.title = '推推棒';
        },
        methods: {
            ...mapActions({
                findById: moduleTypes.article.FIND_ARTICLE_BY_ID,
                loadArticleComments: moduleTypes.comment.LOAD_ARTICLE_COMMENTS,
                addArticleComment: moduleTypes.comment.ADD_ARTICLE_COMMENT,
                updateViewsCount: moduleTypes.article.UPDATE_ARTICLE_VIEWS_COUNT,
                checkUserCardCount: moduleTypes.article.CHECK_USER_CARD_COUNT
            }),
            async loadUserData(){
                if(this.userId){
                    const user = await userApi.getById(this.userId);
                    this.userData = user;
                }
            },
            sharePage(){
                const hrefs = window.location.href.split("#");
                const shareUrl = BASE_URL + '/article/view?host='+encodeURIComponent(hrefs[0])+'&path='+encodeURIComponent(hrefs[1]);


                this.$createToast({
                    msg: '分享功能暂未开放'
                }).show();
            },
            backToHome(){
                this.$router.push('/');
            },
            goUserEdit(){
                this.$router.push('/user/edit/0');
            },
            loadArticle(){
                const loading = this.$createToast({
                    msg: '正在加载，请稍候...',
                    time: 0,
                    type: 'loading'
                });
                loading.show();
                this.findById(this.id).then(() => {
                    document.title = this.articleData.article.title;
                    setTimeout(() => {
                        const imgs = this.$el.querySelectorAll('.content IMG')
                        // alert(imgs.length)
                        console.log(imgs)
                        for (let i = 0; i < imgs.length; i++) {
                            imgs.item(i).style.width = '100%';
                        }

                        this.show = true;
                        loading.hide();
                    }, 500);
                });
            },
            goAnchor(selector) {
                const scrollTarget = this.$refs.page.$el.children[1];
                const anchor = this.$el.querySelector(selector) // 参数为要跳转到的元素id

                scrollTarget.scrollTop = anchor.offsetTop;

                if(this.id && this.userId) {
                    this.checkUserCardCount({articleId: this.id, userId: this.userId});
                }
            },
            showQrCode(){
                this.wxQRCodeVisible = true;
            },
            onCommentClick(){
                // alert('查看评论')
                this.commentsVisible = true;
                this.loadArticleComments();
            },
            async addComment(){
                if(this.commentContent.length >= 100){
                    this.$createToast({
                        msg: '最大长度不超过100个字符！'
                    }).show();
                    return;
                }
                await this.addArticleComment(this.commentContent);
                this.commentContent = '';
                this.articleData.article.commentsCount += 1;
            },
            async onLike(liked, success, fail){
                if(!this.loginUser.id){
                    this.$createToast({msg: '请先登录'}).show();
                    return;
                }
                const re = await articleApi.like(this.id, this.loginUser.id, liked);
                console.log(re)
                if(re === 'success') {
                    success();
                }else {
                    fail();
                }
            },
            async onCollect(collected, success, fail) {
                if(!this.loginUser.id){
                    this.$createToast({msg: '请先登录'}).show();
                    return;
                }
                const re = await articleApi.collect(this.id, this.loginUser.id, collected);
                if(re === 'success') {
                    success();
                }else {
                    fail();
                }
            }
        }
    }
</script>

<style scoped lang="less">

    .article {
        background: #ffffff;
        /*padding: 0.5em;*/
        /*height: 100%;*/
        .container {
            position: relative;
            top: 1em;
            padding: 1em;
            .title {
                font-weight: bold;
                font-size: 1.5em;
                margin-bottom: 1em;
            }

            .desc{
                font-size: 0.9em;
                color: #aa8888;
                margin-bottom: 1em;
                & span:not(:first-child){
                    margin-left: 5px;
                }
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
                    word-break: break-all;
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
                margin: 5em 1em 0 1em;
                padding: 10px 25px;
                border-radius: 5px;
                border: 0.05em solid #aaaaaa;
                background: #52b5ad;
                color: white;
                .username{
                    text-align: center;
                    font-size: 1.2em;
                    font-weight: bold;
                    color: #2c343c;
                }

                .avatar{
                    margin-top: 5px;
                    width: 100%;
                    text-align: center;
                    img{
                        height: 60px;
                        width: 60px;
                        border-radius: 30px;

                    }
                }

                .jobs{
                    margin: 5px;
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

            .foot{
                margin-bottom: 5em;
                padding: 15px;
                color: #e86c3a;
                a{
                    text-decoration-line: underline;
                    color: #5c7fff;
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
                    align-items: center;
                    .user{
                        font-weight: bold;
                    }
                    .date{

                    }
                }

                .content {
                    padding: 10px 0;
                    white-space: pre-line;
                    word-break: break-all;
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