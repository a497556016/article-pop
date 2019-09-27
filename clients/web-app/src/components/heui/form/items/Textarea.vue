<template>
    <div class="he-textarea" :style="{height: height}">
        <textarea v-model="text" @focus="height = '4em'" @blur="onBlur" :placeholder="config.placeholder"></textarea>
    </div>
</template>

<script>
    export default {
        name: "Textarea",
        props: {
            value: String,
            options: {
                type: Object,
                default(){
                    return {}
                }
            }
        },
        model: {
            prop: 'value',
            event: 'change'
        },
        data(){
            return {
                height: '2em',
                text: this.value
            }
        },
        computed: {
            config(){
                return Object.assign({
                    placeholder: 'please type...'
                }, this.options)
            }
        },
        watch: {
            value(v){
                this.setHeight(v);
                this.text = v;
            },
            text(v){
                this.setHeight(v);
                this.$emit('change', v);
            }
        },
        methods: {
            setHeight(v){
                if(v){
                    this.height = '4em';
                }else {
                    this.height = '2em';
                }
            },
            onBlur(){
                this.setHeight(this.text);
            }
        }
    }
</script>

<style scoped lang="less">
    .he-textarea{
        textarea{
            border: 0;
            width: 100%;
            height: 100%;
            padding: 0 5px;
        }
    }
</style>