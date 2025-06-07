import { useUserStore } from '@/stores/user'
import axios from 'axios'
import { ref } from 'vue'

// 和后端统一的 Result 返回接口，需要指定 data 类型
// 详见 useApiComment 中的示例
export interface ApiResult<T> {
  success: boolean
  errorMsg: string
  data: T
  total: number
}

// 用于处理异步请求的函数，类似 Nuxt 中的 useAsyncData
// 无需放置在 OnMounted 生命周期中，声明即调用
// 示例: const { data, error, loading } = useAsyncData(() => $comment.getOne('123'))
export function useAsyncData(asyncFunction: () => any) {
  const data = ref(null)
  const error = ref(null)
  const loading = ref(true)

  async function fetchData() {
    try {
      loading.value = true
      const response = await asyncFunction()
      data.value = response
    } catch (err) {
      error.value = err
    } finally {
      loading.value = false
    }
  }

  fetchData()

  return { data, error, loading, refresh: fetchData }
}

const userStore = useUserStore()

// 针对特定微服务的请求封装
export const commentClient = axios.create({
  baseURL: '/api/comment',
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${userStore.token}`,
  },
})

// 添加请求拦截器，动态添加 token
commentClient.interceptors.request.use((config) => {
  const token = userStore.token
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

export const ratingClient = axios.create({
  baseURL: '/api/rating',
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${userStore.token}`,
  },
})

ratingClient.interceptors.request.use((config) => {
  const token = userStore.token
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

export const contentClient = axios.create({
  baseURL: '/api/content',
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${userStore.token}`,
  },
})

contentClient.interceptors.request.use((config) => {
  const token = userStore.token
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

export const userClient = axios.create({
  baseURL: '/api/user',
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${userStore.token}`,
  },
})

userClient.interceptors.request.use((config) => {
  const token = userStore.token
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})
export const novelClient = axios.create({
  baseURL: '/api/novel',
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
})

export const chapterClient = axios.create({
  baseURL: '/api/chapter',
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
})