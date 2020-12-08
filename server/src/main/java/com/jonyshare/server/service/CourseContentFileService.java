package com.jonyshare.server.service;

import com.jonyshare.server.domain.CourseContentFile;
import com.jonyshare.server.domain.CourseContentFileExample;
import com.jonyshare.server.dto.CourseContentFileDto;
import com.jonyshare.server.mapper.CourseContentFileMapper;
import com.jonyshare.server.util.CopyUtil;
import com.jonyshare.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseContentFileService {

    @Resource
    private CourseContentFileMapper courseContentFileMapper;

    /**
     * 列表查询
     */
    public List<CourseContentFileDto> list(String courseId) {
        CourseContentFileExample example = new CourseContentFileExample();
        CourseContentFileExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        List<CourseContentFile> fileList = courseContentFileMapper.selectByExample(example);
        return CopyUtil.copyList(fileList, CourseContentFileDto.class);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(CourseContentFileDto courseContentFileDto) {
        CourseContentFile courseContentFile = CopyUtil.copy(courseContentFileDto, CourseContentFile.class);
        if (StringUtils.isEmpty(courseContentFileDto.getId())) {
            this.insert(courseContentFile);
        } else {
            this.update(courseContentFile);
        }
    }

    /**
     * 新增
     */
    private void insert(CourseContentFile courseContentFile) {
        courseContentFile.setId(UuidUtil.getShortUuid());
        courseContentFileMapper.insert(courseContentFile);
    }

    /**
     * 更新
     */
    private void update(CourseContentFile courseContentFile) {
        courseContentFileMapper.updateByPrimaryKey(courseContentFile);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        courseContentFileMapper.deleteByPrimaryKey(id);
    }
}
