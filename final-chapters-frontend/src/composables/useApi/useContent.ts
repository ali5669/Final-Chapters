import { contentClient, type ApiResult } from './useApi'

export interface Novel {
  id: number
  authorId: number
  title: string
  summary: string | null
  coverImage: string | null
  category: string | null
  tags: string | null
  createdAt: string
  updatedAt: string
}

export interface Chapter {
  chapterId: number
  novelId: number
  title: string
  content: string
  order: number
  createAt: string
  updateAt: string
}

/**
 * @name novel
 * @description 小说相关接口集合
 */
export const $novel = {
  /**
   * @tags 小说
   * @name getAll
   * @request GET:/api/novels/all
   * @response `ApiResult<Novel[]>`
   */
  getAll: async () => {
    const { data } = await contentClient.get<ApiResult<Novel[]>>('/novels/all')
    console.log('getall',data)
    return data
  },

  /**
   * @tags 小说
   * @name getOne
   * @request GET:/api/novels/{novelId}
   * @response `ApiResult<Novel>`
   */
  getOne: async (novelId: string) => {
    const { data } = await contentClient.get<ApiResult<Novel>>(`/novels/${novelId}`)
    return data
  },

  /**
   * @tags 小说
   * @name searchByTitle
   * @request GET:/api/novels/search/title
   * @response `ApiResult<Novel[]>`
   */
  searchByTitle: async (keyword: string) => {
    const { data } = await contentClient.get<ApiResult<Novel[]>>('/novels/search/title', {
      params: { keyword }
    })
    return data
  },

  /**
   * @tags 小说
   * @name searchByCategory
   * @request GET:/api/novels/search/category
   * @response `ApiResult<Novel[]>`
   */
  searchByCategory: async (category: string) => {
    const { data } = await contentClient.get<ApiResult<Novel[]>>('/novels/search/category', {
      params: { category }
    })
    return data
  },

  /**
   * @tags 小说
   * @name searchByTag
   * @request GET:/api/novels/search/tag
   * @response `ApiResult<Novel[]>`
   */
  searchByTag: async (tag: string) => {
    const { data } = await contentClient.get<ApiResult<Novel[]>>('/novels/search/tag', {
      params: { tag }
    })
    return data
  },

  /**
   * @tags 小说
   * @name searchByAuthor
   * @request GET:/api/novels/search/author/{authorId}
   * @response `ApiResult<Novel[]>`
   */
  searchByAuthor: async (authorId: string) => {
    const { data } = await contentClient.get<ApiResult<Novel[]>>(`/novels/search/author/${authorId}`)
    return data
  },

  /**
   * @tags 小说
   * @name search
   * @request GET:/api/novels/search
   * @response `ApiResult<Novel[]>`
   */
  search: async (params: {
    title?: string
    category?: string
    tag?: string
  }) => {
    const { data } = await contentClient.get<ApiResult<Novel[]>>('/novels/search', { params })
    return data
  }
}

/**
 * @name chapter
 * @description 章节相关接口集合
 */
export const $chapter = {
  /**
   * @tags 章节
   * @name getNovelChapters
   * @request GET:/api/chapters/novel/{novelId}
   * @response `ApiResult<Chapter[]>`
   */
  getNovelChapters: async (novelId: string) => {
    const { data } = await contentClient.get<ApiResult<Chapter[]>>(`/chapters/novel/${novelId}`)
    return data
  },

  /**
   * @tags 章节
   * @name getLatestChapter
   * @request GET:/api/chapters/novel/{novelId}/latest
   * @response `ApiResult<Chapter>`
   */
  getLatestChapter: async (novelId: string) => {
    const { data } = await contentClient.get<ApiResult<Chapter>>(`/chapters/novel/${novelId}/latest`)
    return data
  },

  /**
   * @tags 章节
   * @name getChapterByOrder
   * @request GET:/api/chapters/novel/{novelId}/order/{order}
   * @response `ApiResult<Chapter>`
   */
  getChapterByOrder: async (novelId: string, order: number) => {
    const { data } = await contentClient.get<ApiResult<Chapter>>(`/chapters/novel/${novelId}/order/${order}`)
    return data
  },

  /**
   * @tags 章节
   * @name getChapterCount
   * @request GET:/api/chapters/novel/{novelId}/count
   * @response `ApiResult<{ chapterCount: number }>`
   */
  getChapterCount: async (novelId: string) => {
    const { data } = await contentClient.get<ApiResult<{ chapterCount: number }>>(`/chapters/novel/${novelId}/count`)
    return data
  },

  /**
   * @tags 章节
   * @name searchChaptersByTitle
   * @request GET:/api/chapters/novel/{novelId}/search
   * @response `ApiResult<Chapter[]>`
   */
  searchChaptersByTitle: async (novelId: string, keyword: string) => {
    const { data } = await contentClient.get<ApiResult<Chapter[]>>(`/chapters/novel/${novelId}/search`, {
      params: { keyword }
    })
    return data
  },

  /**
   * @tags 章节
   * @name getChaptersByPage
   * @request GET:/api/chapters/novel/{novelId}/page
   * @response `ApiResult<Chapter[]>`
   */
  getChaptersByPage: async (novelId: string, page: number = 1, size: number = 20) => {
    const { data } = await contentClient.get<ApiResult<Chapter[]>>(`/chapters/novel/${novelId}/page`, {
      params: { page, size }
    })
    return data
  },

  /**
   * @tags 章节
   * @name getOne
   * @request GET:/api/chapters/{chapterId}
   * @response `ApiResult<Chapter>`
   */
  getOne: async (chapterId: string) => {
    const { data } = await contentClient.get<ApiResult<Chapter>>(`/chapters/${chapterId}`)
    return data
  }
} 