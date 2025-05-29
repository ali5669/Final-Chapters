<!-- src/views/auth/Login.vue -->
<template>
  <div class="space-y-6">
    <div class="text-center">
      <h2 class="text-3xl font-bold text-gray-900">Welcome Back</h2>
      <p class="mt-2 text-sm text-gray-600">
        Don't have an account?
        <router-link to="/auth/register" class="text-indigo-600 hover:text-indigo-500">
          Sign up
        </router-link>
      </p>
    </div>

    <form @submit.prevent="handleLogin" class="space-y-4">
      <div>
        <label for="email" class="block text-sm font-medium text-gray-700">Email</label>
        <input
          v-model="email"
          type="email"
          id="email"
          required
          class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
        />
      </div>

      <div>
        <label for="password" class="block text-sm font-medium text-gray-700">Password</label>
        <input
          v-model="password"
          type="password"
          id="password"
          required
          class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
        />
      </div>

      <div class="flex items-center justify-between">
        <div class="flex items-center">
          <input
            type="checkbox"
            id="remember"
            class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
          />
          <label for="remember" class="ml-2 block text-sm text-gray-900">Remember me</label>
        </div>

        <a href="#" class="text-sm text-indigo-600 hover:text-indigo-500">Forgot password?</a>
      </div>

      <button
        type="submit"
        class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
      >
        Sign in
      </button>
    </form>

    <div class="relative">
      <div class="absolute inset-0 flex items-center">
        <div class="w-full border-t border-gray-300"></div>
      </div>
      <div class="relative flex justify-center text-sm">
        <span class="px-2 bg-white text-gray-500">Or continue with</span>
      </div>
    </div>

    <div class="grid grid-cols-2 gap-3">
      <button
        type="button"
        class="w-full inline-flex justify-center py-2 px-4 border border-gray-300 rounded-md shadow-sm bg-white text-sm font-medium text-gray-500 hover:bg-gray-50"
      >
        <img src="/images/google.svg" alt="Google" class="h-5 w-5" />
        <span class="ml-2">Google</span>
      </button>
      <button
        type="button"
        class="w-full inline-flex justify-center py-2 px-4 border border-gray-300 rounded-md shadow-sm bg-white text-sm font-medium text-gray-500 hover:bg-gray-50"
      >
        <img src="/images/facebook.svg" alt="Facebook" class="h-5 w-5" />
        <span class="ml-2">Facebook</span>
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const email = ref('')
const password = ref('')

const handleLogin = async () => {
  try {
    // TODO: Implement actual login logic
    console.log('Login attempt:', { email: email.value, password: password.value })

    // Mock successful login
    const mockUser = {
      id: '1',
      email: email.value,
      username: 'mockuser',
      createdAt: new Date(),
      updatedAt: new Date(),
    }

    userStore.setUser(mockUser)
    userStore.setToken('mock-token')

    router.push('/')
  } catch (error) {
    console.error('Login failed:', error)
  }
}
</script>
