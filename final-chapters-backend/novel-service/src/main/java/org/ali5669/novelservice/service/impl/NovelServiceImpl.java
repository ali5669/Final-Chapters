package org.ali5669.novelservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.ali5669.novelservice.domain.dto.Result;
import org.ali5669.novelservice.domain.po.Novel;
import org.ali5669.novelservice.mapper.NovelMapper;
import org.ali5669.novelservice.service.INovelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NovelServiceImpl extends ServiceImpl<NovelMapper, Novel> implements INovelService {
    private final NovelMapper novelMapper;
    @Override
    public Result addNovel(Novel novel) {
        if(save(novel)){
            Integer novelId = novel.getNovelId();
            return Result.ok(Integer.toString(novelId));
        }
        return Result.fail("create novel failed");
    }

    @Override
    public Result getNovelsByAuthorId(Integer authorId) {
        LambdaQueryWrapper<Novel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Novel::getAuthorId, authorId);

        List<Novel> novels = novelMapper.selectList(queryWrapper);
        return Result.ok(novels);
    }

    @Override
    public Result getNovelsByNovelId(Integer novelId) {
        LambdaQueryWrapper<Novel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Novel::getNovelId, novelId);
        Novel novel = novelMapper.selectOne(wrapper);
        return Result.ok(novel);
    }
}
