package com.jonyshare.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jonyshare.server.domain.Course;
import com.jonyshare.server.domain.CourseContent;
import com.jonyshare.server.domain.CourseExample;
import com.jonyshare.server.dto.CourseContentDto;
import com.jonyshare.server.dto.CourseDto;
import com.jonyshare.server.dto.PageDto;
import com.jonyshare.server.mapper.CourseContentMapper;
import com.jonyshare.server.mapper.CourseMapper;
import com.jonyshare.server.mapper.my.MyCourseMapper;
import com.jonyshare.server.util.CopyUtil;
import com.jonyshare.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CourseService {
    private static final Logger LOG = LoggerFactory.getLogger(CourseService.class);

    @Resource
    private CourseMapper courseMapper;
    @Resource
    private MyCourseMapper myCourseMapper;
    @Resource
    private CourseContentMapper courseContentMapper;
    @Resource
    private CourseCategoryService courseCategoryService;


    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseExample courseExample = new CourseExample();
        courseExample.setOrderByClause("sort asc");
        List<Course> courseList = courseMapper.selectByExample(courseExample);
        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseDto> courseDtoList = CopyUtil.copyList(courseList, CourseDto.class);
        pageDto.setList(courseDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Transactional
    public void save(CourseDto courseDto) {
        Course course = CopyUtil.copy(courseDto, Course.class);
        if (StringUtils.isEmpty(courseDto.getId())) {
            this.insert(course);
        } else {
            this.update(course);
        }

        // 批量保存分类
        courseCategoryService.saveBatch(courseDto.getId(), courseDto.getCategorys());
    }

    /**
     * 新增
     */
    private void insert(Course course) {
        Date now = new Date();
        course.setCreatedAt(now);
        course.setUpdatedAt(now);
        course.setId(UuidUtil.getShortUuid());
        courseMapper.insert(course);
    }

    /**
     * 更新
     */
    private void update(Course course) {
        course.setUpdatedAt(new Date());
        courseMapper.updateByPrimaryKey(course);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        courseMapper.deleteByPrimaryKey(id);
    }

    public void updateTime(String courseId) {
        LOG.info("更新课程时长：{}", courseId);
        myCourseMapper.updateTime(courseId);
    }

    /**
     * 查找课程介绍内容
     * @param courseId
     * @return
     */
    public CourseContentDto findContent(String courseId) {
        CourseContent courseContent = courseContentMapper.selectByPrimaryKey(courseId);
        if (courseContent == null) {
            return null;
        }
        return CopyUtil.copy(courseContent, CourseContentDto.class);
    }

    /**
     * 保存课程内容，包含新增和修改
     * @param courseContentDto
     * @return
     */
    public int saveCourseContent (CourseContentDto courseContentDto) {
        CourseContent courseContent = CopyUtil.copy(courseContentDto, CourseContent.class);
        int i = courseContentMapper.updateByPrimaryKeyWithBLOBs(courseContent);
        // 如果发现更新到的行数为0，在选择插入
        if (i == 0) {
            i = courseContentMapper.insert(courseContent);
        }
        return i;
    }
}
