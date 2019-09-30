<template>
    <he-page :header="true" :footer="false" :body="{padding: false}">
        <he-title-bar slot="header" title="我的收藏目录"></he-title-bar>
        <div slot="body">
            <he-panel>
                <he-article-list v-if="collects.length" :data="collects" :options="{footerHidden: true, actions: ['删除']}" @clickActionsItem="clickActionsItem" @itemClick="onArticleClick"></he-article-list>
                <he-alert v-else>还没有收藏任何文章哟</he-alert>
            </he-panel>
        </div>
        <div slot="footer">

        </div>
    </he-page>
</template>

<script>
    import userApi from "../api/user"
    export default {
        name: "UserCollect",
        props: {id: String},
        data(){
            return {
                collects: []
            }
        },
        mounted() {
            this.loadUserData();
        },
        methods: {
            async loadUserData(){
                const {collectedArticles} = await userApi.getById(this.id);
                if(collectedArticles) {
                    this.collects = collectedArticles.map(article => {
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
                    await userApi.deleteCollectedArticle(this.id, data.metadata.articleId)
                    // this.$createToast(`删除文章[${data.title}]`).show();
                    this.loadUserData();
                }
            }
        }
    }
</script>

<style scoped lang="less">
    .user-collect{

    }
</style>