<!-- src/views/dashboard/Profile.vue -->
<template>
  <div class="space-y-8">
    <h1 class="text-2xl font-bold">Profile Settings</h1>

    <div class="bg-white rounded-lg shadow p-6">
      <form @submit.prevent="saveProfile" class="space-y-6">
        <!-- Avatar Section -->
        <div class="flex items-center space-x-6">
          <div class="relative">
            <img
              :src="userProfile.avatar || '/images/default-avatar.png'"
              alt="Profile"
              class="w-24 h-24 rounded-full object-cover"
            />
            <button
              type="button"
              class="absolute bottom-0 right-0 bg-indigo-600 text-white p-1 rounded-full hover:bg-indigo-700"
            >
              <i class="fas fa-camera"></i>
            </button>
          </div>
          <div>
            <h3 class="text-lg font-medium">Profile Picture</h3>
            <p class="text-sm text-gray-500">Click the camera icon to update your photo</p>
          </div>
        </div>

        <!-- Basic Information -->
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label for="username" class="block text-sm font-medium text-gray-700">Username</label>
            <input
              v-model="userProfile.username"
              type="text"
              id="username"
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
            />
          </div>
          <div>
            <label for="email" class="block text-sm font-medium text-gray-700">Email</label>
            <input
              v-model="userProfile.email"
              type="email"
              id="email"
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
            />
          </div>
          <div class="md:col-span-2">
            <label for="bio" class="block text-sm font-medium text-gray-700">Bio</label>
            <textarea
              v-model="userProfile.bio"
              id="bio"
              rows="4"
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
            ></textarea>
          </div>
        </div>

        <!-- Reading Preferences -->
        <div>
          <h3 class="text-lg font-medium mb-4">Reading Preferences</h3>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label for="theme" class="block text-sm font-medium text-gray-700">Theme</label>
              <select
                v-model="userProfile.preferences.theme"
                id="theme"
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
              >
                <option value="light">Light</option>
                <option value="dark">Dark</option>
              </select>
            </div>
            <div>
              <label for="fontSize" class="block text-sm font-medium text-gray-700"
                >Font Size</label
              >
              <input
                v-model="userProfile.preferences.fontSize"
                type="range"
                id="fontSize"
                min="12"
                max="24"
                class="mt-1 block w-full"
              />
            </div>
          </div>
        </div>

        <div class="flex justify-end space-x-4">
          <button
            type="button"
            class="px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50"
          >
            Cancel
          </button>
          <button
            type="submit"
            class="px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700"
          >
            Save Changes
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { UserProfile } from '@/types/user'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()

const userProfile = ref<UserProfile>({
  id: '1',
  email: 'user@example.com',
  username: 'username',
  bio: 'A passionate reader...',
  avatar: '/images/default-avatar.png',
  preferences: {
    theme: 'light',
    fontSize: 16,
    backgroundColor: '#ffffff',
  },
  createdAt: new Date(),
  updatedAt: new Date(),
})

const saveProfile = async () => {
  try {
    // TODO: Implement actual profile update logic
    userStore.setUserProfile(userProfile.value)
    console.log('Profile updated:', userProfile.value)
  } catch (error) {
    console.error('Failed to update profile:', error)
  }
}
</script>
