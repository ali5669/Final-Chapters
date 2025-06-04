<script setup lang="ts">
import { useAsyncData } from '@/composables/useApi/useApi'
import { $comment } from '@/composables/useApi/useApiComment'

const props = defineProps<{
  commentId: string
}>()

const { data: comment } = useAsyncData(() => $comment.getOne(props.commentId))
</script>

<template>
  <div class="bg-white rounded-lg p-4 flex items-start space-x-4">
    <!-- 用户头像 -->
    <img
      src="https://img.qinxue365.com/uploads/allimg/2009/81-200910140221364.png"
      alt="User Avatar"
      class="w-12 h-12 rounded-full"
    />
    <!-- 评论内容 -->
    <div class="flex-1">
      <!-- 用户名和时间戳 -->
      <div class="flex justify-between items-center mb-2">
        <h2 class="text-lg font-semibold text-gray-800">用户名</h2>
        <span class="text-sm text-gray-500">2 hours ago</span>
      </div>
      <!-- 评论文本框 -->
      <div class="w-full h-24 border-2 border-gray rounded-lg p-2">
        这是用户的评论内容。可以包含多行文本，以展示用户的详细评论。
      </div>
    </div>
  </div>
</template>
