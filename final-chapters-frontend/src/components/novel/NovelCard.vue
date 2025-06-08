<template>
  <div
    :class="[
      'rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-all duration-300',
      themeStore.isDarkMode
        ? 'bg-slate-900/90 border border-gray-700/50'
        : 'bg-white border border-gray-200/50',
    ]"
  >
    <img
      :src="novel.coverImage || '/images/default-cover.jpg'"
      :alt="novel.title"
      class="w-full h-48 object-cover"
    />
    <div class="p-4">
      <h3
        :class="[
          'font-semibold text-lg mb-2',
          themeStore.isDarkMode ? 'text-gray-200' : 'text-gray-800',
        ]"
      >
        {{ novel.title }}
      </h3>
      <p
        v-if="novel.summary"
        :class="[
          'text-sm mb-3 line-clamp-2',
          themeStore.isDarkMode ? 'text-gray-400' : 'text-gray-600',
        ]"
      >
        {{ novel.summary }}
      </p>
      <div class="flex flex-wrap items-center gap-2 mb-3">
        <span
          v-if="novel.category"
          :class="[
            'px-2 py-1 text-xs rounded-full',
            themeStore.isDarkMode
              ? 'bg-indigo-900/50 text-indigo-300'
              : 'bg-indigo-100 text-indigo-700',
          ]"
        >
          {{ novel.category }}
        </span>
        <span
          v-for="tag in getTags(novel.tags)"
          :key="tag"
          :class="[
            'px-2 py-1 text-xs rounded-full',
            themeStore.isDarkMode ? 'bg-gray-800/50 text-gray-300' : 'bg-gray-100 text-gray-600',
          ]"
        >
          {{ tag }}
        </span>
      </div>
      <div class="flex items-center justify-between">
        <router-link
          :to="'/novel/' + novel.id"
          :class="[
            'font-medium transition-colors duration-300',
            themeStore.isDarkMode
              ? 'text-indigo-400 hover:text-indigo-300'
              : 'text-indigo-600 hover:text-indigo-700',
          ]"
        >
          Read More →
        </router-link>
        <span :class="['text-xs', themeStore.isDarkMode ? 'text-gray-400' : 'text-gray-500']">
          {{ formatDate(novel.updatedAt) }}
        </span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useThemeStore } from '@/stores/theme'
import type { Novel } from '@/composables/useApi/useContent'

const themeStore = useThemeStore()

defineProps<{
  novel: Novel
}>()

// 格式化日期
const formatDate = (dateString: string) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
  })
}

// 处理标签字符串
const getTags = (tags: string | null): string[] => {
  if (!tags) return []
  return tags.split(',').map((tag) => tag.trim())
}
</script>
