export interface User {
  id: string
  email: string
  username: string
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
