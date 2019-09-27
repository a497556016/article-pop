<template>
    <div class="he-page-footer" :style="{height: height}">
        <slot></slot>
    </div>
</template>

<script>
    export default {
        name: "PageFooter",
        props: {
            height: String
        }
    }
</script>

<style scoped>

</style>