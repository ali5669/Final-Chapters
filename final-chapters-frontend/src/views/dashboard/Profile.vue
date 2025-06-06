<!-- src/views/dashboard/Profile.vue -->
<template>
  <div class="space-y-8">
    <h1 class="text-2xl font-bold">个人设置</h1>

    <div class="bg-white rounded-lg shadow p-6">
      <!-- profilePicture Section -->
      <div class="mb-8">
        <h2 class="text-lg font-medium mb-4">头像设置</h2>
        <div class="flex items-center space-x-6">
          <div class="relative">
            <img
              :src="profilePicturePreview || '/images/default-profilePicture.png'"
              alt="Profile"
              class="w-24 h-24 rounded-full object-cover"
            />
            <label
              for="profilePicture-upload"
              class="absolute bottom-0 right-0 bg-indigo-600 text-white p-1 rounded-full hover:bg-indigo-700 cursor-pointer"
            >
              <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                <path d="M13.586 3.586a2 2 0 112.828 2.828l-.793.793-2.828-2.828.793-.793zM11.379 5.793L3 14.172V17h2.828l8.38-8.379-2.83-2.828z" />
              </svg>
            </label>
            <input
              id="profilePicture-upload"
              type="file"
              accept="image/*"
              class="hidden"
              @change="handleprofilePictureChange"
            />
          </div>
          <div>
            <p class="text-sm text-gray-500">点击相机图标更新头像</p>
            <p class="text-xs text-gray-400 mt-1">支持 JPG、PNG 格式，大小不超过 2MB</p>
          </div>
        </div>
      </div>

      <!-- Password Change Section -->
      <div class="mb-8">
        <h2 class="text-lg font-medium mb-4">修改密码</h2>
        <form @submit.prevent="handlePasswordChange" class="space-y-4 max-w-md">
          <div>
            <label for="currentPassword" class="block text-sm font-medium text-gray-700">当前密码</label>
            <input
              v-model="passwordForm.oldPassword"
              type="password"
              id="oldPassword"
              required
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
            />
          </div>
          <div>
            <label for="newPassword" class="block text-sm font-medium text-gray-700">新密码</label>
            <input
              v-model="passwordForm.newPassword"
              type="password"
              id="newPassword"
              required
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
            />
          </div>
          <div>
            <label for="confirmNewPassword" class="block text-sm font-medium text-gray-700">确认新密码</label>
            <input
              v-model="passwordForm.confirmNewPassword"
              type="password"
              id="confirmNewPassword"
              required
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
            />
            <p v-if="passwordError" class="mt-1 text-sm text-red-600">{{ passwordError }}</p>
          </div>
          <button
            type="submit"
            class="px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700"
          >
            更新密码
          </button>
        </form>
      </div>

      <!-- VIP Section -->
      <div>
        <h2 class="text-lg font-medium mb-4">VIP 会员</h2>
        <div class="flex items-center space-x-4">
          <div class="flex-1">
            <p class="text-sm text-gray-600">
              当前状态: <span :class="userStore.currentUser?.isVIP ? 'text-green-600' : 'text-gray-600'">
                {{ userStore.currentUser?.isVIP ? 'VIP 会员' : '普通用户' }}
              </span>
            </p>
          </div>
          <button
            v-if="!userStore.currentUser?.isVIP"
            @click="handleBecomeVIP"
            class="px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700"
          >
            成为 VIP
          </button>
          <button
            v-if="userStore.currentUser?.isVIP"
            @click="handleBecomeVIP"
            class="px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700"
          >
            取消 VIP
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { $user } from '@/composables/useApi/useApiUser'

const userStore = useUserStore()

// profilePicture handling
const profilePicturePreview = ref<string | null>(null)

const handleprofilePictureChange = async (event: Event) => {
  const input = event.target as HTMLInputElement
  if (input.files && input.files[0]) {
    const file = input.files[0]

    // Check file size (2MB limit)
    if (file.size > 2 * 1024 * 1024) {
      alert('文件大小不能超过 2MB')
      return
    }

    // Check file type
    if (!file.type.startsWith('image/')) {
      alert('请上传图片文件')
      return
    }

    try {
      const response = await $user.editProfile({
        data: {
          profilePicture: file
        }
      })

      if (response.success) {
        // Create preview
        const reader = new FileReader()
        reader.onload = (e) => {
          profilePicturePreview.value = e.target?.result as string
        }
        reader.readAsDataURL(file)

        // Update user store with new profilePicture
        if (userStore.currentUser) {
          userStore.setUser({
            ...userStore.currentUser,
            profilePicture: response.data.profilePicture
          })
        }
      } else {
        alert(response.errorMsg || '头像上传失败')
      }
    } catch (error) {
      console.error('profilePicture upload failed:', error)
      alert('头像上传失败，请重试')
    }
  }
}

// Password change handling
const passwordForm = ref({
  oldPassword: '',
  newPassword: '',
  confirmNewPassword: ''
})
const passwordError = ref('')

const handlePasswordChange = async () => {
  passwordError.value = ''

  if (passwordForm.value.newPassword !== passwordForm.value.confirmNewPassword) {
    passwordError.value = '两次输入的新密码不匹配'
    return
  }

  try {

    const response = await $user.editPassword({
      data: {
        username: userStore.currentUser?.username,
        oldPassword: passwordForm.value.oldPassword,
        newPassword: passwordForm.value.newPassword
      }
    })

    if (response.success) {
      // Clear form after successful change
      passwordForm.value = {
        oldPassword: '',
        newPassword: '',
        confirmNewPassword: ''
      }
      alert('密码修改成功')
    } else {
      passwordError.value = response.errorMsg || '密码修改失败'
    }
  } catch (error) {
    console.error('Password change failed:', error)
    passwordError.value = '密码修改失败，请重试'
  }
}

// VIP handling
const handleBecomeVIP = async () => {
  try {
    const response = await $user.becomeVIP({
      data: {
        username: userStore.currentUser?.username,
        isVIP: userStore.currentUser?.isVIP
      }
    })

    if (response.success) {
      // Update user store with new VIP status
      if (userStore.currentUser && !userStore.currentUser.isVIP) {
        userStore.setUser({
          ...userStore.currentUser,
          isVIP: true
        })
        alert('恭喜您成为 VIP 会员！')
      } else if (userStore.currentUser && userStore.currentUser.isVIP) {
        userStore.setUser({
          ...userStore.currentUser,
          isVIP: false
        })
        alert('您已取消 VIP 会员')
      }

    } else {
      alert(response.errorMsg || 'VIP 状态修改失败')
    }
  } catch (error) {
    console.error('VIP status change failed:', error)
    alert('VIP 状态修改失败，请重试')
  }
}

// Load user information on mount
onMounted(() => {
  if (userStore.currentUser?.profilePicture) {
    profilePicturePreview.value = userStore.currentUser.profilePicture
  }
})
</script>
