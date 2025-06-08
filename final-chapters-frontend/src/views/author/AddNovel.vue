<template>
  <div
    :class="[
      'max-w-4xl mx-auto space-y-8 py-12',
      themeStore.isDarkMode ? 'text-gray-100' : 'text-gray-800',
    ]"
  >
    <h1
      :class="[
        themeStore.isDarkMode ? 'text-gray-100' : 'text-gray-800',
        'text-3xl font-bold text-center mb-6',
      ]"
    >
      创建新小说
    </h1>

    <form
      @submit.prevent="submitNovel"
      :class="[
        'p-6 rounded-lg shadow-md space-y-6 transition-colors duration-300',
        themeStore.isDarkMode ? 'bg-slate-800/50' : 'bg-white',
      ]"
    >
      <!-- 小说标题 -->
      <div>
        <label
          :class="[
            themeStore.isDarkMode ? 'text-gray-200' : 'text-gray-700',
            'block text-sm font-medium mb-1',
          ]"
          >小说标题</label
        >
        <input
          name="title"
          v-model="novel.otitle"
          type="text"
          required
          :class="[
            'w-full px-4 py-2 rounded-lg border transition-colors duration-300',
            themeStore.isDarkMode
              ? 'bg-slate-700/50 border-slate-600/50 text-gray-100'
              : 'bg-white border-gray-300 text-gray-800',
          ]"
        />
      </div>

      <!-- 小说简介 -->
      <div>
        <label
          :class="[
            themeStore.isDarkMode ? 'text-gray-200' : 'text-gray-700',
            'block text-sm font-medium mb-1',
          ]"
          >小说简介</label
        >
        <textarea
          name="summary"
          v-model="novel.osummary"
          rows="4"
          required
          autocomplete="on"
          :class="[
            'w-full px-4 py-2 rounded-lg border transition-colors duration-300',
            themeStore.isDarkMode
              ? 'bg-slate-700/50 border-slate-600/50 text-gray-100'
              : 'bg-white border-gray-300 text-gray-800',
          ]"
        ></textarea>
      </div>

      <!-- 封面图片 -->
      <div>
        <label
          :class="[
            themeStore.isDarkMode ? 'text-gray-200' : 'text-gray-700',
            'block text-sm font-medium mb-4',
          ]"
          >小说封面</label
        >
        <div class="flex items-center space-x-6">
          <div class="relative">
            <img
              :src="profilePicturePreview || '/images/default-profilePicture.png'"
              alt="Cover"
              class="w-32 h-40 rounded-lg object-cover ring-4 ring-indigo-100 dark:ring-indigo-900"
            />
            <label
              for="cover-upload"
              class="absolute bottom-0 right-0 bg-gradient-to-r from-indigo-500 to-purple-600 text-white p-2 rounded-full hover:from-indigo-600 hover:to-purple-700 cursor-pointer transition-all duration-300 shadow-lg"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-5 w-5"
                viewBox="0 0 20 20"
                fill="currentColor"
              >
                <path
                  d="M13.586 3.586a2 2 0 112.828 2.828l-.793.793-2.828-2.828.793-.793zM11.379 5.793L3 14.172V17h2.828l8.38-8.379-2.83-2.828z"
                />
              </svg>
            </label>
            <input
              id="cover-upload"
              type="file"
              accept="image/*"
              class="hidden"
              @change="handleprofilePictureChange"
            />
          </div>
          <div>
            <p :class="['text-sm', themeStore.isDarkMode ? 'text-gray-400' : 'text-gray-600']">
              点击相机图标上传封面
            </p>
            <p :class="['text-xs mt-1', themeStore.isDarkMode ? 'text-gray-500' : 'text-gray-500']">
              支持 JPG、PNG 格式，大小不超过 2MB
            </p>
          </div>
        </div>
      </div>

      <!-- 小说类别 -->
      <div>
        <label
          :class="[
            themeStore.isDarkMode ? 'text-gray-200' : 'text-gray-700',
            'block text-sm font-medium mb-1',
          ]"
          >小说类别</label
        >
        <input
          name="category"
          v-model="novel.ocategory"
          type="text"
          required
          :class="[
            'w-full px-4 py-2 rounded-lg border transition-colors duration-300',
            themeStore.isDarkMode
              ? 'bg-slate-700/50 border-slate-600/50 text-gray-100'
              : 'bg-white border-gray-300 text-gray-800',
          ]"
        />
      </div>

      <!-- 标签 -->
      <div>
        <label
          :class="[
            themeStore.isDarkMode ? 'text-gray-200' : 'text-gray-700',
            'block text-sm font-medium mb-1',
          ]"
          >标签（逗号分隔）</label
        >
        <input
          name="tags"
          v-model="novel.otags"
          type="text"
          placeholder="例如：奇幻,冒险,热血"
          required
          :class="[
            'w-full px-4 py-2 rounded-lg border transition-colors duration-300',
            themeStore.isDarkMode
              ? 'bg-slate-700/50 border-slate-600/50 text-gray-100 placeholder-gray-400'
              : 'bg-white border-gray-300 text-gray-800 placeholder-gray-500',
          ]"
        />
      </div>

      <!-- 按钮区域 -->
      <div class="flex justify-between">
        <!-- 取消按钮 -->
        <div>
          <button
            type="button"
            @click="$router.push('/author')"
            :class="[
              'px-6 py-2 rounded-lg font-semibold transition-colors duration-300',
              themeStore.isDarkMode
                ? 'bg-gray-600 hover:bg-gray-700 text-white'
                : 'bg-gray-500 hover:bg-gray-600 text-white',
            ]"
          >
            取消
          </button>
        </div>

        <!-- 提交按钮 -->
        <div>
          <button
            type="submit"
            :class="[
              'px-6 py-2 rounded-lg font-semibold transition-colors duration-300',
              themeStore.isDarkMode
                ? 'bg-indigo-600 hover:bg-indigo-700 text-white'
                : 'bg-indigo-500 hover:bg-indigo-600 text-white',
            ]"
          >
            创建小说
          </button>
        </div>
      </div>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { $novel } from '@/composables/useApi/useApiNovel'
