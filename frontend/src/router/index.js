import { createRouter, createWebHashHistory } from 'vue-router';

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      component: () => import('../components/pages/Index.vue'),
    },
    {
      path: '/books',
      component: () => import('../components/ui/BookGrid.vue'),
    },
    {
      path: '/users',
      component: () => import('../components/ui/UserGrid.vue'),
    },
    {
      path: '/subscribes',
      component: () => import('../components/ui/SubscribeGrid.vue'),
    },
    {
      path: '/bookPoints',
      component: () => import('../components/BookPointView.vue'),
    },
    {
      path: '/genData',
      component: () => import('../components/ui/GenDataGrid.vue'),
    },
    {
      path: '/authors',
      component: () => import('../components/ui/AuthorGrid.vue'),
    },
    {
      path: '/subscribeMonitors',
      component: () => import('../components/SubscribeMonitorView.vue'),
    },
    {
      path: '/booklists',
      component: () => import('../components/BooklistView.vue'),
    },
    //ppt8페이지
    {
      path: '/manage',
      component: () => import('../components/ui/ManageAuthorRequests.vue'),
    },
    //myPage 추가
    {
      path: '/mypage',
      component: () => import('../components/ui/MyPage.vue'),
    },
    // BookInfo 추가
    {
      path: '/books/:id',
      component: () => import('../components/ui/BookInfo.vue'),
      props: route => ({ id: route.params.id })
    },
  ],
})

export default router;
