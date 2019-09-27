<template>
    <div class="he-input">
        <template v-if="config.type === 'email'">
            <input type="email" :readonly="config.readonly" v-model="inputValue"></input>
        </template>
        <template v-else>
            <input :type="config.type" :readonly="config.readonly" :placeholder="config.placeholder" v-model="inputValue"/>
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
                    return {}
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
        computed: {
            config(){
                return Object.assign({
                    type: 'text'//default
                }, this.options)
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