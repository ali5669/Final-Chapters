package org.ali5669.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.ali5669.domain.dto.Result;
import org.ali5669.domain.po.Novel;
public interface INovelService extends IService<Novel> {
    public Result addNovel(Novel novel);
    public Result getNovelsByAuthorId(Integer authorId);
    public Result getNovelsByNovelId(Integer novelId);
}
