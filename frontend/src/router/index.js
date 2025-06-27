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
  ],
})

export default router;
