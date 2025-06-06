import axios from 'axios'
import type { ApiResult } from './useApi'
import { userClient } from './useApi'

export interface User {
  userId: number
  username: string
  isVIP: boolean
  profilePicture?: string
}

export interface LoginResponse {
  user: User
  token: string
}

export interface RegisterRequest {
  username: string
  password: string
}

export interface LoginRequest {
  username: string
  password: string
}

export interface EditProfileRequest {
  username: string
  profilePicture: File
}

export interface EditPasswordRequest {
  username: string
  oldPassword: string
  newPassword: string
}

export interface BecomeVIPRequest {
  username: string
  isVIP: boolean
}

/**
 * @name user
 * @description 用户相关请求接口集合
 */
export const $user = {
  /**
   * @tags 用户
   * @name register
   * @request  POST:/api/user/register
   * @response `ApiResult<LoginResponse>`
   */
  register: async (params: { data: RegisterRequest }) => {
    const { data } = await userClient.post<ApiResult<LoginResponse>>('/register', params.data)
    return data
  },

  /**
   * @tags 用户
   * @name login
   * @request  POST:/api/user/login
   * @response `ApiResult<LoginResponse>`
   */
  login: async (params: { data: LoginRequest }) => {
    const { data } = await userClient.post<ApiResult<LoginResponse>>('/login', params.data)
    return data
  },

  /**
   * @tags 用户
   * @name editProfile
   * @request  POST:/api/user/editProfile
   * @response `ApiResult<User>`
   */
  editProfile: async (params: { data: EditProfileRequest }) => {
    const formData = new FormData()
    formData.append('profilePicture', params.data.profilePicture)
    formData.append('username', params.data.username)
    const { data } = await userClient.post<ApiResult<User>>('/editProfile', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    })
    return data
  },

  /**
   * @tags 用户
   * @name editPassword
   * @request  POST:/api/user/editPassword
   * @response `ApiResult<null>`
   */
  editPassword: async (params: { data: EditPasswordRequest }) => {
    const { data } = await userClient.post<ApiResult<null>>('/editPassword', params.data)
    return data
  },

  /**
   * @tags 用户
   * @name becomeVIP
   * @request  POST:/api/user/becomeVIP
   * @response `ApiResult<User>`
   */
  becomeVIP: async (params: { data: BecomeVIPRequest }) => {
    const { data } = await userClient.post<ApiResult<User>>('/becomeVIP', params.data)
    return data
  },
}
