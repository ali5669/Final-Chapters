import axios from 'axios'
import { commentClient, ratingClient, type ApiResult } from './useApi'

export interface Comment {
  commentId: number
  userId: number
  novelId: number
  chapterId: number
  content: string
  createdAt: string
}

export interface Rating {
  ratingId: number
  userId: number
  novelId: number
  rating: number
  createdAt: string
}

export interface RatingCountDTO {
  rating: number
  count: number
}

/**
 * @name comment
 * @description 评论请求接口集合
 */
export const $comment = {
  /**
   * @tags 评论
   * @name addComment
   * @request  POST:/api/comment
   * @response `ApiResult<null>`
   */
  addComment: async (params: {
    data: {
      novelId: string
      chapterId?: string
      content: string
    }
  }) => {
    const { data } = await commentClient.post<ApiResult<null>>('', params.data)
    return data
  },

  /**
   * @tags 评论
   * @name getOne
   * @request  GET:/api/comment/{id}
   * @response `ApiResult<Comment>`
   */
  getOne: async (id: string) => {
    const { data } = await commentClient.get<ApiResult<Comment>>(`/${id}`)
    console.log(data)
    return data
  },

  /**
   * @tags 评论
   * @name listByNovelId
   * @request  GET:/api/comment/list/{novelId}
   * @response `ApiResult<Comment[]>`
   */
  listByNovelId: async (novelId: string) => {
    const { data } = await commentClient.get<ApiResult<Comment[]>>(`/novelList/${novelId}`)
    return data
  },

  /**
   * @tags 评论
   * @name listByChapterId
   * @request  GET:/api/comment/list/{novelId}/{chapterId}
   * @response `ApiResult<Comment[]>`
   */
  listByChapterId: async (novelId: string, chapterId: string) => {
    const { data } = await commentClient.get<ApiResult<Comment[]>>(
      `/chapterList/${novelId}/${chapterId}`,
    )
    return data
  },
}

/**
 * @name rating
 * @description 打分请求接口集合
 */
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
    const { data } = await ratingClient.post<ApiResult<null>>('', params.data)
    return data
  },

  /**
   * @tags 评分
   * @name getAverageRating
   * @request  GET:/api/rating/average/{novelId}
   * @response `ApiResult<number>`
   */
  getAverageRating: async (novelId: string) => {
    const { data } = await ratingClient.get<ApiResult<number>>(`/average/${novelId}`)
    return data
  },

  /**
   * @tags 评分
   * @name listByNovelId
   * @request  GET:/api/rating/list/{novelId}
   * @response `ApiResult<Rating[]>`
   */
  listByNovelId: async (novelId: string) => {
    const { data } = await axios.get<ApiResult<RatingCountDTO[]>>(`/list/${novelId}`)
    return data
  },
}
