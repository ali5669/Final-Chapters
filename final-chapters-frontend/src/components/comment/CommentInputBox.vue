<script setup lang="ts">
import { ApiResult } from '@/composables/useApi/useApi'
import { $comment } from '@/composables/useApi/useApiComment'
import { ref } from 'vue'

const props = defineProps<{ novelId: string; chapterId?: string }>()

const comment = ref('')

const onClickButton = () => {
  console.log(comment.value)
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
      } else {
        alert(res.errorMsg)
      }
    })
}
</script>
<template>
  <div class="max-w-2xl mx-auto p-4">
    <div class="mb-4">
      <h3 class="text-lg font-semibold text-gray-800 dark:text-white">发表评论</h3>
    </div>
    <div class="space-y-4">
      <textarea
        placeholder="请输入评论内容"
        v-model="comment"
        class="w-full px-4 py-2 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent dark:bg-gray-700 dark:border-gray-600 dark:text-white"
        rows="4"
      ></textarea>
      <button
        v-on:click="onClickButton"
        class="px-6 py-2 bg-blue-600 text-white font-medium text-sm leading-tight uppercase rounded-lg shadow-md hover:bg-blue-700 hover:shadow-lg focus:bg-blue-700 focus:shadow-lg focus:outline-none focus:ring-0 transition duration-150 ease-in-out"
      >
        发送
      </button>
    </div>
  </div>
</template>
