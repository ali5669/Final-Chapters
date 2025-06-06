package org.ali5669.content.controller;

import lombok.RequiredArgsConstructor;
import org.ali5669.content.entity.Novel;
import org.ali5669.content.repository.NovelRepository;
import org.ali5669.content.repository.ChapterRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final NovelRepository novelRepository;
    private final ChapterRepository chapterRepository;

    @GetMapping("/hello")
    public Map<String, Object> hello() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello! The service is running!");
        response.put("timestamp", LocalDateTime.now());
        response.put("status", "OK");
        return response;
    }

    @GetMapping("/test/db")
    public Map<String, Object> testDatabase() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 测试小说表
            List<Novel> novels = novelRepository.findAll();
            response.put("novelCount", novels.size());
            if (!novels.isEmpty()) {
                response.put("firstNovel", novels.get(0));
                // 测试章节表
                long chapterCount = chapterRepository.countByNovelId(novels.get(0).getId());
                response.put("firstNovelChapterCount", chapterCount);
            }
            
            response.put("status", "SUCCESS");
            response.put("message", "Database connection test successful");
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Database connection test failed");
            response.put("error", e.getMessage());
        }
        
        return response;
    }
} 