package com.jonyshare.system.controller.admin;

import com.jonyshare.server.domain.Role;
import com.jonyshare.server.dto.PageDto;
import com.jonyshare.server.dto.ResponseDto;
import com.jonyshare.server.dto.RoleDto;
import com.jonyshare.server.service.RoleResourceService;
import com.jonyshare.server.service.RoleService;
import com.jonyshare.server.util.CopyUtil;
import com.jonyshare.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/admin/role")
public class RoleController {

    public static final String BUSINESS_NAME = "角色";

    @Resource
    private RoleService roleService;

    @Resource
    private RoleResourceService roleResourceService;

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        roleService.list(pageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 加载所有的角色
     * @return
     */
    @RequestMapping("/listAll")
    public ResponseDto listAll() {
        List<Role> roles = roleService.listAll();
        List<RoleDto> roleDtos = CopyUtil.copyList(roles, RoleDto.class);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(roleDtos);
        return responseDto;
    }

    /**
     * 保存，包括新增和修改
     * @param roleDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody RoleDto roleDto) {
        // 保存校验
        ValidatorUtil.require(roleDto.getName(), "角色");
        ValidatorUtil.length(roleDto.getName(), "角色", 1, 50);
        ValidatorUtil.require(roleDto.getDesc(), "描述");
        ValidatorUtil.length(roleDto.getDesc(), "描述", 1, 100);
        ResponseDto responseDto = new ResponseDto();
        roleService.save(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        roleService.delete(id);
        return responseDto;
    }

    @RequestMapping("/save-resource")
    public ResponseDto saveResource(@RequestBody RoleDto roleDto) {
        roleService.saveResources(roleDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(roleDto);
        return responseDto;
    }

    @RequestMapping("/list-resource/{roleId}")
    public ResponseDto listRoleResource(@PathVariable String roleId) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(roleService.getRoleResources(roleId));
        return responseDto;
    }

}
