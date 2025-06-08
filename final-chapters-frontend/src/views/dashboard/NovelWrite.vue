<template>
  <div class="space-y-8">
    <div class="flex justify-between items-center">
      <h1 class="text-2xl font-bold bg-gradient-to-r from-indigo-500 via-purple-500 to-pink-500 bg-clip-text text-transparent">
        Write Your Story
      </h1>
      <div class="flex space-x-4">
        <button
          @click="saveChapter"
          class="px-6 py-2 bg-gradient-to-r from-indigo-500 to-purple-600 text-white rounded-lg font-semibold hover:from-indigo-600 hover:to-purple-700 transition-all duration-300 shadow-md"
        >
          Save Chapter
        </button>
        <button
          @click="publishChapter"
          class="px-6 py-2 border-2 border-indigo-500 text-indigo-600 dark:text-indigo-400 rounded-lg font-semibold hover:bg-indigo-50 dark:hover:bg-indigo-900/30 transition-all duration-300"
        >
          Publish
        </button>
      </div>
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-4 gap-8">
      <!-- Novel List -->
      <div class="lg:col-span-1 bg-white/80 dark:bg-gray-900/80 rounded-2xl shadow-lg p-6 backdrop-blur-lg">
        <div class="flex justify-between items-center mb-4">
          <h2 class="text-lg font-semibold text-gray-800 dark:text-gray-200">My Novels</h2>
          <button
            @click="showNewNovelDialog = true"
            class="text-indigo-600 dark:text-indigo-400 hover:text-indigo-700 dark:hover:text-indigo-300 transition-colors duration-300"
          >
            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
            </svg>
          </button>
        </div>
        <div class="space-y-3">
          <div
            v-for="novel in novels"
            :key="novel.id"
            class="p-4 hover:bg-indigo-50 dark:hover:bg-indigo-900/30 rounded-xl cursor-pointer transition-colors duration-300"
            :class="{ 'bg-indigo-50 dark:bg-indigo-900/30': selectedNovel?.id === novel.id }"
            @click="selectNovel(novel)"
          >
            <h3 class="font-medium text-gray-800 dark:text-gray-200">{{ novel.title }}</h3>
            <p class="text-sm text-gray-500 dark:text-gray-400">{{ novel.chapters.length }} chapters</p>
          </div>
        </div>
      </div>

      <!-- Editor Section -->
      <div class="lg:col-span-3 space-y-6">
        <div class="bg-white/80 dark:bg-gray-900/80 rounded-2xl shadow-lg p-6 backdrop-blur-lg">
          <input
            v-model="chapterTitle"
            type="text"
            placeholder="Chapter Title"
            class="w-full text-xl font-semibold mb-4 bg-transparent border-0 focus:ring-0 text-gray-800 dark:text-gray-200 placeholder-gray-400 dark:placeholder-gray-500"
          />
          <div class="prose dark:prose-invert max-w-none">
            <textarea
              v-model="chapterContent"
              rows="20"
              placeholder="Write your story here..."
              class="w-full bg-transparent border-0 focus:ring-0 resize-none text-gray-800 dark:text-gray-200 placeholder-gray-400 dark:placeholder-gray-500"
            ></textarea>
          </div>
        </div>

        <!-- Chapter Settings -->
        <div class="bg-white/80 dark:bg-gray-900/80 rounded-2xl shadow-lg p-6 backdrop-blur-lg">
          <h3 class="text-lg font-semibold mb-4 text-gray-800 dark:text-gray-200">Chapter Settings</h3>
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300">Chapter Number</label>
              <input
                v-model="chapterNumber"
                type="number"
                min="1"
                class="mt-1 block w-full rounded-lg border-gray-300 dark:border-gray-600 bg-white/50 dark:bg-gray-800/50 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 dark:text-gray-200"
              />
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300">Status</label>
              <select
                v-model="chapterStatus"
                class="mt-1 block w-full rounded-lg border-gray-300 dark:border-gray-600 bg-white/50 dark:bg-gray-800/50 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 dark:text-gray-200"
              >
                <option value="draft">Draft</option>
                <option value="published">Published</option>
              </select>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- New Novel Dialog -->
    <div
      v-if="showNewNovelDialog"
      class="fixed inset-0 bg-black/50 backdrop-blur-sm flex items-center justify-center z-50"
    >
      <div class="bg-white dark:bg-gray-800 rounded-2xl p-8 max-w-md w-full shadow-2xl">
        <h2 class="text-xl font-semibold mb-6 text-gray-800 dark:text-gray-200">Create New Novel</h2>
        <form @submit.prevent="createNovel" class="space-y-6">
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300">Title</label>
            <input
              v-model="newNovelTitle"
              type="text"
              required
              class="mt-1 block w-full rounded-lg border-gray-300 dark:border-gray-600 bg-white/50 dark:bg-gray-800/50 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 dark:text-gray-200"
            />
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300">Description</label>
            <textarea
              v-model="newNovelDescription"
              rows="3"
              class="mt-1 block w-full rounded-lg border-gray-300 dark:border-gray-600 bg-white/50 dark:bg-gray-800/50 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 dark:text-gray-200"
            ></textarea>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300">Category</label>
            <select
              v-model="newNovelCategory"
              class="mt-1 block w-full rounded-lg border-gray-300 dark:border-gray-600 bg-white/50 dark:bg-gray-800/50 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 dark:text-gray-200"
            >
              <option value="fantasy">Fantasy</option>
              <option value="romance">Romance</option>
              <option value="scifi">Science Fiction</option>
              <option value="mystery">Mystery</option>
            </select>
          </div>
          <div class="flex justify-end space-x-4">
            <button
              type="button"
              @click="showNewNovelDialog = false"
              class="px-6 py-2 border border-gray-300 dark:border-gray-600 rounded-lg text-gray-700 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-700 transition-colors duration-300"
            >
              Cancel
            </button>
            <button
              type="submit"
              class="px-6 py-2 bg-gradient-to-r from-indigo-500 to-purple-600 text-white rounded-lg font-semibold hover:from-indigo-600 hover:to-purple-700 transition-all duration-300 shadow-md"
            >
              Create
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { Novel } from '@/types/novel'

