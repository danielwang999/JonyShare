package com.jonyshare.business.controller.admin;

import com.jonyshare.server.dto.*;
import com.jonyshare.server.service.CourseCategoryService;
import com.jonyshare.server.service.CourseService;
import com.jonyshare.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/admin/course")
public class CourseController {

    public static final String BUSINESS_NAME = "课程";

    @Resource
    private CourseService courseService;
    @Resource
    private CourseCategoryService courseCategoryService;

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

    /**
     * 查找课程下所有分类
     * @param courseId
     * @return
     */
    @PostMapping("/list-category/{courseId}")
    public ResponseDto listCategory(@PathVariable String courseId) {
        ResponseDto responseDto = new ResponseDto();
        List<CourseCategoryDto> dtoList = courseCategoryService.listByCourse(courseId);
        responseDto.setContent(dtoList);
        return responseDto;
    }

    /**
     * 查找课程简介内容
     * @param courseId
     * @return
     */
    @GetMapping("/find-content/{courseId}")
    public ResponseDto findContent(@PathVariable String courseId) {
        ResponseDto responseDto = new ResponseDto();
        CourseContentDto courseContentDto = courseService.findContent(courseId);
        responseDto.setContent(courseContentDto);
        return responseDto;
    }

    /**
     * 保存课程简介内容，包括新增和更新
     * @param courseContentDto
     * @return
     */
    @PostMapping("/save-content")
    public ResponseDto saveContent(CourseContentDto courseContentDto) {
        ResponseDto responseDto = new ResponseDto();
        courseService.saveCourseContent(courseContentDto);
        return responseDto;
    }

    /**
     * 课程排序顺序的更改
     * @param sortDto
     * @return
     */
    @RequestMapping("/sort")
    public ResponseDto sort(@RequestBody SortDto sortDto) {
        ResponseDto responseDto = new ResponseDto();
        courseService.sort(sortDto);
        return responseDto;
    }

}
