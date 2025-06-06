package org.ali5669.contentservice.controller;

import lombok.RequiredArgsConstructor;
import org.ali5669.contentservice.entity.Novel;
import org.ali5669.contentservice.repository.NovelRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/content/novels")
@RequiredArgsConstructor
public class NovelQueryController {
    private final NovelRepository novelRepository;

    // 测试所有小说查询
    @GetMapping("/all")
    public Result getAllNovels() {
        List<Novel> novels = novelRepository.findAll();
        return Result.ok(novels, (long) novels.size());
    }

    // 测试标题搜索
    @GetMapping("/search/title")
    public Result searchByTitle(@RequestParam String keyword) {
        List<Novel> novels = novelRepository.findByTitleContaining(keyword);
        return Result.ok(novels, (long) novels.size());
    }

    // 测试分类查询
    @GetMapping("/search/category")
    public Result searchByCategory(@RequestParam String category) {
        List<Novel> novels = novelRepository.findByCategory(category);
        return Result.ok(novels, (long) novels.size());
    }

    // 测试标签搜索
    @GetMapping("/search/tag")
    public Result searchByTag(@RequestParam String tag) {
        List<Novel> novels = novelRepository.findByTagsContaining(tag);
        return Result.ok(novels, (long) novels.size());
    }

    // 测试作者查询
    @GetMapping("/search/author/{authorId}")
    public Result searchByAuthor(@PathVariable Long authorId) {
        List<Novel> novels = novelRepository.findByAuthorId(authorId);
        return Result.ok(novels, (long) novels.size());
    }

    // 测试组合查询
    @GetMapping("/search")
    public Result searchNovels(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag) {
        List<Novel> novels = novelRepository.searchNovels(title, category, tag);
        return Result.ok(novels, (long) novels.size());
    }

    // 测试单本小说查询
    @GetMapping("/{novelId}")
    public Result getNovelById(@PathVariable Long novelId) {
        return novelRepository.findById(novelId)
                .map(Result::ok)
                .orElse(Result.fail("Novel not found"));
    }
} 