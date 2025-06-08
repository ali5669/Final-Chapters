<template>
  <div class="space-y-8">
    <!-- Hero Section -->
    <section class="bg-gradient-to-r from-indigo-500 to-purple-600 text-white py-16 rounded-2xl">
      <div class="max-w-4xl mx-auto text-center px-4">
        <h1 class="text-4xl md:text-5xl font-bold mb-6">Discover Your Next Favorite Story</h1>
        <p class="text-xl mb-8">
          Immerse yourself in a world of endless stories. Read, write, and connect with fellow book
          lovers.
        </p>
        <div class="flex justify-center gap-4">
          <router-link
            to="/browse"
            class="bg-white text-indigo-600 px-6 py-3 rounded-lg font-semibold hover:bg-opacity-90"
          >
            Start Reading
          </router-link>
          <router-link
            to="/auth/register"
            class="border-2 border-white text-white px-6 py-3 rounded-lg font-semibold hover:bg-white hover:text-indigo-600"
          >
            Join Now
          </router-link>
        </div>
      </div>
    </section>

    <!-- Featured Novels -->
    <section>
      <h2
        :class="[
          'text-2xl font-bold mb-6',
          themeStore.isDarkMode ? 'text-gray-200' : 'text-gray-800',
        ]"
      >
        Featured Novels
      </h2>
      <div v-if="loading" class="flex justify-center py-8">
        <div
          class="animate-spin rounded-full h-12 w-12 border-4 border-indigo-500 border-t-transparent"
        ></div>
      </div>
      <div v-else-if="error" class="text-center py-8 text-red-500">
        Failed to load novels. Please try again later.
      </div>
      <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <NovelCard v-for="novel in novelData.data" :key="novel.id" :novel="novel" />
      </div>
    </section>

    <!-- Categories -->
    <section>
      <h2
        :class="[
          'text-2xl font-bold mb-6',
          themeStore.isDarkMode ? 'text-gray-200' : 'text-gray-800',
        ]"
      >
        Browse by Category
      </h2>
      <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
        <button
          v-for="category in categories"
          :key="category"
          @click="handleCategoryClick(category)"
          :class="[
            'p-6 rounded-lg shadow-md text-center hover:shadow-lg transition-all duration-300',
            themeStore.isDarkMode
              ? 'bg-slate-900/90 border border-gray-700/50 text-gray-200 hover:bg-slate-800/90'
              : 'bg-white border border-gray-200/50 text-gray-800 hover:bg-gray-50',
          ]"
        >
          <span class="text-lg font-medium">{{ category }}</span>
        </button>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useThemeStore } from '@/stores/theme'
import { $novel } from '../composables/useApi/useContent'
import type { Novel } from '../composables/useApi/useContent'
import { useAsyncData } from '../composables/useApi/useApi'
import NovelCard from '@/components/novel/NovelCard.vue'

const router = useRouter()
const themeStore = useThemeStore()

// 获取小说列表
const { data: novelData, loading, error } = useAsyncData(() => $novel.getAll())

// 从 API 响应中提取小说数据
const novels = ref<Novel[]>([])

onMounted(async () => {
  if (novelData.value.success) {
    novels.value = novelData.value.data
    console.log(novels.value)
  }
})

// 预设分类列表
const categories = ref(['奇幻', '科幻', '言情', '悬疑', '冒险', '恐怖', '历史', '现代'])

// 分类点击处理
const handleCategoryClick = (category: string) => {
  router.push({
    path: '/browse',
    query: { category },
  })
}
</script>
