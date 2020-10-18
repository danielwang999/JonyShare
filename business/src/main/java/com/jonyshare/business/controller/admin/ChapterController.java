package com.jonyshare.business.controller.admin;

import com.github.pagehelper.Page;
import com.jonyshare.server.domain.Chapter;
import com.jonyshare.server.dto.ChapterDto;
import com.jonyshare.server.dto.PageDto;
import com.jonyshare.server.dto.ResponseDto;
import com.jonyshare.server.service.ChapterService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WangQiang
 * @date 2020/10/13-16:49
 */
@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    @RequestMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        chapterService.list(pageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @RequestMapping("/save")
    public ResponseDto save(@RequestBody ChapterDto ChapterDto) {
        ResponseDto responseDto = new ResponseDto();
        chapterService.save(ChapterDto);
        responseDto.setContent(ChapterDto);
        return responseDto;
    }

}
