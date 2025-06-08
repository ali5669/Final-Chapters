<template>
  <div class="space-y-8">
    <div class="flex justify-between items-center">
      <h1 class="text-2xl font-bold bg-gradient-to-r from-indigo-500 via-purple-500 to-pink-500 bg-clip-text text-transparent">
        My Library
      </h1>
      <div class="flex space-x-4">
        <input
          type="text"
          v-model="searchQuery"
          placeholder="Search in library..."
          class="px-6 py-2 bg-white/50 dark:bg-gray-800/50 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-indigo-500 focus:border-indigo-500 dark:text-gray-200 placeholder-gray-400 dark:placeholder-gray-500"
        />
        <select
          v-model="filterStatus"
          class="px-6 py-2 bg-white/50 dark:bg-gray-800/50 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-indigo-500 focus:border-indigo-500 dark:text-gray-200"
        >
          <option value="">All Status</option>
          <option value="reading">Reading</option>
          <option value="completed">Completed</option>
          <option value="plan_to_read">Plan to Read</option>
        </select>
      </div>
    </div>

    <!-- Reading Progress -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div
        v-for="book in filteredBooks"
        :key="book.id"
        class="bg-white/80 dark:bg-gray-900/80 rounded-2xl shadow-lg overflow-hidden backdrop-blur-lg hover:shadow-xl transition-all duration-300"
      >
        <div class="flex">
          <img :src="book.cover" :alt="book.title" class="w-24 h-32 object-cover" />
          <div class="flex-1 p-4">
            <h3 class="font-semibold text-lg mb-1 text-gray-800 dark:text-gray-200">{{ book.title }}</h3>
            <p class="text-sm text-gray-600 dark:text-gray-400 mb-2">{{ book.author }}</p>
            <div class="space-y-2">
              <div class="flex items-center text-sm">
                <span class="text-gray-500 dark:text-gray-400">Progress:</span>
                <span class="ml-2 font-medium text-indigo-600 dark:text-indigo-400">{{ book.progress }}%</span>
              </div>
              <div class="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-2">
                <div
                  class="bg-gradient-to-r from-indigo-500 to-purple-600 h-2 rounded-full transition-all duration-300"
                  :style="{ width: book.progress + '%' }"
                ></div>
              </div>
              <div class="flex justify-between text-sm">
                <span class="text-gray-500 dark:text-gray-400"
                  >Chapter {{ book.currentChapter }}/{{ book.totalChapters }}</span
                >
                <router-link
                  :to="'/read/' + book.id + '/' + book.currentChapterId"
                  class="text-indigo-600 dark:text-indigo-400 hover:text-indigo-700 dark:hover:text-indigo-300 transition-colors duration-300"
                >
                  Continue Reading →
                </router-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Reading Statistics -->
    <div class="bg-white/80 dark:bg-gray-900/80 rounded-2xl shadow-lg p-8 backdrop-blur-lg">
      <h2 class="text-xl font-semibold mb-6 text-gray-800 dark:text-gray-200">Reading Statistics</h2>
      <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div class="text-center p-4 bg-gradient-to-br from-indigo-50 to-purple-50 dark:from-indigo-900/50 dark:to-purple-900/50 rounded-xl">
          <p class="text-3xl font-bold bg-gradient-to-r from-indigo-500 to-purple-600 bg-clip-text text-transparent">
            {{ statistics.totalBooks }}
          </p>
          <p class="text-gray-600 dark:text-gray-400">Total Books</p>
        </div>
        <div class="text-center p-4 bg-gradient-to-br from-indigo-50 to-purple-50 dark:from-indigo-900/50 dark:to-purple-900/50 rounded-xl">
          <p class="text-3xl font-bold bg-gradient-to-r from-indigo-500 to-purple-600 bg-clip-text text-transparent">
            {{ statistics.booksCompleted }}
          </p>
          <p class="text-gray-600 dark:text-gray-400">Books Completed</p>
        </div>
        <div class="text-center p-4 bg-gradient-to-br from-indigo-50 to-purple-50 dark:from-indigo-900/50 dark:to-purple-900/50 rounded-xl">
          <p class="text-3xl font-bold bg-gradient-to-r from-indigo-500 to-purple-600 bg-clip-text text-transparent">
            {{ statistics.chaptersRead }}
          </p>
          <p class="text-gray-600 dark:text-gray-400">Chapters Read</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'

interface Book {
  id: string
  title: string
  author: string
  cover: string
  progress: number
  currentChapter: number
  totalChapters: number
  currentChapterId: string
  status: 'reading' | 'completed' | 'plan_to_read'
}

const searchQuery = ref('')
const filterStatus = ref('')

// Mock data
const books = ref<Book[]>([
  {
    id: '1',
    title: 'The Lost Kingdom',
    author: 'Jane Doe',
    cover: '/images/novel1.jpg',
    progress: 45,
    currentChapter: 9,
    totalChapters: 20,
    currentChapterId: '9',
    status: 'reading',
  },
  // Add more books...
])

const statistics = ref({
  totalBooks: 12,
  booksCompleted: 5,
  chaptersRead: 127,
})

const filteredBooks = computed(() => {
  let result = books.value

  if (searchQuery.value) {
    result = result.filter(
      (book) =>
        book.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
        book.author.toLowerCase().includes(searchQuery.value.toLowerCase()),
    )
  }

  if (filterStatus.value) {
    result = result.filter((book) => book.status === filterStatus.value)
  }

  return result
})
</script>
