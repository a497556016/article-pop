<template>
    <div class="user-edit">
        <he-title-bar title="编辑信息"></he-title-bar>
        <div class="he-body">
            <he-form :model="userData" @validate="validateHandler" @submit="submitHandler">
                <he-form-group title="基本信息">
                    <he-form-item v-for="field in fields" :field="field"></he-form-item>
                </he-form-group>
            </he-form>
        </div>

    </div>
</template>

<script>
    import {mapState, mapGetters, mapMutations, mapActions} from "vuex"
    import moduleTypes from "../store/types"
    export default {
        name: "UserEdit",
        data(){
            return {
                fields: [
                    {type: 'input', label: '用户名', name: 'username', options: {readonly: true}},
                    {type: 'input', label: '所在公司', name: 'company', options: {readonly: false}, rules: {required: true, message: '公司名称不能为空哦！'}},
                    {type: 'input', label: '职位', name: 'post', options: {readonly: false}},
                    {type: 'input', label: '手机号', name: 'phone', options: {readonly: false, type: 'number'}, rules: {required: true, maxLength: 13}},
                    {type: 'input', label: '邮箱', name: 'email', options: {readonly: false, type: 'email'}},
                    {type: 'textarea', label: '个人签名', name: 'declaration', options: {}},
                    {type: 'submit', label: '提交保存', options: {}},
                    {type: 'reset', label: '重置', options: {}}
                ]
            }
        },
        computed: {
            ...mapState({
                userData: state => state.user.userData
            })
        },
        methods: {
            validateHandler(valid, errMsg){
                if(!valid){
                    this.$createToast({
                        msg: errMsg,
                        type: 'warn',
                        time: 3000
                    }).show()
                }
            },
            submitHandler(){
                console.log(this.userData)
            }
        }
    }
</script>

<style scoped lang="less">
    .user-edit {

    }
</style>