<template>
    <div class="he-tool-bar" :style="toolbarStyle">
        <div class="action" v-for="(action, index) in actions">
            <slot name="action" :action="action" :index="index"></slot>
        </div>
        <div v-if="moreActions.length" class="more-actions">
            <i class="fa fa-ellipsis-h"></i>
        </div>
    </div>
</template>

<script>
    const defaultStyle = {
        position: 'fixed',
        left: '10px',
        right: '10px',
        height: '2.5em',
        lineHeight: '2.5em',
        background: '#ffffff',
        borderRadius: '5px',
        // border: '0.05em solid #efefef',

    }
    export default {
        name: "ToolBar",
        props: {
            fixed: {
                type: Boolean,
                default: true
            },
            position: {
                type: String | Object,
                default: 'bottom'
            },
            shadow: {
                type: Boolean,
                default: true
            },
            actions: {
                type: Array,
                default(){
                    return []
                }
            },
            moreActions: {
                type: Array,
                default(){
                    return []
                }
            }
        },
        computed: {
            toolbarStyle(){
                defaultStyle.position = this.fixed?'fixed':'relative';
                if(this.position == 'top') {
                    defaultStyle.top = '10px';
                }else if(this.position == 'bottom'){
                    defaultStyle.bottom = '10px';
                }else if(this.position.top) {
                    defaultStyle.top = this.position.top;
                }else if(this.position.bottom) {
                    defaultStyle.bottom = this.position.bottom;
                }
                if(this.shadow) {
                    defaultStyle.boxShadow = '0px 0px 2px #aaaaaa';
                }

                return defaultStyle;
            }
        }
    }
</script>

<style scoped>

</style>