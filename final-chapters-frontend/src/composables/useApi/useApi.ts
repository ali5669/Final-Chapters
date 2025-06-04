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
