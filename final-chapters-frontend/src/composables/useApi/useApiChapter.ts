import { chapterClient, type ApiResult } from './useApi'

export interface Chapter {
  chapterId: string
  novelId: string
  title: string
  content: string
  order: number
}

/**
 * @name chapter
 * @description 章节请求接口集合
 */
export const $chapter = {
  /**
   * @tags 章节
   * @name addChapter
   * @request  POST:/api/chapter/addChapter
   * @response `ApiResult<string>`
   */
  addChapter: async (params: {
    data: {
      novelId: string
      title: string
      content: string
    }
  }) => {
    const { data } = await chapterClient.post<ApiResult<string>>('/addChapter', params.data)
    return data
  },

  /**
   * @tags 章节
   * @name getNewOrder
   * @request  POST:/api/chapter/getNewOrder
   * @response `ApiResult<string>`
   */
  getNewOrder: async (params: {
    data: {
      novelId: string
    }
  }) => {
    const { data } = await chapterClient.post<ApiResult<string>>('/getNewOrder', params.data)
    return data
  },

  /**
   * @tags 章节
   * @name getChapters
   * @request  POST:/api/chapter/getChapters
   * @response `ApiResult<Chapter[]>`
   */
  getChapters: async (params: {
    data: {
      novelId: string
    }
  }) => {
    const { data } = await chapterClient.post<ApiResult<Chapter[]>>('/getChapters', params.data)
    return data
  },
}
