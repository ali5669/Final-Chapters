<template>
  <div class="space-y-8">
    <!-- Search and Filters -->
    <div class="bg-white p-6 rounded-lg shadow-sm">
      <div class="flex flex-col md:flex-row gap-4">
        <div class="flex-1">
          <input
            v-model="searchQuery"
            type="text"
            placeholder="搜索小说..."
            @input="handleSearch"
            class="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-indigo-500 focus:border-indigo-500"
          />
        </div>
        <div class="flex gap-4">
          <select
            v-model="selectedCategory"
            @change="handleCategoryChange"
            class="px-4 py-2 border border-gray-300 rounded-md focus:ring-indigo-500 focus:border-indigo-500"
          >
            <option value="">全部分类</option>
            <option v-for="category in categories" :key="category" :value="category">
              {{ category }}
            </option>
          </select>
          <select
            v-model="sortBy"
            class="px-4 py-2 border border-gray-300 rounded-md focus:ring-indigo-500 focus:border-indigo-500"
          >
            <option value="latest">最新发布</option>
            <option value="popular">最受欢迎</option>
            <option value="rating">评分最高</option>
          </select>
          <button
            @click="showHistoryModal = true"
            class="px-4 py-2 bg-indigo-100 text-indigo-700 rounded-md hover:bg-indigo-200"
          >
            我的收藏
          </button>
        </div>
      </div>
    </div>

    <!-- History Modal -->
    <div
      v-if="showHistoryModal"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
    >
      <div class="bg-white rounded-lg p-6 max-w-2xl w-full mx-4">
        <div class="flex justify-between items-center mb-4">
          <h2 class="text-xl font-bold">我的收藏历史</h2>
          <button @click="showHistoryModal = false" class="text-gray-500 hover:text-gray-700">
            <span class="text-2xl">&times;</span>
          </button>
        </div>

        <div v-if="historyLoading" class="flex justify-center py-8">
          <div
            class="animate-spin rounded-full h-8 w-8 border-4 border-indigo-500 border-t-transparent"
          ></div>
        </div>

        <div v-else-if="histories.length === 0" class="text-center py-8 text-gray-500">
          暂无收藏记录
        </div>

        <div v-else class="space-y-4">
          <div
            v-for="history in histories"
            :key="history.historyId"
            class="flex items-center justify-between p-4 bg-gray-50 rounded-md"
          >
            <div class="flex-1">
              <router-link
                :to="`/novel/${history.novelId}`"
                class="text-lg font-medium text-indigo-600 hover:text-indigo-700"
              >
                {{ getNovelTitle(history.novelId) }}
              </router-link>
              <p class="text-sm text-gray-500">最近阅读：{{ formatDate(history.lastReadTime) }}</p>
            </div>
            <div class="flex items-center space-x-2">
              <button
                @click="deleteHistory(history.historyId)"
                class="text-red-600 hover:text-red-700 p-2"
              >
                删除
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Loading State -->
    <div v-if="loading" class="flex justify-center py-8">
      <div
        class="animate-spin rounded-full h-12 w-12 border-4 border-indigo-500 border-t-transparent"
      ></div>
    </div>

    <!-- Error State -->
    <div v-else-if="error" class="text-center py-8 text-red-500">加载失败，请稍后重试</div>

    <!-- Novel Grid -->
    <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
      <div
        v-for="novel in displayedNovels"
        :key="novel.id"
        class="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow"
      >
        <img
          :src="novel.coverImage || '/images/default-cover.jpg'"
          :alt="novel.title"
          class="w-full h-48 object-cover"
        />
        <div class="p-4">
          <h3 class="font-semibold text-lg mb-2">{{ novel.title }}</h3>
          <p class="text-gray-500 text-sm mb-4 line-clamp-2">{{ novel.summary }}</p>
          <div class="flex items-center justify-between">
            <div class="flex flex-wrap gap-2">
              <span
                v-if="novel.category"
                class="px-2 py-1 bg-indigo-100 text-xs rounded-full text-indigo-700"
              >
                {{ novel.category }}
              </span>
              <span
                v-for="tag in getTags(novel.tags)"
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
              阅读更多 →
            </router-link>
          </div>
        </div>
      </div>
    </div>

    <!-- Empty State -->
    <div
      v-if="!loading && !error && displayedNovels.length === 0"
      class="text-center py-8 text-gray-500"
    >
      没有找到相关小说
    </div>

    <!-- Pagination -->
    <div v-if="totalPages > 1" class="flex justify-center space-x-2">
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
import { ref, computed, onMounted, watch } from 'vue'
import { $novel, $history } from '../../composables/useApi/useContent'
import type { Novel, BrowsingHistory } from '../../composables/useApi/useContent'
import { useAsyncData } from '../../composables/useApi/useApi'

