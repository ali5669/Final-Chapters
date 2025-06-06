<template>
  <div class="chapter-management">
    <!-- Novel Info Section -->
    <div class="novel-info">
      <img :src="novel.cover" alt="Cover" class="cover-image" />
      <div class="info-details">
        <h2>{{ novel.title }}</h2>
        <p><strong>简介:</strong> {{ novel.summary }}</p>
        <p><strong>类别:</strong> {{ novel.genre }}</p>
        <!-- <p><strong>标签:</strong> {{ novel.tags.join(', ') }}</p> -->
      </div>
    </div>

    <!-- Chapter List Section -->
    <div class="chapter-list">
      <h3>章节列表</h3>
      <ul>
        <li v-for="chapter in chapters" :key="chapter.id" class="chapter-item">
          <span>{{ chapter.order }}</span>
          <span>{{ chapter.title }}</span>
          <!-- <button @click="deleteChapter(chapter.id)">删除</button> -->
        </li>
      </ul>
    </div>

    <!-- Add Chapter Button -->
    <div class="add-chapter">
      <button @click="goToAddChapter">添加章节</button>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { $chapter } from '@/composables/useApi/useApiChapter'
import axios from 'axios';

const route = useRoute();
const router = useRouter();

// 存储小说信息和章节列表
const novel = ref({});
const chapters = ref([]);

// 获取小说ID
const novelId = route.params.novelId;

// 查询小说信息和章节数据
const fetchNovelData = async () => {
//   try {
//     const response = await axios.get(`/api/novels/${novelId}`);
//     novel.value = response.data;
//   } catch (error) {
//     console.error('获取小说信息失败:', error);
//   }
    novel.value = {
      id: novelId,
      title: '小说标题',
      summary: '小说简介',
      genre: '小说类别',
      tags: ['标签1', '标签2'],
      cover: 'http://gips3.baidu.com/it/u=3886271102,3123389489&fm=3028&app=3028&f=JPEG&fmt=auto?w=1280&h=960',
    };
};

const fetchChapters = async () => {
//   try {
//     const response = await axios.get(`/api/chapters?novelId=${novelId}`);
//     chapters.value = response.data;
//   } catch (error) {
//     console.error('获取章节列表失败:', error);
//   }
  const { data: chapterList } = await $chapter.getChapters({data:{novelId:novelId}});
  chapters.value = chapterList;
  // chapters.value = [
  //   { id: 1, title: '章节1', content: '章节内容1' },
  //   { id: 2, title: '章节2', content: '章节内容2' },
  // ];
};

// 删除章节
const deleteChapter = async (chapterId) => {
  if (confirm('确定要删除该章节吗？')) {
    try {
      await axios.delete(`/api/chapters/${chapterId}`);
      // 刷新章节列表
      await fetchChapters();
    } catch (error) {
      console.error('删除章节失败:', error);
    }
  }
};

// 跳转到添加章节页面
const goToAddChapter = () => {
  router.push({ path: `/author/addChapter/${novelId}` });
};

// 页面加载时获取数据
onMounted(async () => {
  await Promise.all([fetchNovelData(), fetchChapters()]);
});
</script>

<style scoped>
.chapter-management {
  padding: 20px;
}

.novel-info {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.cover-image {
  width: 150px;
  height: 200px;
  object-fit: cover;
  margin-right: 20px;
}

.info-details {
  flex: 1;
}

.chapter-list ul {
  list-style-type: none;
  padding: 0;
}

.chapter-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #eee;
}

.add-chapter {
  margin-top: 20px;
}
</style>