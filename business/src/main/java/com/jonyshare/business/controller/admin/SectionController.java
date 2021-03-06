package com.jonyshare.business.controller.admin;

import com.jonyshare.server.dto.ResponseDto;
import com.jonyshare.server.dto.SectionDto;
import com.jonyshare.server.dto.SectionPageDto;
import com.jonyshare.server.service.SectionService;
import com.jonyshare.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/admin/section")
public class
SectionController {

    public static final String BUSINESS_NAME = "小节";

    @Resource
    private SectionService sectionService;

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody SectionPageDto pageDto) {
        ValidatorUtil.require(pageDto.getCourseId(), "课程ID");
        ValidatorUtil.require(pageDto.getChapterId(), "大章ID");
        sectionService.list(pageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存，包括新增和修改
     * @param sectionDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody SectionDto sectionDto) {
        // 保存校验
        ValidatorUtil.require(sectionDto.getTitle(), "标题");
        ValidatorUtil.length(sectionDto.getTitle(), "标题", 1, 50);
        ValidatorUtil.length(sectionDto.getVideo(), "视频", 1, 200);
        ResponseDto responseDto = new ResponseDto();
        sectionService.save(sectionDto);
        responseDto.setContent(sectionDto);
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
        sectionService.delete(id);
        return responseDto;
    }

}
