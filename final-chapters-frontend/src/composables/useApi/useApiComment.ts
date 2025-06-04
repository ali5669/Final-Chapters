import axios from 'axios'
import type { ApiResult } from './useApi'

export interface Comment {
  commentId: number
  userId: number
  novelId: number
  chapterId: number
  content: string
  createAt: string
}

export interface Rating {
  ratingId: number
  userId: number
  novelId: number
  rating: number
  createAt: string
}

export interface RatingCountDTO {
  novelId: number
  count: number
}

export const $comment = {
  /**
   * @tags 评论
   * @name addComment
   * @request  POST:/api/comment
   * @response `ApiResult<null>`
   */
  addComment: async (params: {
    data: {
      userId: string
      novelId: string
      chapterId: string
      content: string
    }
  }) => {
    const { data } = await axios.post<ApiResult<null>>('/api/comment', params.data)
    return data
  },

  /**
   * @tags 评论
   * @name getOne
   * @request  GET:/api/comment/{id}
   * @response `ApiResult<Comment>`
   */
  getOne: async (id: string) => {
    const { data } = await axios.get<ApiResult<Comment>>(`/api/comment/${id}`)
    return data
  },

  /**
   * @tags 评论
   * @name listByNovelId
   * @request  GET:/api/comment/list/{novelId}
   * @response `ApiResult<Comment[]>`
   */
  listByNovelId: async (novelId: string) => {
    const { data } = await axios.get<ApiResult<Comment[]>>(`/api/comment/list/${novelId}`)
    return data
  },

  /**
   * @tags 评论
   * @name listByChapterId
   * @request  GET:/api/comment/list/{novelId}/{chapterId}
   * @response `ApiResult<Comment[]>`
   */
  listByChapterId: async (novelId: string, chapterId: string) => {
    const { data } = await axios.get<ApiResult<Comment[]>>(
      `/api/comment/list/${novelId}/${chapterId}`,
    )
    return data
  },
}

export const $rating = {
  /**
   * @tags 评分
   * @name addRating
   * @request  POST:/api/rating
   * @response `ApiResult<null>`
   */
  addRating: async (params: {
    data: {
      userId: string
      novelId: string
      rating: string
    }
  }) => {
    const { data } = await axios.post<ApiResult<null>>('/api/rating', params.data)
    return data
  },

  /**
   * @tags 评分
   * @name getAverageRating
   * @request  GET:/api/rating/average/{novelId}
   * @response `ApiResult<number>`
   */
  getAverageRating: async (novelId: string) => {
    const { data } = await axios.get<ApiResult<number>>(`/api/rating/average/${novelId}`)
    return data
  },

  /**
   * @tags 评分
   * @name listByNovelId
   * @request  GET:/api/rating/list/{novelId}
   * @response `ApiResult<Rating[]>`
   */
  listByNovelId: async (novelId: string) => {
    const { data } = await axios.get<ApiResult<RatingCountDTO[]>>(`/api/rating/list/${novelId}`)
    return data
  },
}
