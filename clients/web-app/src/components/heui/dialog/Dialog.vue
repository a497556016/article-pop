<template>
    <div class="he-dialog" v-if="visible">
        <he-modal :z-index="999" @click="close"></he-modal>
        <div class="he-dialog-body" :style="bodyStyle">
            <div v-if="header" class="header">
                <div class="close" @click="close"><i class="fa fa-close"></i></div>
                <div class="title">{{header.title}}</div>
                <div class="actions"></div>
            </div>
            <slot name="body"></slot>
        </div>
    </div>
</template>

<script>
    import Modal from "./Modal";
    export default {
        name: "Dialog",
        components: {"he-modal": Modal},
        props: {
            value: Boolean,
            header: {
                type: Object,
                default(){
                    return null
                }
            },
            type: {
                type: Number,
                default: 0// 0： 靠底部，左右占满真个屏幕， 1：居中位置
            },
            size: {
                type: Array,
                default(){
                    return ['50%', '50%']
                }
            } //仅在type=1时起作用, 宽高
        },
        model: {
            prop: 'value',
            event: 'change'
        },
        data(){
            return {
                visible: this.value
            }
        },
        computed: {
            bodyStyle(){
                if(this.type === 1){
                    return {
                        zIndex: 999,
                        height: this.size[1],
                        width: this.size[0],
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                    }
                }
                return {
                    zIndex: 999,
                    animation: 'dialog-move-up 0.3s',
                    webkitAnimation: 'dialog-move-up 0.3s' /*Safari and Chrome*/
                }
            }
        },
        watch: {
            value(v){
                this.visible = v;
                if(v){
                    document.documentElement.style.overflow = 'hidden';
                }else {
                    document.documentElement.style.overflow = 'auto';
                }
            },
            visible(v){
                this.$emit('change', v);

            }
        },
        methods: {
            close(){
                this.visible = false
            }
        }
    }
</script>

<style scoped>

</style>