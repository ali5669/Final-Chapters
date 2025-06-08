<script setup lang="ts">
import { useAsyncData } from '@/composables/useApi/useApi'
import { $comment } from '@/composables/useApi/useApiComment'
import CommentCard from './CommentCard.vue'

const props = defineProps<{ novelId: string }>()

const {
  data: commentList,
  loading,
  error,
} = useAsyncData(() => $comment.listByNovelId(props.novelId))
</script>
<template>
  <div class="p-1">
    <div v-if="loading">正在加载评论...</div>

    <div v-else-if="error">
      {{ error.message }}
    </div>

    <!-- 显示评论列表 -->
    <div v-else class="flex flex-col gap-2">
      <div v-for="item in commentList?.data" :key="item.commentId">
        <CommentCard :commentId="item.commentId"></CommentCard>
      </div>
    </div>
  </div>
</template>
