<!-- src/views/Reader.vue -->
<template>
  <div class="min-h-screen flex flex-col">
    <!-- Reader Header -->
    <header class="bg-white shadow-sm py-2 px-4">
      <div class="flex items-center justify-between">
        <div class="flex items-center space-x-4">
          <router-link to="/" class="text-gray-600 hover:text-indigo-600">
            <i class="fas fa-home"></i>
          </router-link>
          <h1 class="text-lg font-semibold">{{ chapter?.title }}</h1>
        </div>
        <div class="flex items-center space-x-4">
          <button @click="toggleSettings" class="text-gray-600 hover:text-indigo-600">
            <i class="fas fa-cog"></i>
          </button>
        </div>
      </div>
    </header>

    <!-- Reader Content -->
    <main class="flex-1 px-4 py-8 transition-colors duration-200" :class="readerStyles">
      <div class="max-w-3xl mx-auto">
        <div class="prose max-w-none" :class="fontSizeClass">
          {{ chapter?.content }}
        </div>

        <!-- Navigation -->
        <div class="flex justify-between mt-8">
          <button
            v-if="previousChapterId"
            @click="navigateToChapter(previousChapterId)"
            class="text-indigo-600 hover:text-indigo-700"
          >
            ← Previous Chapter
          </button>
          <button
            v-if="nextChapterId"
            @click="navigateToChapter(nextChapterId)"
            class="text-indigo-600 hover:text-indigo-700 ml-auto"
          >
            Next Chapter →
          </button>
        </div>
      </div>
    </main>

    <!-- Settings Panel -->
    <div
      v-if="showSettings"
      class="fixed inset-y-0 right-0 w-64 bg-white shadow-lg p-4 transform transition-transform duration-200"
      :class="{ 'translate-x-0': showSettings, 'translate-x-full': !showSettings }"
    >
      <div class="space-y-6">
        <div>
          <h3 class="text-lg font-semibold mb-2">Font Size</h3>
          <input type="range" v-model="fontSize" min="14" max="24" class="w-full" />
        </div>
        <div>
          <h3 class="text-lg font-semibold mb-2">Theme</h3>
          <select v-model="theme" class="w-full border rounded-md p-2">
            <option value="light">Light</option>
            <option value="sepia">Sepia</option>
            <option value="dark">Dark</option>
          </select>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import type { Chapter } from '@/types/novel'

const route = useRoute()
const router = useRouter()

// Reader settings
const fontSize = ref(16)
const theme = ref('light')
const showSettings = ref(false)

// Mock chapter data (replace with actual API call)
const chapter = ref<Chapter>({
  id: '1',
  novelId: '1',
  title: 'Chapter 1: The Beginning',
  content: 'Lorem ipsum dolor sit amet...',
  chapterNumber: 1,
  createdAt: new Date(),
  updatedAt: new Date(),
})

const previousChapterId = ref('prev-chapter-id')
const nextChapterId = ref('next-chapter-id')

// Computed styles
const readerStyles = computed(() => ({
  'bg-white': theme.value === 'light',
  'bg-[#f4ecd8]': theme.value === 'sepia',
  'bg-gray-900 text-gray-100': theme.value === 'dark',
}))

const fontSizeClass = computed(() => `text-[${fontSize.value}px]`)

// Methods
const toggleSettings = () => {
  showSettings.value = !showSettings.value
}

const navigateToChapter = (chapterId: string) => {
  router.push(`/read/${route.params.novelId}/${chapterId}`)
}
</script>
