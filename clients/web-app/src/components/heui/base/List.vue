<template>
    <div class="he-list">
        <div class="he-list-item" v-for="(item, index) in data">
            <slot name="item" :item="item" :index="index"></slot>
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