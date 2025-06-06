<!-- src/views/NovelReader.vue -->
<template>
  <div class="min-h-screen bg-gray-50" :class="{ dark: isDarkMode }">
    <!-- Loading State -->
    <div v-if="loading" class="flex justify-center py-20">
      <div class="animate-spin rounded-full h-12 w-12 border-4 border-indigo-500 border-t-transparent"></div>
    </div>

    <!-- Error State -->
    <div v-else-if="error" class="text-center py-20 text-red-500">
      加载失败，请稍后重试
    </div>

    <template v-else>
      <!-- Reader Header -->
      <header class="fixed top-0 left-0 right-0 bg-white dark:bg-gray-800 shadow-sm z-10">
        <div class="container mx-auto px-4 py-2">
          <div class="flex items-center justify-between">
            <div class="flex items-center space-x-4">
              <router-link :to="`/novel/${novelId}`" class="text-gray-600 dark:text-gray-300 hover:text-indigo-600">
                <i class="fas fa-arrow-left"></i>
              </router-link>
              <h1 class="text-lg font-medium text-gray-900 dark:text-white">{{ novel?.title }}</h1>
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
          <h2 class="text-2xl font-bold mb-8">第 {{ chapter?.order }} 章：{{ chapter?.title }}</h2>
          <div v-html="chapter?.content"></div>
        </div>

        <!-- Chapter Navigation -->
        <div class="max-w-3xl mx-auto mt-8 flex justify-between">
          <button
            v-if="hasPreviousChapter"
            @click="navigateChapter('prev')"
            class="px-4 py-2 text-indigo-600 hover:text-indigo-700"
          >
            ← 上一章
          </button>
          <button
            v-if="hasNextChapter"
            @click="navigateChapter('next')"
            class="px-4 py-2 text-indigo-600 hover:text-indigo-700 ml-auto"
          >
            下一章 →
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
          <h3 class="text-lg font-semibold mb-4 text-gray-900 dark:text-white">阅读设置</h3>
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300">主题</label>
              <div class="mt-2 flex space-x-4">
                <button
                  @click="isDarkMode = false"
                  class="px-4 py-2 rounded-md"
                  :class="{
                    'bg-indigo-600 text-white': !isDarkMode,
                    'bg-gray-200 text-gray-700': isDarkMode,
                  }"
                >
                  浅色
                </button>
                <button
                  @click="isDarkMode = true"
                  class="px-4 py-2 rounded-md"
                  :class="{
                    'bg-indigo-600 text-white': isDarkMode,
                    'bg-gray-200 text-gray-700': !isDarkMode,
                  }"
                >
                  深色
                </button>
              </div>
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300">
                字体大小 ({{ fontSize }}px)
              </label>
              <input v-model="fontSize" type="range" min="12" max="24" class="mt-2 w-full" />
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300">字体</label>
              <select
                v-model="fontFamily"
                class="mt-2 block w-full rounded-md border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white"
              >
                <option value="serif">宋体</option>
                <option value="sans-serif">黑体</option>
                <option value="monospace">等宽</option>
              </select>
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300">
                行高 ({{ lineHeight }})
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
    </template>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { $novel, $chapter } from '../../composables/useApi/useContent'
import type { Novel, Chapter } from '../../composables/useApi/useContent'
import { useAsyncData } from '../../composables/useApi/useApi'

const route = useRoute()
const router = useRouter()

const novelId = route.params.novelId as string
const chapterId = ref(route.params.chapterId as string)

// 阅读设置
const isDarkMode = ref(false)
const fontSize = ref(18)
const fontFamily = ref('serif')
const lineHeight = ref(1.8)
const showSettings = ref(false)

// 小说和章节数据
const { data: novelData, loading: novelLoading } = useAsyncData(() => $novel.getOne(novelId))
const { data: chapterData, loading: chapterLoading, error, refresh: refreshChapter } = useAsyncData(
  () => $chapter.getOne(chapterId.value)
)

const novel = ref<Novel | null>(null)
const chapter = ref<Chapter | null>(null)
const chapterCount = ref(0)

const loading = computed(() => novelLoading.value || chapterLoading.value)

// 监听路由参数变化
watch(
  () => route.params.chapterId,
  async (newChapterId) => {
    if (newChapterId) {
      chapterId.value = newChapterId as string
      await refreshChapter()
    }
  }
)

// 监听数据变化
watch(novelData, (newData) => {
  if (newData?.success) {
    novel.value = newData.data
  }
}, { immediate: true })

watch(chapterData, (newData) => {
  if (newData?.success) {
    chapter.value = newData.data
    // 滚动到页面顶部
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }
}, { immediate: true })

// 导航状态
const hasPreviousChapter = computed(() => chapter.value && chapter.value.order > 1)
const hasNextChapter = computed(() => chapter.value && chapter.value.order < chapterCount.value)

// 方法
const toggleSettings = () => {
  showSettings.value = !showSettings.value
}

const navigateChapter = async (direction: 'prev' | 'next') => {
  if (!chapter.value) return

  const targetOrder = direction === 'next' ? chapter.value.order + 1 : chapter.value.order - 1
  const result = await $chapter.getChapterByOrder(novelId, targetOrder)
  
  if (result.success && result.data) {
    router.push(`/read/${novelId}/${result.data.chapterId}`)
  }
}


// 初始化
onMounted(async () => {
  // 获取章节总数
  const countResult = await $chapter.getChapterCount(novelId)
  if (countResult.success) {
    chapterCount.value = countResult.data
  }

  // 保存阅读进度
  // TODO: 实现阅读进度保存功能
})
</script>
