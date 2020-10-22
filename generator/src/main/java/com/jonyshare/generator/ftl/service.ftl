package com.jonyshare.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jonyshare.server.domain.${Domain};
import com.jonyshare.server.domain.${Domain}Example;
import com.jonyshare.server.dto.${Domain}Dto;
import com.jonyshare.server.dto.PageDto;
import com.jonyshare.server.mapper.${Domain}Mapper;
import com.jonyshare.server.util.CopyUtil;
import com.jonyshare.server.util.UuidUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ${Domain}Service {

    @Resource
    ${Domain}Mapper ${domain}Mapper;

    /**
     * 列表查询服务
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        ${domain}Example.setOrderByClause("id desc");
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize()); //要查第几页和每页的条数
        List<${Domain}> ${domain}List = ${domain}Mapper.selectByExample(${domain}Example);
        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}List);
        pageDto.setTotal(pageInfo.getTotal());

        List<${Domain}Dto> ${domain}DtoList = new ArrayList<>();
        for (int i = 0, l = ${domain}List.size(); i < l; i++) {
            ${Domain} ${domain} = ${domain}List.get(i);
            ${Domain}Dto ${domain}Dto = new ${Domain}Dto();
            BeanUtils.copyProperties(${domain}, ${domain}Dto);
            ${domain}DtoList.add(${domain}Dto);
        }
        pageDto.setList(${domain}DtoList);
    }

    /**
     * 保存服务，包括新增和修改
     * @param ${domain}Dto
     */
    public void save(${Domain}Dto ${domain}Dto) {
        ${Domain} ${domain} = CopyUtil.copy(${domain}Dto, ${Domain}.class);
        if (StringUtils.isEmpty(${domain}Dto.getId())) {
            this.insert(${domain});
        } else {
            this.update(${domain});
        }
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id) {
        ${domain}Mapper.deleteByPrimaryKey(id);
    }

    private void insert(${Domain} ${domain}) {
        ${domain}.setId(UuidUtil.getShortUuid());
        ${domain}Mapper.insert(${domain});
    }

    private void update(${Domain} ${domain}) {
        ${domain}Mapper.updateByPrimaryKey(${domain});
    }
}
