package com.jonyshare.server.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jonyshare.server.domain.Chapter;
import com.jonyshare.server.domain.ChapterExample;
import com.jonyshare.server.dto.ChapterDto;
import com.jonyshare.server.dto.ChapterPageDto;
import com.jonyshare.server.mapper.ChapterMapper;
import com.jonyshare.server.util.CopyUtil;
import com.jonyshare.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WangQiang
 * @date 2020/10/8-9:18
 */
@Service
public class ChapterService {

    @Resource
    ChapterMapper chapterMapper;

    /**
     * 大章列表查询服务
     * @param pageDto
     */
    public void list(ChapterPageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize()); //要查第几页和每页的条数
        ChapterExample chapterExample = new ChapterExample();

        ChapterExample.Criteria criteria = chapterExample.createCriteria();
        if (!StringUtils.isEmpty(pageDto.getCourseId())) {
            criteria.andCourseIdEqualTo(pageDto.getCourseId());
        }

        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
        pageDto.setTotal(pageInfo.getTotal());

        List<ChapterDto> chapterDtoList = CopyUtil.copyList(chapterList, ChapterDto.class);
        pageDto.setList(chapterDtoList);
    }

    /**
     * 大章保存服务，包括新增和修改
     * @param chapterDto
     */
    public void save(ChapterDto chapterDto) {
        Chapter chapter = CopyUtil.copy(chapterDto, Chapter.class);
        if (StringUtils.isEmpty(chapterDto.getId())) {
            this.insert(chapter);
        } else {
            this.update(chapter);
        }
    }

    /**
     * 大章删除
     * @param id
     */
    public void delete(String id) {
        chapterMapper.deleteByPrimaryKey(id);
    }

    private void insert(Chapter chapter) {
        chapter.setId(UuidUtil.getShortUuid());
        chapterMapper.insert(chapter);
    }

    private void update(Chapter chapter) {
        chapterMapper.updateByPrimaryKey(chapter);
    }

    /**
     * 根据courseId，查出此课程下所有的大章
     * @param id
     * @return
     */
    public List<ChapterDto> listByCourse(String id) {
        ChapterExample ce = new ChapterExample();
        ce.createCriteria().andCourseIdEqualTo(id);
        List<Chapter> chapters = chapterMapper.selectByExample(ce);
        return CopyUtil.copyList(chapters, ChapterDto.class);
    }
}
