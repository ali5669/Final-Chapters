package org.ali5669.content.controller;

import lombok.RequiredArgsConstructor;
import org.ali5669.content.entity.Novel;
import org.ali5669.content.repository.NovelRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/novels")
@RequiredArgsConstructor
public class NovelQueryController {
    private final NovelRepository novelRepository;

    // 测试所有小说查询
    @GetMapping("/all")
    public ResponseEntity<List<Novel>> getAllNovels() {
        return ResponseEntity.ok(novelRepository.findAll());
    }

    // 测试标题搜索
    @GetMapping("/search/title")
    public ResponseEntity<List<Novel>> searchByTitle(@RequestParam String keyword) {
        return ResponseEntity.ok(novelRepository.findByTitleContaining(keyword));
    }

    // 测试分类查询
    @GetMapping("/search/category")
    public ResponseEntity<List<Novel>> searchByCategory(@RequestParam String category) {
        return ResponseEntity.ok(novelRepository.findByCategory(category));
    }

    // 测试标签搜索
    @GetMapping("/search/tag")
    public ResponseEntity<List<Novel>> searchByTag(@RequestParam String tag) {
        return ResponseEntity.ok(novelRepository.findByTagsContaining(tag));
    }

    // 测试作者查询
    @GetMapping("/search/author/{authorId}")
    public ResponseEntity<List<Novel>> searchByAuthor(@PathVariable Long authorId) {
        return ResponseEntity.ok(novelRepository.findByAuthorId(authorId));
    }

    // 测试组合查询
    @GetMapping("/search")
    public ResponseEntity<List<Novel>> searchNovels(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag) {
        return ResponseEntity.ok(novelRepository.searchNovels(title, category, tag));
    }

    // 测试单本小说查询
    @GetMapping("/{novelId}")
    public ResponseEntity<Novel> getNovelById(@PathVariable Long novelId) {
        return novelRepository.findById(novelId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
} 