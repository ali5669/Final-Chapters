package org.ali5669.content.controller;

import lombok.RequiredArgsConstructor;
import org.ali5669.content.entity.Chapter;
import org.ali5669.content.repository.ChapterRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/chapters")
@RequiredArgsConstructor
public class ChapterQueryController {
    private final ChapterRepository chapterRepository;

    // 测试获取小说的所有章节
    @GetMapping("/novel/{novelId}")
    public ResponseEntity<List<Chapter>> getNovelChapters(@PathVariable Long novelId) {
        return ResponseEntity.ok(chapterRepository.findByNovelIdOrderByOrderAsc(novelId));
    }

    // 测试获取小说最新章节
    @GetMapping("/novel/{novelId}/latest")
    public ResponseEntity<Chapter> getLatestChapter(@PathVariable Long novelId) {
        Chapter chapter = chapterRepository.findFirstByNovelIdOrderByOrderDesc(novelId);
        return chapter != null ? ResponseEntity.ok(chapter) : ResponseEntity.notFound().build();
    }

    // 测试获取指定章节
    @GetMapping("/novel/{novelId}/order/{order}")
    public ResponseEntity<Chapter> getChapterByOrder(
            @PathVariable Long novelId,
            @PathVariable Integer order) {
        Chapter chapter = chapterRepository.findByNovelIdAndOrder(novelId, order);
        return chapter != null ? ResponseEntity.ok(chapter) : ResponseEntity.notFound().build();
    }

    // 测试章节统计
    @GetMapping("/novel/{novelId}/count")
    public ResponseEntity<Map<String, Long>> getChapterCount(@PathVariable Long novelId) {
        long count = chapterRepository.countByNovelId(novelId);
        return ResponseEntity.ok(Map.of("chapterCount", count));
    }

    // 测试章节标题搜索
    @GetMapping("/novel/{novelId}/search")
    public ResponseEntity<List<Chapter>> searchChaptersByTitle(
            @PathVariable Long novelId,
            @RequestParam String keyword) {
        return ResponseEntity.ok(chapterRepository.findByNovelIdAndTitleContaining(novelId, keyword));
    }

    // 测试分页查询
    @GetMapping("/novel/{novelId}/page")
    public ResponseEntity<List<Chapter>> getChaptersByPage(
            @PathVariable Long novelId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        int offset = (page - 1) * size;
        return ResponseEntity.ok(chapterRepository.findChaptersByPage(novelId, size, offset));
    }

    // 测试获取单个章节
    @GetMapping("/{chapterId}")
    public ResponseEntity<Chapter> getChapterById(@PathVariable Long chapterId) {
        return chapterRepository.findById(chapterId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
} 