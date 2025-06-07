package org.ali5669.novelservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.ali5669.novelservice.domain.dto.Result;
import org.ali5669.novelservice.domain.po.Chapter;

public interface IChapterService extends IService<Chapter> {
    public Result getNewOrder(Integer novelId);
    public Result addChapter(Chapter chapter);
    public Result getChaptersByNovelId(Integer novelId);
}
