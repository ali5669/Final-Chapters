<!-- src/views/dashboard/Write.vue -->
<template>
  <div class="space-y-8">
    <div class="flex justify-between items-center">
      <h1 class="text-2xl font-bold">Write Your Story</h1>
      <div class="flex space-x-4">
        <button
          @click="saveChapter"
          class="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700"
        >
          Save Chapter
        </button>
        <button
          @click="publishChapter"
          class="px-4 py-2 border border-indigo-600 text-indigo-600 rounded-md hover:bg-indigo-50"
        >
          Publish
        </button>
      </div>
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-4 gap-8">
      <!-- Novel List -->
      <div class="lg:col-span-1 bg-white rounded-lg shadow-sm p-4">
        <div class="flex justify-between items-center mb-4">
          <h2 class="text-lg font-semibold">My Novels</h2>
          <button @click="showNewNovelDialog = true" class="text-indigo-600 hover:text-indigo-700">
            <i class="fas fa-plus"></i>
          </button>
        </div>
        <div class="space-y-2">
          <div
            v-for="novel in novels"
            :key="novel.id"
            class="p-2 hover:bg-gray-50 rounded cursor-pointer"
            :class="{ 'bg-gray-50': selectedNovel?.id === novel.id }"
            @click="selectNovel(novel)"
          >
            <h3 class="font-medium">{{ novel.title }}</h3>
            <p class="text-sm text-gray-500">{{ novel.chapters.length }} chapters</p>
          </div>
        </div>
      </div>

      <!-- Editor Section -->
      <div class="lg:col-span-3 space-y-4">
        <div class="bg-white rounded-lg shadow-sm p-6">
          <input
            v-model="chapterTitle"
            type="text"
            placeholder="Chapter Title"
            class="w-full text-xl font-semibold mb-4 border-0 focus:ring-0"
          />
          <div class="prose max-w-none">
            <textarea
              v-model="chapterContent"
              rows="20"
              placeholder="Write your story here..."
              class="w-full border-0 focus:ring-0 resize-none"
            ></textarea>
          </div>
        </div>

        <!-- Chapter Settings -->
        <div class="bg-white rounded-lg shadow-sm p-6">
          <h3 class="text-lg font-semibold mb-4">Chapter Settings</h3>
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700">Chapter Number</label>
              <input
                v-model="chapterNumber"
                type="number"
                min="1"
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
              />
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700">Status</label>
              <select
                v-model="chapterStatus"
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
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
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center"
    >
      <div class="bg-white rounded-lg p-6 max-w-md w-full">
        <h2 class="text-xl font-semibold mb-4">Create New Novel</h2>
        <form @submit.prevent="createNovel" class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700">Title</label>
            <input
              v-model="newNovelTitle"
              type="text"
              required
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
            />
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700">Description</label>
            <textarea
              v-model="newNovelDescription"
              rows="3"
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
            ></textarea>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700">Category</label>
            <select
              v-model="newNovelCategory"
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
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
              class="px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50"
            >
              Cancel
            </button>
            <button
              type="submit"
              class="px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700"
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
  const newNovel = {
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
