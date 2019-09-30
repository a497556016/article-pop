<template>
    <he-page :header="true" :footer="false">
        <he-title-bar slot="header" title="我的点赞记录"></he-title-bar>
        <div slot="body">
            <he-panel>
                <he-article-list v-if="likes.length" :data="likes" :options="{footerHidden: true, actions: ['删除']}" @clickActionsItem="clickActionsItem" @itemClick="onArticleClick"></he-article-list>
                <he-alert v-else type="normal">还没有点赞任何文章哟</he-alert>
            </he-panel>
        </div>
        <div slot="footer">

        </div>
    </he-page>
</template>

<script>
    import userApi from "../api/user"
    export default {
        name: "UserLike",
        props: {id: String},
        data(){
            return {
                likes: []
            }
        },
        mounted() {
            this.loadUserData();
        },
        methods: {
            async loadUserData(){
                const {likedArticles} = await userApi.getById(this.id);
                if(likedArticles) {
                    this.likes = likedArticles.map(article => {
                        return {
                            title: article.title,
                            content: article.abstractText,
                            metadata: article
                        }
                    });
                }
            },
            onArticleClick(article){
                const path = '/article/'+article.metadata.articleId+'/'+this.id;

                this.$router.push({
                    path
                })
            },
            async clickActionsItem(label, data){
                if(label == '删除'){
                    await userApi.deleteLikedArticle(this.id, data.metadata.articleId)
                    // this.$createToast(`删除文章[${data.title}]`).show();
                    this.loadUserData();
                }
            }
        }
    }
</script>

<style scoped>

</style>