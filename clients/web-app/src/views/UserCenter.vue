<template>
    <div class="user-center">
        <he-title-bar title="用户中心"></he-title-bar>

        <div class="he-body">
            <he-panel>
                <div class="user-info">
                    <div class="l">
                        <div class="username">
                            {{userData.username}}
                        </div>
                        <div class="avatar">
                            <img :src="userData.avatar"/>
                        </div>
                    </div>
                    <div class="r">
                        <div class="login" v-if="!userData.id" @click="toLogin">点击登录</div>
                        <div v-else>
                            <div>{{userData.company}}</div>
                            <div>{{userData.post}}</div>
                            <div style="color: #3ab566;margin-top: 10px;word-break: break-all;">{{userData.declaration}}</div>
                        </div>
                    </div>
                    <div class="ac" @click="toEditUser"><i class="fa fa-angle-right"></i></div>
                </div>
            </he-panel>

            <he-panel>
                <he-list :data="actions">
                    <he-list-item slot="item" slot-scope="{item}" @click="itemClick(item)" :data="item"></he-list-item>
                </he-list>
            </he-panel>
        </div>
    </div>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from "vuex"
    import moduleTypes from "../store/types"
    export default {
        name: "UserCenter",
        data(){
            return {
                actions: [
                    {icon: 'fa fa-star', iconColor: '#4288ff', label: '收藏'},
                    {icon: 'fa fa-heart', iconColor: '#ff5956', label: '点赞'},
                    {icon: 'fa fa-cube', iconColor: '#8bff82', label: '数据'},
                    {icon: 'fa fa-cog', iconColor: '#47e3b4', label: '设置'}
                ]
            }
        },
        computed: {
            ...mapGetters({
                userData: moduleTypes.user.GET_LOGIN_USER_DATA
            })
        },
        methods: {
            toEditUser(){
                this.$router.push('/user/edit/'+(this.userData.id||'0'))
            },
            toLogin(){
                this.$router.push('/user/login')
            },
            itemClick(item){
                if(!this.userData.id){
                    this.$createToast({msg: '请先登录！'}).show();
                    return;
                }
                switch (item.label) {
                    default: break;
                    case '设置':
                        this.$router.push('/setting')
                        break;
                    case '收藏':
                        this.$router.push('/user/collect/'+this.userData.id)
                        break;
                    case '点赞':
                        this.$router.push('/user/like/'+this.userData.id)
                        break;
                }
            }
        }
    }
</script>

<style scoped lang="less">
    .ac{
        color: #a8a8a8;
        width: 30px;
        text-align: right;
        border-left: 0.02em solid #efefef;
        i{
            position: relative;
            top: 50%;
            transform: translateY(-50%);
        }
    }
    .user-center {
        .he-body {
            .he-panel{
                margin-bottom: 1em;
                padding: 0.5em;
            }

            .user-info {
                display: flex;
                color: #888888;
                padding: 0 5px;

                .l {
                    width: 100px;
                    .avatar {
                        height: 80px;
                        width: 80px;
                        border: 0.05em solid #efefef;
                        text-align: center;
                        img{
                            width: 100%;
                            height: 100%;
                        }
                    }
                    .username{
                        /*text-align: center;*/
                        margin-bottom: 5px;
                        font-size: 0.8em;
                        color: #999999;
                        /*font-weight: bold;*/
                    }
                }
                .r {
                    margin-top: 1.5em;
                    margin-right: 1em;
                    flex: 1;
                    font-size: 0.9em;

                    .login{
                        text-decoration-line: underline;
                        color: #5c7fff;
                    }
                }

            }
        }
    }
</style>