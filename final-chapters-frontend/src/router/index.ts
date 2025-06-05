import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      component: () => import('@/layouts/DefaultLayout.vue'),
      children: [
        {
          path: '',
          name: 'home',
          component: () => import('@/views/HomePage.vue'),
        },
        {
          path: 'browse',
          name: 'browse',
          component: () => import('@/views/novel/NovelBrowse.vue'),
        },
        {
          path: 'novel/:id',
          name: 'novel-detail',
          component: () => import('@/views/novel/NovelDetail.vue'),
        },
        {
          path: 'read/:novelId/:chapterId',
          name: 'reader',
          component: () => import('@/views/novel/NovelReader.vue'),
        },
      ],
    },
    {
      path: '/auth',
      component: () => import('@/layouts/AuthLayout.vue'),
      children: [
        {
          path: 'login',
          name: 'login',
          component: () => import('@/views/auth/UserLogin.vue'),
        },
        {
          path: 'register',
          name: 'register',
          component: () => import('@/views/auth/Register.vue'),
        },
      ],
    },
    {
      path: '/dashboard',
      component: () => import('@/layouts/DashboardLayout.vue'),
      meta: { requiresAuth: true },
      children: [
        {
          path: 'library',
          name: 'library',
          component: () => import('@/views/dashboard/UserLibrary.vue'),
        },
        {
          path: 'write',
          name: 'write',
          component: () => import('@/views/dashboard/NovelWrite.vue'),
        },
      ],
    },
  ],
})

// Navigation guards
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()

  if (to.meta.requiresAuth && !userStore.isAuthenticated) {
    next({ name: 'login', query: { redirect: to.fullPath } })
  } else {
    next()
  }
})

export default router
