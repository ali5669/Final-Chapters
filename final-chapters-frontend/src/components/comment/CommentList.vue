<script setup lang="ts">
import { useAsyncData } from '@/composables/useApi/useApi'
import { $comment } from '@/composables/useApi/useApiComment'
import CommentCard from './CommentCard.vue'
import { watch } from 'vue'

const props = defineProps<{ novelId: string; chapterId?: string }>()

const {
  data: commentList,
  loading,
  error,
  refresh: refreshComments,
} = useAsyncData(() =>
  props.chapterId
    ? $comment.listByChapterId(props.novelId, props.chapterId)
    : $comment.listByNovelId(props.novelId),
)

// 监听 chapterId 变化，重新获取评论列表
watch(
  () => props.chapterId,
  () => {
    refreshComments()
  },
)
</script>

<template>
  <div class="p-1">
    <div v-if="loading">正在加载评论...</div>
    <div v-else-if="error">
      {{ error }}
    </div>
    <!-- 显示评论列表 -->
    <div v-else class="flex flex-col gap-2">
      <div v-for="comment in commentList.data" :key="comment.id">
        <CommentCard :commentId="comment.commentId"></CommentCard>
      </div>
    </div>
  </div>
</template>
