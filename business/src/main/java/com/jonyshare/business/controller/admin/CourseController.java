package com.jonyshare.business.controller.admin;

import com.github.pagehelper.Page;
import com.jonyshare.server.domain.Course;
import com.jonyshare.server.dto.CourseDto;
import com.jonyshare.server.dto.PageDto;
import com.jonyshare.server.dto.ResponseDto;
import com.jonyshare.server.service.CourseService;
import com.jonyshare.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/admin/course")
public class CourseController {

    public static final String BUSINESS_NAME = "课程";

    @Resource
    private CourseService courseService;

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        courseService.list(pageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存，包括新增和修改
     * @param courseDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CourseDto courseDto) {
        // 保存校验
        ValidatorUtil.require(courseDto.getName(), "名称");
        ValidatorUtil.length(courseDto.getName(), "名称", 1, 50);
        ValidatorUtil.length(courseDto.getSummary(), "概述", 1, 2000);
        ValidatorUtil.length(courseDto.getImage(), "封面", 1, 100);
        ResponseDto responseDto = new ResponseDto();
        courseService.save(courseDto);
        responseDto.setContent(courseDto);
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
        courseService.delete(id);
        return responseDto;
    }

}
