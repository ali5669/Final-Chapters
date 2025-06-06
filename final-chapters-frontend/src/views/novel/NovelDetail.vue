<!-- src/views/NovelDetail.vue -->
<template>
  <div class="space-y-8">
    <!-- Novel Header -->
    <div class="bg-white rounded-lg shadow-sm p-6">
      <div class="flex flex-col md:flex-row gap-8">
        <div class="w-full md:w-64">
          <img :src="novel.cover" :alt="novel.title" class="w-full h-80 object-cover rounded-lg" />
        </div>
        <div class="flex-1 space-y-4">
          <h1 class="text-3xl font-bold text-gray-900">{{ novel.title }}</h1>
          <p class="text-lg text-gray-600">by {{ novel.author }}</p>
          <div class="flex space-x-2">
            <span
              v-for="tag in novel.tags"
              :key="tag"
              class="px-3 py-1 bg-gray-100 rounded-full text-sm text-gray-600"
            >
              {{ tag }}
            </span>
          </div>
          <p class="text-gray-700">{{ novel.description }}</p>
          <div class="flex items-center space-x-4">
            <button
              @click="startReading"
              class="px-6 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700"
            >
              Start Reading
            </button>
            <button
              @click="toggleBookmark"
              class="px-6 py-2 border border-gray-300 rounded-md hover:bg-gray-50"
              :class="{ 'text-indigo-600 border-indigo-600': isBookmarked }"
            >
              {{ isBookmarked ? 'Bookmarked' : 'Bookmark' }}
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Chapter List -->
    <div class="bg-white rounded-lg shadow-sm p-6">
      <h2 class="text-2xl font-bold mb-4">Chapters</h2>
      <div class="space-y-2">
        <div
          v-for="chapter in chapters"
          :key="chapter.id"
          class="flex items-center justify-between p-4 hover:bg-gray-50 rounded-md"
        >
          <div>
            <h3 class="font-medium">{{ chapter.title }}</h3>
            <p class="text-sm text-gray-500">
              Updated {{ new Date(chapter.updatedAt).toLocaleDateString() }}
            </p>
          </div>
          <router-link
            :to="'/read/' + novel.id + '/' + chapter.id"
            class="text-indigo-600 hover:text-indigo-700"
          >
            Read →
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import type { Novel, Chapter } from '@/types/novel'

const router = useRouter()

// Mock data
const novel = ref<Novel>({
  id: '1',
  title: 'The Lost Kingdom',
  author: 'Jane Doe',
  cover: '/images/novel1.jpg',
  description: 'An epic fantasy adventure that follows the journey of a young hero...',
  tags: ['Fantasy', 'Adventure', 'Magic'],
  category: 'Fantasy',
  status: 'ongoing',
  createdAt: new Date(),
  updatedAt: new Date(),
})

const chapters = ref<Chapter[]>([
  {
    id: '1',
    novelId: '1',
    title: 'Chapter 1: The Beginning',
    content: 'Lorem ipsum...',
    chapterNumber: 1,
    createdAt: new Date(),
    updatedAt: new Date(),
  },
  // Add more chapters...
])

const isBookmarked = ref(false)

// Methods
const startReading = () => {
  if (chapters.value.length > 0) {
    router.push(`/read/${novel.value.id}/${chapters.value[0].id}`)
  }
}

const toggleBookmark = () => {
  isBookmarked.value = !isBookmarked.value
  // TODO: Implement actual bookmark logic
}
</script>
