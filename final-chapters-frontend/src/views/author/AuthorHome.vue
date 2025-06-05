<template>
  <div class="max-w-4xl mx-auto space-y-8 py-12">
    <!-- 作者信息 -->
    <section class="bg-white rounded-lg shadow-md p-6 flex items-center">
      <img :src="author.avatar" alt="Avatar" class="w-20 h-20 rounded-full object-cover mr-6" />
      <h2 class="text-2xl font-bold">{{ author.username }}</h2>
    </section>

    <!-- 作家的作品 -->
    <section>
      <h2 class="text-2xl font-bold mb-6">我的作品</h2>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <div
          v-for="work in authorWorks"
          :key="work.id"
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

<script>
export default {
  data() {
    return {
      // 作者信息示例数据
      author: {
        avatar: 'http://gips3.baidu.com/it/u=3886271102,3123389489&fm=3028&app=3028&f=JPEG&fmt=auto?w=1280&h=960', // 示例头像
        username: '作家名', // 示例用户名
      },
      // 作家的作品列表
      authorWorks: [
        { id: 1, title: '作品一' },
        { id: 2, title: '作品二' },
        { id: 3, title: '作品三' },
      ],
    };
  },
  methods: {
    addWork() {
      this.$router.push(`/author/addWork`);
    },
    manageChapters(work) {
      this.$router.push(`/author/manageChapters/${work.id}`);
    },
  },
};
</script>