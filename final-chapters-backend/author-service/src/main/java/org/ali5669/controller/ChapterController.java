package org.ali5669.controller;

import lombok.RequiredArgsConstructor;
import org.ali5669.domain.dto.ChapterCreateDTO;
import org.ali5669.domain.dto.GetChaptersDTO;
import org.ali5669.domain.dto.GetNewOrderDTO;
import org.ali5669.domain.dto.Result;
import org.ali5669.domain.po.Chapter;
import org.ali5669.service.IChapterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/chapter")
@RequiredArgsConstructor
public class ChapterController {
    private final IChapterService chapterService;

    @PostMapping("/getNewOrder")
    public Result getNewOrder(@RequestBody GetNewOrderDTO getNewOrderDTO) {
        Integer novelIdInt = Integer.parseInt(getNewOrderDTO.getNovelId());
        return chapterService.getNewOrder(novelIdInt);
    }
    @PostMapping("/addChapter")
    public Result addNovel(@RequestBody ChapterCreateDTO createDTO) {
        Integer novelId = Integer.parseInt(createDTO.getNovelId());
        String title = createDTO.getTitle();
        String content = createDTO.getContent();
        Integer order = Integer.parseInt(createDTO.getOrder());

        Chapter chapter = new Chapter();
        chapter.setNovelId(novelId);
        chapter.setTitle(title);
        chapter.setContent(content);
        chapter.setOrder(order);

        return chapterService.addChapter(chapter);
    }
    @PostMapping("/getChapters")
    public Result getChapters(@RequestBody GetChaptersDTO getChaptersDTO) {
        Integer novelId = Integer.parseInt(getChaptersDTO.getNovelId());
        return chapterService.getChaptersByNovelId(novelId);
    }
}