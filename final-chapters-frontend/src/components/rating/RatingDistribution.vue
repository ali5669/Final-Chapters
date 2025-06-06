<script lang="ts" setup>
import { computed, defineProps } from 'vue'
import { useAsyncData } from '@/composables/useApi/useApi'
import { $rating, RatingCountDTO } from '@/composables/useApi/useApiComment'

const props = defineProps<{ novelId: string }>()

const { data: ratings } = useAsyncData(() => $rating.listByNovelId(props.novelId))

// 统计各评分的数量
const ratingCounts = computed(() => {
  const counts = [0, 0, 0, 0, 0] // index 0 -> rating 1
  if (ratings.value && ratings.value.length) {
    ratings.value.forEach((r: RatingCountDTO) => {
      if (r.rating >= 1 && r.rating <= 5) {
        counts[r.rating - 1]++
      }
    })
  }
  return counts
})

// 计算总评分数量
const totalRatings = computed(() => ratingCounts.value.reduce((a, b) => a + b, 0))

// 计算每个评分的百分比
const ratingPercentages = computed(() =>
  ratingCounts.value.map((count) => (totalRatings.value ? (count / totalRatings.value) * 100 : 0)),
)
</script>

<template>
  <div class="px-4 py-2">
    <h3 class="text-lg font-semibold mb-2">评分分布</h3>
    <div class="space-y-2">
      <div v-for="(percentage, index) in ratingPercentages" :key="index" class="flex items-center">
        <div class="w-12 text-right mr-2">{{ index + 1 }}星</div>
        <div class="flex-grow bg-gray-200 rounded-full h-2 overflow-hidden">
          <div
            class="bg-yellow-500 h-full transition-all duration-300 ease-in-out"
            :style="{ width: percentage + '%' }"
          ></div>
        </div>
        <div class="ml-2 w-8 text-sm text-gray-600">{{ ratingCounts[index] }}</div>
      </div>
    </div>
  </div>
</template>
