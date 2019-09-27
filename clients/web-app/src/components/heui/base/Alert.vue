<template>
    <div class="he-alert" :style="{backgroundColor: colors[type].backColor, color: colors[type].color}">
        <slot></slot>
    </div>
</template>

<script>
    const colors = {
        'normal': {backColor: '#efefef', color: '#5d6072'},
        'success': {backColor: '#3abe7b', color: '#ffffff'},
        'warn': {backColor: '#ff6f45', color: '#ffffff'},
        'error': {backColor: '#ff4662', color: '#ffffff'}
    }
    export default {
        name: "Alert",
        props: {
            type: {
                type: String,
                default: 'normal' //normal success warn error
            }
        },
        data(){
            return {
                colors
            }
        }
    }
</script>

<style scoped>

</style>