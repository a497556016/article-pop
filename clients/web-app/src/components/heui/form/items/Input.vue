<template>
    <div class="he-input">
        <template v-if="options.type === 'email'">
            <input type="email" :readonly="options.readonly" v-model="inputValue"></input>
        </template>
        <template v-else>
            <input :type="options.type" :readonly="options.readonly" :placeholder="options.placeholder" v-model="inputValue"/>
        </template>

    </div>
</template>

<script>
    export default {
        name: "Input",
        props: {
            options: {
                type: Object,
                default(){
                    return {
                        type: 'text'//default
                    }
                }
            },
            value: Object|String
        },
        model: {
            prop: 'value',
            event: 'change'
        },
        data(){
            return {
                inputValue: this.value
            }
        },
        watch: {
            value(v){
                this.inputValue = v;
            },
            inputValue(v){
                this.$emit('change', v);
            }
        },
        mounted() {

        }
    }
</script>

<style scoped lang="less">
    .he-input{
        height: 2em;
        /*line-height: 100%;*/
        width: 100%;
        input{
            height: 100%;
            width: 100%;
            border: 0;
            padding: 0 5px;
        }
    }
</style>