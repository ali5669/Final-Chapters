package org.ali5669.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.ali5669.domain.dto.Result;
import org.ali5669.domain.po.Chapter;
import org.ali5669.mapper.ChapterMapper;
import org.ali5669.service.IChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements IChapterService {
    private final ChapterMapper chapterMapper;
    @Override
    public Result getNewOrder(Integer novelId) {

        Integer maxOrder = chapterMapper.selectMaxOrder(novelId);
        if(maxOrder !=  null){
            return Result.ok(Integer.toString(maxOrder+1));
        }
        return Result.fail("getNewOrder failed");
    }

    @Override
    public Result addChapter(Chapter chapter) {
        if(save(chapter)){
            Integer chapterId = chapter.getChapterId();
            return Result.ok(Integer.toString(chapterId));
        }
        return Result.fail("create chapter failed");
    }

    @Override
    public Result getChaptersByNovelId(Integer novelId) {
        LambdaQueryWrapper<Chapter> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Chapter::getNovelId, novelId)
                .orderByAsc(Chapter::getOrder);  // 这里 order 是实体类字段名

        List<Chapter> chapters = chapterMapper.selectList(queryWrapper);
        return Result.ok(chapters);

    }
}
