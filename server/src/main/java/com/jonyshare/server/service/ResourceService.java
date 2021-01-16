package com.jonyshare.server.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jonyshare.server.domain.Resource;
import com.jonyshare.server.domain.ResourceExample;
import com.jonyshare.server.dto.PageDto;
import com.jonyshare.server.dto.ResourceDto;
import com.jonyshare.server.mapper.ResourceMapper;
import com.jonyshare.server.util.CopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceService {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceService.class);


    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        ResourceExample resourceExample = new ResourceExample();
        List<Resource> resourceList = resourceMapper.selectByExample(resourceExample);
        PageInfo<Resource> pageInfo = new PageInfo<>(resourceList);
        pageDto.setTotal(pageInfo.getTotal());
        List<ResourceDto> resourceDtoList = CopyUtil.copyList(resourceList, ResourceDto.class);
        pageDto.setList(resourceDtoList);
    }

    /**
     * 按约定把资源列表转成树
     * 要求：ID正序排列
     * @return
     */
    public List<ResourceDto> loadTree() {
        ResourceExample resourceExample = new ResourceExample();
        resourceExample.setOrderByClause("id asc"); // 按id升序查找
        List<Resource> resourceList = resourceMapper.selectByExample(resourceExample);
        List<ResourceDto> resourceDtoList = CopyUtil.copyList(resourceList, ResourceDto.class);
        for (int i = resourceDtoList.size() - 1; i >= 0; i--) {
            // 从后往前遍历
            ResourceDto child = resourceDtoList.get(i);
            if (StringUtils.isEmpty(child.getParent())) {
                continue;
            }
            for (int j = i - 1; j >= 0; j--) {
                ResourceDto parent = resourceDtoList.get(j);
                if (child.getParent().equals(parent.getId())) {
                    // 找到父亲资源
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(0, resourceDtoList.get(i));
                    resourceDtoList.remove(i);
                    break;
                }
            }
        }
        return resourceDtoList;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(ResourceDto resourceDto) {
        Resource resource = CopyUtil.copy(resourceDto, Resource.class);
        if (StringUtils.isEmpty(resourceDto.getId())) {
            this.insert(resource);
        } else {
            this.update(resource);
        }
    }

    /**
     * 新增
     */
    private void insert(Resource resource) {
        resourceMapper.insert(resource);
    }

    /**
     * 更新
     */
    private void update(Resource resource) {
        resourceMapper.updateByPrimaryKey(resource);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        resourceMapper.deleteByPrimaryKey(id);
    }

    /**
     * 保存资源树
     * @param json
     */
    public void saveJson(String json) {
        List<ResourceDto> jsonList = JSON.parseArray(json, ResourceDto.class);
        List<ResourceDto> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jsonList)) {
            for (ResourceDto d: jsonList) {
                d.setParent("");
                add(list, d);
            }
        }
        LOG.info("共{}条", list.size());

        resourceMapper.deleteByExample(null);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
            this.insert(CopyUtil.copy(list.get(i), Resource.class));
        }
    }

    /**
     * 递归，将树型结构的节点全部取出来，放到list
     * @param list
     * @param dto
     */
    public void add(List<ResourceDto> list, ResourceDto dto) {
        list.add(dto);
        if (!CollectionUtils.isEmpty(dto.getChildren())) {
            for (ResourceDto d: dto.getChildren()) {
                d.setParent(dto.getId());
                add(list, d);
            }
        }
    }
}
