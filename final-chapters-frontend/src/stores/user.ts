import { defineStore } from 'pinia'
import type { User, UserProfile } from '@/types/user'
import axios from 'axios'
import type { ApiResult } from '@/composables/useApi/useApi'

export const useUserStore = defineStore('user', {
  state: () => ({
    currentUser: null as User | null,
    userProfile: null as UserProfile | null,
    token: localStorage.getItem('token') || (null as string | null),
  }),

  getters: {
    isAuthenticated: (state) => !!state.token && !!state.currentUser,
    userPreferences: (state) => state.userProfile?.preferences,
  },

  actions: {
    setUser(user: User) {
      this.currentUser = user
    },
    setToken(token: string) {
      this.token = token
      localStorage.setItem('token', token)
    },
    setUserProfile(profile: UserProfile) {
      this.userProfile = profile
    },
    logout() {
      this.currentUser = null
      this.userProfile = null
      this.token = null
      localStorage.removeItem('token')
    },
    restoreSession() {
      const storedToken = localStorage.getItem('token')
      if (storedToken) {
        this.token = storedToken
        axios
          .get<ApiResult<User>>('/api/user', {
            headers: {
              Authorization: `Bearer ${this.token}`,
            },
          })
          .then((response) => {
            this.setUser(response.data.data)
          })
      }
    },
  },
})
