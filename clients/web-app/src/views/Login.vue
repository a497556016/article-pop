<template>
    <div class="login">
        <div class="login-panel">
            <he-form :model="user" @validate="validateHandler" @submit="submitHandler">
                <he-form-group title="账号密码登录">
                    <he-form-item :field="{type: 'input', label: '用户名', name: 'username', options: {placeholder: '请输入用户名'}, rules: {required: true, maxLength: 12}}"></he-form-item>
                    <he-form-item :field="{type: 'input', label: '密码', name: 'password', options: {type: 'password', placeholder: '请输入密码'}, rules: {required: true, maxLength: 12}}"></he-form-item>
                    <he-form-item :field="{type: 'submit', label: '登录'}"></he-form-item>
                </he-form-group>
            </he-form>
        </div>
    </div>
</template>

<script>
    import {mapState, mapGetters, mapMutations, mapActions} from "vuex"
    import moduleTypes from "../store/types"
    export default {
        name: "Login",
        data(){
            return {
                user: {
                    username: '',
                    password: ''
                }
            }
        },
        methods: {
            ...mapActions({
                loginWithPwd: moduleTypes.user.LOGIN_WITH_PWD
            }),
            validateHandler(valid, err){
                if (!valid){
                    this.$createToast({
                        type: 'warn',
                        position: 'top',
                        msg: err
                    }).show();
                }
            },
            async submitHandler(){
                await this.loginWithPwd(this.user);
                this.$createToast({msg: '登录成功！'});
                this.$router.replace('/')
            }
        }
    }
</script>

<style scoped lang="less">
    .login {
        background: white;
        height: 100%;

        .login-panel{
            margin: 25px;
            position: relative;
            top: 30%;
            transform: translateY(-50%);
        }
    }
</style>