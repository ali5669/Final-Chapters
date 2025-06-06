export interface User {
  userId: number
  username: string
  isVIP: boolean
  profilePicture?: string
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
