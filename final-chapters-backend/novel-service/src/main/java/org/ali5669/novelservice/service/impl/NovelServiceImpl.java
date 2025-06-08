package org.ali5669.novelservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.ali5669.novelservice.domain.dto.Result;
import org.ali5669.novelservice.domain.po.Novel;
import org.ali5669.novelservice.mapper.NovelMapper;
import org.ali5669.novelservice.service.INovelService;
import org.ali5669.novelservice.utils.AliOssUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NovelServiceImpl extends ServiceImpl<NovelMapper, Novel> implements INovelService {
    private final NovelMapper novelMapper;
    private final AliOssUtil aliOssUtil;
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

    @Override
    public String upCoverToOSS(MultipartFile cover) {
        String filePath = null;
        try{
            System.out.println("开始上传文件");
            System.out.println(aliOssUtil);
            String originalProfilePicture = cover.getOriginalFilename();
            String type=originalProfilePicture.substring(originalProfilePicture.lastIndexOf(".")+1);
            String newFilename = UUID.randomUUID().toString().replace("-","")+"."+type;
            filePath=aliOssUtil.upload(cover.getBytes(),newFilename);
            System.out.println("文件上传成功");
        }catch (Exception e){
            System.out.println("文件上传失败");
        }
        return filePath;
    }
}
