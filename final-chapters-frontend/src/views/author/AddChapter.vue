<template>
  <div class="add-chapter">
    <h2>添加新章节 - {{ novel.title }}</h2>
    <form @submit.prevent="submitChapter">
      <div class="form-group">
        <label for="chapterTitle">章节标题:</label>
        <input id="chapterTitle" v-model="chapter.title" type="text" required />
      </div>
      <div class="form-group">
        <label for="chapterContent">章节内容:</label>
        <textarea id="chapterContent" v-model="chapter.content" rows="10" required></textarea>
      </div>
      <div class="form-actions">
        <button type="submit">提交</button>
        <button type="button" @click="cancel">取消</button>
      </div>
    </form>
  </div>
</template>
<script setup>
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { $chapter } from '@/composables/useApi/useApiChapter'
import axios from 'axios';

const route = useRoute();
const router = useRouter();

// 获取路由参数
const novelId = route.params.novelId;

// 章节数据模型
const chapter = ref({
  title: '',
  content: ''
});

// 小说信息
const novel = ref({});

// 提交新章节
const submitChapter = async () => {
// 检查章节标题和内容是否为空
    if (!chapter.value.title.trim() || !chapter.value.content.trim()) {
      alert('章节标题和内容不能为空！');
      return;
    }
    try {
      // 获取章节顺序
      const { data: newOrder } = await $chapter.getNewOrder({data:{novelId:novelId}});
      console.log('章节顺序:', newOrder);
      // 构造完整的小说对象
      const newChapeter = {
        "novelId": novelId,
        "title": chapter.value.title,
        "content": chapter.value.content,
        "order": newOrder
      };
      // 输出结果，或替换为 API 调用
      console.log('提交的章节数据:', newChapeter);
      const { data: res } = await $chapter.addChapter({data: newChapeter})
      console.log('章节id:', res);
      // 跳转回章节管理页面
      router.push({ name: 'manageChapters', params: { novelId } });
    } catch (error) {
      console.error('提交章节失败:', error);
    }
};

// 取消并返回管理页面
const cancel = () => {
  router.push({ name: 'manageChapters', params: { novelId } });
};

// 获取小说信息（可选）
const fetchNovelInfo = async () => {
  try {
    const response = await axios.get(`/api/novels/${novelId}`);
    novel.value = response.data;
  } catch (error) {
    console.error('获取小说信息失败:', error);
  }
};

// 页面加载时获取小说信息
import { onMounted } from 'vue';
onMounted(async () => {
  // await fetchNovelInfo();
});
</script><style scoped>
.add-chapter {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input[type="text"],
textarea {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.form-actions {
  margin-top: 20px;
}

button {
  margin-right: 10px;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button[type="submit"] {
  background-color: #42b983;
  color: white;
}

button[type="button"] {
  background-color: #ccc;
  color: #333;
}
</style>