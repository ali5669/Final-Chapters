package org.ali5669.content.controller;

import lombok.RequiredArgsConstructor;
import org.ali5669.content.entity.BrowsingHistory;
import org.ali5669.content.repository.BrowsingHistoryRepository;
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
    public Result getUserHistory(@PathVariable Long userId) {
        List<BrowsingHistory> histories = historyRepository.findByUserIdOrderByLastReadTimeDesc(userId);
        return Result.ok(histories, (long) histories.size());
    }

    // 添加或更新阅读历史
    @PostMapping("/add")
    public Result addHistory(
            @RequestParam Long userId,
            @RequestParam Long novelId) {
        try {
            BrowsingHistory history = historyRepository.findByUserIdAndNovelId(userId, novelId)
                    .orElse(new BrowsingHistory());
            
            history.setUserId(userId);
            history.setNovelId(novelId);
            history.setLastReadTime(LocalDateTime.now());
            
            BrowsingHistory saved = historyRepository.save(history);
            return Result.ok(saved);
        } catch (Exception e) {
            return Result.fail("Failed to save browsing history: " + e.getMessage());
        }
    }

    // 获取用户最近的阅读历史
    @GetMapping("/user/{userId}/recent")
    public Result getRecentHistory(@PathVariable Long userId) {
        List<BrowsingHistory> histories = historyRepository.findRecentHistory(userId);
        return Result.ok(histories, (long) histories.size());
    }

    // 删除特定的阅读历史
    @DeleteMapping("/{historyId}")
    public Result deleteHistory(@PathVariable Long historyId) {
        try {
            historyRepository.deleteById(historyId);
            return Result.ok();
        } catch (Exception e) {
            return Result.fail("Failed to delete history: " + e.getMessage());
        }
    }
} 