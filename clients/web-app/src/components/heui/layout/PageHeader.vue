<template>
    <div class="he-page-header" :style="{height: height}">
        <slot></slot>
    </div>
</template>

<script>
    import TitleBar from "../base/TitleBar";
    export default {
        name: "PageHeader",
        components: {
            "he-title-bar": TitleBar
        },
        props: {
            height: String
        }
    }
</script>

<style scoped>

</style>