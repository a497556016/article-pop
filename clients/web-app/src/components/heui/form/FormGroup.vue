<template>
    <div class="he-form-group">
        <div class="he-form-group-title" v-if="title">{{title}}</div>
        <slot></slot>
    </div>
</template>

<script>
    export default {
        name: "FormGroup",
        props: {
            title: String
        }
    }
</script>

<style scoped>

</style>