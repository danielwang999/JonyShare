package com.jonyshare.file.controller.admin;


import com.jonyshare.server.dto.FileDto;
import com.jonyshare.server.dto.ResponseDto;
import com.jonyshare.server.enums.FileUseEnum;
import com.jonyshare.server.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * @author WangQiang
 * @date 2020/10/7-11:53
 */
@RestController
public class UploadController {
    public static final String BUSINESS_NAME = "文件上传";

    public static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    @Value("${file.domain}")
    private String FILE_DOMAIN;

    @Value("${file.path}")
    private String FILE_PATH;

    @Resource
    private FileService fileService;

    @RequestMapping("/upload")
    public ResponseDto upload(@RequestParam MultipartFile shard,
                              String use,
                              String name,
                              String suffix,
                              Integer size,
                              Integer shardIndex,
                              Integer shardSize,
                              Integer shardTotal,
                              String key) throws IOException {

        LOG.info("上传文件开始");
        // 获得文件使用类型枚举，若分类文件夹不存在，为其创建
        FileUseEnum fileUseEnum = FileUseEnum.getByCode(use);
        String dir = fileUseEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dir);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }

        String path = dir + File.separator + key + "." + suffix;
        String fullPath = FILE_PATH + path;
        File dest = new File(fullPath);
        shard.transferTo(dest);
        LOG.info(dest.getAbsolutePath());

        LOG.info("保存文件记录开始");
        FileDto fileDto = new FileDto();
        fileDto.setPath(path);
        fileDto.setName(name);
        fileDto.setSize(size);
        fileDto.setSuffix(suffix);
        fileDto.setUse(use);
        fileDto.setShardIndex(shardIndex);
        fileDto.setShardSize(shardSize);
        fileDto.setShardTotal(shardTotal);
        fileDto.setKey(key); // 前端利用md5信息摘要生成的文件的唯一标识
        fileService.save(fileDto);

        ResponseDto responseDto = new ResponseDto();
        fileDto.setPath(FILE_DOMAIN + path);
        responseDto.setContent(fileDto);
        return responseDto;
    }
}
