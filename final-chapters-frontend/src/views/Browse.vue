<!-- src/views/Browse.vue -->
<template>
  <div class="space-y-8">
    <!-- Search and Filters -->
    <div class="bg-white p-6 rounded-lg shadow-sm">
      <div class="flex flex-col md:flex-row gap-4">
        <div class="flex-1">
          <input
            v-model="searchQuery"
            type="text"
            placeholder="Search novels..."
            class="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-indigo-500 focus:border-indigo-500"
          />
        </div>
        <div class="flex gap-4">
          <select
            v-model="selectedCategory"
            class="px-4 py-2 border border-gray-300 rounded-md focus:ring-indigo-500 focus:border-indigo-500"
          >
            <option value="">All Categories</option>
            <option v-for="category in categories" :key="category.id" :value="category.id">
              {{ category.name }}
            </option>
          </select>
          <select
            v-model="sortBy"
            class="px-4 py-2 border border-gray-300 rounded-md focus:ring-indigo-500 focus:border-indigo-500"
          >
            <option value="latest">Latest</option>
            <option value="popular">Most Popular</option>
            <option value="rating">Highest Rated</option>
          </select>
        </div>
      </div>
    </div>

    <!-- Novel Grid -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
      <div
        v-for="novel in filteredNovels"
        :key="novel.id"
        class="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow"
      >
        <img :src="novel.cover" :alt="novel.title" class="w-full h-48 object-cover" />
        <div class="p-4">
          <h3 class="font-semibold text-lg mb-2">{{ novel.title }}</h3>
          <p class="text-gray-600 text-sm mb-2">{{ novel.author }}</p>
          <p class="text-gray-500 text-sm mb-4 line-clamp-2">{{ novel.description }}</p>
          <div class="flex items-center justify-between">
            <div class="flex space-x-2">
              <span
                v-for="tag in novel.tags.slice(0, 2)"
                :key="tag"
                class="px-2 py-1 bg-gray-100 text-xs rounded-full text-gray-600"
              >
                {{ tag }}
              </span>
            </div>
            <router-link
              :to="'/novel/' + novel.id"
              class="text-indigo-600 font-medium hover:text-indigo-700"
            >
              Read More →
            </router-link>
          </div>
        </div>
      </div>
    </div>

    <!-- Pagination -->
    <div class="flex justify-center space-x-2">
      <button
        v-for="page in totalPages"
        :key="page"
        @click="currentPage = page"
        class="px-4 py-2 border rounded-md"
        :class="{
          'bg-indigo-600 text-white': currentPage === page,
          'hover:bg-gray-50': currentPage !== page,
        }"
      >
        {{ page }}
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import type { Novel } from '@/types/novel'

const searchQuery = ref('')
const selectedCategory = ref('')
const sortBy = ref('latest')
const currentPage = ref(1)

// Mock categories data
const categories = ref([
  { id: 'fantasy', name: 'Fantasy' },
  { id: 'romance', name: 'Romance' },
  { id: 'scifi', name: 'Science Fiction' },
  { id: 'mystery', name: 'Mystery' },
])

// Mock novels data
const novels = ref<Novel[]>([
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
  },
  // Add more mock novels...
])

// Computed properties
const filteredNovels = computed(() => {
  let result = novels.value

  if (searchQuery.value) {
    result = result.filter(
      (novel) =>
        novel.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
        novel.author.toLowerCase().includes(searchQuery.value.toLowerCase()),
    )
  }

  if (selectedCategory.value) {
    result = result.filter((novel) => novel.category === selectedCategory.value)
  }

  // Sort novels
  switch (sortBy.value) {
    case 'popular':
      // Add sorting logic
      break
    case 'rating':
      // Add sorting logic
      break
    default:
      result = result.sort((a, b) => b.createdAt.getTime() - a.createdAt.getTime())
  }

  return result
})

const totalPages = computed(() => Math.ceil(filteredNovels.value.length / 12))
</script>
