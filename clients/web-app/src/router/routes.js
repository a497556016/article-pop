export default [
    {path: '/', redirect: '/home'},
    {name: 'home', path: '/home', component: () => import('@/views/Home')},
    {
        path: '/article/:id/:userId',
        component: () => import('@/views/Article'),
        props: (router) => ({id: router.params.id, userId: router.params.userId==='0'?null:router.params.userId})
    },
    {name: 'user', path: '/user', component: () => import("@/views/UserCenter")},
    {
        path: '/user/edit/:id',
        component: () => import("@/views/UserEdit"),
        props: (router) => ({id: router.params.id})
    },
    {
        path: '/user/collect/:id',
        component: () => import("@/views/UserCollect"),
        props: (router) => ({id: router.params.id})
    },
    {
        path: '/user/like/:id',
        component: () => import("@/views/UserLike"),
        props: (router) => ({id: router.params.id})
    },
    {
        path: '/user/views_record/:id',
        component: () => import("@/views/UserViewsRecord"),
        props: (router) => ({id: router.params.id})
    },
    {path: '/user/login', component: () => import("@/views/Login")},
    {path: '/user/setting', component: () => import("@/views/Setting")}
]