<script lang="ts" setup>
import { useAsyncData } from '@/composables/useApi/useApi'
import { $rating } from '@/composables/useApi/useApiComment'

const props = defineProps<{ novelId: string }>()

const { data: ratingAverage } = useAsyncData(() => $rating.getAverageRating(props.novelId))
</script>

<template>
  <div class="text-center">
    <!-- 星型图标 -->
    <div class="flex justify-center mb-2">
      <div class="flex">
        <template v-for="star in 5" :key="star">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 24 24"
            fill="currentColor"
            class="w-6 h-6"
            :class="{
              'text-yellow-500': star <= Math.round(ratingAverage.data),
              'text-gray-300': star > Math.round(ratingAverage.data),
            }"
          >
            <path
              d="M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z"
            />
          </svg>
        </template>
      </div>
    </div>

    <!-- 平均评分展示 -->
    <div class="flex justify-center items-end space-x-1">
      <div class="text-4xl font-bold">{{ ratingAverage.data }}</div>
      <div class="text-sm text-gray-500">/5</div>
    </div>
  </div>
</template>
