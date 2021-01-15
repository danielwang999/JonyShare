package com.jonyshare.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jonyshare.server.domain.RoleUser;
import com.jonyshare.server.domain.RoleUserExample;
import com.jonyshare.server.dto.PageDto;
import com.jonyshare.server.dto.RoleUserDto;
import com.jonyshare.server.mapper.RoleUserMapper;
import com.jonyshare.server.util.CopyUtil;
import com.jonyshare.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleUserService {

    @Resource
    private RoleUserMapper roleUserMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RoleUserExample roleUserExample = new RoleUserExample();
        List<RoleUser> roleUserList = roleUserMapper.selectByExample(roleUserExample);
        PageInfo<RoleUser> pageInfo = new PageInfo<>(roleUserList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleUserDto> roleUserDtoList = CopyUtil.copyList(roleUserList, RoleUserDto.class);
        pageDto.setList(roleUserDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(RoleUserDto roleUserDto) {
        RoleUser roleUser = CopyUtil.copy(roleUserDto, RoleUser.class);
        if (StringUtils.isEmpty(roleUserDto.getId())) {
            this.insert(roleUser);
        } else {
            this.update(roleUser);
        }
    }

    /**
     * 新增
     */
    private void insert(RoleUser roleUser) {
        roleUser.setId(UuidUtil.getShortUuid());
        roleUserMapper.insert(roleUser);
    }

    /**
     * 更新
     */
    private void update(RoleUser roleUser) {
        roleUserMapper.updateByPrimaryKey(roleUser);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        roleUserMapper.deleteByPrimaryKey(id);
    }


    /**
     * 根据userId找出其下所拥有的角色，返回roleIds字符串数组
     * @param userId
     * @return
     */
    public List<String> findRoles(String userId) {
        RoleUserExample rue = new RoleUserExample();
        rue.createCriteria().andUserIdEqualTo(userId);
        List<RoleUser> roleUsers = roleUserMapper.selectByExample(rue);
        List<String> userRoleIds = new ArrayList<>();
        for (RoleUser roleUser : roleUsers) {
            userRoleIds.add(roleUser.getRoleId());
        }
        return userRoleIds;
    }

    @Transactional
    public void saveUserRoles(String userId, List<String> roleIds) {
        RoleUserExample rue = new RoleUserExample();
        rue.createCriteria().andUserIdEqualTo(userId);
        roleUserMapper.deleteByExample(rue);

        for (String roleId : roleIds) {
            RoleUser roleUser = new RoleUser();
            roleUser.setId(UuidUtil.getShortUuid());
            roleUser.setUserId(userId);
            roleUser.setRoleId(roleId);
            roleUserMapper.insert(roleUser);
        }
    }
}
