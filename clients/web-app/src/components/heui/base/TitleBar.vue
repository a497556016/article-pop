<template>
    <div class="he-title-bar">
        <div class="he-title-left" @click="onLeftClick">
            <i v-if="left.back" class="fa fa-arrow-left"></i>
            <i v-else-if="left.icon" :class="[left.icon]"></i>
        </div>
        <div class="he-title-center">{{title}}</div>
        <div class="he-title-right" @click="onRightClick">
            <i :class="[right.icon]"></i>
        </div>
    </div>
</template>

<script>
    export default {
        name: "TitleBar",
        props: {
            title: String,
            left: {
                type: Object,
                default(){
                    return {
                        back: true,
                        icon: ''
                    }
                }
            },
            right: {
                type: Object,
                default(){
                    return {
                        icon: ''
                    }
                }
            }
        },
        methods: {
            onLeftClick(){
                console.log('back')
                if(this.left.back) {
                    history.back();
                }else {
                    this.$emit('leftClick');
                }
            },
            onRightClick(){
                this.$emit('rightClick');
            }
        }
    }
</script>

<style scoped lang="less">
    /*@import "../default-theme/lib/components/base/title-bar";*/
</style>