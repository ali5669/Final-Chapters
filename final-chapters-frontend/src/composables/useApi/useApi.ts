import { ref } from 'vue'

export interface ApiResult<T> {
  success: boolean
  errorMsg: string
  data: T
  total: number
}

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
