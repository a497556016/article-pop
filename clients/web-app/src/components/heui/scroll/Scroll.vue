<template>
    <div class="he-scroll" :style="style">
        <slot></slot>
    </div>
</template>

<script>
    const directions = {
        VERTICAL: 'vertical', //竖
        HORIZONTAL: 'horizontal' //横
    }
    export default {
        name: "Scroll",
        props: {
            direction: {
                type: String,
                default: directions.VERTICAL
            },
            options: {
                type: Object,
                default(){
                    return {

                    }
                }
            }
        },
        data(){
            return {
                style: {
                    height: '100%',
                    width: '100%',
                    overflowX: '',
                    overflowY: '',
                    display: 'flex',
                    justifyContent: 'flex-start'
                }
            }
        },
        mounted() {
            if(this.direction == directions.VERTICAL) {
                this.style.overflowX = 'hidden';
                this.style.overflowY = 'auto';
                this.style.flexDirection = 'column';
            }else {
                this.style.overflowY = 'hidden';
                this.style.overflowX = 'auto';
                this.style.flexDirection = 'row';
            }
        }
    }
</script>

<style scoped>

</style>