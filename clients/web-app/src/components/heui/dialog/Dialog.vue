<template>
    <div class="he-dialog" v-if="visible">
        <he-modal :z-index="999" @click="close"></he-modal>
        <div class="he-dialog-body" :style="{zIndex: 999}">
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
            }
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
        watch: {
            value(v){
                this.visible = v;
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