import { useUserStore } from '@/stores/user'
import { useThemeStore } from '@/stores/theme'
import { useRouter } from 'vue-router'

const router = useRouter()
const userStore = useUserStore()
const themeStore = useThemeStore()
const authorId = userStore.currentUser.userId.toString()

// 初始化小说对象
const novel = ref({
  otitle: '',
  osummary: '',
  ocategory: '',
  otags: '',
})

// 封面文件
const coverFile = ref(null)

/**
 * 提交小说方法
 * 检查小说信息是否完整，并准备提交数据
 */
const submitNovel = async () => {
  try {
    const { otitle, osummary, ocategory, otags } = novel.value
    // 检查所有字段是否填写
    if (!otitle || !osummary || !ocategory || !otags) {
      alert('请填写所有必填字段')
      return
    }
    // 检查封面文件是否上传
    if (!coverFile.value) {
      alert('请上传封面图片')
      return
    }
    // 构造完整的小说对象
    const newNovel = {
      authorId: authorId,
      title: novel.value.otitle,
      summary: novel.value.osummary,
      category: novel.value.ocategory,
      tags: novel.value.otags,
      cover: coverFile.value,
    }
    // 输出结果，或替换为 API 调用
    console.log('提交的小说数据:', newNovel)
    const { data: res } = await $novel.addNovelWithCover({ data: newNovel })
    console.log('结果res:', res)
    // 返回上一页
    router.push('/author')
  } catch (error) {
    console.error('提交小说失败:', error)
  }
}
// profilePicture handling
const profilePicturePreview = ref<string | null>(null)

const handleprofilePictureChange = async (event: Event) => {
  // 回显封面
  const input = event.target as HTMLInputElement
  const file = input.files?.[0]
  if (file) {
    // Check file size (2MB limit)
    if (file.size > 2 * 1024 * 1024) {
      alert('文件大小不能超过 2MB')
      return
    }
    // Check file type
    const allowedTypes = ['image/jpeg', 'image/png']
    if (!allowedTypes.includes(file.type)) {
      alert('请上传 JPG 或 PNG 格式的图片')
      return
    }
    coverFile.value = file
    // console.log(file)
    // console.log(coverFile.value)
    const reader = new FileReader()
    reader.onload = (e) => {
      profilePicturePreview.value = e.target?.result as string
    }
    reader.readAsDataURL(file)
  }

  //   try {
  //     const response = await $novel.addNovelWithCover({
  //       data: {
  //         username: userStore.currentUser.username,
  //         profilePicture: file
  //       }
  //     })

  //     if (response.success) {
  //       // Create preview
  //       userStore.currentUser.profilePicture=response.data.profilePicture
  //       const reader = new FileReader()
  //       reader.onload = (e) => {
  //         profilePicturePreview.value = e.target?.result as string
  //       }
  //       reader.readAsDataURL(file)

  //       // Update user store with new profilePicture
  //       if (userStore.currentUser) {
  //         userStore.setUser({
  //           ...userStore.currentUser,
  //           profilePicture: userStore.currentUser.profilePicture
  //         })
  //       }
  //       alert('头像上传成功')
  //     } else {
  //       alert(response.errorMsg || '头像上传失败')
  //     }
  //   } catch (error) {
  //     console.error('profilePicture upload failed:', error)
  //     alert('头像上传失败，请重试')
  //   }
  // }
}
</script>
