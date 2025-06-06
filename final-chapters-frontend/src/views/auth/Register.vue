<!-- src/views/auth/Register.vue -->
<template>
  <div class="space-y-6">
    <div class="text-center">
      <h2 class="text-3xl font-bold text-gray-900">Create an Account</h2>
      <p class="mt-2 text-sm text-gray-600">
        Already have an account?
        <router-link to="/auth/login" class="text-indigo-600 hover:text-indigo-500">
          Sign in
        </router-link>
      </p>
    </div>

    <form @submit.prevent="handleRegister" class="space-y-4">
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
      </div>

      <div>
        <label for="confirmPassword" class="block text-sm font-medium text-gray-700">
          Confirm Password
        </label>
        <input
          v-model="confirmPassword"
          type="password"
          id="confirmPassword"
          required
          class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
        />
        <p v-if="passwordError" class="mt-1 text-sm text-red-600">{{ passwordError }}</p>
        <p v-if="registerError" class="mt-1 text-sm text-red-600">{{ registerError }}</p>
      </div>

      <div class="flex items-center">
        <input
          type="checkbox"
          id="terms"
          v-model="acceptTerms"
          required
          class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
        />
        <label for="terms" class="ml-2 block text-sm text-gray-900">
          I agree to the
          <a href="#" class="text-indigo-600 hover:text-indigo-500">Terms of Service</a>
          and
          <a href="#" class="text-indigo-600 hover:text-indigo-500">Privacy Policy</a>
        </label>
      </div>

      <button
        type="submit"
        class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
      >
        Create Account
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
const confirmPassword = ref('')
const acceptTerms = ref(false)
const passwordError = ref('')
const registerError = ref('')

const handleRegister = async () => {
  passwordError.value = ''
  registerError.value = ''

  if (password.value !== confirmPassword.value) {
    passwordError.value = 'Passwords do not match'
    return
  }

  try {
    const response = await $user.register({
      data: {
        username: username.value,
        password: password.value
      }
    })

    if (response.success) {
      router.push('/auth/login')
    } else {
      registerError.value = response.errorMsg || 'Registration failed'
    }
  } catch (error) {
    console.error('Registration failed:', error)
    registerError.value = 'Registration failed, please try again'
  }
}
</script>
