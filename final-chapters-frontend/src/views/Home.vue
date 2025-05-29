<!-- src/views/Home.vue -->

<template>
  <div class="space-y-8">
    <!-- Hero Section -->
    <section class="bg-gradient-to-r from-indigo-500 to-purple-600 text-white py-16 rounded-2xl">
      <div class="max-w-4xl mx-auto text-center px-4">
        <h1 class="text-4xl md:text-5xl font-bold mb-6">Discover Your Next Favorite Story</h1>
        <p class="text-xl mb-8">
          Immerse yourself in a world of endless stories. Read, write, and connect with fellow book
          lovers.
        </p>
        <div class="flex justify-center gap-4">
          <router-link
            to="/browse"
            class="bg-white text-indigo-600 px-6 py-3 rounded-lg font-semibold hover:bg-opacity-90"
          >
            Start Reading
          </router-link>
          <router-link
            to="/auth/register"
            class="border-2 border-white text-white px-6 py-3 rounded-lg font-semibold hover:bg-white hover:text-indigo-600"
          >
            Join Now
          </router-link>
        </div>
      </div>
    </section>

    <!-- Featured Novels -->
    <section>
      <h2 class="text-2xl font-bold mb-6">Featured Novels</h2>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <div
          v-for="novel in featuredNovels"
          :key="novel.id"
          class="bg-white rounded-lg shadow-md overflow-hidden"
        >
          <img :src="novel.cover" :alt="novel.title" class="w-full h-48 object-cover" />
          <div class="p-4">
            <h3 class="font-semibold text-lg mb-2">{{ novel.title }}</h3>
            <p class="text-gray-600 text-sm mb-4">{{ novel.author }}</p>
            <router-link
              :to="'/novel/' + novel.id"
              class="text-indigo-600 font-medium hover:text-indigo-700"
            >
              Read More →
            </router-link>
          </div>
        </div>
      </div>
    </section>

    <!-- Categories -->
    <section>
      <h2 class="text-2xl font-bold mb-6">Browse by Category</h2>
      <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
        <router-link
          v-for="category in categories"
          :key="category.id"
          :to="'/browse?category=' + category.id"
          class="bg-white p-6 rounded-lg shadow-md text-center hover:shadow-lg transition-shadow"
        >
          <span class="text-lg font-medium text-gray-800">{{ category.name }}</span>
        </router-link>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { Novel } from '@/types/novel'

const featuredNovels = ref<Novel[]>([
  {
    id: '1',
    title: 'The Lost Kingdom',
    author: 'Jane Doe',
    cover: '/images/novel1.jpg',
    description: 'An epic fantasy adventure...',
    tags: ['Fantasy', 'Adventure'],
    category: 'Fantasy',
    status: 'ongoing',
    createdAt: new Date(),
    updatedAt: new Date(),
  },
  // Add more featured novels...
])

const categories = ref([
  { id: 'fantasy', name: 'Fantasy' },
  { id: 'romance', name: 'Romance' },
  { id: 'scifi', name: 'Science Fiction' },
  { id: 'mystery', name: 'Mystery' },
  // Add more categories...
])
</script>

<style scoped>
.vertical-layout {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  height: 100vh; /* 设置整个布局高度为视口高度，以确保内容在竖屏中居中显示 */
}
header,
main,
footer {
  width: 80%; /* 设置宽度为80% */
  min-width: 800px; /* 设置最小宽度为400px */
  max-width: 1200px; /* 设置最大宽度为1200px */
  padding: 20px;
  box-sizing: border-box;
  text-align: center;
  background-color: #f0f0f0;
  margin: 10px 0;
}

@media screen and (max-width: 600px) {
  /* 在小屏幕上，设置宽度为100% */
  header,
  main,
  footer {
    width: 100%;
  }
}
</style>
