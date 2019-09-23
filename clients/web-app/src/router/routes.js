export default [
    {path: '/', redirect: '/home'},
    {path: '/home', component: () => import('@/ui/Home')},
    {path: '/article/:id', component: () => import('@/ui/Article'), props: (router) => ({id: router.params.id})}
]