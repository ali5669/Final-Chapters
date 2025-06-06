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
        <label for="username" class="block text-sm font-medium text-gray-700">Username</label>
        <input
          v-model="username"
          type="text"
          id="username"
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
        <p v-if="loginError" class="mt-1 text-sm text-red-600">{{ loginError }}</p>
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
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { $user } from '@/composables/useApi/useApiUser'

const router = useRouter()
const userStore = useUserStore()
const username = ref('')
const password = ref('')
const loginError = ref('')

const handleLogin = async () => {
  loginError.value = ''

  try {
    const response = await $user.login({
      data: {
        username: username.value,
        password: password.value
      }
    })

    if (response.success) {
      userStore.setUser(response.data.user)
      userStore.setToken(response.data.token)
      router.push('/')
    } else {
      loginError.value = response.errorMsg || 'Login failed'
    }
  } catch (error) {
    console.error('Login failed:', error)
    loginError.value = 'Login failed, please try again'
  }
}
</script>
