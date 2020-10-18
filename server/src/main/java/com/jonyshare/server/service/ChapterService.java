package com.jonyshare.server.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jonyshare.server.domain.Chapter;
import com.jonyshare.server.domain.ChapterExample;
import com.jonyshare.server.dto.ChapterDto;
import com.jonyshare.server.dto.PageDto;
import com.jonyshare.server.mapper.ChapterMapper;
import com.jonyshare.server.util.UuidUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WangQiang
 * @date 2020/10/8-9:18
 */
@Service
public class ChapterService {

    @Resource
    ChapterMapper chapterMapper;

    public void list(PageDto pageDto) {
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.setOrderByClause("id desc");
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize()); //要查第几页和每页的条数
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
        pageDto.setTotal(pageInfo.getTotal());

        List<ChapterDto> chapterDtoList = new ArrayList<>();
        for (int i = 0, l = chapterList.size(); i < l; i++) {
            Chapter chapter = chapterList.get(i);
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter, chapterDto);
            chapterDtoList.add(chapterDto);
        }
        pageDto.setList(chapterDtoList);
    }

    public ChapterDto save(ChapterDto chapterDto) {
        chapterDto.setId(UuidUtil.getShortUuid());
        Chapter chapter = new Chapter();
        BeanUtils.copyProperties(chapterDto, chapter);
        chapterMapper.insert(chapter);
        return chapterDto;
    }
}
