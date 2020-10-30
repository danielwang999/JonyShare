package com.jonyshare.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jonyshare.server.domain.Section;
import com.jonyshare.server.domain.SectionExample;
import com.jonyshare.server.dto.SectionDto;
import com.jonyshare.server.dto.SectionPageDto;
import com.jonyshare.server.enums.SectionChargeEnum;
import com.jonyshare.server.mapper.SectionMapper;
import com.jonyshare.server.util.CopyUtil;
import com.jonyshare.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SectionService {

    @Resource
    private SectionMapper sectionMapper;

    @Resource
    private CourseService courseService;

    /**
     * 列表查询
     */
    public void list(SectionPageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        SectionExample sectionExample = new SectionExample();

        SectionExample.Criteria criteria = sectionExample.createCriteria();
        if (!StringUtils.isEmpty(pageDto.getCourseId())) {
            criteria.andCourseIdEqualTo(pageDto.getCourseId());
        }
        if (!StringUtils.isEmpty(pageDto.getChapterId())) {
            criteria.andChapterIdEqualTo(pageDto.getChapterId());
        }

        sectionExample.setOrderByClause("sort asc");
        List<Section> sectionList = sectionMapper.selectByExample(sectionExample);
        PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
        pageDto.setTotal(pageInfo.getTotal());
        List<SectionDto> sectionDtoList = CopyUtil.copyList(sectionList, SectionDto.class);
        pageDto.setList(sectionDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     * 默认情况下，@Transactional事务遇到Exception异常不会回滚，遇到RuntimeException异常回回滚
     * 可以使用Transactional(rollbackFor = Exception.class) 来设置遇到Exception回滚。
     *
     * 同一个类内部方法互相调用，methodA调用methodB，methodB事务不起作用，Spring的事务处理是利用AOP
     * 生成动态代理类，内部方法调用时不经过代理类，所以事务不生效。
     */
    @Transactional()
    public void save(SectionDto sectionDto) {
        Section section = CopyUtil.copy(sectionDto, Section.class);
        if (StringUtils.isEmpty(sectionDto.getId())) {
            this.insert(section);
        } else {
            this.update(section);
        }
        courseService.updateTime(section.getCourseId());
    }

    /**
     * 新增
     */
    private void insert(Section section) {
        Date now = new Date();
        section.setCreatedAt(now);
        section.setUpdatedAt(now);
        section.setId(UuidUtil.getShortUuid());
        section.setCharge(SectionChargeEnum.FREE.getCode());
        sectionMapper.insert(section);
    }

    /**
     * 更新
     */
    private void update(Section section) {
        section.setUpdatedAt(new Date());
        sectionMapper.updateByPrimaryKey(section);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        sectionMapper.deleteByPrimaryKey(id);
    }
}
