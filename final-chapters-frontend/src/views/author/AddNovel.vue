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

      <!-- 封面图片 -->
      <div>
        <img :src="profilePicturePreview || '/images/default-profilePicture.png'" alt="Profile" class="w-24 h-24 rounded-full object-cover"/>
        <input
          id="profilePicture-upload"
          type="file"
          accept="image/*"
          @change="handleprofilePictureChange"
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
const authorId = userStore.currentUser.userId.toString();

// 初始化小说对象
const novel = ref({
  otitle: '',
  osummary: '',
  ocategory: '',
  otags: ''
});

// 封面文件
const coverFile = ref(null);

/**
 * 提交小说方法
 * 检查小说信息是否完整，并准备提交数据
 */
const submitNovel = async() => {
  try {
    const { otitle, osummary, ocategory, otags } = novel.value;
    // 检查所有字段是否填写
    if (!otitle || !osummary || !ocategory || !otags) {
      alert('请填写所有必填字段');
      return;
    }
    // 检查封面文件是否上传
    if (!coverFile.value) {
      alert('请上传封面图片');
      return;
    }
    // 构造完整的小说对象
    const newNovel = {
      "authorId": authorId,
      "title": novel.value.otitle,
      "summary": novel.value.osummary,
      "category": novel.value.ocategory,
      "tags": novel.value.otags,
      "cover": coverFile.value,
    };
    // 输出结果，或替换为 API 调用
    console.log('提交的小说数据:', newNovel);
    const { data: res } = await $novel.addNovelWithCover({data: newNovel})
    console.log('结果res:', res);
    // 返回上一页
    router.push('/author');


  } catch (error) {
    console.error('提交小说失败:', error);
  }
  
};
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