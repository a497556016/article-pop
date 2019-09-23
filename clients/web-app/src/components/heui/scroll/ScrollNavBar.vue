<template>
    <div class="he-scroll-nav-bar" :style="{height: '2.5em', lineHeight: '2.5em'}">
        <he-scroll direction="horizontal">
            <div v-for="(item, index) in data" :class="['he-scroll-item', activeIndex_ === index?'active':'']" @click="onItemClick(item, index)">{{item}}</div>
        </he-scroll>
    </div>
</template>

<script>
    export default {
        name: "ScrollNavBar",
        props: {
            activeIndex: {
                type: Number,
                default: 0
            },
            data: {
                type: Array,
                default(){
                    return [

                    ]
                }
            }
        },
        data(){
            return {
                activeIndex_: this.activeIndex
            }
        },
        methods: {
            onItemClick(item, index){
                this.activeIndex_ = index;
                this.$emit('change', item, index);
            },
            getActive(){
                return this.activeIndex;
            },
            getActiveData(){
                return this.data[this.activeIndex];
            }
        }
    }
</script>

<style scoped>

</style>