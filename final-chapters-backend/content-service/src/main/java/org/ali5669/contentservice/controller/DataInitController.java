package org.ali5669.contentservice.controller;

import lombok.RequiredArgsConstructor;
import org.ali5669.contentservice.entity.Novel;
import org.ali5669.contentservice.entity.Chapter;
import org.ali5669.contentservice.repository.NovelRepository;
import org.ali5669.contentservice.repository.ChapterRepository;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/init")
@RequiredArgsConstructor
public class DataInitController {
    private final NovelRepository novelRepository;
    private final ChapterRepository chapterRepository;

    @PostMapping("/novel")
    public Novel insertNovel(@RequestBody Novel novel) {
        if (novel.getCreatedAt() == null) {
            novel.setCreatedAt(LocalDateTime.now());
        }
        if (novel.getUpdatedAt() == null) {
            novel.setUpdatedAt(LocalDateTime.now());
        }
        return novelRepository.save(novel);
    }

    @PostMapping("/chapter")
    public Chapter insertChapter(@RequestBody Chapter chapter) {
        if (chapter.getCreatedAt() == null) {
            chapter.setCreatedAt(LocalDateTime.now());
        }
        if (chapter.getUpdatedAt() == null) {
            chapter.setUpdatedAt(LocalDateTime.now());
        }
        return chapterRepository.save(chapter);
    }
} 