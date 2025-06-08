<script setup lang="ts">
import { ApiResult } from '@/composables/useApi/useApi'
import { $comment } from '@/composables/useApi/useApiComment'
import { useThemeStore } from '@/stores/theme'
import { ref } from 'vue'

const props = defineProps<{ novelId: string; chapterId?: string }>()
const themeStore = useThemeStore()
const comment = ref('')

const onClickButton = () => {
  console.log(comment.value)
  try {
    $comment
      .addComment({
        data: {
          novelId: props.novelId,
          chapterId: props.chapterId,
          content: comment.value,
        },
      })
      .then((res: ApiResult<null>) => {
        if (res.success) {
          alert('发表成功')
          location.reload()
        } else {
          alert(res.errorMsg)
        }
      })
  } catch (err) {
    console.error('发送失败！', err)
  }
}
</script>
<template>
  <div
    :class="['max-w-2xl mx-auto p-4', themeStore.isDarkMode ? 'text-gray-100' : 'text-gray-800']"
  >
    <div class="mb-4">
      <h3
        :class="[
          'text-lg font-semibold',
          themeStore.isDarkMode ? 'text-gray-100' : 'text-gray-800',
        ]"
      >
        发表评论
      </h3>
    </div>
    <div class="space-y-4">
      <textarea
        placeholder="请输入评论内容"
        v-model="comment"
        :class="[
          'w-full px-4 py-2 rounded-lg border focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-colors duration-300',
          themeStore.isDarkMode
            ? 'bg-slate-800/50 border-slate-700/50 text-gray-100 placeholder-gray-400'
            : 'bg-white border-gray-300 text-gray-800 placeholder-gray-500',
        ]"
        rows="4"
      ></textarea>
      <button
        v-on:click="onClickButton"
        :class="[
          'px-6 py-2 font-medium text-sm leading-tight uppercase rounded-lg shadow-md transition-all duration-300',
          themeStore.isDarkMode
            ? 'bg-indigo-600 hover:bg-indigo-700 text-white'
            : 'bg-blue-600 hover:bg-blue-700 text-white',
        ]"
      >
        发送
      </button>
    </div>
  </div>
</template>