interface Chapter {
  id: string
  title: string
  content: string
  number: number
  status: 'draft' | 'published'
}

// Novel list state
const novels = ref<(Novel & { chapters: Chapter[] })[]>([
  {
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
    chapters: [],
  },
])

// Editor state
const selectedNovel = ref<(Novel & { chapters: Chapter[] }) | null>(null)
const chapterTitle = ref('')
const chapterContent = ref('')
const chapterNumber = ref(1)
const chapterStatus = ref<'draft' | 'published'>('draft')

// New novel dialog state
const showNewNovelDialog = ref(false)
const newNovelTitle = ref('')
const newNovelDescription = ref('')
const newNovelCategory = ref('fantasy')

// Methods
const selectNovel = (novel: Novel & { chapters: Chapter[] }) => {
  selectedNovel.value = novel
}

const saveChapter = async () => {
  if (!selectedNovel.value) return

  // TODO: Implement actual save logic
  console.log('Saving chapter:', {
    title: chapterTitle.value,
    content: chapterContent.value,
    number: chapterNumber.value,
    status: chapterStatus.value,
  })
}

const publishChapter = async () => {
  if (!selectedNovel.value) return

  // TODO: Implement actual publish logic
  chapterStatus.value = 'published'
  await saveChapter()
}

const createNovel = async () => {
  // TODO: Implement actual novel creation logic
  const newNovel: Novel & { chapters: Chapter[] } = {
    id: String(novels.value.length + 1),
    title: newNovelTitle.value,
    author: 'Current User', // Replace with actual user
    cover: '/images/default-cover.jpg',
    description: newNovelDescription.value,
    tags: [],
    category: newNovelCategory.value,
    status: 'ongoing',
    createdAt: new Date(),
    updatedAt: new Date(),
    chapters: [],
  }

  novels.value.push(newNovel)
  showNewNovelDialog.value = false
  newNovelTitle.value = ''
  newNovelDescription.value = ''
  newNovelCategory.value = 'fantasy'
}
</script>
