package com.jonyshare.business.controller.admin;

import com.jonyshare.server.dto.PageDto;
import com.jonyshare.server.dto.ResponseDto;
import com.jonyshare.server.dto.TeacherDto;
import com.jonyshare.server.service.TeacherService;
import com.jonyshare.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/admin/teacher")
public class TeacherController {

    public static final String BUSINESS_NAME = "讲师";

    @Resource
    private TeacherService teacherService;

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        teacherService.list(pageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存，包括新增和修改
     * @param teacherDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody TeacherDto teacherDto) {
        // 保存校验
        ValidatorUtil.require(teacherDto.getName(), "姓名");
        ValidatorUtil.length(teacherDto.getName(), "姓名", 1, 50);
        ValidatorUtil.length(teacherDto.getNickname(), "昵称", 1, 50);
        ValidatorUtil.length(teacherDto.getImage(), "头像", 1, 100);
        ValidatorUtil.length(teacherDto.getPosition(), "职位", 1, 50);
        ValidatorUtil.length(teacherDto.getMotto(), "座右铭", 1, 50);
        ValidatorUtil.length(teacherDto.getIntro(), "简介", 1, 500);
        ResponseDto responseDto = new ResponseDto();
        teacherService.save(teacherDto);
        responseDto.setContent(teacherDto);
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
        teacherService.delete(id);
        return responseDto;
    }

}
