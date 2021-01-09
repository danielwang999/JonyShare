package com.jonyshare.business.controller.admin;

import com.github.pagehelper.Page;
import com.jonyshare.server.domain.RoleResource;
import com.jonyshare.server.dto.RoleResourceDto;
import com.jonyshare.server.dto.PageDto;
import com.jonyshare.server.dto.ResponseDto;
import com.jonyshare.server.service.RoleResourceService;
import com.jonyshare.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/admin/roleResource")
public class RoleResourceController {

    public static final String BUSINESS_NAME = "角色资源关联";

    @Resource
    private RoleResourceService roleResourceService;

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        roleResourceService.list(pageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存，包括新增和修改
     * @param roleResourceDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody RoleResourceDto roleResourceDto) {
        // 保存校验
        ValidatorUtil.require(roleResourceDto.getRoleId(), "角色");
        ValidatorUtil.require(roleResourceDto.getResourceId(), "资源");
        ResponseDto responseDto = new ResponseDto();
        roleResourceService.save(roleResourceDto);
        responseDto.setContent(roleResourceDto);
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
        roleResourceService.delete(id);
        return responseDto;
    }

}
