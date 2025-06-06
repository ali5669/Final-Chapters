package org.ali5669.contentservice.controller;

import lombok.RequiredArgsConstructor;
import org.ali5669.contentservice.entity.Chapter;
import org.ali5669.contentservice.repository.ChapterRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/content/chapters")
@RequiredArgsConstructor
public class ChapterQueryController {
    private final ChapterRepository chapterRepository;

    // 测试获取小说的所有章节
    @GetMapping("/novel/{novelId}")
    public Result getNovelChapters(@PathVariable Long novelId) {
        List<Chapter> chapters = chapterRepository.findByNovelIdOrderByOrderAsc(novelId);
        return Result.ok(chapters, (long) chapters.size());
    }

    // 测试获取小说最新章节
    @GetMapping("/novel/{novelId}/latest")
    public Result getLatestChapter(@PathVariable Long novelId) {
        Chapter chapter = chapterRepository.findFirstByNovelIdOrderByOrderDesc(novelId);
        return chapter != null ? Result.ok(chapter) : Result.fail("No chapters found");
    }

    // 测试获取指定章节
    @GetMapping("/novel/{novelId}/order/{order}")
    public Result getChapterByOrder(
            @PathVariable Long novelId,
            @PathVariable Integer order) {
        Chapter chapter = chapterRepository.findByNovelIdAndOrder(novelId, order);
        return chapter != null ? Result.ok(chapter) : Result.fail("Chapter not found");
    }

    // 测试章节统计
    @GetMapping("/novel/{novelId}/count")
    public Result getChapterCount(@PathVariable Long novelId) {
        long count = chapterRepository.countByNovelId(novelId);
        return Result.ok(count);
    }

    // 测试章节标题搜索
    @GetMapping("/novel/{novelId}/search")
    public Result searchChaptersByTitle(
            @PathVariable Long novelId,
            @RequestParam String keyword) {
        List<Chapter> chapters = chapterRepository.findByNovelIdAndTitleContaining(novelId, keyword);
        return Result.ok(chapters, (long) chapters.size());
    }

    // 测试分页查询
    @GetMapping("/novel/{novelId}/page")
    public Result getChaptersByPage(
            @PathVariable Long novelId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        int offset = (page - 1) * size;
        List<Chapter> chapters = chapterRepository.findChaptersByPage(novelId, size, offset);
        long total = chapterRepository.countByNovelId(novelId);
        return Result.ok(chapters, total);
    }

    // 测试获取单个章节
    @GetMapping("/{chapterId}")
    public Result getChapterById(@PathVariable Long chapterId) {
        return chapterRepository.findById(chapterId)
                .map(Result::ok)
                .orElse(Result.fail("Chapter not found"));
    }
} 