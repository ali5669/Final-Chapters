package org.ali5669.controller;

import lombok.RequiredArgsConstructor;
import org.ali5669.domain.dto.NovelCreateDTO;
import org.ali5669.domain.dto.Result;
import org.ali5669.domain.po.Novel;
import org.ali5669.service.INovelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/novel")
@RequiredArgsConstructor
public class NovelController {
    private final INovelService novelService;

    @PostMapping("/addNovel")
    public Result addNovel(@RequestBody NovelCreateDTO createDTO) {
        Integer authorIdInt = Integer.parseInt(createDTO.getAuthorId());
        String title = createDTO.getTitle();
        String summary = createDTO.getSummary();
        String category = createDTO.getCategory();
        String tags = createDTO.getTags();

        Novel novel = new Novel();
        novel.setAuthorId(authorIdInt);
        novel.setTitle(title);
        novel.setSummary(summary);
        novel.setCategory(category);
        novel.setTags(tags);

        return novelService.addNovel(novel);
    }
}