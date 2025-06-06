<template>
  <div class="max-w-4xl mx-auto space-y-8 py-12">
    <h1 class="text-3xl font-bold text-center mb-6">创建新小说</h1>

    <form @submit.prevent="submitNovel" class="bg-white p-6 rounded-lg shadow-md space-y-6">
      <!-- 小说标题 -->
      <div>
        <label for="title" class="block text-sm font-medium text-gray-700 mb-1">小说标题</label>
        <input
          name="title"
          v-model="novel.otitle"
          type="text"
          required
          class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500"
        />
      </div>

      <!-- 小说简介 -->
      <div>
        <label for="summary" class="block text-sm font-medium text-gray-700 mb-1">小说简介</label>
        <textarea
          name="summary"
          v-model="novel.osummary"
          rows="4"
          required
          autocomplete="on"
          class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500"
        ></textarea>
      </div>

      <!-- 封面图片路径 -->
      <div>
        <label for="cover" class="block text-sm font-medium text-gray-700 mb-1">封面图片路径</label>
        <input
          name="cover"
          v-model="novel.ocover_image"
          type="text"
          required
          class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500"
        />
      </div>

      <!-- 小说类别 -->
      <div>
        <label for="category" class="block text-sm font-medium text-gray-700 mb-1">小说类别</label>
        <input
          name="category"
          v-model="novel.ocategory"
          type="text"
          required
          class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500"
        />
      </div>

      <!-- 标签 -->
      <div>
        <label for="tags" class="block text-sm font-medium text-gray-700 mb-1">标签（逗号分隔）</label>
        <input
          name="tags"
          v-model="novel.otags"
          type="text"
          placeholder="例如：奇幻,冒险,热血"
          required
          class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500"
        />
      </div>

      <!-- 按钮区域 -->
      <div class="flex justify-between">
        <!-- 取消按钮 -->
        <div>
          <button
            type="button"
            @click="$router.push('/author')"
            class="bg-gray-500 text-white px-6 py-2 rounded-lg font-semibold hover:bg-gray-600 transition"
          >
            取消
          </button>
        </div>
      
        <!-- 提交按钮 -->
        <div>
          <button
            type="submit"
            class="bg-indigo-600 text-white px-6 py-2 rounded-lg font-semibold hover:bg-indigo-700 transition"
          >
            创建小说
          </button>
        </div>
      </div>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { $novel } from '@/composables/useApi/useApiNovel'
import { useUserStore } from '@/stores/user'
import { useRouter } from 'vue-router'
const router = useRouter()
const userStore = useUserStore()
const authorId = userStore.currentUser.id;

// 初始化小说对象
const novel = ref({
  otitle: '',
  osummary: '',
  ocover_image: '',
  ocategory: '',
  otags: ''
});



/**
 * 提交小说方法
 * 检查小说信息是否完整，并准备提交数据
 */
const submitNovel = async() => {
  try {
    const { otitle, osummary, ocover_image, ocategory, otags } = novel.value;
    // 检查所有字段是否填写
    if (!otitle || !osummary || !ocover_image || !ocategory || !otags) {
      alert('请填写所有必填字段');
      return;
    }

    // 构造完整的小说对象
    const newNovel = {
      "authorId": authorId,
      "title": novel.value.otitle,
      "summary": novel.value.osummary,
      "category": novel.value.ocategory,
      "tags": novel.value.otags
    };
    // 输出结果，或替换为 API 调用
    console.log('提交的小说数据:', newNovel);
    const { data: res } = await $novel.addNovel({data: newNovel})
    console.log('结果res:', res);
    // 返回上一页
    router.push('/author');


  } catch (error) {
    console.error('提交小说失败:', error);
  }
  
};
</script>