<!-- src/views/NovelDetail.vue -->
<template>
  <div class="space-y-8">
    <!-- Loading State -->
    <div v-if="loading" class="flex justify-center py-8">
      <div
        class="animate-spin rounded-full h-12 w-12 border-4 border-indigo-500 border-t-transparent"
      ></div>
    </div>

    <!-- Error State -->
    <div v-else-if="error" class="text-center py-8 text-red-500">加载失败，请稍后重试</div>

    <template v-else>
      <!-- Novel Header -->
      <div class="bg-white rounded-lg shadow-sm p-6">
        <div class="flex flex-col md:flex-row gap-8">
          <div class="w-full md:w-64">
            <img
              :src="novel.coverImage || '/images/default-cover.jpg'"
              :alt="novel.title"
              class="w-full h-80 object-cover rounded-lg"
            />
          </div>
          <div class="flex-1 space-y-4">
            <h1 class="text-3xl font-bold text-gray-900">{{ novel.title }}</h1>
            <div class="flex space-x-2">
              <span
                v-if="novel.category"
                class="px-3 py-1 bg-indigo-100 rounded-full text-sm text-indigo-700"
              >
                {{ novel.category }}
              </span>
              <span
                v-for="tag in getTags(novel.tags)"
                :key="tag"
                class="px-3 py-1 bg-gray-100 rounded-full text-sm text-gray-600"
              >
                {{ tag }}
              </span>
            </div>
            <p class="text-gray-700">{{ novel.summary }}</p>
            <div class="flex items-center space-x-4">
              <button
                @click="startReading"
                class="px-6 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700"
              >
                开始阅读
              </button>
              <button
                @click="toggleBookmark"
                class="px-6 py-2 border border-gray-300 rounded-md hover:bg-gray-50"
                :class="{ 'text-indigo-600 border-indigo-600': isBookmarked }"
              >
                {{ isBookmarked ? '已收藏' : '收藏' }}
              </button>
            </div>
            <div class="text-sm text-gray-500">
              <p>更新时间：{{ formatDate(novel.updatedAt) }}</p>
              <p>创建时间：{{ formatDate(novel.createdAt) }}</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Chapter List -->
      <div class="bg-white rounded-lg shadow-sm p-6">
        <div class="flex justify-between items-center mb-4">
          <h2 class="text-2xl font-bold">章节列表</h2>
          <div class="flex items-center gap-4">
            <span class="text-gray-500">共 {{ chapterCount }} 章</span>
            <select
              v-model="currentPage"
              class="px-3 py-1 border border-gray-300 rounded-md focus:ring-indigo-500 focus:border-indigo-500"
            >
              <option v-for="page in totalPages" :key="page" :value="page">第 {{ page }} 页</option>
            </select>
          </div>
        </div>

        <div v-if="chaptersLoading" class="flex justify-center py-8">
          <div
            class="animate-spin rounded-full h-8 w-8 border-4 border-indigo-500 border-t-transparent"
          ></div>
        </div>

        <div v-else class="space-y-2">
          <div
            v-for="chapter in chapters"
            :key="chapter.chapterId"
            class="flex items-center justify-between p-4 hover:bg-gray-50 rounded-md"
          >
            <div>
              <h3 class="font-medium">第 {{ chapter.order }} 章：{{ chapter.title }}</h3>
            </div>
            <router-link
              :to="`/read/${novel.id}/${chapter.chapterId}`"
              class="text-indigo-600 hover:text-indigo-700"
            >
              阅读 →
            </router-link>
          </div>
        </div>

        <!-- Pagination -->
        <div v-if="totalPages > 1" class="mt-6 flex justify-center space-x-2">
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

      <CommentList :novel-id="novelId"></CommentList>
      <CommentInputBox :novel-id="novelId"></CommentInputBox>
    </template>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { $novel, $chapter, $history } from '../../composables/useApi/useContent'
import type { Novel, Chapter, BrowsingHistory } from '../../composables/useApi/useContent'
import { useAsyncData } from '../../composables/useApi/useApi'
import CommentList from '@/components/comment/CommentList.vue'
import CommentInputBox from '@/components/comment/CommentInputBox.vue'

const route = useRoute()
const router = useRouter()
const novelId = route.params.id as string

// 小说数据
const { data: novelData, loading, error } = useAsyncData(() => $novel.getOne(novelId))
const novel = ref<Novel | null>(null)

watch(
  novelData,
  (newData) => {
    if (newData?.success) {
      novel.value = newData.data
    }
  },
  { immediate: true },
)

// 章节数据
const pageSize = 20
const currentPage = ref(1)
const chapters = ref<Chapter[]>([])
const chapterCount = ref(0)
const chaptersLoading = ref(false)

const totalPages = computed(() => Math.ceil(chapterCount.value / pageSize))

// 加载章节列表
const loadChapters = async () => {
  chaptersLoading.value = true
  try {
    const [chaptersResult, countResult] = await Promise.all([
      $chapter.getChaptersByPage(novelId),
      $chapter.getChapterCount(novelId),
    ])

    console.log('数据' + chaptersResult.data)
    if (chaptersResult.success && countResult.success) {
      chapters.value = chaptersResult.data
      chapterCount.value = countResult.data
    }
  } catch (err) {
    console.error('Failed to load chapters:', err)
  } finally {
    chaptersLoading.value = false
  }
}

// 监听页码变化
watch(currentPage, () => {
  loadChapters()
})

// 收藏功能
const isBookmarked = ref(false)
const checkIfBookmarked = async () => {
  try {
    const result = await $history.getUserHistory()

    if (result.success) {
      isBookmarked.value = result.data.some((h) => h.novelId === Number(novelId))
    }
  } catch (err) {
    console.error('Failed to check bookmark status:', err)
  }
}

const toggleBookmark = async () => {
  try {
    if (isBookmarked.value) {
      // 查找并删除收藏
      const result = await $history.getUserHistory()
      if (result.success) {
        const history = result.data.find((h) => h.novelId === Number(novelId))
        if (history) {
          await $history.deleteHistory(history.historyId.toString())
          isBookmarked.value = false
        }
      }
    } else {
      // 添加收藏
      const result = await $history.addHistory(novelId)
      if (result.success) {
        isBookmarked.value = true
      }
    }
  } catch (err) {
    console.error('Failed to toggle bookmark:', err)
  }
}

// 开始阅读
const startReading = async () => {
  const result = await $chapter.getChapterByOrder(novelId, 1)
  if (result.success && result.data) {
    router.push(`/read/${novelId}/${result.data.chapterId}`)
  }
}

// 处理标签
const getTags = (tags: string | null): string[] => {
  if (!tags) return []
  return tags.split(',').map((tag) => tag.trim())
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

// 初始化
onMounted(() => {
  loadChapters()
  checkIfBookmarked()
})
</script>