const searchQuery = ref('')
const selectedCategory = ref('')
const sortBy = ref('latest')
const currentPage = ref(1)
const pageSize = 12

// 预设分类列表
const categories = ref(['奇幻', '科幻', '言情', '悬疑', '冒险', '恐怖', '历史', '现代'])

// 获取小说列表
const { data: novelData, loading, error, refresh } = useAsyncData(() => $novel.getAll())

// 从 API 响应中提取小说数据
const novels = ref<Novel[]>([])
watch(
  novelData,
  (newData) => {
    if (newData?.success) {
      novels.value = newData.data
    }
  },
  { immediate: true },
)

// 处理搜索
const handleSearch = async () => {
  if (searchQuery.value.trim()) {
    const result = await $novel.searchByTitle(searchQuery.value)
    if (result.success) {
      novels.value = result.data
    }
  } else {
    refresh()
  }
}

// 处理分类筛选
const handleCategoryChange = async () => {
  if (selectedCategory.value) {
    const result = await $novel.searchByCategory(selectedCategory.value)
    if (result.success) {
      novels.value = result.data
    }
  } else {
    refresh()
  }
}

// 处理标签字符串
const getTags = (tags: string | null): string[] => {
  if (!tags) return []
  return tags.split(',').map((tag) => tag.trim())
}

// 计算属性：过滤和排序后的小说列表
const filteredNovels = computed(() => {
  const result = [...novels.value]

  // 排序
  switch (sortBy.value) {
    case 'latest':
      result.sort((a, b) => new Date(b.updatedAt).getTime() - new Date(a.updatedAt).getTime())
      break
    case 'popular':
      // 暂时保持原顺序，后续可以根据阅读量等数据排序
      break
    case 'rating':
      // 暂时保持原顺序，后续可以根据评分数据排序
      break
  }

  return result
})

// 分页显示
const displayedNovels = computed(() => {
  const start = (currentPage.value - 1) * pageSize
  const end = start + pageSize
  return filteredNovels.value.slice(start, end)
})

const totalPages = computed(() => Math.ceil(filteredNovels.value.length / pageSize))

// 监听分页变化，滚动到顶部
watch(currentPage, () => {
  window.scrollTo({ top: 0, behavior: 'smooth' })
})

// 初始化加载
onMounted(async () => {
  await refresh()
})

// 历史记录相关
const showHistoryModal = ref(false)
const historyLoading = ref(false)
const histories = ref<BrowsingHistory[]>([])
const novelTitles = ref<Record<number, string>>({})

// 加载历史记录
const loadHistories = async () => {
  historyLoading.value = true
  try {
    const result = await $history.getUserHistory()
    if (result.success) {
      histories.value = result.data
      // 获取所有小说的标题
      await Promise.all(
        histories.value.map(async (history) => {
          const novelResult = await $novel.getOne(history.novelId.toString())
          if (novelResult.success) {
            novelTitles.value[history.novelId] = novelResult.data.title
          }
        }),
      )
    }
  } catch (err) {
    console.error('Failed to load histories:', err)
  } finally {
    historyLoading.value = false
  }
}

// 删除历史记录
const deleteHistory = async (historyId: number) => {
  try {
    const result = await $history.deleteHistory(historyId.toString())
    if (result.success) {
      histories.value = histories.value.filter((h) => h.historyId !== historyId)
    }
  } catch (err) {
    console.error('Failed to delete history:', err)
  }
}

// 获取小说标题
const getNovelTitle = (novelId: number) => {
  return novelTitles.value[novelId] || '未知小说'
}

// 格式化日期
const formatDate = (dateString: string) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
  })
}

// 监听模态框打开
watch(showHistoryModal, (newValue) => {
  if (newValue) {
    loadHistories()
  }
})
</script>
