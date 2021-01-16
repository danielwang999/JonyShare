package com.jonyshare.system.controller.admin;

import com.jonyshare.server.dto.PageDto;
import com.jonyshare.server.dto.ResponseDto;
import com.jonyshare.server.service.ResourceService;
import com.jonyshare.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/resource")
public class ResourceController {

    public static final String BUSINESS_NAME = "资源";

    @javax.annotation.Resource
    private ResourceService resourceService;

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        resourceService.list(pageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 树形返回所有资源
     * @return
     */
    @RequestMapping("/load-tree")
    public ResponseDto listTree() {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(resourceService.loadTree());
        return responseDto;
    }

    /**
     * 保存，包括新增和修改
     * @param jsonStr
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody String jsonStr) {
        // 保存校验
        ValidatorUtil.require(jsonStr, "资源");

        ResponseDto responseDto = new ResponseDto();
        resourceService.saveJson(jsonStr);
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
        resourceService.delete(id);
        return responseDto;
    }

}
