<template>
    <he-page :header="true" :footer="false" :body="{padding: false}">
        <he-title-bar slot="header" title="我的收藏目录"></he-title-bar>
        <div slot="body">
            <he-panel>
                <he-article-list v-if="collects.length" :data="collects" @itemClick="onArticleClick"></he-article-list>
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
                const user = await userApi.getById(this.id);
                this.collects = user.collectedArticles.map(article => {
                    return {
                        title: article.title,
                        content: article.abstractText,
                        metadata: article
                    }
                });
            },
            onArticleClick(article){
                const path = '/article/'+article.metadata.articleId+'/'+this.id;

                this.$router.push({
                    path
                })
            }
        }
    }
</script>

<style scoped lang="less">
    .user-collect{

    }
</style>