import { defineStore } from 'pinia'
import type { User, UserProfile } from '@/types/user'

export const useUserStore = defineStore('user', {
  state: () => ({
    currentUser: null as User | null,
    userProfile: null as UserProfile | null,
    token: null as string | null,
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
  },
})
