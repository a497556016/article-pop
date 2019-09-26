export default [
    {path: '/', redirect: '/home'},
    {path: '/home', component: () => import('@/views/Home')},
    {path: '/article/:id', component: () => import('@/views/Article'), props: (router) => ({id: router.params.id})},
    {path: '/user', component: () => import("@/views/UserCenter")},
    {path: '/user/edit', component: () => import("@/views/UserEdit")},
    {path: '/user/login', component: () => import("@/views/Login")}
]