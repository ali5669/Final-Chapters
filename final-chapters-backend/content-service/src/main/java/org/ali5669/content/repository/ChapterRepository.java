package org.ali5669.content.repository;

import org.ali5669.content.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    // 根据小说ID查询所有章节
    List<Chapter> findByNovelIdOrderByOrderAsc(Long novelId);
    
    // 查询小说的最新章节
    Chapter findFirstByNovelIdOrderByOrderDesc(Long novelId);
    
    // 查询指定章节号的章节
    Chapter findByNovelIdAndOrder(Long novelId, Integer order);
    
    // 统计小说章节数
    long countByNovelId(Long novelId);
    
    // 根据标题模糊查询
    List<Chapter> findByNovelIdAndTitleContaining(Long novelId, String title);
    
    // 分页查询章节
    @Query(value = "SELECT * FROM chapter WHERE novel_id = :novelId " +
           "ORDER BY `order` LIMIT :pageSize OFFSET :offset", 
           nativeQuery = true)
    List<Chapter> findChaptersByPage(
        @Param("novelId") Long novelId,
        @Param("pageSize") int pageSize,
        @Param("offset") int offset
    );
} 