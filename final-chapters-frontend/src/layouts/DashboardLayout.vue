<template>
  <div class="min-h-screen bg-gray-50">
    <header class="bg-white shadow-sm">
      <nav class="container mx-auto px-4 py-3">
        <div class="flex items-center justify-between">
          <div class="flex items-center space-x-6">
            <router-link to="/" class="text-2xl font-bold text-indigo-600">
              NovelReader
            </router-link>
            <div class="hidden md:flex space-x-4">
              <router-link to="/dashboard/profile" class="text-gray-600 hover:text-indigo-600">
                Profile
              </router-link>
              <router-link to="/dashboard/library" class="text-gray-600 hover:text-indigo-600">
                My Library
              </router-link>
              <router-link to="/dashboard/write" class="text-gray-600 hover:text-indigo-600">
                Write
              </router-link>
            </div>
          </div>
          <div class="flex items-center space-x-4">
            <button @click="userStore.logout" class="text-gray-600 hover:text-indigo-600">
              Logout
            </button>
          </div>
        </div>
      </nav>
    </header>

    <main class="container mx-auto px-4 py-8">
      <router-view />
    </main>
  </div>
</template>

<script setup lang="ts">
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
</script>
