<template>
    <div class="setting">
        <he-title-bar title="设置中心"></he-title-bar>
        <div class="he-body">
            <he-panel>
                <he-list :data="items">
                    <he-list-item slot="item" slot-scope="{item}" :data="item" @click="onItemClick(item)"></he-list-item>
                </he-list>
            </he-panel>
        </div>
    </div>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from "vuex"
    import moduleTypes from "../store/types"
    export default {
        name: "Setting",
        data(){
            return {
                items: [
                    {label: '切换账号', icon: 'fa fa-users', iconColor: '#5ae579', textColor: '',},
                    {label: '退出登录', icon: 'fa fa-user', iconColor: '#2457e5', textColor: '',}
                ]
            }
        },
        methods: {
            ...mapMutations({
                logout: moduleTypes.user.LOGOUT
            }),
            onItemClick(item){
                switch (item.label) {
                    default:break;
                    case "退出登录":
                        this.logout();
                        this.$router.push('/user/login')
                        break;
                }
            }
        }
    }
</script>

<style scoped>

</style>