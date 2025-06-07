import axios from 'axios'
import { novelClient, type ApiResult } from './useApi'

export interface Novel {
  authorId: string
  novelId: string
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
  /**
   * @tags 获取作者的所有小说
   * @name getNovels
   * @request  POST:/api/novel/getNovels
   * @response `ApiResult<Novel[]>`
   */
  getNovels: async (params: {
    data: {
        authorId: string
    }
    }) => {
      const { data } = await novelClient.post<ApiResult<Novel[]>>('/getNovels', params.data)
      return data
    },
  
  /**
   * @tags 小说
   * @name getNovelById
   * @request  POST:/api/novel/getNovelById
   * @response `ApiResult<string>`
   */
  getNovelById: async (params: {
    data: {
        novelId: string
    }
    }) => {
      const { data } = await novelClient.post<ApiResult<Novel>>('/getNovelById', params.data)
      return data
    },

  
}