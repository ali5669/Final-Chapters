<template>
  <div :class="['chapter-management', themeStore.isDarkMode ? 'text-gray-100' : 'text-gray-800']">
    <!-- Novel Info Section -->
    <div
      :class="[
        'novel-info p-6 rounded-lg shadow-md transition-colors duration-300',
        themeStore.isDarkMode ? 'bg-slate-800/50' : 'bg-white',
      ]"
    >
      <img :src="novel.coverImage" alt="Cover" class="cover-image" />
      <div class="info-details">
        <h2 :class="[themeStore.isDarkMode ? 'text-gray-100' : 'text-gray-800']">
          {{ novel.title }}
        </h2>
        <p :class="[themeStore.isDarkMode ? 'text-gray-300' : 'text-gray-600']">
          <strong :class="[themeStore.isDarkMode ? 'text-gray-200' : 'text-gray-700']"
            >简介:</strong
          >
          {{ novel.summary }}
        </p>
        <p :class="[themeStore.isDarkMode ? 'text-gray-300' : 'text-gray-600']">
          <strong :class="[themeStore.isDarkMode ? 'text-gray-200' : 'text-gray-700']"
            >类别:</strong
          >
          {{ novel.category }}
        </p>
        <!-- <p><strong>标签:</strong> {{ novel.tags.join(', ') }}</p> -->
      </div>
    </div>

    <!-- Chapter List Section -->
    <div
      :class="[
        'chapter-list mt-8 p-6 rounded-lg shadow-md transition-colors duration-300',
        themeStore.isDarkMode ? 'bg-slate-800/50' : 'bg-white',
      ]"
    >
      <h3
        :class="[
          themeStore.isDarkMode ? 'text-gray-100' : 'text-gray-800',
          'text-xl font-bold mb-4',
        ]"
      >
        章节列表
      </h3>
      <ul>
        <li
          v-for="chapter in chapters"
          :key="chapter.chapterId"
          :class="[
            'chapter-item p-4 transition-colors duration-300',
            themeStore.isDarkMode
              ? 'border-b border-slate-700/50 hover:bg-slate-700/30'
              : 'border-b border-gray-200 hover:bg-gray-50',
          ]"
        >
          <span :class="[themeStore.isDarkMode ? 'text-gray-300' : 'text-gray-600']">{{
            chapter.order
          }}</span>
          <span :class="[themeStore.isDarkMode ? 'text-gray-100' : 'text-gray-800']">{{
            chapter.title
          }}</span>
          <!-- <button @click="deleteChapter(chapter.id)">删除</button> -->
        </li>
      </ul>
    </div>

    <!-- Add Chapter Button -->
    <div class="add-chapter mt-6">
      <button
        @click="goToAddChapter"
        :class="[
          'px-6 py-2 rounded-lg font-semibold transition-colors duration-300',
          themeStore.isDarkMode
            ? 'bg-indigo-600 hover:bg-indigo-700 text-white'
            : 'bg-indigo-500 hover:bg-indigo-600 text-white',
        ]"
      >
        添加章节
      </button>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { $chapter } from '@/composables/useApi/useApiChapter'
import { $novel } from '@/composables/useApi/useApiNovel'
import { useThemeStore } from '@/stores/theme'
import axios from 'axios'

const route = useRoute()
const router = useRouter()
const themeStore = useThemeStore()

// 存储小说信息和章节列表
const novel = ref({})
const chapters = ref([])

// 获取小说ID
const novelId = route.params.novelId

// 查询小说信息
const fetchNovelData = async () => {
  const { data: novelData } = await $novel.getNovelById({ data: { novelId: novelId } })
  novel.value = novelData
}

const fetchChapters = async () => {
  const { data: chapterList } = await $chapter.getChapters({ data: { novelId: novelId } })
  chapters.value = chapterList
  // chapters.value = [
  //   { id: 1, title: '章节1', content: '章节内容1' },
  //   { id: 2, title: '章节2', content: '章节内容2' },
  // ];
}

// 删除章节
const deleteChapter = async (chapterId) => {
  if (confirm('确定要删除该章节吗？')) {
    try {
      await axios.delete(`/api/chapters/${chapterId}`)
      // 刷新章节列表
      await fetchChapters()
    } catch (error) {
      console.error('删除章节失败:', error)
    }
  }
}

// 跳转到添加章节页面
const goToAddChapter = () => {
  router.push({ path: `/author/addChapter/${novelId}` })
}

// 页面加载时获取数据
onMounted(async () => {
  await Promise.all([fetchNovelData(), fetchChapters()])
})
</script>

<style scoped>
.chapter-management {
  padding: 20px;
}

.novel-info {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.cover-image {
  width: 150px;
  height: 200px;
  object-fit: cover;
  margin-right: 20px;
  border-radius: 8px;
}

.info-details {
  flex: 1;
}

.chapter-list ul {
  list-style-type: none;
  padding: 0;
}

.chapter-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 1rem;
}

.add-chapter {
  margin-top: 20px;
}
</style>
