package org.ali5669.novelservice.controller;

import lombok.RequiredArgsConstructor;
import org.ali5669.novelservice.domain.dto.*;
import org.ali5669.novelservice.domain.po.Novel;
import org.ali5669.novelservice.service.INovelService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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
    @PostMapping(value="/addNovelWithCover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result addNovelWithCover(@ModelAttribute NovelCreateWithCoverDTO createDTO) {
        Integer authorIdInt = Integer.parseInt(createDTO.getAuthorId());
        String title = createDTO.getTitle();
        String summary = createDTO.getSummary();
        String category = createDTO.getCategory();
        String tags = createDTO.getTags();
        MultipartFile cover = createDTO.getCover();
        String coverUrl = novelService.upCoverToOSS(cover);
        if (coverUrl == null) {
            return Result.fail("上传封面失败");
        }

        Novel novel = new Novel();
        novel.setAuthorId(authorIdInt);
        novel.setTitle(title);
        novel.setSummary(summary);
        novel.setCategory(category);
        novel.setTags(tags);
        novel.setCoverImage(coverUrl);

        return novelService.addNovel(novel);
    }
    @PostMapping("/getNovels")
    public Result getNovels(@RequestBody AuthorIdDTO authorIdDTO) {
        Integer authorIdInt = Integer.parseInt(authorIdDTO.getAuthorId());

        return novelService.getNovelsByAuthorId(authorIdInt);
    }

    @PostMapping("/getNovelById")
    public Result getNovels(@RequestBody NovelIdDTO novelIdDTO) {
        Integer novelIdInt = Integer.parseInt(novelIdDTO.getNovelId());

        return novelService.getNovelsByNovelId(novelIdInt);
    }
}