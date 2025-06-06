package org.ali5669.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.ali5669.domain.dto.Result;
import org.ali5669.domain.po.Novel;
import org.ali5669.mapper.NovelMapper;
import org.ali5669.service.INovelService;
import org.springframework.stereotype.Service;

@Service
public class NovelServiceImpl extends ServiceImpl<NovelMapper, Novel> implements INovelService {
    @Override
    public Result addNovel(Novel novel) {
        if(save(novel)){
            return Result.ok();
        }
        return Result.fail("create comment failed");
    }
}
