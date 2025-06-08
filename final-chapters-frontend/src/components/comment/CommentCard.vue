<script setup lang="ts">
import { useAsyncData } from '@/composables/useApi/useApi'
import { $comment } from '@/composables/useApi/useApiComment'
import { $user } from '@/composables/useApi/useApiUser'
import { computed, ref, watch } from 'vue'

const props = defineProps<{ commentId: number }>()

const {
  data: comment,
  loading: commentLoading,
  error: commentError,
} = useAsyncData(() => $comment.getOne(props.commentId.toString()))

// // 用户数据
// const {
//   data: userData,
//   loading: userLoading,
//   error: userError,
// } = useAsyncData(() => $user.getUserById(comment.value.data.userId))

const userId = computed(() => {
  return comment.value?.data?.userId
})

const userData = ref(null)
const userLoading = ref(false)
const userError = ref(null)

watch(
  userId,
  (newUserId) => {
    if (newUserId) {
      userLoading.value = true
      $user
        .getUserById(newUserId)
        .then((response) => {
          userData.value = response
        })
        .catch((err) => {
          userError.value = err
        })
        .finally(() => {
          userLoading.value = false
        })
      console.log('userId changed')
      console.log(userData.value)
    }
  },
  { immediate: true },
)
</script>

<template>
  <div class="bg-white rounded-lg p-4 flex items-start space-x-4">
    <!-- 用户头像 -->
    <div v-if="userLoading">正在加载用户...</div>
    <div v-else-if="userError"></div>
    <img
      v-else
      :src="userData?.data.profilePicture"
      alt="User Avatar"
      class="w-12 h-12 rounded-full"
    />

    <!-- 评论内容 -->
    <div class="flex-1">
      <div v-if="commentLoading">正在加载评论...</div>
      <div v-else-if="commentError"></div>
      <div v-else class="flex flex-col gap-2">
        <!-- 用户名和时间戳 -->
        <div class="flex justify-between items-center mb-2">
          <h2 v-if="userData?.data" class="text-lg font-semibold text-gray-800">
            {{ userData.data.username }}
          </h2>
          <span class="text-sm text-gray-500">{{ comment.data.createdAt }}</span>
        </div>
        <!-- 评论文本框 -->
        <div class="w-full h-24 border-2 border-gray rounded-lg p-2">
          {{ comment.data.content }}
        </div>
      </div>
    </div>
  </div>
</template>
