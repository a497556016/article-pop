<template>
    <div class="he-button" :style="{width: inline?'':'100%'}">
        <button @click="onClick" :disabled="disabled" :style="{backgroundColor: backgroundColor, border: borderColor?'0.01em solid '+borderColor:'', color: color}">
            <slot></slot>
        </button>
    </div>
</template>

<script>
    const primaryColor = '#344b5d';
    const whiteColor = '#ffffff';
    const lightColor = '#aaaaaa';
    const warnColor = '#ff3a3c';
    export default {
        name: "Button",
        props: {
            inline: {
                type: Boolean,
                default: false
            },
            type: {
                type: String,
                default: 'outline'
            },
            disabled: Boolean
        },
        data(){
            return {
                borderColor: primaryColor,
                color: primaryColor,
                backgroundColor: whiteColor
            }
        },
        watch: {
            disabled(disabled){
                if(disabled){
                    this.setButtonDisabled();
                }else {
                    this.setButtonType();
                }
            }
        },
        mounted() {
            if(this.disabled){
                this.setButtonDisabled();
            }else {
                this.setButtonType();
            }
        },
        methods: {
            onClick(){
                this.$emit('click');
            },
            setButtonDisabled(){
                this.borderColor = lightColor;
                this.backgroundColor = lightColor;
                this.color = whiteColor;
            },
            setButtonType(){
                if(this.type == 'primary'){
                    this.borderColor = '';
                    this.backgroundColor = primaryColor;
                    this.color = whiteColor;
                }else if(this.type == 'outline') {
                    this.borderColor = primaryColor;
                    this.backgroundColor = whiteColor;
                    this.color = primaryColor;
                }else if(this.type == 'light'){
                    this.borderColor = '';
                    this.backgroundColor = whiteColor;
                    this.color = lightColor;
                }else if(this.type == 'warn') {
                    this.borderColor = warnColor;
                    this.backgroundColor = whiteColor;
                    this.color = warnColor;
                }
            }
        }
    }
</script>

<style scoped>

</style>