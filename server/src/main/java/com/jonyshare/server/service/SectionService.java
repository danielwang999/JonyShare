package com.jonyshare.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jonyshare.server.domain.Section;
import com.jonyshare.server.domain.SectionExample;
import com.jonyshare.server.dto.SectionDto;
import com.jonyshare.server.dto.PageDto;
import com.jonyshare.server.mapper.SectionMapper;
import com.jonyshare.server.util.CopyUtil;
import com.jonyshare.server.util.UuidUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SectionService {

    @Resource
    SectionMapper sectionMapper;

    /**
     * 列表查询服务
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        SectionExample sectionExample = new SectionExample();
        sectionExample.setOrderByClause("id desc");
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize()); //要查第几页和每页的条数
        List<Section> sectionList = sectionMapper.selectByExample(sectionExample);
        PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
        pageDto.setTotal(pageInfo.getTotal());

        List<SectionDto> sectionDtoList = new ArrayList<>();
        for (int i = 0, l = sectionList.size(); i < l; i++) {
            Section section = sectionList.get(i);
            SectionDto sectionDto = new SectionDto();
            BeanUtils.copyProperties(section, sectionDto);
            sectionDtoList.add(sectionDto);
        }
        pageDto.setList(sectionDtoList);
    }

    /**
     * 保存服务，包括新增和修改
     * @param sectionDto
     */
    public void save(SectionDto sectionDto) {
        Section section = CopyUtil.copy(sectionDto, Section.class);
        if (StringUtils.isEmpty(sectionDto.getId())) {
            this.insert(section);
        } else {
            this.update(section);
        }
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id) {
        sectionMapper.deleteByPrimaryKey(id);
    }

    private void insert(Section section) {
        section.setId(UuidUtil.getShortUuid());
        sectionMapper.insert(section);
    }

    private void update(Section section) {
        sectionMapper.updateByPrimaryKey(section);
    }
}
