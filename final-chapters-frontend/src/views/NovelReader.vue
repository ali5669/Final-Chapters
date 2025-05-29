<!-- src/views/NovelReader.vue -->
<template>
  <div class="min-h-screen bg-gray-50" :class="{ dark: isDarkMode }">
    <!-- Reader Header -->
    <header class="fixed top-0 left-0 right-0 bg-white dark:bg-gray-800 shadow-sm z-10">
      <div class="container mx-auto px-4 py-2">
        <div class="flex items-center justify-between">
          <div class="flex items-center space-x-4">
            <router-link to="/" class="text-gray-600 dark:text-gray-300 hover:text-indigo-600">
              <i class="fas fa-arrow-left"></i>
            </router-link>
            <h1 class="text-lg font-medium text-gray-900 dark:text-white">{{ novel.title }}</h1>
          </div>
          <div class="flex items-center space-x-4">
            <button
              @click="toggleSettings"
              class="text-gray-600 dark:text-gray-300 hover:text-indigo-600"
            >
              <i class="fas fa-cog"></i>
            </button>
          </div>
        </div>
      </div>
    </header>

    <!-- Reader Content -->
    <main class="container mx-auto px-4 py-20">
      <div
        class="max-w-3xl mx-auto prose prose-lg dark:prose-invert"
        :style="{
          fontSize: `${fontSize}px`,
          fontFamily: fontFamily,
          lineHeight: lineHeight,
        }"
      >
        <h2 class="text-2xl font-bold mb-8">{{ chapter.title }}</h2>
        <div v-html="chapter.content"></div>
      </div>

      <!-- Chapter Navigation -->
      <div class="max-w-3xl mx-auto mt-8 flex justify-between">
        <button
          v-if="hasPreviousChapter"
          @click="navigateChapter('prev')"
          class="px-4 py-2 text-indigo-600 hover:text-indigo-700"
        >
          ← Previous Chapter
        </button>
        <button
          v-if="hasNextChapter"
          @click="navigateChapter('next')"
          class="px-4 py-2 text-indigo-600 hover:text-indigo-700 ml-auto"
        >
          Next Chapter →
        </button>
      </div>
    </main>

    <!-- Reader Settings -->
    <div
      v-if="showSettings"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-20"
      @click.self="showSettings = false"
    >
      <div class="bg-white dark:bg-gray-800 rounded-lg p-6 max-w-md w-full">
        <h3 class="text-lg font-semibold mb-4 text-gray-900 dark:text-white">Reading Settings</h3>
        <div class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300">Theme</label>
            <div class="mt-2 flex space-x-4">
              <button
                @click="isDarkMode = false"
                class="px-4 py-2 rounded-md"
                :class="{
                  'bg-indigo-600 text-white': !isDarkMode,
                  'bg-gray-200 text-gray-700': isDarkMode,
                }"
              >
                Light
              </button>
              <button
                @click="isDarkMode = true"
                class="px-4 py-2 rounded-md"
                :class="{
                  'bg-indigo-600 text-white': isDarkMode,
                  'bg-gray-200 text-gray-700': !isDarkMode,
                }"
              >
                Dark
              </button>
            </div>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300">
              Font Size ({{ fontSize }}px)
            </label>
            <input v-model="fontSize" type="range" min="12" max="24" class="mt-2 w-full" />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300"
              >Font Family</label
            >
            <select
              v-model="fontFamily"
              class="mt-2 block w-full rounded-md border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white"
            >
              <option value="serif">Serif</option>
              <option value="sans-serif">Sans Serif</option>
              <option value="monospace">Monospace</option>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300">
              Line Height ({{ lineHeight }})
            </label>
            <input
              v-model="lineHeight"
              type="range"
              min="1.5"
              max="2.5"
              step="0.1"
              class="mt-2 w-full"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import type { Novel } from '@/types/novel'

const route = useRoute()
const router = useRouter()

// Reading settings
const isDarkMode = ref(false)
const fontSize = ref(18)
const fontFamily = ref('serif')
const lineHeight = ref(1.8)
const showSettings = ref(false)

// Mock data
const novel = ref<Novel>({
  id: '1',
  title: 'The Lost Kingdom',
  author: 'Jane Doe',
  cover: '/images/novel1.jpg',
  description: 'An epic fantasy adventure...',
  tags: ['Fantasy', 'Adventure'],
  category: 'Fantasy',
  status: 'ongoing',
  createdAt: new Date(),
  updatedAt: new Date(),
})

const chapter = ref({
  id: route.params.chapterId as string,
  title: 'Chapter 1: The Beginning',
  content: `
    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
    <p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
    <!-- Add more paragraphs -->
  `,
  number: 1,
})

// Navigation state
const hasPreviousChapter = ref(true)
const hasNextChapter = ref(true)

// Methods
const toggleSettings = () => {
  showSettings.value = !showSettings.value
}

const navigateChapter = (direction: 'prev' | 'next') => {
  const currentChapter = parseInt(route.params.chapterId as string)
  const nextChapter = direction === 'next' ? currentChapter + 1 : currentChapter - 1

  // TODO: Implement actual chapter navigation logic
  router.push(`/read/${route.params.novelId}/${nextChapter}`)
}

// Load chapter data
onMounted(async () => {
  // TODO: Implement actual chapter loading logic
  console.log('Loading chapter:', {
    novelId: route.params.novelId,
    chapterId: route.params.chapterId,
  })
})
</script>

<style>
/* Dark mode styles */
.dark {
  @apply bg-gray-900 text-gray-100;
}

.dark .prose {
  @apply prose-invert;
}
</style>
