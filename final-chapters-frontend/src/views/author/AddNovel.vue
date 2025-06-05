<template>
  <div class="max-w-4xl mx-auto space-y-8 py-12">
    <h1 class="text-3xl font-bold text-center mb-6">创建新小说</h1>

    <form @submit.prevent="submitNovel" class="bg-white p-6 rounded-lg shadow-md space-y-6">
      <!-- 小说标题 -->
      <div>
        <label for="title" class="block text-sm font-medium text-gray-700 mb-1">小说标题</label>
        <input
          id="title"
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
          id="summary"
          v-model="novel.osummary"
          rows="4"
          required
          class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500"
        ></textarea>
      </div>

      <!-- 封面图片路径 -->
      <div>
        <label for="cover" class="block text-sm font-medium text-gray-700 mb-1">封面图片路径</label>
        <input
          id="cover"
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
          id="category"
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
          id="tags"
          v-model="novel.otags"
          type="text"
          placeholder="例如：奇幻,冒险,热血"
          required
          class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500"
        />
      </div>

      <!-- 提交按钮 -->
      <div class="flex justify-end">
        <button
          type="submit"
          class="bg-indigo-600 text-white px-6 py-2 rounded-lg font-semibold hover:bg-indigo-700 transition"
        >
          创建小说
        </button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 固定 author_id
      author_id: '12345',
      // 初始化小说对象
      novel: {
        otitle: '',
        osummary: '',
        ocover_image: '',
        ocategory: '',
        otags: '',
        ocreated_at: null,
        updated_at: null,
      },
    };
  },
  methods: {
    submitNovel() {
        const { otitle, osummary, ocover_image, ocategory, otags } = this.novel;

        // 检查所有字段是否填写
        if (!otitle || !osummary || !ocover_image || !ocategory || !otags) {
            alert('请填写所有必填字段');
            return;
        }

        const now = new Date().toISOString();
        this.novel.ocreated_at = now;
        this.novel.updated_at = now;

        // 构造完整的小说对象
        const newNovel = {
            author_id: this.author_id,
            ...this.novel,
        };

        // 输出结果，或替换为 API 调用
        console.log('提交的小说数据:', newNovel);

        // 示例：可替换为 API 请求
        // await axios.post('/api/novels', newNovel);
    }
  },
};
</script>