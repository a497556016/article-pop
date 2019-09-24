<template>
    <div class="he-modal" @click="onClick($event)" :style="{zIndex: zIndex, opacity: opacity}">

    </div>
</template>

<script>
    export default {
        name: "Modal",
        props: {
            zIndex: {
                type: Number,
                default: 999
            },
            opacity: {
                type: Number,
                default: 0.5
            }
        },
        methods: {
            onClick(e){
                this.$emit('click', e)
            }
        }
    }
</script>

<style scoped>

</style>