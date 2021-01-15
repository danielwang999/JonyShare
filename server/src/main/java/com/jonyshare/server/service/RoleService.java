package com.jonyshare.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jonyshare.server.domain.Role;
import com.jonyshare.server.domain.RoleExample;
import com.jonyshare.server.domain.RoleResource;
import com.jonyshare.server.domain.RoleResourceExample;
import com.jonyshare.server.dto.PageDto;
import com.jonyshare.server.dto.RoleDto;
import com.jonyshare.server.mapper.RoleMapper;
import com.jonyshare.server.mapper.RoleResourceMapper;
import com.jonyshare.server.util.CopyUtil;
import com.jonyshare.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleResourceMapper roleResourceMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RoleExample roleExample = new RoleExample();
        List<Role> roleList = roleMapper.selectByExample(roleExample);
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleDto> roleDtoList = CopyUtil.copyList(roleList, RoleDto.class);
        pageDto.setList(roleDtoList);
    }

    public List<Role> listAll() {
        return roleMapper.selectByExample(new RoleExample());
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(RoleDto roleDto) {
        Role role = CopyUtil.copy(roleDto, Role.class);
        if (StringUtils.isEmpty(roleDto.getId())) {
            this.insert(role);
        } else {
            this.update(role);
        }
    }

    /**
     * 新增
     */
    private void insert(Role role) {
        role.setId(UuidUtil.getShortUuid());
        roleMapper.insert(role);
    }

    /**
     * 更新
     */
    private void update(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    /**
     * 保存角色的资源
     * @param roleDto
     */
    @Transactional
    public void saveResources(RoleDto roleDto) {
        String roleId = roleDto.getId();
        List<String> resourceIds = roleDto.getResourceIds();
        // 清空当前角色的所有资源
        RoleResourceExample rre = new RoleResourceExample();
        rre.createCriteria().andRoleIdEqualTo(roleId);
        roleResourceMapper.deleteByExample(rre);

        for (String resourceId : resourceIds) {
            RoleResource rr = new RoleResource();
            rr.setId(UuidUtil.getShortUuid());
            rr.setRoleId(roleId);
            rr.setResourceId(resourceId);
            roleResourceMapper.insert(rr);
        }
    }

    /**
     * 根据roleId，查找相应角色所拥有的resourceIds
     * @param roleId
     * @return
     */
    public List<String> getRoleResources(String roleId) {
        RoleResourceExample rre = new RoleResourceExample();
        rre.createCriteria().andRoleIdEqualTo(roleId);
        List<RoleResource> rrs = roleResourceMapper.selectByExample(rre);
        List<String> resourcesIds = new ArrayList<>();
        for (RoleResource rr : rrs) {
            resourcesIds.add(rr.getResourceId());
        }
        return resourcesIds;
    }
}
