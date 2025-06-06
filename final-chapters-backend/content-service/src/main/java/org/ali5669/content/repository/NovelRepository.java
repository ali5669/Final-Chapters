package org.ali5669.content.repository;

import org.ali5669.content.entity.Novel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface NovelRepository extends JpaRepository<Novel, Long> {
    // 根据标题模糊查询
    List<Novel> findByTitleContaining(String title);
    
    // 根据分类查询
    List<Novel> findByCategory(String category);
    
    // 根据标签模糊查询
    List<Novel> findByTagsContaining(String tag);
    
    // 根据作者ID查询
    List<Novel> findByAuthorId(Long authorId);
    
    // 组合查询
    @Query("SELECT n FROM Novel n WHERE " +
           "(:title IS NULL OR n.title LIKE %:title%) AND " +
           "(:category IS NULL OR n.category = :category) AND " +
           "(:tag IS NULL OR n.tags LIKE %:tag%)")
    List<Novel> searchNovels(
        @Param("title") String title,
        @Param("category") String category,
        @Param("tag") String tag
    );
} 