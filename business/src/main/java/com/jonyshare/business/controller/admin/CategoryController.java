package com.jonyshare.business.controller.admin;

import com.jonyshare.server.dto.CategoryDto;
import com.jonyshare.server.dto.PageDto;
import com.jonyshare.server.dto.ResponseDto;
import com.jonyshare.server.service.CategoryService;
import com.jonyshare.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    public static final String BUSINESS_NAME = "分类";

    @Resource
    private CategoryService categoryService;

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        categoryService.list(pageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存，包括新增和修改
     * @param categoryDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CategoryDto categoryDto) {
        // 保存校验
        ValidatorUtil.require(categoryDto.getParent(), "父id");
        ValidatorUtil.require(categoryDto.getName(), "名称");
        ValidatorUtil.length(categoryDto.getName(), "名称", 1, 50);
        ResponseDto responseDto = new ResponseDto();
        categoryService.save(categoryDto);
        responseDto.setContent(categoryDto);
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
        categoryService.delete(id);
        return responseDto;
    }

}
