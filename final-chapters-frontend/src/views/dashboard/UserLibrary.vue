<template>
  <div class="space-y-8">
    <div class="flex justify-between items-center">
      <h1
        class="text-2xl font-bold bg-gradient-to-r from-indigo-500 via-purple-500 to-pink-500 bg-clip-text text-transparent"
      >
        My Library
      </h1>
      <div class="flex space-x-4">
        <input
          type="text"
          v-model="searchQuery"
          placeholder="Search in library..."
          :class="[
            'px-6 py-2 rounded-lg focus:ring-indigo-500 focus:border-indigo-500 transition-colors duration-300',
            themeStore.isDarkMode
              ? 'bg-slate-900/50 border-gray-700/50 text-gray-200 placeholder-gray-500'
              : 'bg-white/50 border-gray-300 text-gray-800 placeholder-gray-400',
          ]"
        />
        <select
          v-model="filterStatus"
          :class="[
            'px-6 py-2 rounded-lg focus:ring-indigo-500 focus:border-indigo-500 transition-colors duration-300',
            themeStore.isDarkMode
              ? 'bg-slate-900/50 border-gray-700/50 text-gray-200'
              : 'bg-white/50 border-gray-300 text-gray-800',
          ]"
        >
          <option value="">All Status</option>
          <option value="reading">Reading</option>
          <option value="completed">Completed</option>
          <option value="plan_to_read">Plan to Read</option>
        </select>
      </div>
    </div>

    <!-- Novel Grid -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
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
          :src="novel.coverImage"
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
          <div class="tags flex items-center">
            <span :class="[themeStore.isDarkMode ? 'text-gray-300' : 'text-gray-600', 'ml-1']">
              {{ novel.tags }}
            </span>
          </div>
        </div>
      </div>
    </div>

    <!-- Reading Statistics -->
    <div
      :class="[
        'rounded-2xl shadow-lg p-8 backdrop-blur-lg',
        themeStore.isDarkMode
          ? 'bg-slate-900/90 border border-gray-700/50'
          : 'bg-white/90 border border-gray-200/50',
      ]"
    >
      <h2
        :class="[
          'text-xl font-semibold mb-6',
          themeStore.isDarkMode ? 'text-gray-200' : 'text-gray-800',
        ]"
      >
        Reading Statistics
      </h2>
      <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div
          :class="[
            'text-center p-4 rounded-xl',
            themeStore.isDarkMode
              ? 'bg-gradient-to-br from-indigo-900/50 to-purple-900/50'
              : 'bg-gradient-to-br from-indigo-50 to-purple-50',
          ]"
        >
          <p
            class="text-3xl font-bold bg-gradient-to-r from-indigo-500 to-purple-600 bg-clip-text text-transparent"
          >
            {{ statistics.totalBooks }}
          </p>
          <p :class="themeStore.isDarkMode ? 'text-gray-400' : 'text-gray-600'">Total Books</p>
        </div>
        <div
          :class="[
            'text-center p-4 rounded-xl',
            themeStore.isDarkMode
              ? 'bg-gradient-to-br from-indigo-900/50 to-purple-900/50'
              : 'bg-gradient-to-br from-indigo-50 to-purple-50',
          ]"
        >
          <p
            class="text-3xl font-bold bg-gradient-to-r from-indigo-500 to-purple-600 bg-clip-text text-transparent"
          >
            {{ statistics.booksCompleted }}
          </p>
          <p :class="themeStore.isDarkMode ? 'text-gray-400' : 'text-gray-600'">Books Completed</p>
        </div>
        <div
          :class="[
            'text-center p-4 rounded-xl',
            themeStore.isDarkMode
              ? 'bg-gradient-to-br from-indigo-900/50 to-purple-900/50'
              : 'bg-gradient-to-br from-indigo-50 to-purple-50',
          ]"
        >
          <p
            class="text-3xl font-bold bg-gradient-to-r from-indigo-500 to-purple-600 bg-clip-text text-transparent"
          >
            {{ statistics.chaptersRead }}
          </p>
          <p :class="themeStore.isDarkMode ? 'text-gray-400' : 'text-gray-600'">Chapters Read</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useThemeStore } from '@/stores/theme'
import { $novel, $history } from '@/composables/useApi/useContent'
import type { Novel, BrowsingHistory } from '@/composables/useApi/useContent'

const router = useRouter()
const themeStore = useThemeStore()

// State
const searchQuery = ref('')
const filterStatus = ref('')
const novels = ref<Novel[]>([])
const loading = ref(false)
const error = ref<string | null>(null)

// Statistics
const statistics = ref({
  totalBooks: 0,
  booksCompleted: 0,
  chaptersRead: 0,
})

// Computed
const filteredNovels = computed(() => {
  let filtered = [...novels.value]

  // Apply search filter
  if (searchQuery.value) {
    filtered = filtered.filter(
      (novel) =>
        novel.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
        novel.summary?.toLowerCase().includes(searchQuery.value.toLowerCase()),
    )
  }

  // Apply status filter
  if (filterStatus.value) {
    filtered = filtered.filter((novel) => novel.tags?.includes(filterStatus.value))
  }

  return filtered
})

// Methods
const viewNovel = (novelId: number) => {
  router.push(`/novel/${novelId}`)
}

const loadNovels = async () => {
  loading.value = true
  error.value = null
  try {
    // Get user's bookmarked novels from history
    const historyResult = await $history.getUserHistory()
    if (!historyResult.success) {
      throw new Error('Failed to load bookmarked novels')
    }

    // Get details for each bookmarked novel
    const bookmarkedNovels = await Promise.all(
      historyResult.data.map(async (history) => {
        const novelResult = await $novel.getOne(history.novelId.toString())
        return novelResult.success ? novelResult.data : null
      }),
    )

    // Filter out any null results and update novels
    novels.value = bookmarkedNovels.filter((novel): novel is Novel => novel !== null)

    // Update statistics
    statistics.value = {
      totalBooks: novels.value.length,
      booksCompleted: novels.value.filter((n) => n.tags?.includes('completed')).length,
      chaptersRead: 0, // This should be calculated based on reading history
    }
  } catch (err) {
    error.value = 'An error occurred while loading novels'
    console.error(err)
  } finally {
    loading.value = false
  }
}

// Initialize
onMounted(() => {
  loadNovels()
})
</script>

<style scoped>
.novel-card {
  transition: transform 0.2s;
}

.novel-card:hover {
  transform: translateY(-4px);
}
</style>
