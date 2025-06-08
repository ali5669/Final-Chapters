<template>
  <div class="novel-browse">
    <!-- Search and Filter Section -->
    <div
      :class="[
        'search-filter p-6 rounded-lg shadow-md mb-8 transition-colors duration-300',
        themeStore.isDarkMode ? 'bg-slate-800/50' : 'bg-white',
      ]"
    >
      <div class="search-box mb-4">
        <input
          type="text"
          v-model="searchQuery"
          placeholder="搜索小说..."
          :class="[
            'w-full px-4 py-2 rounded-lg border transition-colors duration-300',
            themeStore.isDarkMode
              ? 'bg-slate-700 border-slate-600 text-gray-100 placeholder-gray-400'
              : 'bg-white border-gray-300 text-gray-800 placeholder-gray-500',
          ]"
        />
      </div>
      <div class="filters flex gap-4">
        <select
          v-model="selectedCategory"
          :class="[
            'px-4 py-2 rounded-lg border transition-colors duration-300',
            themeStore.isDarkMode
              ? 'bg-slate-700 border-slate-600 text-gray-100'
              : 'bg-white border-gray-300 text-gray-800',
          ]"
        >
          <option value="">所有类别</option>
          <option v-for="category in categories" :key="category" :value="category">
            {{ category }}
          </option>
        </select>
        <select
          v-model="sortBy"
          :class="[
            'px-4 py-2 rounded-lg border transition-colors duration-300',
            themeStore.isDarkMode
              ? 'bg-slate-700 border-slate-600 text-gray-100'
              : 'bg-white border-gray-300 text-gray-800',
          ]"
        >
          <option value="latest">最新发布</option>
          <option value="popular">最受欢迎</option>
          <option value="rating">最高评分</option>
        </select>
      </div>
    </div>

    <!-- Novel Grid -->
    <div class="novel-grid grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
      <div
        v-for="novel in filteredNovels"
        :key="novel.id"
        :class="[
          'novel-card p-4 rounded-lg shadow-md transition-all duration-300 cursor-pointer',
          themeStore.isDarkMode
            ? 'bg-slate-800/50 hover:bg-slate-700/50'
            : 'bg-white hover:bg-gray-50',
        ]"
        @click="viewNovel(novel.id)"
      >
        <img
          :src="novel.cover"
          :alt="novel.title"
          class="w-full h-48 object-cover rounded-lg mb-4"
        />
        <h3
          :class="[
            themeStore.isDarkMode ? 'text-gray-100' : 'text-gray-800',
            'text-lg font-semibold mb-2',
          ]"
        >
          {{ novel.title }}
        </h3>
        <p :class="[themeStore.isDarkMode ? 'text-gray-300' : 'text-gray-600', 'text-sm mb-2']">
          {{ novel.summary }}
        </p>
        <div class="novel-meta flex justify-between items-center">
          <span :class="[themeStore.isDarkMode ? 'text-gray-400' : 'text-gray-500', 'text-sm']">
            {{ novel.category }}
          </span>
          <div class="rating flex items-center">
            <span :class="[themeStore.isDarkMode ? 'text-yellow-400' : 'text-yellow-500']">★</span>
            <span :class="[themeStore.isDarkMode ? 'text-gray-300' : 'text-gray-600', 'ml-1']">
              {{ novel.rating }}
            </span>
          </div>
        </div>
      </div>
    </div>

    <!-- Pagination -->
    <div class="pagination mt-8 flex justify-center gap-2">
      <button
        v-for="page in totalPages"
        :key="page"
        @click="currentPage = page"
        :class="[
          'px-4 py-2 rounded-lg transition-colors duration-300',
          currentPage === page
            ? themeStore.isDarkMode
              ? 'bg-indigo-600 text-white'
              : 'bg-indigo-500 text-white'
            : themeStore.isDarkMode
              ? 'bg-slate-700 text-gray-300 hover:bg-slate-600'
              : 'bg-gray-200 text-gray-700 hover:bg-gray-300',
        ]"
      >
        {{ page }}
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useThemeStore } from '@/stores/theme'

const router = useRouter()
const themeStore = useThemeStore()

// State
const searchQuery = ref('')
const selectedCategory = ref('')
const sortBy = ref('latest')
const currentPage = ref(1)
const novels = ref([])

// Categories
const categories = ['玄幻', '武侠', '都市', '科幻', '历史', '游戏', '体育', '军事', '其他']

// Computed
const filteredNovels = computed(() => {
  let filtered = [...novels.value]

  // Apply search filter
  if (searchQuery.value) {
    filtered = filtered.filter(
      (novel) =>
        novel.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
        novel.summary.toLowerCase().includes(searchQuery.value.toLowerCase()),
    )
  }

  // Apply category filter
  if (selectedCategory.value) {
    filtered = filtered.filter((novel) => novel.category === selectedCategory.value)
  }

  // Apply sorting
  switch (sortBy.value) {
    case 'latest':
      filtered.sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime())
      break
    case 'popular':
      filtered.sort((a, b) => b.views - a.views)
      break
    case 'rating':
      filtered.sort((a, b) => b.rating - a.rating)
      break
  }

  return filtered
})

const totalPages = computed(() => Math.ceil(filteredNovels.value.length / 12))

// Methods
const viewNovel = (novelId: string) => {
  router.push(`/novel/${novelId}`)
}

// ... rest of the existing code ...
</script>

<style scoped>
.novel-browse {
  padding: 20px;
}

.novel-card {
  transition: transform 0.2s;
}

.novel-card:hover {
  transform: translateY(-4px);
}
</style>
