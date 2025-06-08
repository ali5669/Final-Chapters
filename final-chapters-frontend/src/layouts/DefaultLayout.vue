qin<!-- src/layouts/DefaultLayout.vue -->
<template>
  <div
    :class="[
      'min-h-screen flex flex-col transition-colors duration-300',
      themeStore.isDarkMode
        ? 'bg-gradient-to-br from-slate-900 via-indigo-950 to-purple-950 text-gray-100'
        : 'bg-gradient-to-br from-blue-50 via-indigo-50 to-purple-50 text-gray-800',
    ]"
  >
    <!-- 顶部导航栏 -->
    <header
      :class="[
        'sticky top-0 z-20 backdrop-blur-xl shadow-2xl transition-colors duration-300',
        themeStore.isDarkMode
          ? 'bg-gradient-to-r from-slate-900/95 via-indigo-950/90 to-purple-950/90 text-white'
          : 'bg-gradient-to-r from-indigo-100/95 via-purple-100/95 to-pink-100/95 border-b border-indigo-200/50 shadow-[0_2px_15px_-3px_rgba(0,0,0,0.07)] text-gray-900',
      ]"
    >
      <nav class="max-w-7xl mx-auto px-8 py-4 flex items-center justify-between">
        <!-- Logo -->
        <router-link to="/" class="flex items-center space-x-3 select-none">
          <span
            class="text-3xl font-extrabold tracking-tight bg-gradient-to-r from-indigo-500 via-purple-500 to-pink-500 bg-clip-text text-transparent drop-shadow-lg"
          >
            FinalChapters
          </span>
        </router-link>
        <!-- 导航项 -->
        <div class="hidden md:flex items-center space-x-8">
          <router-link
            to="/author"
            class="relative px-3 py-2 text-sm font-medium hover:text-indigo-600 dark:hover:text-indigo-300 transition-colors duration-300 group"
          >
            <span class="relative z-10">作者中心</span>
            <span
              class="absolute bottom-0 left-0 w-0 h-0.5 bg-indigo-500 transition-all duration-300 group-hover:w-full"
            ></span>
          </router-link>
          <router-link
            to="/browse"
            class="relative px-3 py-2 text-sm font-medium hover:text-indigo-600 dark:hover:text-indigo-300 transition-colors duration-300 group"
          >
            <span class="relative z-10">浏览</span>
            <span
              class="absolute bottom-0 left-0 w-0 h-0.5 bg-indigo-500 transition-all duration-300 group-hover:w-full"
            ></span>
          </router-link>
          <template v-if="userStore.isAuthenticated">
            <router-link
              to="/dashboard/profile"
              class="relative px-3 py-2 text-sm font-medium hover:text-indigo-600 dark:hover:text-indigo-300 transition-colors duration-300 group"
            >
              <span class="relative z-10">个人设置</span>
              <span
                class="absolute bottom-0 left-0 w-0 h-0.5 bg-indigo-500 transition-all duration-300 group-hover:w-full"
              ></span>
            </router-link>
            <router-link
              to="/dashboard/library"
              class="relative px-3 py-2 text-sm font-medium hover:text-indigo-600 dark:hover:text-indigo-300 transition-colors duration-300 group"
            >
              <span class="relative z-10">我的书架</span>
              <span
                class="absolute bottom-0 left-0 w-0 h-0.5 bg-indigo-500 transition-all duration-300 group-hover:w-full"
              ></span>
            </router-link>
            <button
              @click="userStore.logout"
              class="relative px-3 py-2 text-sm font-medium hover:text-indigo-600 dark:hover:text-indigo-300 transition-colors duration-300 group"
            >
              <span class="relative z-10">退出</span>
              <span
                class="absolute bottom-0 left-0 w-0 h-0.5 bg-indigo-500 transition-all duration-300 group-hover:w-full"
              ></span>
            </button>
          </template>
          <template v-else>
            <router-link
              to="/auth/login"
              class="relative px-3 py-2 text-sm font-medium hover:text-indigo-600 dark:hover:text-indigo-300 transition-colors duration-300 group"
            >
              <span class="relative z-10">登录</span>
              <span
                class="absolute bottom-0 left-0 w-0 h-0.5 bg-indigo-500 transition-all duration-300 group-hover:w-full"
              ></span>
            </router-link>
            <router-link
              to="/auth/register"
              :class="[
                'px-6 py-2 rounded-full font-semibold shadow-md transition-all duration-300 transform hover:scale-105',
                themeStore.isDarkMode
                  ? 'bg-gradient-to-r from-indigo-500 to-purple-600 hover:from-indigo-600 hover:to-purple-700 text-white'
                  : 'bg-gradient-to-r from-indigo-400 to-purple-500 hover:from-indigo-500 hover:to-purple-600 text-white',
              ]"
            >
              注册
            </router-link>
          </template>
          <!-- 主题切换 -->
          <button
            @click="themeStore.toggleDarkMode"
            class="p-2 rounded-full transition-all duration-300 border border-transparent hover:border-indigo-400 transform hover:scale-110"
            :class="
              themeStore.isDarkMode ? 'bg-gray-700 text-yellow-300' : 'bg-gray-200 text-gray-700'
            "
            aria-label="切换主题"
          >
            <svg
              v-if="themeStore.isDarkMode"
              xmlns="http://www.w3.org/2000/svg"
              class="h-5 w-5"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z"
              />
            </svg>
            <svg
              v-else
              xmlns="http://www.w3.org/2000/svg"
              class="h-5 w-5"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z"
              />
            </svg>
          </button>
        </div>
        <!-- 移动端菜单按钮 -->
        <div class="md:hidden flex items-center space-x-2">
          <button
            @click="themeStore.toggleDarkMode"
            class="p-2 rounded-full transition-all duration-300 border border-transparent hover:border-indigo-400 transform hover:scale-110"
            :class="
              themeStore.isDarkMode ? 'bg-gray-700 text-yellow-300' : 'bg-gray-200 text-gray-700'
            "
            aria-label="切换主题"
          >
            <svg
              v-if="themeStore.isDarkMode"
              xmlns="http://www.w3.org/2000/svg"
              class="h-5 w-5"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z"
              />
            </svg>
            <svg
              v-else
              xmlns="http://www.w3.org/2000/svg"
              class="h-5 w-5"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z"
              />
            </svg>
          </button>
          <button
            @click="isMobileMenuOpen = !isMobileMenuOpen"
            class="p-2 transform transition-transform duration-300 hover:scale-110"
            aria-label="展开菜单"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="h-7 w-7"
              :class="themeStore.isDarkMode ? 'text-white' : 'text-gray-800'"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M4 6h16M4 12h16m-7 6h7"
              />
            </svg>
          </button>
        </div>
      </nav>
      <!-- 移动端菜单 -->
      <div
        v-if="isMobileMenuOpen"
        class="md:hidden px-8 pb-4 space-y-3 opacity-0 translate-y-[-10px] animate-[fadeInDown_0.3s_ease-out_forwards]"
      >
        <router-link
          to="/author"
          class="block relative px-3 py-2 text-sm font-medium hover:text-indigo-600 dark:hover:text-indigo-300 transition-colors duration-300 group"
        >
          <span class="relative z-10">作者中心</span>
          <span
            class="absolute bottom-0 left-0 w-0 h-0.5 bg-indigo-500 transition-all duration-300 group-hover:w-full"
          ></span>
        </router-link>
        <router-link
          to="/browse"
          class="block relative px-3 py-2 text-sm font-medium hover:text-indigo-600 dark:hover:text-indigo-300 transition-colors duration-300 group"
        >
          <span class="relative z-10">浏览</span>
          <span
            class="absolute bottom-0 left-0 w-0 h-0.5 bg-indigo-500 transition-all duration-300 group-hover:w-full"
          ></span>
        </router-link>
        <template v-if="userStore.isAuthenticated">
          <router-link
            to="/dashboard/profile"
            class="block relative px-3 py-2 text-sm font-medium hover:text-indigo-600 dark:hover:text-indigo-300 transition-colors duration-300 group"
          >
            <span class="relative z-10">个人设置</span>
            <span
              class="absolute bottom-0 left-0 w-0 h-0.5 bg-indigo-500 transition-all duration-300 group-hover:w-full"
            ></span>
          </router-link>
          <router-link
            to="/dashboard/library"
            class="block relative px-3 py-2 text-sm font-medium hover:text-indigo-600 dark:hover:text-indigo-300 transition-colors duration-300 group"
          >
            <span class="relative z-10">我的书架</span>
            <span
              class="absolute bottom-0 left-0 w-0 h-0.5 bg-indigo-500 transition-all duration-300 group-hover:w-full"
            ></span>
          </router-link>
          <router-link
            to="/dashboard/write"
            class="block relative px-3 py-2 text-sm font-medium hover:text-indigo-600 dark:hover:text-indigo-300 transition-colors duration-300 group"
          >
            <span class="relative z-10">写作</span>
            <span
              class="absolute bottom-0 left-0 w-0 h-0.5 bg-indigo-500 transition-all duration-300 group-hover:w-full"
            ></span>
          </router-link>
          <button
            @click="userStore.logout"
            class="block w-full text-left relative px-3 py-2 text-sm font-medium hover:text-indigo-600 dark:hover:text-indigo-300 transition-colors duration-300 group"
          >
            <span class="relative z-10">退出</span>
            <span
              class="absolute bottom-0 left-0 w-0 h-0.5 bg-indigo-500 transition-all duration-300 group-hover:w-full"
            ></span>
          </button>
        </template>
        <template v-else>
          <router-link
            to="/auth/login"
            class="block relative px-3 py-2 text-sm font-medium hover:text-indigo-600 dark:hover:text-indigo-300 transition-colors duration-300 group"
          >
            <span class="relative z-10">登录</span>
            <span
              class="absolute bottom-0 left-0 w-0 h-0.5 bg-indigo-500 transition-all duration-300 group-hover:w-full"
            ></span>
          </router-link>
          <router-link
            to="/auth/register"
            :class="[
              'block px-6 py-2 rounded-full text-center font-semibold shadow-md transition-all duration-300 transform hover:scale-105',
              themeStore.isDarkMode
                ? 'bg-gradient-to-r from-indigo-600 to-purple-700 hover:from-indigo-700 hover:to-purple-800 text-white'
                : 'bg-gradient-to-r from-indigo-500 to-purple-600 hover:from-indigo-600 hover:to-purple-700 text-white',
            ]"
          >
            注册
          </router-link>
        </template>
      </div>
    </header>

    <!-- 主内容区 -->
    <main class="flex-1 flex justify-center items-start py-10 px-4">
      <div
        :class="[
          'w-full max-w-5xl rounded-3xl shadow-xl p-8 md:p-12 transition-colors duration-300 backdrop-blur-lg border',
          themeStore.isDarkMode
            ? 'bg-slate-900/90 border-gray-700/50'
            : 'bg-white/90 border-gray-200/50',
        ]"
      >
        <router-view />
      </div>
    </main>

    <!-- 页脚 -->
    <footer
      :class="[
        'w-full mt-auto transition-colors duration-300',
        themeStore.isDarkMode
          ? 'bg-gradient-to-r from-slate-900 via-indigo-950 to-purple-950 text-gray-300'
          : 'bg-gradient-to-r from-indigo-900 via-purple-900 to-pink-900 text-white',
      ]"
    >
      <div
        class="max-w-7xl mx-auto px-8 py-10 flex flex-col md:flex-row justify-between items-center gap-6"
      >
        <div class="flex flex-col items-center md:items-start gap-2">
          <span
            class="text-2xl font-bold bg-gradient-to-r from-indigo-400 to-purple-400 bg-clip-text text-transparent"
            >FinalChapters</span
          >
          <span class="text-sm opacity-80">让阅读更美好</span>
        </div>
        <div class="flex flex-col md:flex-row items-center gap-4 md:gap-8">
          <a
            href="#"
            class="relative px-3 py-2 text-sm font-medium hover:text-indigo-600 dark:hover:text-indigo-300 transition-colors duration-300 group"
          >
            <span class="relative z-10">关于我们</span>
            <span
              class="absolute bottom-0 left-0 w-0 h-0.5 bg-indigo-400 transition-all duration-300 group-hover:w-full"
            ></span>
          </a>
          <a
            href="#"
            class="relative px-3 py-2 text-sm font-medium hover:text-indigo-600 dark:hover:text-indigo-300 transition-colors duration-300 group"
          >
            <span class="relative z-10">联系我们</span>
            <span
              class="absolute bottom-0 left-0 w-0 h-0.5 bg-indigo-400 transition-all duration-300 group-hover:w-full"
            ></span>
          </a>
          <a
            href="#"
            class="relative px-3 py-2 text-sm font-medium hover:text-indigo-600 dark:hover:text-indigo-300 transition-colors duration-300 group"
          >
            <span class="relative z-10">隐私政策</span>
            <span
              class="absolute bottom-0 left-0 w-0 h-0.5 bg-indigo-400 transition-all duration-300 group-hover:w-full"
            ></span>
          </a>
        </div>
      </div>
      <div class="border-t border-white/20 dark:border-gray-700/40 py-4">
        <p class="text-center text-xs opacity-70">
          &copy; 2024 FinalChapters. All rights reserved.
        </p>
      </div>
    </footer>
  </div>
</template>

<script setup lang="ts">
import { useUserStore } from '@/stores/user'
import { useThemeStore } from '@/stores/theme'
import { ref } from 'vue'

const userStore = useUserStore()
const themeStore = useThemeStore()
const isMobileMenuOpen = ref(false)

// 初始化主题
themeStore.initDarkMode()
</script>
