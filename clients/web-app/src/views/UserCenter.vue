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
                        <div>{{userData.company}}</div>
                        <div>{{userData.post}}</div>
                        <div style="margin-top: 15px;color: #3ab566">{{userData.declaration}}</div>
                    </div>
                    <div class="ac" @click="toEditUser"><i class="fa fa-angle-right"></i></div>
                </div>
            </he-panel>

            <he-panel>
                <he-list :data="actions">
                    <div style="display: flex;color: #362e44" slot="item" slot-scope="{item}">
                        <div :style="{color: item.color, marginTop: '1px', fontSize: '1.2em'}"><i :class="[item.icon]"></i></div>
                        <div style="flex: 1;margin-left: 1.5em">{{item.label}}</div>
                        <div class="ac"><i class="fa fa-angle-right"></i></div>
                    </div>
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
                    {icon: 'fa fa-star', color: '#4288ff', label: '收藏'},
                    {icon: 'fa fa-heart', color: '#ff5956', label: '点赞'},
                    {icon: 'fa fa-cog', color: '#47e3b4', label: '设置'}
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
                this.$router.push('/user/edit')
            }
        }
    }
</script>

<style scoped lang="less">
    .ac{
        color: #a8a8a8;
        width: 25px;
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
                padding: 1em;
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
                    flex: 1;
                    padding: 10px 0;
                    font-size: 0.9em;
                }

            }
        }
    }
</style>