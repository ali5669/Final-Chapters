<script setup lang="ts">
import { useAsyncData } from '@/composables/useApi/useApi'
import { $comment } from '@/composables/useApi/useApiComment'

const props = defineProps<{ novelId: string }>()

const { data: comment, loading, error } = useAsyncData(() => $comment.listByNovelId(props.novelId))
</script>
<template>
  <div class="p-1">
    <div v-if="loading">正在加载评论...</div>

    <div v-else-if="error">
      {{ error.message }}
    </div>

    <!-- 显示评论列表 -->
    <div v-else class="flex flex-col gap-2">
      <comment-card
        v-for="item in comment?.data"
        :key="item.commentId"
        :commentId="item.commentId"
      />
    </div>
  </div>
</template>
