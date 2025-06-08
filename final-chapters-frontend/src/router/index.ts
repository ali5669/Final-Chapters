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
        {
          path: 'author',
          name: 'author',
          component: () => import('@/views/author/AuthorHome.vue'),
        },
        {
          path: 'author/addWork',
          name: 'addWork',
          component: () => import('@/views/author/AddNovel.vue'),
        },
        {
          path: 'author/manageChapters/:novelId',
          name: 'manageChapters',
          component: () => import('@/views/author/ManageChapter.vue'),
        },
        {
          path: '/author/addChapter/:novelId',
          name: 'addChapter',
          component: () => import('@/views/author/AddChapter.vue'),
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
          component: () => import('@/views/auth/Login.vue'),
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
      component: () => import('@/layouts/DefaultLayout.vue'),
      meta: { requiresAuth: true },
      children: [
        {
          path: 'profile',
          name: 'profile',
          component: () => import('@/views/dashboard/Profile.vue'),
        },
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
