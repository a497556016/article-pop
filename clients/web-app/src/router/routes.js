export default [
    {path: '/', redirect: '/home'},
    {path: '/home', component: () => import('@/views/Home')},
    {
        path: '/article/:id/:userId',
        component: () => import('@/views/Article'),
        props: (router) => ({id: router.params.id, userId: router.params.userId})
    },
    {path: '/user', component: () => import("@/views/UserCenter")},
    {
        path: '/user/edit/:id',
        component: () => import("@/views/UserEdit"),
        props: (router) => ({id: router.params.id})
    },
    {path: '/user/login', component: () => import("@/views/Login")},
    {path: '/setting', component: () => import("@/views/Setting")}
]