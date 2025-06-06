package org.ali5669.contentservice.controller;

import lombok.RequiredArgsConstructor;
import org.ali5669.contentservice.entity.BrowsingHistory;
import org.ali5669.contentservice.repository.BrowsingHistoryRepository;
import org.ali5669.contentservice.utils.UserContext;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/content/history")
@RequiredArgsConstructor
public class BrowsingHistoryController {
    private final BrowsingHistoryRepository historyRepository;

    // 获取用户的阅读历史
    @GetMapping()
    public Result getUserHistory() {
        Long userId = UserContext.getUserId();
        List<BrowsingHistory> histories = historyRepository.findByUserIdOrderByLastReadTimeDesc(userId);
        return Result.ok(histories, (long) histories.size());
    }

    // 添加或更新阅读历史
    @PostMapping("/add")
    public Result addHistory(
            @RequestParam Long novelId) {
        try {
            Long userId = UserContext.getUserId();
            System.out.println("userId:" + userId);
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
    @GetMapping("/recent")
    public Result getRecentHistory() {
        Long userId = UserContext.getUserId();
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