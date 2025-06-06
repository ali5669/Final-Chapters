import axios from 'axios'
import { novelClient, type ApiResult } from './useApi'

export interface Novel {
  authorId: string
  title: string
  summary: string
  category: string
  tags: string
}


/**
 * @name novel
 * @description 小说请求接口集合
 */
export const $novel = {
  /**
   * @tags 小说
   * @name addNovel
   * @request  POST:/api/novel/addNovel
   * @response `ApiResult<string>`
   */
  addNovel: async (params: {
    data: {
        authorId: string
        title: string
        summary: string
        category: string
        tags: string
    }
    }) => {
    const { data } = await novelClient.post<ApiResult<string>>('/addNovel', params.data)
    return data
  },

  
}