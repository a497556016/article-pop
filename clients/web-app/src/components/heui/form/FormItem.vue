<template>
    <div class="he-form-item">
        <template v-if="field.type=='submit'">
            <he-button type="primary" @click="submitForm">{{field.label}}</he-button>
        </template>
        <template v-else-if="field.type=='reset'">
            <he-button @click="resetForm">{{field.label}}</he-button>
        </template>
        <template v-else>
            <div class="he-form-label">{{field.label}}</div>
            <component class="he-form-input" :is="'he-'+field.type" v-model="form.data[field.name]" :options="field.options"></component>
        </template>
    </div>
</template>

<script>
    import Input from "./items/Input";
    import Textarea from "./items/Textarea";
    import Button from "../base/Button";
    const field = {
        type: 'input' //default input, input textarea switch select upload checkbox checkbox-group radio radio-group
    }
    export default {
        name: "FormItem",
        components: {
            'he-input': Input,
            'he-textarea': Textarea,
            'he-button': Button
        },
        props: {
            field: {
                type: Object,
                default(){
                    return field
                }
            }
        },
        data(){
            return {
                form: {
                    data: {}
                }
            }
        },
        mounted() {
            this.upForm(this);
            // console.log(this.form)
        },
        methods: {
            submitForm(){
                if(this.form.submitForm){
                    this.form.submitForm();
                }
            },
            resetForm(){
                // console.log(this.form)
                if(this.form.resetForm){
                    this.form.resetForm();
                }
            },
            upForm: function (comp) {
                if (!comp) {
                    return;
                }
                const tagName = comp.$parent.$options._componentTag;
                // console.log(tagName)
                if (!tagName) {

                } else if (tagName == 'he-form') {
                    // console.log(tagName, comp.$parent)
                    this.form = comp.$parent;
                } else {
                    this.upForm(comp.$parent);
                }
            }
        }
    }
</script>

<style scoped>

</style>