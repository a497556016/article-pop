<template>
    <div class="he-list-item" @click="onClick($event)">
        <div class="icon" :style="{color: data.iconColor}"><i :class="[data.icon]"></i></div>
        <div class="label" :style="{color: data.textColor}">{{data.label}}</div>
        <div class="ac"><i class="fa fa-angle-right"></i></div>
    </div>
</template>

<script>
    export default {
        name: "ListItem",
        props: {
            data: Object
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