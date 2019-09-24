<template>
    <div class="he-toast" v-if="visible">
        <he-modal v-if="mask"></he-modal>
        <div class="he-toast-msg">
            <div class="text"><i v-if="type != 'text'" :class="[iconClass]"></i>{{msg}}</div>
        </div>
    </div>
</template>

<script>
    import Modal from "./Modal";
    export default {
        name: "Toast",
        components: {"he-modal": Modal},
        data(){
          return {
              visible: false,
              msg: '',
              mask: false,
              time: 2000,
              type: 'text' //text loading warn error
          }
        },
        computed: {
            iconClass(){
                if(this.type == 'loading') {
                    return 'fa fa-spin fa-spinner';
                }else if(this.type == 'warn') {
                    return 'fa fa-warning';
                }else if(this.type == 'error') {
                    return 'fa fa-close';
                }
                return '';
            }
        },
        mounted(){

        },
        methods: {
            init(options){
                this.msg = options.msg || '';
                this.mask = options.mask || false;
                this.time = options.time || 2000;
                this.type = options.type || 'text';
                return this;
            },
            afterShow(){
                if(this.time > 0) {
                    setTimeout(() => this.hide(), this.time);
                }
            },
            show(){
                this.visible = true;

                this.afterShow();
            },
            hide(){
                // this.$el.remove();
                this.visible = false;
            }
        }
    }
</script>

<style scoped>

</style>