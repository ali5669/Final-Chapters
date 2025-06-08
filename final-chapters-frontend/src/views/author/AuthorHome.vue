<template>
  <div class="max-w-4xl mx-auto space-y-8 py-12">
    <!-- 作者信息 -->
    <section class="bg-white rounded-lg shadow-md p-6 flex items-center">
      <img :src="author.profilePicture" alt="Avatar" class="w-20 h-20 rounded-full object-cover mr-6" />
      <h2 class="text-2xl font-bold">{{ author.username }}</h2>
    </section>

    <!-- 作家的作品 -->
    <section>
      <h2 class="text-2xl font-bold mb-6">我的作品</h2>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <div
          v-for="work in authorWorks"
          :key="work.novelId"
          class="bg-white rounded-lg shadow-md overflow-hidden"
        >
          <div class="p-4">
            <h3 class="font-semibold text-lg mb-2">{{ work.title }}</h3>
            <button
              @click.stop="manageChapters(work)"
              class="text-indigo-600 font-medium hover:text-indigo-700"
            >
              查看章节 →
            </button>
          </div>
        </div>
      </div>
    </section>

    <!-- 操作按钮 -->
    <div class="flex gap-4 mt-6">
      <button @click="addWork" class="bg-indigo-600 text-white px-6 py-2 rounded-lg font-semibold hover:bg-indigo-700 transition">
        添加作品
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { $novel } from '@/composables/useApi/useApiNovel'

const router = useRouter()
const userStore = useUserStore()



// Reactive state
const author = ref({
  profilePicture: 'http://gips3.baidu.com/it/u=3886271102,3123389489&fm=3028&app=3028&f=JPEG&fmt=auto?w=1280&h=960',
  // username: userStore.currentUser.username,
  username: 'userStore.currentUser.username',
})

const authorWorks = ref([
  { novelId: '13', title: '王者荣耀攻略' },
  { novelId: '2', title: '作品二' },
  { novelId: '3', title: '作品三' },
])

// Methods
const addWork = () => {
  router.push('/author/addWork')
}

const manageChapters = (work) => {
  router.push(`/author/manageChapters/${work.novelId}`)
}
// 查询作者的小说
const fetchNovelData = async () => {
  const { data: novels } = await $novel.getNovels({data:{authorId:userStore.currentUser.userId.toString()}});
  authorWorks.value = novels

};
onMounted(async() => {
  try{
    
    author.value.username = userStore.currentUser.username
    author.value.profilePicture = userStore.currentUser.profilePicture
    await Promise.all([fetchNovelData()]);
  }catch(err){
    console.error(err)
  }
  
});
</script>