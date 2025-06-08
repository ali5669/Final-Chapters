<!-- src/views/NovelReader.vue -->
<template>
  <div
    :class="[
      'min-h-screen transition-colors duration-300',
      themeStore.isDarkMode
        ? 'bg-gradient-to-br from-slate-900 via-indigo-950 to-purple-950 text-gray-100'
        : 'bg-gradient-to-br from-blue-50 via-indigo-50 to-purple-50 text-gray-800',
    ]"
  >
    <!-- Loading State -->
    <div v-if="loading" class="flex justify-center py-20">
      <div
        class="animate-spin rounded-full h-12 w-12 border-4 border-indigo-500 border-t-transparent"
      ></div>
    </div>

    <!-- Error State -->
    <div v-else-if="error" class="text-center py-20 text-red-500">加载失败，请稍后重试</div>

    <template v-else>
      <!-- Reader Header -->
      <header
        :class="[
          'fixed top-0 left-0 right-0 z-10 backdrop-blur-xl shadow-2xl transition-colors duration-300',
          themeStore.isDarkMode
            ? 'bg-gradient-to-r from-slate-900/95 via-indigo-950/90 to-purple-950/90 text-white'
            : 'bg-gradient-to-r from-indigo-100/95 via-purple-100/95 to-pink-100/95 border-b border-indigo-200/50 shadow-[0_2px_15px_-3px_rgba(0,0,0,0.07)] text-gray-900',
        ]"
      >
        <div class="container mx-auto px-4 py-2">
          <div class="flex items-center justify-between">
            <div class="flex items-center space-x-4">
              <router-link
                :to="`/novel/${novelId}`"
                class="text-gray-600 dark:text-gray-300 hover:text-indigo-600 dark:hover:text-indigo-400 transition-colors duration-300"
              >
                <i class="fas fa-arrow-left"></i>
              </router-link>
              <h1 class="text-lg font-medium">{{ novel?.title }}</h1>
            </div>
            <div class="flex items-center space-x-4">
              <button
                @click="toggleSettings"
                class="text-gray-600 dark:text-gray-300 hover:text-indigo-600 dark:hover:text-indigo-400 transition-colors duration-300"
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
          :class="[
            'max-w-3xl mx-auto prose prose-lg transition-colors duration-300',
            themeStore.isDarkMode ? 'prose-invert' : '',
          ]"
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
            class="px-4 py-2 text-indigo-600 dark:text-indigo-400 hover:text-indigo-700 dark:hover:text-indigo-300 transition-colors duration-300"
          >
            ← 上一章
          </button>
          <button
            v-if="hasNextChapter"
            @click="navigateChapter('next')"
            class="px-4 py-2 text-indigo-600 dark:text-indigo-400 hover:text-indigo-700 dark:hover:text-indigo-300 transition-colors duration-300 ml-auto"
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
        <div
          :class="[
            'rounded-lg p-6 max-w-md w-full transition-colors duration-300',
            themeStore.isDarkMode
              ? 'bg-slate-900/90 border border-gray-700/50'
              : 'bg-white/90 border border-gray-200/50',
          ]"
        >
          <h3 class="text-lg font-semibold mb-4">阅读设置</h3>
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium mb-2">主题</label>
              <div class="mt-2 flex space-x-4">
                <button
                  @click="themeStore.toggleDarkMode"
                  class="px-4 py-2 rounded-md transition-colors duration-300"
                  :class="{
                    'bg-indigo-600 text-white': !themeStore.isDarkMode,
                    'bg-gray-200 text-gray-700 dark:bg-gray-700 dark:text-gray-300':
                      themeStore.isDarkMode,
                  }"
                >
                  浅色
                </button>
                <button
                  @click="themeStore.toggleDarkMode"
                  class="px-4 py-2 rounded-md transition-colors duration-300"
                  :class="{
                    'bg-indigo-600 text-white': themeStore.isDarkMode,
                    'bg-gray-200 text-gray-700 dark:bg-gray-700 dark:text-gray-300':
                      !themeStore.isDarkMode,
                  }"
                >
                  深色
                </button>
              </div>
            </div>

            <div>
              <label class="block text-sm font-medium mb-2"> 字体大小 ({{ fontSize }}px) </label>
              <input
                v-model="fontSize"
                type="range"
                min="12"
                max="24"
                class="mt-2 w-full accent-indigo-600"
              />
            </div>

            <div>
              <label class="block text-sm font-medium mb-2">字体</label>
              <select
                v-model="fontFamily"
                :class="[
                  'mt-2 block w-full rounded-md transition-colors duration-300',
                  themeStore.isDarkMode
                    ? 'bg-gray-800 border-gray-700 text-gray-100'
                    : 'bg-white border-gray-300 text-gray-900',
                ]"
              >
                <option value="serif">宋体</option>
                <option value="sans-serif">黑体</option>
                <option value="monospace">等宽</option>
              </select>
            </div>

            <div>
              <label class="block text-sm font-medium mb-2"> 行高 ({{ lineHeight }}) </label>
              <input
                v-model="lineHeight"
                type="range"
                min="1.5"
                max="2.5"
                step="0.1"
                class="mt-2 w-full accent-indigo-600"
              />
            </div>
          </div>
        </div>
      </div>
    </template>

    <CommentList :novel-id="novelId" :chapter-id="chapterId"></CommentList>
    <CommentInputBox :novel-id="novelId" :chapter-id="chapterId"></CommentInputBox>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { $novel, $chapter } from '../../composables/useApi/useContent'
import type { Novel, Chapter } from '../../composables/useApi/useContent'
import { useAsyncData } from '../../composables/useApi/useApi'
import { useThemeStore } from '@/stores/theme'
import CommentList from '@/components/comment/CommentList.vue'
import CommentInputBox from '@/components/comment/CommentInputBox.vue'

const route = useRoute()
const router = useRouter()
const themeStore = useThemeStore()

const novelId = route.params.novelId as string
const chapterId = ref(route.params.chapterId as string)

// 阅读设置
const fontSize = ref(18)
const fontFamily = ref('serif')
const lineHeight = ref(1.8)
const showSettings = ref(false)

// 小说和章节数据
const { data: novelData, loading: novelLoading } = useAsyncData(() => $novel.getOne(novelId))
const {
  data: chapterData,
  loading: chapterLoading,
  error,
  refresh: refreshChapter,
} = useAsyncData(() => $chapter.getOne(chapterId.value))

const novel = ref<Novel | null>(null)
const chapter = ref<Chapter | null>(null)
const chapterCount = ref(null)

const loading = computed(() => novelLoading.value || chapterLoading.value)

// 监听路由参数变化
watch(
  () => route.params.chapterId,
  async (newChapterId) => {
    if (newChapterId) {
      chapterId.value = newChapterId as string
      await refreshChapter()
    }
  },
)

// 监听数据变化
watch(
  novelData,
  (newData) => {
    if (newData?.success) {
      novel.value = newData.data
    }
  },
  { immediate: true },
)

watch(
  chapterData,
  (newData) => {
    if (newData?.success) {
      chapter.value = newData.data
      // 滚动到页面顶部
      window.scrollTo({ top: 0, behavior: 'smooth' })
    }
  },
  { immediate: true },
)

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
})
</script>
