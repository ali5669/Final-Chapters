package org.ali5669.content.controller;

import lombok.RequiredArgsConstructor;
import org.ali5669.content.entity.BrowsingHistory;
import org.ali5669.content.repository.BrowsingHistoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class BrowsingHistoryController {
    private final BrowsingHistoryRepository historyRepository;

    // 获取用户的阅读历史
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BrowsingHistory>> getUserHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(historyRepository.findByUserIdOrderByLastReadTimeDesc(userId));
    }

    // 添加或更新阅读历史
    @PostMapping("/add")
    public ResponseEntity<BrowsingHistory> addHistory(
            @RequestParam Long userId,
            @RequestParam Long novelId) {
        
        BrowsingHistory history = historyRepository.findByUserIdAndNovelId(userId, novelId)
                .orElse(new BrowsingHistory());
        
        history.setUserId(userId);
        history.setNovelId(novelId);
        history.setLastReadTime(LocalDateTime.now());
        
        return ResponseEntity.ok(historyRepository.save(history));
    }

    // 获取用户最近的阅读历史
    @GetMapping("/user/{userId}/recent")
    public ResponseEntity<List<BrowsingHistory>> getRecentHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(historyRepository.findRecentHistory(userId));
    }

    // 删除特定的阅读历史
    @DeleteMapping("/{historyId}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Long historyId) {
        historyRepository.deleteById(historyId);
        return ResponseEntity.ok().build();
    }
} 