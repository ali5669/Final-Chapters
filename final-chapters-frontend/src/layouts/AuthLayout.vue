<template>
  <div
    class="min-h-screen bg-gradient-to-br from-indigo-500 to-purple-600 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8"
  >
    <div class="max-w-md w-full space-y-8 bg-white p-8 rounded-xl shadow-lg">
      <div class="text-center">
        <router-link to="/" class="text-2xl font-bold text-indigo-600"> NovelReader </router-link>
      </div>
      <router-view />
    </div>
  </div>
</template>

<script setup lang="ts">
// Auth layout specific logic can be added here
</script>
