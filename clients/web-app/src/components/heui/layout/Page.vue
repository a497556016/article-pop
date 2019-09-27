<template>
    <div class="he-page">
        <he-page-header v-if="header" :height="header.height">
            <slot name="header"></slot>
        </he-page-header>
        <he-page-body :style="{top: header?'3em':'0', bottom: footer?'3em':'0', padding: body.padding?'10px':'0'}">
            <slot name="body"></slot>
        </he-page-body>
        <he-page-footer v-if="footer" :height="footer.height">
            <slot name="footer"></slot>
        </he-page-footer>
    </div>
</template>

<script>
    import PageHeader from "./PageHeader";
    import PageBody from "./PageBody";
    import PageFooter from "./PageFooter";
    export default {
        name: "Page",
        components: {
            "he-page-header": PageHeader,
            "he-page-body": PageBody,
            "he-page-footer": PageFooter
        },
        props: {
            header: {
                type: Object|Boolean,
                default: false
            },
            footer: {
                type: Object|Boolean,
                default: false
            },
            body: {
                type: Object,
                default(){
                    return {
                        padding: false
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>