package com.jonyshare.server.service;


import com.jonyshare.server.domain.Chapter;
import com.jonyshare.server.domain.ChapterExample;
import com.jonyshare.server.dto.ChapterDto;
import com.jonyshare.server.mapper.ChapterMapper;
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

    public List<ChapterDto> list() {
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.setOrderByClause("id desc");
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        List<ChapterDto> chapterDtoList = new ArrayList<>();
        for (int i = 0, l = chapterList.size(); i < l; i++) {
            Chapter chapter = chapterList.get(i);
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter, chapterDto);
            chapterDtoList.add(chapterDto);
        }
        return chapterDtoList;
    }
}
