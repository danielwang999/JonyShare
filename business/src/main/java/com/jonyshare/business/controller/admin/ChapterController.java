package com.jonyshare.business.controller.admin;

import com.jonyshare.server.domain.Chapter;
import com.jonyshare.server.dto.ChapterDto;
import com.jonyshare.server.service.ChapterService;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public List<ChapterDto> list() {
        return chapterService.list();
    }
}
