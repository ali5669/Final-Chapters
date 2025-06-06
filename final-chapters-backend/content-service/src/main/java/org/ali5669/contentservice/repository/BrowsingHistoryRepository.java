package org.ali5669.contentservice.repository;

import org.ali5669.contentservice.entity.BrowsingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BrowsingHistoryRepository extends JpaRepository<BrowsingHistory, Long> {
    
    // Find user's reading history ordered by last read time
    List<BrowsingHistory> findByUserIdOrderByLastReadTimeDesc(Long userId);
    
    // Find specific history record
    Optional<BrowsingHistory> findByUserIdAndNovelId(Long userId, Long novelId);
    
    // Find user's recent reading history with limit
    @Query(value = "SELECT h FROM BrowsingHistory h WHERE h.userId = :userId ORDER BY h.lastReadTime DESC")
    List<BrowsingHistory> findRecentHistory(@Param("userId") Long userId);
} 