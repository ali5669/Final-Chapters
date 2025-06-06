package org.ali5669.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.ali5669.domain.dto.Result;
import org.ali5669.domain.po.Chapter;

public interface IChapterService extends IService<Chapter> {
    public Result getNewOrder(Integer novelId);
    public Result addChapter(Chapter chapter);
    public Result getChaptersByNovelId(Integer novelId);
}
