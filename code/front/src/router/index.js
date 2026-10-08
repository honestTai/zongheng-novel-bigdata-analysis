import Vue from 'vue';
import Router from 'vue-router';

//路由
Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/home'
        },
        {
            path: '/',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: { title: '自述文件' },
            children: [
                {
                    path: '/home',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/dataShow/bookShow.vue'),
                    meta: { title: '书籍分析' }
                },
                {
                    path: '/taskList',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/task/taskList.vue'),
                    meta: { title: '任务列表' }
                },
                {
                    path: '/pythonList',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/python/pythonList.vue'),
                    meta: { title: 'python脚本列表' }
                },
                {
                    path: '/result',
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/dataShow/result.vue'),
                    meta: { title: '结果查看' }
                },
                {
                    path: '/clickData',
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/dataShow/clickData.vue'),
                    meta: { title: '点击榜单' }
                },
                {
                    path: '/month',
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/dataShow/month.vue'),
                    meta: { title: '月票榜单' }
                },
                {
                    path: '/commond',
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/dataShow/commond.vue'),
                    meta: { title: '推荐榜单' }
                },
                {
                    path: '/bookShow',
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/dataShow/bookShow.vue'),
                    meta: { title: '书籍信息' }
                },{
                    path: '/comments',
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/dataShow/comments.vue'),
                    meta: { title: '评论信息' }
                },{
                    path: '/emotion',
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/dataShow/emotion.vue'),
                    meta: { title: '情感分析' }
                },{
                    path: '/chart',
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/dataShow/chartShow.vue'),
                    meta: { title: '榜单分析' }
                },{
                    path: '/books',
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/dataShow/books.vue'),
                    meta: { title: '书籍信息' }
                },

            ]
        },
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/login/Login.vue'),
            meta: { title: '登录' }
        },

    ]
});
