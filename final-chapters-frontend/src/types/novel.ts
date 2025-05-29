export interface Novel {
  id: string
  title: string
  author: string
  cover?: string
  description: string
  tags: string[]
  category: string
  status: 'ongoing' | 'completed'
  createdAt: Date
  updatedAt: Date
}

export interface Chapter {
  id: string
  novelId: string
  title: string
  content: string
  chapterNumber: number
  createdAt: Date
  updatedAt: Date
}

export interface Bookmark {
  id: string
  userId: string
  novelId: string
  chapterId: string
  progress: number
  createdAt: Date
}

export interface ReadingHistory {
  id: string
  userId: string
  novelId: string
  chapterId: string
  lastReadAt: Date
  progress: number
}
