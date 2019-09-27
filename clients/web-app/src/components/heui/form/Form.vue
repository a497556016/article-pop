<template>
    <div class="he-form">
        <slot></slot>
    </div>
</template>

<script>
    export default {
        name: "Form",
        props: {
            model: {
                type: Object
            }
        },
        model: {
            prop: 'model',
            event: 'change'
        },
        data(){
            const copy = JSON.parse(JSON.stringify(this.model));
            console.log('copy', copy)
            return {
                srcData: copy,
                data: this.model,

                fields: []
            }
        },
        watch: {
            model(m){
                this.data = m;
            },
            data(d){
                this.$emit('change', d);
            }
        },
        mounted(){
            this.findFields(this.$children);
        },
        methods: {
            resetForm(){
                this.data = JSON.parse(JSON.stringify(this.srcData));
            },
            validateForm(){
                let valid = true, errMsg = '';
                // console.log('验证表单', this.fields);
                for (let i = 0; i < this.fields.length; i++) {
                    const field = this.fields[i].field;
                    const rules = field.rules;
                    if(rules){
                        const value = this.data[field.name];
                        if(rules.check){
                            const result = rules.check(value);
                            if(result === false){
                                valid = false;
                                errMsg += (rules.message || (field.label+'验证失败！'))
                            }else if(typeof result === 'string'){
                                valid = false;
                                errMsg += result;
                            }
                        }else if(rules.required && !value){
                            valid = false;
                            errMsg += (rules.message || (field.label+'不能为空！'))
                        }else if(rules.maxLength !== 'undefined' && value.length > rules.maxLength){
                            valid = false;
                            errMsg += (rules.message || (field.label+`最大长度为${rules.maxLength}！`))
                        }else if(rules.minLength !== 'undefined' && value.length < rules.minLength){
                            valid = false;
                            errMsg += (rules.message || (field.label+`最小长度为${rules.minLength}！`))
                        }
                        if(!valid){
                            break;
                        }
                    }
                }
                console.log(valid);
                return {valid, errMsg}
            },
            submitForm(){
                const validResult = this.validateForm();
                this.$emit('validate', validResult.valid, validResult.errMsg);
                if(validResult.valid) {
                    this.$emit('submit');
                }
            },
            findFields(children){
                // console.log(this.$children)
                if(children) {
                    children.forEach(child => {
                        if (child.$options._componentTag === 'he-form-item') {
                            this.fields.push(child);
                        } else {
                            this.findFields(child.$children);
                        }
                    })
                }
            }
        }
    }
</script>

<style scoped>

</style>