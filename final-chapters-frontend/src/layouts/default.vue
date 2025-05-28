<template>
  <div class="h-screen bg-gradient-to-br from-gray-50 via-blue-50 to-purple-50">
    <!-- 顶部导航 -->
    <div class="h-14 w-full bg-white shadow z-topnav relative flex items-center px-4">
      <div class="h-full flex-grow">
        <slot name="header"></slot>
      </div>

      <!-- 学习空间控制面板 -->
      <div class="ml-auto pl-4">
        <LearnSpacePanel />
      </div>
    </div>

    <!-- 主要内容区域 -->
    <section
      v-if="user.isLogin.value"
      class="overflow-x-hidden overflow-y-scroll relative"
      style="height: calc(100% - 3.5rem)"
    >
      <div class="flex mx-auto pl-4 pr-2">
        <div class="flex-grow py-4 relative">
          <!-- 左侧学习内容 -->
          <div class="lg:flex w-full mb-6 min-h-screen" v-scroll-in>
            <slot name="content"></slot>
          </div>

          <!-- 下方笔记区 -->
          <div
            v-scroll-in
            class="sticky transition-all duration-300 z-fixed flex justify-end"
            :class="[
              layoutState.isNotesExpanded
                ? 'h-[calc(50vh)] mx-2 bottom-0 rounded-t-lg'
                : 'h-14 mx-4 bottom-3 rounded-lg ',
              '  ',
            ]"
          >
            <div
              class="max-w-3xl w-full bg-white border h-full px-4 shadow-lg"
              :class="[layoutState.isNotesExpanded ? 'rounded-t-lg' : 'rounded-lg ', '  ']"
            >
              <slot name="notes" :show="layoutState.isNotesExpanded"></slot>
            </div>
          </div>
        </div>

        <!-- 右侧面板 -->
        <div class="w-96 flex-grow-0 flex-shrink-0 pl-4">
          <div class="flex w-full overflow-hidden sticky top-0">
            <!-- 选项卡内容 -->
            <div class="flex flex-col py-4 pr-2" style="width: calc(100% - 3rem)">
              <slot name="right-cards"></slot>
            </div>

            <!-- 选项卡标签 -->
            <div class="w-12 flex-shrink-0 flex flex-col items-center py-4">
              <slot name="right-tabs"></slot>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { provide, reactive } from 'vue'

defineOptions({
  name: 'LearnSpaceLayout',
})

const user = useUser()

// 布局状态管理
const layoutState = reactive({
  isNotesExpanded: false,
  toggleNotes: () => {
    layoutState.isNotesExpanded = !layoutState.isNotesExpanded
  },
})

// 提供给子组件使用
provide('layoutState', layoutState)
</script>
