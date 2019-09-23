<template>
    <div class="he-list">
        <div class="he-list-item" v-for="item in data">
            <slot name="item" :item="item"></slot>
        </div>
    </div>
</template>

<script>
    export default {
        name: "List",
        props: {
            data: {
                type: Array,
                default(){
                    return []
                }
            }
        }
    }
</script>

<style scoped>

</style>