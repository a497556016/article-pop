<template>
    <div class="user-edit">
        <he-title-bar title="编辑信息"></he-title-bar>
        <div class="he-body">
            <he-form :model="user" @validate="validateHandler" @submit="submitHandler">
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
    import userApi from "../api/user"
    export default {
        name: "UserEdit",
        data(){
            return {
                user: {},
                fields: [
                    {type: 'input', label: '用户名', name: 'username', options: {readonly: false}, rules: {required: true, message: '麻烦填一下用户名哦！'}},
                    {type: 'input', label: '密码', name: 'password', options: {type: 'password', placeholder: '设置新密码'}, rules: {required: true, message: '麻烦设置一下密码哦！'}},
                    {type: 'input', label: '所在公司', name: 'company', options: {readonly: false}, rules: {required: true, message: '公司名称不能为空哦！'}},
                    {type: 'input', label: '职位', name: 'post', options: {readonly: false}, rules: {required: true}},
                    {type: 'input', label: '手机号', name: 'phone', options: {readonly: false, type: 'number'}, rules: {required: true, maxLength: 13}},
                    {type: 'input', label: '邮箱', name: 'email', options: {readonly: false, type: 'email'}, rules: {required: true}},
                    {type: 'textarea', label: '个人签名', name: 'declaration', options: {placeholder: '请输入个人签名'}, rules: {required: true}},
                    {type: 'submit', label: '提交保存', options: {}},
                    {type: 'reset', label: '重置', options: {}}
                ]
            }
        },
        computed: {
            ...mapState({
                // userData: state => state.user.userData
            }),
            ...mapGetters({
                userData: moduleTypes.user.GET_LOGIN_USER_DATA
            })
        },
        activated(){
           this.getUserById();
        },
        methods: {
            ...mapActions({
                saveUser: moduleTypes.user.SAVE_USER
            }),
            async getUserById(){
                if(this.userData.id){
                    this.user = await userApi.getById(this.userData.id);
                    delete this.user.password;
                    if(this.user.username){
                        this.fields[0].options.readonly = true;
                    }
                }
            },
            validateHandler(valid, errMsg){
                if(!valid){
                    this.$createToast({
                        msg: errMsg,
                        type: 'warn',
                        time: 3000
                    }).show()
                }
            },
            async submitHandler(){
                console.log(this.user)
                await this.saveUser(this.user);
                this.$createToast({
                    msg: '保存成功',
                    type: 'success'
                }).show();
            }
        }
    }
</script>

<style scoped lang="less">
    .user-edit {

    }
</style>