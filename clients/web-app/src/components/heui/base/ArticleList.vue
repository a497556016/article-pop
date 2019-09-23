<template>
    <div class="he-article-list">
        <he-list :data="data">
            <template slot="item" slot-scope="{item}">
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
            </template>
        </he-list>
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
                        imagePosition: 'top' //left top right bottom
                    }
                }
            }
        },
        methods: {
            onItemClick(item){
                this.$emit('itemClick', item)
            }
        }
    }
</script>

<style scoped>

</style>