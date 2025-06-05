export interface User {
  id: string
  username: string
  isVIP: boolean
  avatar?: string
  createdAt: Date
  updatedAt: Date
}

export interface UserProfile extends User {
  bio?: string
  preferences: {
    theme: 'light' | 'dark'
    fontSize: number
    backgroundColor: string
  }
}

export interface AuthResponse {
  user: User
  token: string
}
